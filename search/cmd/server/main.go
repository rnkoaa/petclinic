package main

import (
	"fmt"
	"os"
)

var (
	ver = "v1.0.0"
)

func main() {
	// use PORT environment variable, or default to 8080
	port := os.Getenv("PORT")
	if len(port) == 0 {
		port = "9090"
	}

	// app := ap

	app := App{}
	app.Initializer()
	app.Run(fmt.Sprintf("0.0.0.0:%s", port))

}
