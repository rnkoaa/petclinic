package search

import (
	"fmt"

	"github.com/blevesearch/bleve"
	"github.com/blevesearch/bleve/mapping"
)

const (
	textFieldAnalyzer = "en"
)

// Pet - pet object
type Pet struct {
	ID   string `json:"id,omitempty"`
	Type string `json:"type"`
	Name string `json:"name"`
}

// Owner - Owner object for serializing and deserializing owner object
type Owner struct {
	ID        string `json:"id,omitempty"`
	Address   string `json:"address,omitempty"`
	State     string `json:"state,omitempty"`
	Zip       string `json:"zip,omitempty"`
	Email     string `json:"email,omitempty"`
	City      string `json:"city,omitempty"`
	LastName  string `json:"last_name,omitempty"`
	FirstName string `json:"first_name,omitempty"`
	Phone     string `json:"telephone,omitempty"`
	Pets      []Pet  `json:"pets,omitempty"`
}

// Index -
type Index struct {
	Name        string
	BatchSize   int
	SearchIndex bleve.Index
}

// OwnerIndex - the indexable object of owner
type OwnerIndex struct {
	ID        string `json:"id,omitempty"`
	Address   string `json:"address,omitempty"`
	State     string `json:"state,omitempty"`
	Zip       string `json:"zip,omitempty"`
	Email     string `json:"email,omitempty"`
	City      string `json:"city,omitempty"`
	LastName  string `json:"last_name,omitempty"`
	FirstName string `json:"first_name,omitempty"`
	Phone     string `json:"telephone,omitempty"`
	Pets      []Pet  `json:"pets,omitempty"`
	Type      string `json:"type"`
}

func fromOwner(owner Owner) OwnerIndex {
	return OwnerIndex{
		ID:        owner.ID,
		Address:   owner.Address,
		State:     owner.State,
		Zip:       owner.Zip,
		Email:     owner.Email,
		City:      owner.City,
		LastName:  owner.LastName,
		FirstName: owner.FirstName,
		Phone:     owner.Phone,
		Pets:      owner.Pets,
		Type:      "owner",
	}
}

// BulkIndexOwner - bulk owners
func (i *Index) BulkIndexOwners(owners []Owner) []error {
	errs := make([]error, 0, 0)
	ownersIdx := make([]OwnerIndex, 0, len(owners))
	for _, owner := range owners {
		ownerIdx := fromOwner(owner)
		ownersIdx = append(ownersIdx, ownerIdx)
	}

	batch := i.SearchIndex.NewBatch()
	count := 0
	for _, owner := range ownersIdx {
		batch.Index(owner.ID, owner)
		count++
		if count == i.BatchSize || count == len(owners) {
			if err := i.SearchIndex.Batch(batch); err != nil {
				fmt.Printf("failed to index batch: %s\n", err.Error())
				errs = append(errs, err)
			}
			batch = i.SearchIndex.NewBatch()
		}
	}
	return errs
}

// IndexOwner -
func (i *Index) IndexOwner(owner Owner) error {
	ownerIdx := fromOwner(owner)
	return i.SearchIndex.Index(ownerIdx.ID, ownerIdx)
}

func createOwnerMapping() *mapping.DocumentMapping {
	ownerMapping := bleve.NewDocumentMapping()

	ownerMapping.AddFieldMappingsAt("address", longTextFieldMapping())
	ownerMapping.AddFieldMappingsAt("first_name", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("last_name", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("email", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("state", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("zip", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("city", englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("phone", keywordFieldMapping())
	ownerMapping.AddFieldMappingsAt("type", keywordFieldMapping())

	petMapping := bleve.NewDocumentMapping()
	petMapping.AddFieldMappingsAt("name", englishTextFieldMapping(), edgeNgram325FieldMapping())
	petMapping.AddFieldMappingsAt("id", keywordFieldMapping())
	petMapping.AddFieldMappingsAt("type", keywordFieldMapping(), englishTextFieldMapping(), edgeNgram325FieldMapping())
	ownerMapping.AddSubDocumentMapping("pets", petMapping)

	return ownerMapping
}
