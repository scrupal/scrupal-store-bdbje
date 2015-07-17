package scrupal.store.bdbje

import java.net.URI
import java.time.Instant

import com.sleepycat.je.Environment
import scrupal.storage.api._

import scala.concurrent.{Future, ExecutionContext}

case class BDBJEStore(driver: BDBJEDriver, uri: URI, env: Environment, override val name: String) extends Store {

  def withSchema[T](schema: String)(f: (Schema) => T): T = ???

  def schemas: Map[String, Schema] = ???

  def hasSchema(name: String): Boolean = ???

  def withCollection[S <: Storable, T](schema: String, collection: String)(f: (Collection[S]) => T): T = ???

  def addSchema(design: SchemaDesign)(implicit ec: ExecutionContext): Future[Schema] = ???

  def dropSchema(name: String)(implicit ec: ExecutionContext): Future[WriteResult] = ???

  def created: Instant = ???

  def drop(implicit ec: ExecutionContext): Future[WriteResult] = ???

  def size: Long = ???

  def close(): Unit = ???
}
