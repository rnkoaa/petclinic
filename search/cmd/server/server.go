package main

import (
	"fmt"
	"net/http"
	"time"

	bleveHttp "github.com/blevesearch/bleve/http"
	gohandlers "github.com/gorilla/handlers"
	"github.com/gorilla/mux"
	"github.com/rnkoaa/petclinic/cmd/internal/app"
	"github.com/rnkoaa/petclinic/pkg/search"
)

type ServerParams struct {
	Port        string
	Address     string
	fullAddress string
	DataDir     string
}

// Server - entrypoint to the application
type Server struct {
	Params *ServerParams
	App    *app.App
	Router *mux.Router
}

// NewHttpServer - creates a new server object
func NewHttpServer() *Server {
	return &Server{
		App: app.New(),
		Params: &ServerParams{
			Port:    "8080",
			Address: "0.0.0.0",
			DataDir: ".",
		},
	}
}

// Initialize - Initialize the app object
func (s *Server) Initialize(dataDir string) {
	// s.App =
	if dataDir != "" {
		s.Params.DataDir = dataDir
	}

	index := &search.Index{
		Name:      "petclinic.bleve",
		BatchSize: 1000,
	}

	index.Initialize(s.Params.DataDir, false, s.App.DocumentMappings)
	s.App.Index = index
	bleveHttp.RegisterIndexName("petclinic", index.SearchIndex)

	s.Router = mux.NewRouter()
	s.Router.HandleFunc("/", s.searchForItems).Methods("GET")
	s.Router.HandleFunc("/", s.processSearch).Methods("POST")
	s.Router.HandleFunc("/home", func(w http.ResponseWriter, r *http.Request) {
		res := struct {
			Status string `json:"status"`
		}{
			Status: "ok",
		}
		respondWithJSON(w, 200, res)
	})
	s.Router.HandleFunc("/owner", s.createOwner).Methods("POST")
	s.Router.HandleFunc("/owner/bulk", s.createBulkOwner).Methods("POST")
	s.Router.HandleFunc("/health", healthz)
	s.Router.HandleFunc("/version", version)
}

// Run - this runs the app
func (s *Server) Run(addr string) *http.Server {

	// CORS
	fullAddress := fmt.Sprintf("%s:%s", s.Params.Address, s.Params.Port)
	ch := gohandlers.CORS(gohandlers.AllowedOrigins([]string{"*"}))

	srv := &http.Server{
		Handler: ch(s.Router), // set the default handler
		Addr:    fullAddress,
		// Good practice: enforce timeouts for servers you create!
		WriteTimeout: 15 * time.Second,
		ReadTimeout:  15 * time.Second,
	}
	return srv
}

// endpoint to test the health of the app
func healthz(w http.ResponseWriter, r *http.Request) {
	w.WriteHeader(http.StatusOK)
	_, _ = fmt.Fprintf(w, "OK")
}

// version returns the service version
func version(w http.ResponseWriter, r *http.Request) {
	_, _ = fmt.Fprintf(w, ver)
}
