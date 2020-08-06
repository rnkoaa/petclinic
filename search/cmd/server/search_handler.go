package main

import (
	"fmt"
	"io/ioutil"
	"net/http"

	"github.com/blevesearch/bleve"
	"github.com/rnkoaa/petclinic/cmd/internal/app"
)

func (s *Server) processSearch(w http.ResponseWriter, r *http.Request) {

	// read the request body
	requestBody, err := ioutil.ReadAll(r.Body)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error validating query: `+err.Error()+`"}`)
	}

	searchRequest, err := app.SearchRequestFromJSON(requestBody)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error validating query: `+err.Error()+`"}`)
		return
	}

	// execute the query
	searchResults, err := s.App.PerformSearch(searchRequest)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error executing query: `+err.Error()+`"}`)
		return
	}

	respondWithJSON(w, 200, searchResults)
}

func (s *Server) searchForItems(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	queryVals := r.URL.Query()
	searchQuery := queryVals.Get("q")
	docType := queryVals.Get("type")
	fields := queryVals.Get("fields")
	resultFields := queryVals.Get("result_fields")
	var err error

	// do not honor request if no query is passed.
	if searchQuery == "" {
		w.WriteHeader(404)
		_, _ = fmt.Fprintf(w, `{"message": "no response for empty search query"}`)
		return
	}

	sr := app.CreateSearchRequest(docType, searchQuery, fields, resultFields)
	// execute the query
	searchResults, err := s.App.PerformSearch(sr)
	if err != nil {
		respondWithJSON(w, 400, `{"message": "error executing query: `+err.Error()+`"}`)
		return
	}

	respondWithJSON(w, 200, searchResults)
}

// SearchByType performs a search of the index using the given query. Returns IDs of documents
// which satisfy all queries. Returns Doc IDs in sorted order, ascending.
func (s *Server) SearchByType(docType, q string, searchFields []string) (*app.SearchResults, error) {
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
	req := bleve.NewQueryStringQuery(queryStr)
	sr := bleve.NewSearchRequestOptions(req, 100, 0, false)
	sr.Fields = []string{"*"}
	return s.App.PerformSearch(sr)
}

// Search performs a search of the index using the given query. Returns IDs of documents
// which satisfy all queries. Returns Doc IDs in sorted order, ascending.
func (s *Server) Search(q string) (*bleve.SearchResult, error) {
	prefixQuery := bleve.NewPrefixQuery(q)
	matchQuery := bleve.NewMatchQuery(q)
	query := bleve.NewDisjunctionQuery(prefixQuery, matchQuery)
	searchRequest := bleve.NewSearchRequest(query)
	searchRequest.Fields = []string{"*"}
	searchRequest.Size = s.App.MaxSearchHitSize
	return s.App.Index.SearchIndex.Search(searchRequest)
}

// SearchByIndex - search by index
func (s *Server) SearchByIndex(indexName, q string) (*bleve.SearchResult, error) {
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
	searchRequest.Size = s.App.MaxSearchHitSize
	return index.Search(searchRequest)
}
