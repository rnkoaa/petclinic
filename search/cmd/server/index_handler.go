package main

import (
	"fmt"
	"net/http"
)

func indexOwner(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	searchQuery := r.URL.Query().Get("q")
	// vars := mux.Vars(r)
	// searchQuery := vars["q"]
	fmt.Printf("Search Query %s\n", searchQuery)
	if searchQuery == "" {
		w.WriteHeader(404)
		_, _ = fmt.Fprintf(w, `{"message": "no response for empty search query"}`)
		return
	}

	_, _ = fmt.Fprintf(w, `{"message": "processing request for"}`)
	return

}
