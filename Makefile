JAR_FILE := target/scala-2.11/geotrellis-ingest-example-assembly-0.0.1-SNAPSHOT.jar
DATA_DIR := /tmp/geotrellis-ingest-example/data

${JAR_FILE}:
	./sbt assembly

ingest: ${JAR_FILE}
	if [ ! -d ${DATA_DIR} ]; \
		then scripts/download-data.sh; \
	fi
	INPUT_FILE=file://${PWD}/conf/input-nearest-neighbor.json OUTPUT_FILE=file://${PWD}/conf/output-nearest-neighbor.json BACKEND_PROFILES_FILE=file://${PWD}/conf/backend-profiles.json scripts/multiband-ingest.sh
	INPUT_FILE=file://${PWD}/conf/input-bilinear.json OUTPUT_FILE=file://${PWD}/conf/output-bilinear.json BACKEND_PROFILES_FILE=file://${PWD}/conf/backend-profiles.json scripts/multiband-ingest.sh
	INPUT_FILE=file://${PWD}/conf/input-cubic-spline.json OUTPUT_FILE=file://${PWD}/conf/output-cubic-spline.json BACKEND_PROFILES_FILE=file://${PWD}/conf/backend-profiles.json scripts/multiband-ingest.sh
	INPUT_FILE=file://${PWD}/conf/input-cubic-convolution.json OUTPUT_FILE=file://${PWD}/conf/output-cubic-convolution.json BACKEND_PROFILES_FILE=file://${PWD}/conf/backend-profiles.json scripts/multiband-ingest.sh
	INPUT_FILE=file://${PWD}/conf/input-lanczos.json OUTPUT_FILE=file://${PWD}/conf/output-lanczos.json BACKEND_PROFILES_FILE=file://${PWD}/conf/backend-profiles.json scripts/multiband-ingest.sh

server:
	./sbt run

