package main

import (
	"context"
	"fmt"
	"log"
	"net/http"
	"os"
	"os/signal"
	"syscall"
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

// New - creates a new server object
func New() *Server {
	return &Server{
		App: app.New(),
		Params: &ServerParams{
			Port:    "8080",
			Address: "0.0.0.0",
			DataDir: ".",
		},
	}
}

// Initializer - Initialize the app object
func (s *Server) Initializer(dataDir string) {
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
func (s *Server) Run(addr string) {

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

	serveAndGracefulShutdown(srv)
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

// Starts server with gracefully shutdown semantics
func serveAndGracefulShutdown(svr *http.Server) {
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, syscall.SIGINT, syscall.SIGTERM)

	// wait for requests and serve
	serveAndWait := make(chan error)
	go func() {
		log.Printf("Server listening on port %s", svr.Addr)
		serveAndWait <- svr.ListenAndServe()
	}()

	// block until either an error or OS-level signals
	// to shutdown gracefully
	select {
	case err := <-serveAndWait:
		log.Fatal(err)
	case <-sigChan:
		log.Printf("Shutdown signal received... closing server")
		// gracefully shutdown the server, waiting max 30 seconds for current operations to complete
		ctx, _ := context.WithTimeout(context.Background(), 30*time.Second)
		_ = svr.Shutdown(ctx)
	}
}
