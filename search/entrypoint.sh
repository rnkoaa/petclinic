#!/bin/sh
set -e

./petclinic-search serve --port $PORT --data-dir $DATA_DIR

exec "$@"
