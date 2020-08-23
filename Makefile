.PHONY: go-protos
# protos/src/main/protos/com/petclinic
go-protos:
	mkdir -p search/protos
	protoc --proto_path=protos/src/main/protos/com/petclinic \
		protos/src/main/protos/com/petclinic/Owner.proto \
		--go_out=plugins=grpc:search/protos


kt-protos:
	./gradlew :protos:build
