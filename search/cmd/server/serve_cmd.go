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

	"github.com/spf13/cobra"
)

func serveCmd() *cobra.Command {

	server := NewHttpServer()

	serveCmd := &cobra.Command{
		Use:   "serve",
		Short: "used to run the server of this application",
		Long: `A longer description that spans multiple lines and likely contains examples
and usage of using your command. For example:

Cobra is a CLI library for Go that empowers applications.
This application is a tool to generate the needed files
to quickly create a Cobra application.`,
		PreRunE: func(cmd *cobra.Command, args []string) error {

			return initParams(cmd, server.Params)
		},
		Run: func(cmd *cobra.Command, args []string) {
			server.Initialize("")
			// create the http server
			srv := server.Run("")

			// create the grpc
			grpcServer := NewGrpcServer()

			// start both the grpc and http server
			serveAndGracefulShutdown(srv, grpcServer)
		},
	}
	fmt.Printf("%v\n", server)
	serveCmd.Flags().StringVarP(&server.Params.Port, "port", "p", "8080", "port to run server on")
	serveCmd.Flags().StringVarP(&server.Params.DataDir, "data-dir", "d", "./", "directory where data needs to be stored")
	// the rest of the mapping from app.Props to flags
	return serveCmd
}

// Starts server with gracefully shutdown semantics
func serveAndGracefulShutdown(svr *http.Server, grpcServer *GrpcServer) {
	sigChan := make(chan os.Signal, 1)
	signal.Notify(sigChan, syscall.SIGINT, syscall.SIGTERM)

	// wait for requests and serve
	serveAndWait := make(chan error)
	go func() {
		log.Printf("Grpc Server listening on port %s", grpcServer.Addr)
		serveAndWait <- grpcServer.ListenAndServe()
	}()

	go func() {
		log.Printf("Http Server listening on port %s", svr.Addr)
		serveAndWait <- svr.ListenAndServe()

	}()

	// go func() {
	// 	grpcServer.ListenAndServe()
	// 	svr.ListenAndServe()
	// }()

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
		_ = grpcServer.Shutdown(ctx)
	}
}
