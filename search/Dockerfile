FROM golang:1.14.0 as base_builder

WORKDIR /go/src/github.com/rnkoaa/petclinic

ENV GO111MODULE=on

COPY go.mod go.sum ./

RUN go mod download

# copy the contents of the image and build it.
FROM base_builder as builder
COPY . ./
RUN CGO_ENABLED=0 GOOS=linux go build -mod=readonly -v -o petclinic-search ./cmd/server/...

# now create a minimal image that will build the application.
FROM alpine:3.7

ENV PORT=8080
ENV DATA_DIR=/var/lib/petclinic

VOLUME [ "/var/lib/petclinic" ]

ENV GOSU_VERSION 1.12

RUN apk add --no-cache ca-certificates && \
  addgroup -g 1000 -S petclinic-data && \
  adduser -u 1000 -S petclinic-data -G petclinic-data && \
  mkdir -p /var/lib/petclinic && \
  chown -R petclinic-data:petclinic-data /var/lib/petclinic && \
  mkdir /app && \
  chown -R petclinic-data:petclinic-data /app/

COPY ./entrypoint.sh /app/
RUN chown -R petclinic-data:petclinic-data /app/entrypoint.sh \
    && chmod +x /app/entrypoint.sh

USER petclinic-data
WORKDIR /app/

COPY --from=builder /go/src/github.com/rnkoaa/petclinic/petclinic-search .

ENTRYPOINT [ "./entrypoint.sh"]

EXPOSE ${PORT}
