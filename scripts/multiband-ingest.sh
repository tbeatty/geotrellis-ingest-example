#!/bin/bash

JAR_FILE=${JAR_FILE:-target/scala-2.11/geotrellis-ingest-example-assembly-0.0.1-SNAPSHOT.jar}

spark-submit \
  --master 'local[*]' \
  --class geotrellis.spark.etl.MultibandIngest \
  $JAR_FILE \
  --input $INPUT_FILE \
  --output $OUTPUT_FILE \
  --backend-profiles $BACKEND_PROFILES_FILE

