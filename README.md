# Geotrellis Ingest Example

This project demonstrates an issue with some of the resampling method options with the `MultibandIngest` tool from [Geotrellis](https://github.com/locationtech/geotrellis).

See: [https://github.com/locationtech/geotrellis/issues/2547](https://github.com/locationtech/geotrellis/issues/2547) 

## Running the example

Run the commands below to ingest a sample Geotiff and start up a server to view the results:

```
make ingest
make server
```

The results can be viewed in a browser at `http://localhost:8080`.

Screenshot:

![example image](/docs/images/bilinear.png)

