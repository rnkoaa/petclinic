package app

import (
	"encoding/json"
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

// SearchRequestFromJSON - parses bleve.SearchRequest from json input
func SearchRequestFromJSON(requestBody []byte) (*bleve.SearchRequest, error) {
	// parse the request
	var searchRequest bleve.SearchRequest
	err := json.Unmarshal(requestBody, &searchRequest)
	if err != nil {
		return nil, err
	}

	// validate the query
	if srqv, ok := searchRequest.Query.(query.ValidatableQuery); ok {
		if err := srqv.Validate(); err != nil {
			return nil, err
		}
		return &searchRequest, nil
	}
	return nil, nil
}

// CreateSearchRequest - creates a search request object
func CreateSearchRequest(docType, searchQuery, requestFields, resultFields string) *bleve.SearchRequest {
	var sr *bleve.SearchRequest
	searchFields := make([]string, 0, 0)
	if requestFields != "" {
		searchFields = strings.Split(requestFields, ",")
	}

	req := CreateDisjunctSearch(docType, searchQuery, searchFields)
	sr = bleve.NewSearchRequestOptions(req, 100, 0, false)

	if resultFields != "" {
		resultFieldSplit := strings.Split(resultFields, ",")
		sr.Fields = resultFieldSplit
	} else {
		sr.Fields = []string{"*"}
	}
	return sr
}

// CreateDisjunctSearch - creates a search which takens in a disjunction
func CreateDisjunctSearch(docType, q string, fields []string) *query.ConjunctionQuery {
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

// PerformSearch - does a search against the index
func (a *App) PerformSearch(searchRequest *bleve.SearchRequest) (*SearchResults, error) {
	searchResult, err := a.Index.SearchIndex.Search(searchRequest)
	if err != nil {
		return nil, err
	}

	hits := make([]SearchHit, 0, searchResult.Hits.Len())
	for _, hit := range searchResult.Hits {

		searchHit := SearchHit{
			ID:     hit.ID,
			Fields: hit.Fields,
		}
		hits = append(hits, searchHit)
	}

	searchResults := &SearchResults{
		Took:  searchResult.Took,
		Total: searchResult.Total,
		Hits:  hits,
	}
	return searchResults, nil
}
