package app

import (
	"fmt"

	"github.com/blevesearch/bleve"
	"github.com/blevesearch/bleve/mapping"
	"github.com/rnkoaa/petclinic/pkg/search"
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

// BulkIndexOwners - bulk owners
func (a *App) BulkIndexOwners(owners []Owner) []error {
	errs := make([]error, 0, 0)
	ownersIdx := make([]OwnerIndex, 0, len(owners))
	for _, owner := range owners {
		ownerIdx := fromOwner(owner)
		ownersIdx = append(ownersIdx, ownerIdx)
	}

	batch := a.Index.SearchIndex.NewBatch()
	count := 0
	for _, owner := range ownersIdx {
		batch.Index(owner.ID, owner)
		count++
		if count == a.Index.BatchSize || count == len(owners) {
			if err := a.Index.SearchIndex.Batch(batch); err != nil {
				fmt.Printf("failed to index batch: %s\n", err.Error())
				errs = append(errs, err)
			}
			batch = a.Index.SearchIndex.NewBatch()
		}
	}
	return errs
}

// IndexOwner -
func (a *App) IndexOwner(owner Owner) error {
	ownerIdx := fromOwner(owner)
	return a.Index.SearchIndex.Index(ownerIdx.ID, ownerIdx)
}

func createOwnerMapping() *mapping.DocumentMapping {
	ownerMapping := bleve.NewDocumentMapping()

	ownerMapping.AddFieldMappingsAt("address", search.LongTextFieldMapping())
	ownerMapping.AddFieldMappingsAt("first_name", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("last_name", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("email", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("state", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("zip", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("city", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddFieldMappingsAt("phone", search.KeywordFieldMapping())
	ownerMapping.AddFieldMappingsAt("type", search.KeywordFieldMapping())

	petMapping := bleve.NewDocumentMapping()
	petMapping.AddFieldMappingsAt("name", search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	petMapping.AddFieldMappingsAt("id", search.KeywordFieldMapping())
	petMapping.AddFieldMappingsAt("type", search.KeywordFieldMapping(), search.EnglishTextFieldMapping(), search.EdgeNgram325FieldMapping())
	ownerMapping.AddSubDocumentMapping("pets", petMapping)

	return ownerMapping
}
