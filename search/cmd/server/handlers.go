package main

import (
	"encoding/json"
	"net/http"
)

func sendResponse(res interface{}, w http.ResponseWriter) {
	js, err := json.Marshal(res)
	if err != nil {
		http.Error(w, err.Error(), http.StatusInternalServerError)
		return
	}

	w.Header().Set("Content-Type", "application/json")
	_, _ = w.Write(js)
}
