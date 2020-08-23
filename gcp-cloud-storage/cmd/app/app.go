package app

import (
	"context"
	"fmt"
	"io"
	"io/ioutil"

	"cloud.google.com/go/storage"
)

type App struct {
	client *storage.Client
}

func NewApp(c *storage.Client) *App {
	return &App{
		client: c,
	}
}

func (a *App) DownloadFile(ctx context.Context, w io.Writer, bucket, object, destination string) error {

	if destination == "" {
		return fmt.Errorf("file destination path is required")
	}
	rc, err := a.client.Bucket(bucket).Object(object).NewReader(ctx)
	if err != nil {
		return fmt.Errorf("Object(%q).NewReader: %v", object, err)
	}
	defer rc.Close()

	data, err := ioutil.ReadAll(rc)
	if err != nil {
		return fmt.Errorf("ioutil.ReadAll: %v", err)
	}

	err = ioutil.WriteFile(destination, data, 0644)
	if err != nil {
		return fmt.Errorf("error while downloading object: %v", err)
	}
	return nil
}
