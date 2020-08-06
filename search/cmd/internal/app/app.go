package app

import (
	"github.com/blevesearch/bleve/mapping"
	"github.com/rnkoaa/petclinic/pkg/search"
)

// App - an app that can be used as an interface to do search and indexing
type App struct {
	DataDir          string
	Index            *search.Index
	DocumentMappings map[string]*mapping.DocumentMapping
	MaxSearchHitSize int
}

// New - creates a new App object for doing search work
func New() *App {
	app := &App{
		DataDir:          "./",
		MaxSearchHitSize: 100,
	}
	app.DocumentMappings = map[string]*mapping.DocumentMapping{
		"owner": createOwnerMapping(),
	}

	return app
}

// WithIndex - adding a new Index to the app
func (a *App) WithIndex(index *search.Index) *App {
	a.Index = index
	return a
}
