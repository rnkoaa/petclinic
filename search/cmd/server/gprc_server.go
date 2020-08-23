package main

import (
	"context"
	"fmt"
	"net"

	"github.com/rnkoaa/petclinic/protos"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
)

type GrpcServerParams struct {
	Port        string
	Address     string
	fullAddress string
}

// GrpcServer - struct to represent a grpc server object
type GrpcServer struct {
	Addr   string
	Params *ServerParams
	Server *grpc.Server
}

// NewGrpcServer -
func NewGrpcServer() *GrpcServer {
	return &GrpcServer{
		Params: &ServerParams{
			Port:    "15050",
			Address: "0.0.0.0",
		},
		Addr: fmt.Sprintf("%s:%s", "0.0.0.0", "15050"),
	}
}

// ListenAndServe - start the server
func (s *GrpcServer) ListenAndServe() error {
	lis, err := net.Listen("tcp", fmt.Sprintf(":%s", s.Params.Port))
	if err != nil {
		// log.Fatalf("failed to listen: %v", err)
		return err
	}

	rs := NewOwnerIndexGrpcService()
	gs := grpc.NewServer()

	reflection.Register(gs)

	protos.RegisterOwnerIndexServiceServer(gs, rs)
	s.Server = gs
	if err := gs.Serve(lis); err != nil {
		return err
	}
	return nil
}

// Shutdown - gracefully shutdown the grpc server
func (s *GrpcServer) Shutdown(ctx context.Context) error {
	s.Server.GracefulStop()
	return nil
}

// OwnerIndexGrpcService - struct used to implement a grpc service
type OwnerIndexGrpcService struct{}

// NewOwnerIndexGrpcService - create a new OwnerIndexGrpcService object
func NewOwnerIndexGrpcService() *OwnerIndexGrpcService {
	return &OwnerIndexGrpcService{}
}

// IndexOwner - a grpc method to index an object object
func (o *OwnerIndexGrpcService) IndexOwner(ctx context.Context, req *protos.OwnerIndexRequest) (*protos.OwnerIndexResponse, error) {
	fmt.Printf("Got request to index owner object %v", req)
	return &protos.OwnerIndexResponse{}, nil
}
