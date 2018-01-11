#!/bin/bash

DATA_DIR=${DATA_DIR:-/tmp/geotrellis-ingest-example/data}

mkdir -p $DATA_DIR

wget https://storage.googleapis.com/geotrellis-ingest-example/example-ndvi.tif --directory-prefix=$DATA_DIR

