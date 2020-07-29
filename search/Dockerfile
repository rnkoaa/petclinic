FROM golang:1.14.0 as base_builder

WORKDIR /go/src/github.com/rnkoaa/petclinic

ENV GO111MODULE=on

COPY go.mod go.sum ./

RUN go mod download

# copy the contents of the image and build it.
FROM base_builder as builder
COPY . ./
RUN CGO_ENABLED=0 GOOS=linux go build -o petclinic-search ./cmd/server/...

# now create a minimal image that will build the application.
FROM alpine:3.7

ENV PORT=8080
ENV DATA_DIR=/var/lib/petclinic

VOLUME [ "/var/lib/petclinic" ]

ENV GOSU_VERSION 1.12
RUN set -eux; \
	\
	apk add --no-cache --virtual .gosu-deps \
		ca-certificates \
		dpkg \
		gnupg \
	; \
	\
	dpkgArch="$(dpkg --print-architecture | awk -F- '{ print $NF }')"; \
	wget -O /usr/local/bin/gosu "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch"; \
	wget -O /usr/local/bin/gosu.asc "https://github.com/tianon/gosu/releases/download/$GOSU_VERSION/gosu-$dpkgArch.asc"; \
	\
# verify the signature
	export GNUPGHOME="$(mktemp -d)"; \
	gpg --batch --keyserver hkps://keys.openpgp.org --recv-keys B42F6819007F00F88E364FD4036A9C25BF357DD4; \
	gpg --batch --verify /usr/local/bin/gosu.asc /usr/local/bin/gosu; \
	command -v gpgconf && gpgconf --kill all || :; \
	rm -rf "$GNUPGHOME" /usr/local/bin/gosu.asc; \
	\
# clean up fetch dependencies
	apk del --no-network .gosu-deps; \
	\
	chmod +x /usr/local/bin/gosu; \
# verify that the binary works
	gosu --version; \
	gosu nobody true

RUN addgroup -g 1000 -S petclinic-data \
    && adduser -u 1000 -S petclinic-data -G petclinic-data \
	&& mkdir -p /var/lib/petclinic \
    && chown -R petclinic-data:petclinic-data /var/lib/petclinic \
    && mkdir /app \
    && chown -R petclinic-data:petclinic-data /app/

COPY ./entrypoint.sh /app/
RUN chown -R petclinic-data:petclinic-data /app/entrypoint.sh \
    && chmod +x /app/entrypoint.sh

USER petclinic-data
WORKDIR /app/

COPY --from=builder /go/src/github.com/rnkoaa/petclinic/petclinic-search .

ENTRYPOINT [ "./entrypoint.sh"]
# ENTRYPOINT ["sh", "-c", "./entrypoint.sh"]

EXPOSE ${PORT}
