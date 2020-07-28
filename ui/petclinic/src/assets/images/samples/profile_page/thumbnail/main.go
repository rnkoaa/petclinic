package main

import (
	"fmt"
	"os"
	"os/exec"
)

const (
	baseImageUrl = "https://www.bootstrapdash.com/demo/star-admin-pro/src/assets/images/samples/profile_page/thumbnail/%s.jpg"
)

func main() {
	end := 12
	for i := 1; i <= end; i++ {
		id := fmt.Sprintf("%d", i)
		if i < 10 {
			id = fmt.Sprintf("0%d", i)
		}
		imageUrl := fmt.Sprintf(baseImageUrl, id)
		wget(imageUrl, fmt.Sprintf("%s.jpg", id))
	}
}

func wget(url, filepath string) error {
	// run shell `wget URL -O filepath`
	cmd := exec.Command("wget", url, "-O", filepath)
	cmd.Stdout = os.Stdout
	cmd.Stderr = os.Stderr
	return cmd.Run()
}
