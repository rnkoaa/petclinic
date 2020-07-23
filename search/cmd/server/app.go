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

	gohandlers "github.com/gorilla/handlers"
	"github.com/gorilla/mux"
)

type App struct {
	Router *mux.Router
}

func (a *App) Initializer() {
	a.Router = mux.NewRouter()
	a.Router.HandleFunc("/search", search)
	a.Router.HandleFunc("/index", indexOwner).Methods("POST")
	a.Router.HandleFunc("/health", healthz)
	a.Router.HandleFunc("/version", version)
}

func (a *App) Run(addr string) {
	//
	// CORS
	ch := gohandlers.CORS(gohandlers.AllowedOrigins([]string{"*"}))

	srv := &http.Server{
		Handler: ch(a.Router), // set the default handler
		Addr:    addr,
		// Addr:    fmt.Sprintf("0.0.0.0:%s", port),
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
