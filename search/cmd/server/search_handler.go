package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"strings"
	"time"

	"github.com/blevesearch/bleve"
	"github.com/blevesearch/bleve/search/query"
)

// SearchResults - the object returned back to the request
type SearchResults struct {
	Type  string        `json:"type"`
	Hits  []SearchHit   `json:"hits"`
	Total uint64        `json:"total_hits"`
	Took  time.Duration `json:"took"`
	Err   string        `json:"err,omitempty"`
}

// SearchHit - records of search hit
type SearchHit struct {
	ID     string                 `json:"id"`
	Fields map[string]interface{} `json:"fields"`
}

func createDisjunctSearch(docType, q string, fields []string) *query.ConjunctionQuery {
	conjunct := bleve.NewConjunctionQuery()
	if docType != "" {
		docQuery := bleve.NewMatchQuery(docType)
		docQuery.SetField("type")
		conjunct.AddQuery(docQuery)
	}

	disjunct := bleve.NewDisjunctionQuery()
	if len(fields) > 0 {
		for _, field := range fields {
			m := bleve.NewMatchQuery(q)
			m.SetField(field)
			disjunct.AddQuery(m)
			p := bleve.NewPrefixQuery(q)
			p.SetField(field)
			disjunct.AddQuery(p)
		}
	} else {
		disjunct.AddQuery(bleve.NewMatchQuery(q))
		disjunct.AddQuery(bleve.NewPrefixQuery(q))
	}
	conjunct.AddQuery(disjunct)
	return conjunct
}

func (a *App) processSearch(w http.ResponseWriter, r *http.Request) {

	// read the request body
	requestBody, err := ioutil.ReadAll(r.Body)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error validating query: `+err.Error()+`"}`)
	}

	// parse the request
	var searchRequest bleve.SearchRequest
	err = json.Unmarshal(requestBody, &searchRequest)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error validating query: `+err.Error()+`"}`)
	}

	// validate the query
	if srqv, ok := searchRequest.Query.(query.ValidatableQuery); ok {
		err = srqv.Validate()
		if err != nil {
			respondWithJSON(w, 400, `{"message": "error validating query: `+err.Error()+`"}`)
		}
	}
	// execute the query
	searchResult, err := a.Index.SearchIndex.Search(&searchRequest)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error executing query: `+err.Error()+`"}`)
		return
	}

	hits := make([]SearchHit, 0, searchResult.Hits.Len())
	for _, hit := range searchResult.Hits {

		searchHit := SearchHit{
			ID:     hit.ID,
			Fields: hit.Fields,
		}
		hits = append(hits, searchHit)
	}

	searchResults := SearchResults{
		Took:  searchResult.Took,
		Total: searchResult.Total,
		Hits:  hits,
	}

	respondWithJSON(w, 200, searchResults)
}

func (a *App) searchForItems(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryVals := r.URL.Query()
	searchQuery := queryVals.Get("q")
	docType := queryVals.Get("type")
	fields := queryVals.Get("fields")
	resultFields := queryVals.Get("result_fields")

	var searchResult *bleve.SearchResult
	var err error

	// do not honor request if no query is passed.
	if searchQuery == "" {
		w.WriteHeader(404)
		_, _ = fmt.Fprintf(w, `{"message": "no response for empty search query"}`)
		return
	}

	var sr *bleve.SearchRequest
	searchFields := make([]string, 0, 0)
	if fields != "" {
		searchFields = strings.Split(fields, ",")
	}

	req := createDisjunctSearch(docType, searchQuery, searchFields)
	sr = bleve.NewSearchRequestOptions(req, 100, 0, false)

	if resultFields != "" {
		resultFieldSplit := strings.Split(resultFields, ",")
		sr.Fields = resultFieldSplit
	} else {
		sr.Fields = []string{"*"}
	}
	searchResult, err = a.Index.SearchIndex.Search(sr)

	if err != nil {
		searchResults := SearchResults{
			Err: err.Error(),
		}
		respondWithJSON(w, 400, searchResults)
	}

	hits := make([]SearchHit, 0, searchResult.Hits.Len())
	for _, hit := range searchResult.Hits {

		searchHit := SearchHit{
			ID:     hit.ID,
			Fields: hit.Fields,
		}
		hits = append(hits, searchHit)
	}

	searchResults := SearchResults{
		Took:  searchResult.Took,
		Total: searchResult.Total,
		Hits:  hits,
	}

	respondWithJSON(w, 200, searchResults)
}

// SearchByType performs a search of the index using the given query. Returns IDs of documents
// which satisfy all queries. Returns Doc IDs in sorted order, ascending.
func (a *App) SearchByType(docType, q string, searchFields []string) (*bleve.SearchResult, error) {
	str := ""
	if len(searchFields) > 0 {
		for _, field := range searchFields {
			str += fmt.Sprintf("%s:%s ", field, q)
		}
	}

	queryStr := fmt.Sprintf("+type:%s", docType)
	if str != "" {
		queryStr += " " + str // + "-" + q
	} else {
		queryStr += " " + q
	}
	fmt.Printf("Query String: %s\n", queryStr)
	// queryStr := fmt.Sprintf("+type:%s %s", docType, q)
	req := bleve.NewQueryStringQuery(queryStr)
	sr := bleve.NewSearchRequestOptions(req, 100, 0, false)
	sr.Fields = []string{"*"}
	return a.Index.SearchIndex.Search(sr)
}

// Search performs a search of the index using the given query. Returns IDs of documents
// which satisfy all queries. Returns Doc IDs in sorted order, ascending.
func (a *App) Search(q string) (*bleve.SearchResult, error) {
	prefixQuery := bleve.NewPrefixQuery(q)
	matchQuery := bleve.NewMatchQuery(q)
	query := bleve.NewDisjunctionQuery(prefixQuery, matchQuery)
	searchRequest := bleve.NewSearchRequest(query)
	searchRequest.Fields = []string{"*"}
	searchRequest.Size = a.MaxSearchHitSize
	return a.Index.SearchIndex.Search(searchRequest)
}

// SearchByIndex - search by index
func (a *App) SearchByIndex(indexName, q string) (*bleve.SearchResult, error) {
	index, err := bleve.Open(indexName)
	if err != nil {
		return nil, err
	}
	defer index.Close()

	prefixQuery := bleve.NewPrefixQuery(q)
	matchQuery := bleve.NewMatchQuery(q)
	query := bleve.NewDisjunctionQuery(prefixQuery, matchQuery)
	searchRequest := bleve.NewSearchRequest(query)
	searchRequest.Fields = []string{"*"}
	searchRequest.Size = a.MaxSearchHitSize
	return index.Search(searchRequest)
}
