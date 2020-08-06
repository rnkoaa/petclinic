package main

import (
	"encoding/json"
	"fmt"
	"net/http"

	guuid "github.com/google/uuid"
	"github.com/rnkoaa/petclinic/cmd/internal/app"
)

func (s *Server) createOwner(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var o app.Owner
	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&o); err != nil {
		respondWithError(w, http.StatusBadRequest, "Invalid request payload")
		return
	}

	if o.ID == "" {
		fmt.Println("owner id is empty, so generating a new one")
		id, err := guuid.NewUUID()
		if err != nil {
			respondWithError(w, http.StatusBadRequest, "error generating new owner id")
			return
		}
		o.ID = id.String()
	}
	fmt.Printf("Indexing Owner with Id: %s\n", o.ID)

	if err := s.App.IndexOwner(o); err != nil {
		fmt.Printf("Error %v\n", err)
		respondWithError(w, http.StatusBadRequest, "Invalid request payload")
		return
	}
	defer r.Body.Close()
	respondWithJSON(w, http.StatusCreated, o)
}

func (s *Server) createBulkOwner(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	var owners []app.Owner
	decoder := json.NewDecoder(r.Body)
	if err := decoder.Decode(&owners); err != nil {
		respondWithError(w, http.StatusBadRequest, "Invalid request payload")
		return
	}

	var badOwners = make([]app.Owner, 0, 0)
	for _, o := range owners {
		if o.ID == "" {
			badOwners = append(badOwners, o)
		}
	}

	if len(badOwners) > 0 {

		// Initial declaration
		m := map[string]interface{}{
			"message": "the following owners require id. no owner was indexed",
			"owners":  badOwners,
		}
		respondWithJSON(w, http.StatusBadRequest, m)
		return
	}

	errs := s.App.BulkIndexOwners(owners)
	if len(errs) > 0 {
		fmt.Printf("There were %d errors during bulk index.\n", len(errs))
		for _, e := range errs {
			fmt.Println(e.Error())
		}
	}
	respondWithJSON(w, http.StatusCreated, owners)
}
