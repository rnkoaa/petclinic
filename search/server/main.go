// package main

// import (
// 	"encoding/csv"
// 	"fmt"
// 	"os"

// 	"github.com/blevesearch/bleve"
// 	"github.com/blevesearch/bleve/analysis/analyzer/custom"
// 	"github.com/blevesearch/bleve/analysis/analyzer/keyword"
// 	"github.com/blevesearch/bleve/analysis/lang/en"
// 	"github.com/blevesearch/bleve/analysis/token/edgengram"
// 	"github.com/blevesearch/bleve/analysis/token/lowercase"
// 	"github.com/blevesearch/bleve/analysis/tokenizer/unicode"
// 	"github.com/blevesearch/bleve/mapping"
// )

// const (
// 	textFieldAnalyzer = "en"
// )

// type Author struct {
// 	Name  string
// 	Email string
// }

// type Blog struct {
// 	Title       string
// 	CreatedDate string
// 	UpdatedDate string
// 	Body        string
// 	Tags        []string
// 	Author      Author
// }

// type Receipe struct {
// 	Name        string `json:"name"`
// 	Ingredients string `json:"ingredients"`
// 	URL         string `json:"url"`
// 	Image       string `json:"image"`
// 	CookTime    string `json:"cookTime"`
// 	Source      string `json:"source"`
// 	RecipeYield string `json:"recipeYield"`
// 	published   string `json:"datePublished"`
// 	PrepTime    string `json:"prepTime"`
// 	Description string `json:"description"`
// }

// type Owner struct {
// 	ID        string
// 	Address   string
// 	City      string
// 	LastName  string
// 	FirstName string
// 	Phone     string
// }

// func readOwners() ([]Owner, error) {
// 	recordFile, err := os.Open("owners.csv")
// 	if err != nil {
// 		fmt.Println("An error encountered ::", err)
// 		return nil, err
// 	}

// 	// Setup the reader
// 	reader := csv.NewReader(recordFile)

// 	// Read the records
// 	allRecords, err := reader.ReadAll()
// 	if err != nil {
// 		fmt.Println("An error encountered ::", err)
// 		return nil, err
// 	}

// 	err = recordFile.Close()
// 	if err != nil {
// 		fmt.Println("An error encountered ::", err)
// 		return nil, err
// 	}
// 	owners := make([]Owner, 0)
// 	for _, record := range allRecords {
// 		fmt.Println(record[0])
// 		owner := Owner{
// 			ID:        record[0],
// 			Address:   record[1],
// 			City:      record[2],
// 			FirstName: record[3],
// 			LastName:  record[4],
// 			Phone:     record[5],
// 		}
// 		owners = append(owners, owner)
// 	}

// 	return owners, nil
// }

// func main() {
// 	app := App{}
// 	app.Initializer()
// 	app.Run(":9090")
// 	// owners, _ := readOwners()
// 	// for _, owner := range owners {
// 	// 	fmt.Println(owner)
// 	// }

// 	// indexName := "blogs.bleve"
// 	// err := os.RemoveAll("./" + indexName)
// 	// if err != nil {
// 	// 	fmt.Println(err)
// 	// }

// 	// open a new index
// 	// mapping := bleve.NewIndexMapping()
// 	// blogMapping := bleve.NewDocumentMapping()
// 	// mapping.AddDocumentMapping("blog", blogMapping)

// 	// nameFieldMapping := bleve.NewTextFieldMapping()
// 	// nameFieldMapping.Analyzer = en.AnalyzerName
// 	// blogMapping.AddFieldMappingsAt("name", nameFieldMapping)

// 	// author := bleve.NewDocumentMapping()
// 	// authorNameFieldMapping := bleve.NewTextFieldMapping()

// 	// // do not store the author name
// 	// authorNameFieldMapping.Store = false

// 	// author.AddFieldMappingsAt("name", authorNameFieldMapping)
// 	// nameFieldMapping.Analyzer = en.AnalyzerName
// 	// authorEmailFieldMapping := bleve.NewTextFieldMapping()

// 	// // do not include email address in _all field
// 	// authorEmailFieldMapping.IncludeInAll = false
// 	// nameFieldMapping.Analyzer = en.AnalyzerName
// 	// author.AddFieldMappingsAt("email", authorEmailFieldMapping)

// 	// // blog.AddSubDocumentMapping("author", author)
// 	// blogMapping.AddSubDocumentMapping("author", author)

// 	// mapping, err := createMapping()
// 	// if err != nil {
// 	// 	fmt.Println(err)
// 	// 	os.Exit(1)
// 	// }

// 	// // mapping.AddDocumentMapping
// 	// index, err := bleve.New(indexName, mapping)
// 	// if err != nil {
// 	// 	fmt.Println(err)
// 	// 	return
// 	// }

// 	// blog := Blog{
// 	// 	Title: "This is my bleve learning",
// 	// 	Body:  "This is the body of  learning bleve on my machine.",
// 	// 	Tags:  []string{"first", "second", "post"},
// 	// 	Author: Author{
// 	// 		Name:  "Richard Agyei",
// 	// 		Email: "richard.agyei@gmail.com",
// 	// 	},
// 	// }
// 	// blog2 := Blog{
// 	// 	Title: "Kubernetes the hard way",
// 	// 	Body:  "This is the body of  learning bleve on my machine.",
// 	// 	Tags:  []string{"kubernetes", "docker", "second"},
// 	// 	Author: Author{
// 	// 		Name:  "Kelsey Hightower",
// 	// 		Email: "kelsey.hightower@gmail.com",
// 	// 	},
// 	// }

// 	// // index some data
// 	// index.Index("id1", blog)
// 	// index.Index("id2", blog2)

// 	// // query := bleve.NewMatchQuery("ble")
// 	// query := bleve.NewPrefixQuery("ble")
// 	// search := bleve.NewSearchRequest(query)

// 	// searchResults, err := index.Search(search)
// 	// if err != nil {
// 	// 	fmt.Println(err)
// 	// 	return
// 	// }
// 	// fmt.Println(searchResults)
// 	// hits := searchResults.Hits
// 	// for _, h := range hits {
// 	// 	ID := h.ID
// 	// 	fmt.Println(ID)
// 	// }
// }

// func createMapping() (mapping.IndexMapping, error) {
// 	indexMapping := bleve.NewIndexMapping()
// 	// a custom field definition that uses our custom analyzer
// 	edgeNgram325FieldMapping := bleve.NewTextFieldMapping()
// 	edgeNgram325FieldMapping.Analyzer = "enWithEdgeNgram325"

// 	// a generic reusable mapping for english text
// 	englishTextFieldMapping := bleve.NewTextFieldMapping()
// 	englishTextFieldMapping.Analyzer = en.AnalyzerName

// 	// a generic reusable mapping for keyword text
// 	keywordFieldMapping := bleve.NewTextFieldMapping()
// 	keywordFieldMapping.Analyzer = keyword.Name

// 	// a specific mapping to index the description fields
// 	// detected language
// 	descriptionLangFieldMapping := bleve.NewTextFieldMapping()
// 	descriptionLangFieldMapping.Name = "descriptionLang"
// 	descriptionLangFieldMapping.Analyzer = "enWithEdgeNgram325"
// 	descriptionLangFieldMapping.Store = false
// 	descriptionLangFieldMapping.IncludeTermVectors = false
// 	descriptionLangFieldMapping.IncludeInAll = false

// 	blogMapping := bleve.NewDocumentMapping()

// 	// title
// 	blogMapping.AddFieldMappingsAt("title", englishTextFieldMapping, edgeNgram325FieldMapping)

// 	// body
// 	blogMapping.AddFieldMappingsAt("Body", edgeNgram325FieldMapping, descriptionLangFieldMapping)

// 	// tags
// 	blogMapping.AddFieldMappingsAt("tags", keywordFieldMapping)

// 	authorMapping := bleve.NewDocumentMapping()
// 	authorMapping.AddFieldMappingsAt("name", edgeNgram325FieldMapping, englishTextFieldMapping)
// 	authorMapping.AddFieldMappingsAt("email", edgeNgram325FieldMapping, englishTextFieldMapping, keywordFieldMapping)

// 	blogMapping.AddSubDocumentMapping("author", authorMapping)
// 	indexMapping.AddDocumentMapping("blog", blogMapping)

// 	indexMapping.TypeField = "type"
// 	indexMapping.DefaultAnalyzer = textFieldAnalyzer

// 	err := indexMapping.AddCustomTokenFilter("edgeNgram325",
// 		map[string]interface{}{
// 			"type": edgengram.Name,
// 			"min":  3.0,
// 			"max":  25.0,
// 		})

// 	if err != nil {
// 		return nil, err
// 	}

// 	err = indexMapping.AddCustomAnalyzer("enWithEdgeNgram325",
// 		map[string]interface{}{
// 			"type":      custom.Name,
// 			"tokenizer": unicode.Name,
// 			"token_filters": []string{
// 				en.PossessiveName,
// 				lowercase.Name,
// 				en.StopName,
// 				"edgeNgram325",
// 			},
// 		})

// 	if err != nil {
// 		return nil, err
// 	}
// 	return indexMapping, nil
// }
