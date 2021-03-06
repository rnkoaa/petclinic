package search

import (
	"fmt"
	"log"
	"os"

	"github.com/blevesearch/bleve"
	"github.com/blevesearch/bleve/analysis/analyzer/custom"
	"github.com/blevesearch/bleve/analysis/analyzer/keyword"
	"github.com/blevesearch/bleve/analysis/lang/en"
	"github.com/blevesearch/bleve/analysis/token/edgengram"
	"github.com/blevesearch/bleve/analysis/token/lowercase"
	"github.com/blevesearch/bleve/analysis/tokenizer/unicode"
	"github.com/blevesearch/bleve/mapping"
)

const (
	textFieldAnalyzer = "en"
)

// Index -
type Index struct {
	Name        string
	BatchSize   int
	SearchIndex bleve.Index
}

// creates an index.
// if should recreate index,it will delete
func createOrOpenIndex(indexLocation string, mappings map[string]*mapping.DocumentMapping) (bleve.Index, error) {
	var index bleve.Index
	if _, err := os.Stat(indexLocation); os.IsNotExist(err) {
		indexMapping, err := createMapping(mappings)
		if err != nil {
			return nil, fmt.Errorf("creating mapping document at %s => %v", indexLocation, err)
		}
		if index, err = bleve.New(indexLocation, indexMapping); err != nil {
			return nil, fmt.Errorf("creating index at %s => %v", indexLocation, err)
		}
	} else {
		log.Printf("Opening index %s", indexLocation)
		if index, err = bleve.Open(indexLocation); err != nil {
			return nil, fmt.Errorf("opening index at %s => %v", indexLocation, err)
		}
	}

	return index, nil
}

// Initialize -
func (i *Index) Initialize(path string, recreate bool, mappings map[string]*mapping.DocumentMapping) {
	if recreate {
		// delete anything if it exists in the directory
		err := os.RemoveAll(path + "/" + i.Name)
		if err != nil {
			fmt.Println(err)
		}
	}
	index, err := createOrOpenIndex(path+"/"+i.Name, mappings)
	if err != nil {
		log.Fatal("error opening or creating index.")
	}
	i.SearchIndex = index
}

func createMapping(mappings map[string]*mapping.DocumentMapping) (mapping.IndexMapping, error) {
	indexMapping := bleve.NewIndexMapping()

	for key, value := range mappings {
		indexMapping.AddDocumentMapping(key, value)
	}
	// a custom field definition that uses our custom analyzer
	// indexMapping.AddDocumentMapping("owner", createOwnerMapping())

	indexMapping.TypeField = "type"
	indexMapping.DefaultAnalyzer = textFieldAnalyzer

	err := indexMapping.AddCustomTokenFilter("edgeNgram325",
		map[string]interface{}{
			"type": edgengram.Name,
			"min":  3.0,
			"max":  25.0,
		})

	if err != nil {
		return nil, err
	}

	err = indexMapping.AddCustomAnalyzer("enWithEdgeNgram325",
		map[string]interface{}{
			"type":      custom.Name,
			"tokenizer": unicode.Name,
			"token_filters": []string{
				en.PossessiveName,
				lowercase.Name,
				en.StopName,
				"edgeNgram325",
			},
		})

	if err != nil {
		return nil, err
	}
	return indexMapping, nil
}

func KeywordFieldMapping() *mapping.FieldMapping {
	// a generic reusable mapping for keyword text
	keywordFieldMapping := bleve.NewTextFieldMapping()
	keywordFieldMapping.Analyzer = keyword.Name
	return keywordFieldMapping
}

func EnglishTextFieldMapping() *mapping.FieldMapping {
	// a generic reusable mapping for keyword text

	// a generic reusable mapping for english text
	englishTextFieldMapping := bleve.NewTextFieldMapping()
	englishTextFieldMapping.Analyzer = en.AnalyzerName

	return englishTextFieldMapping
}

func EdgeNgram325FieldMapping() *mapping.FieldMapping {
	// a custom field definition that uses our custom analyzer
	edgeNgram325FieldMapping := bleve.NewTextFieldMapping()
	edgeNgram325FieldMapping.Analyzer = "enWithEdgeNgram325"
	return edgeNgram325FieldMapping
}

func LongTextFieldMapping() *mapping.FieldMapping {
	// a specific mapping to index the description fields
	// detected language
	descriptionLangFieldMapping := bleve.NewTextFieldMapping()
	descriptionLangFieldMapping.Name = "descriptionLang"
	descriptionLangFieldMapping.Analyzer = "enWithEdgeNgram325"
	descriptionLangFieldMapping.Store = false
	descriptionLangFieldMapping.IncludeTermVectors = false
	descriptionLangFieldMapping.IncludeInAll = false

	return descriptionLangFieldMapping
}
