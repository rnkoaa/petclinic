package main

import (
	"fmt"

	"github.com/spf13/cobra"
)

func serveCmd() *cobra.Command {

	server := New()

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
			server.Initializer("")
			server.Run("")
		},
	}
	fmt.Printf("%v\n", server)
	serveCmd.Flags().StringVarP(&server.Params.Port, "port", "p", "8080", "port to run server on")
	serveCmd.Flags().StringVarP(&server.Params.DataDir, "data-dir", "d", "./", "directory where data needs to be stored")
	// the rest of the mapping from app.Props to flags
	return serveCmd
}
