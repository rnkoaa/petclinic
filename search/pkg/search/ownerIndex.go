package search

import (
	"fmt"
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

type Owner struct {
	ID        string
	Address   string
	State     string
	Zip       string
	Email     string
	City      string
	LastName  string
	FirstName string
	Phone     string
}

// Index -
type Index struct {
	Name        string
	SearchIndex bleve.Index
}

// Initialize -
func (i *Index) Initialize(path string) {
	err := os.RemoveAll("./" + path)
	if err != nil {
		fmt.Println(err)
	}
	mapping, err := createMapping()
	if err != nil {
		fmt.Printf("error creating mapping, %v\n", err)
		os.Exit(1)
	}

	index, err := bleve.New(i.Name, mapping)
	if err != nil {
		fmt.Println(err)
		return
	}
	i.SearchIndex = index
}

// IndexOwner -
func (i *Index) IndexOwner(owner Owner) error {
	return i.SearchIndex.Index(owner.ID, owner)
}

func createOwnerMapping() *mapping.DocumentMapping {
	ownerMapping := bleve.NewDocumentMapping()

	// address
	ownerMapping.AddFieldMappingsAt("address", longTextFieldMapping())
	// title
	ownerMapping.AddFieldMappingsAt("firstName",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("email",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("state",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("zip",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	// title
	ownerMapping.AddFieldMappingsAt("lastName",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	// title
	ownerMapping.AddFieldMappingsAt("city",
		englishTextFieldMapping(),
		edgeNgram325FieldMapping())
	// title
	ownerMapping.AddFieldMappingsAt("phone", keywordFieldMapping())

	return ownerMapping
}

func createMapping() (mapping.IndexMapping, error) {
	indexMapping := bleve.NewIndexMapping()
	// a custom field definition that uses our custom analyzer
	indexMapping.AddDocumentMapping("owner", createOwnerMapping())

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

func keywordFieldMapping() *mapping.FieldMapping {
	// a generic reusable mapping for keyword text
	keywordFieldMapping := bleve.NewTextFieldMapping()
	keywordFieldMapping.Analyzer = keyword.Name
	return keywordFieldMapping
}

func englishTextFieldMapping() *mapping.FieldMapping {
	// a generic reusable mapping for keyword text

	// a generic reusable mapping for english text
	englishTextFieldMapping := bleve.NewTextFieldMapping()
	englishTextFieldMapping.Analyzer = en.AnalyzerName

	return englishTextFieldMapping
}

func edgeNgram325FieldMapping() *mapping.FieldMapping {
	// a custom field definition that uses our custom analyzer
	edgeNgram325FieldMapping := bleve.NewTextFieldMapping()
	edgeNgram325FieldMapping.Analyzer = "enWithEdgeNgram325"
	return edgeNgram325FieldMapping
}

func longTextFieldMapping() *mapping.FieldMapping {
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
