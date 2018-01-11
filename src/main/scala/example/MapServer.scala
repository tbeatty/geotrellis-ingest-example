package example

import akka.actor.ActorSystem
import akka.event.{Logging, LoggingAdapter}
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import com.typesafe.config.ConfigFactory
import geotrellis.raster.render.Png
import geotrellis.raster._
import geotrellis.spark.io._
import geotrellis.spark.{LayerId, SpatialKey}
import geotrellis.spark.io.file.FileValueReader

import scala.concurrent._

object MapServer extends App with Server {

  val conf = ConfigFactory.load()

  val catalogPath = conf.getString("catalog.path")
  val valueReader = FileValueReader(catalogPath)

  def multibandReader(layerId: LayerId) = valueReader.reader[SpatialKey, MultibandTile](layerId)

  override implicit val system = ActorSystem("map-system")
  override implicit val executor = system.dispatcher
  override implicit val materializer = ActorMaterializer()
  override val logger = Logging(system, getClass)

  logger.info("Initializing HTTP service")

  Http().bindAndHandle(root, "0.0.0.0", conf.getInt("http.port"))
}

trait Server {

  implicit val system: ActorSystem
  implicit def executor: ExecutionContextExecutor
  implicit val materializer: Materializer

  val logger: LoggingAdapter

  def pngAsHttpResponse(png: Png): HttpResponse =
    HttpResponse(entity = HttpEntity(ContentType(MediaTypes.`image/png`), png.bytes))

  def root =
    pathPrefix(Segment / IntNumber / IntNumber / IntNumber) { (render, zoom, x, y) =>
      complete {
        Future {
          val layerId = LayerId(render, zoom)
          try {
            val tile = MapServer.multibandReader(layerId).read(x, y)
            pngAsHttpResponse(tile.renderPng)
          } catch {
            case _ : Throwable => HttpResponse(StatusCodes.NotFound)
          }
        }
      }
    } ~
      pathEndOrSingleSlash {
        getFromResource("static/index.html")
      } ~
      pathPrefix("") {
        getFromResourceDirectory("static")
      }
}
