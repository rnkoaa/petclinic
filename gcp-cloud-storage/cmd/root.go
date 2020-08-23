package main

import (
	"context"
	"fmt"
	"os"
	"time"

	"cloud.google.com/go/storage"
	"github.com/rnkoaa/petclinic/gcp/cloudstorage/cmd/app"
	"github.com/spf13/cobra"
)

var (
	bucket      string
	object      string
	destination string
)

func throwError(message string) {
	fmt.Println(message)
	os.Exit(1)
}

var rootCmd = &cobra.Command{
	Use:   "gcp-storage-download",
	Short: "GCP Storage Downloader allows us to download artifacts from Google cloud storage",
	Run: func(cmd *cobra.Command, args []string) {
		if object == "" {
			throwError("object file name is required")
		}
		if destination == "" {
			destination = object
		}
		if bucket == "" {
			throwError("gcp bucket name is required")
		}

		// cloud environment is only set when running in gcp upon which we are already authenticated
		cloudEnvironment := os.Getenv("CLOUD_ENVIRONMENT")
		appCredentials := os.Getenv("GOOGLE_APPLICATION_CREDENTIALS")

		if cloudEnvironment == "local" && appCredentials == "" {
			throwError("downloader requires enviroment variable GOOGLE_APPLICATION_CREDENTIALS to be set correctly")
		}

		ctx := context.Background()

		ctx, cancel := context.WithTimeout(ctx, time.Second*50)
		defer cancel()
		client, err := storage.NewClient(ctx)

		app := app.NewApp(client)

		defer client.Close()
		err = app.DownloadFile(ctx, os.Stdout, bucket, object, destination)
		if err != nil {
			throwError(fmt.Sprintf("error while downloading object. %v\n", err))
		}
	},
}

func Execute() {
	if err := rootCmd.Execute(); err != nil {
		fmt.Println(err)
		os.Exit(1)
	}
}

func init() {
	rootCmd.Flags().StringVarP(&bucket, "bucket", "b", "", "the google cloud bucket to use for downloading objects")
	rootCmd.Flags().StringVarP(&destination, "destination", "d", "", "the google cloud bucket to use for downloading objects")
	rootCmd.Flags().StringVarP(&object, "object", "o", "", "the google cloud bucket to use for downloading objects")
}
