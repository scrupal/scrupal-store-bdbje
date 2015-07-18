package scrupal.store.bdbje

import java.io.File
import java.net.URI
import java.util.concurrent.TimeUnit

import com.sleepycat.je._
import scrupal.storage.api.{Store, WriteResult, StorageDriver}
import scrupal.storage.impl.CommonStorageDriver

import scala.concurrent.{Future, ExecutionContext}

class BDBJEDriver extends CommonStorageDriver with ExceptionListener {

  def name: String = "Berkeley Database Java Edition"

  def scheme: String = "scrupal-bdbje"

  override def isDriverFor(uri: URI): Boolean = {
    super.isDriverFor(uri) && uri.getHost.equals("localhost")
  }

  private val envConfig : EnvironmentConfig = {
    new EnvironmentConfig()
      .setAllowCreate(true)
      .setLocking(true)
      .setLockTimeout(60, TimeUnit.SECONDS)
      .setCacheMode(CacheMode.DEFAULT)
      .setCachePercent(50)
      .setDurability(Durability.COMMIT_SYNC)
      .setExceptionListener(this)
      .asInstanceOf[EnvironmentConfig]
  }

  private def convertURI(uri: URI, allowCreate: Boolean = false) : (Environment, String) = {
    val path = new File(uri.getPath)
    val dir = path.getParentFile
    if (!dir.isDirectory)
      throw new DatabaseNotFoundException(s"Database environment path (${dir.getAbsolutePath}) is not a directory")
    else {
      new Environment(dir, envConfig.setAllowCreate(allowCreate)) -> path.getName
    }
  }

  override def canOpen(uri: URI): Boolean = {
    try {
      val (env, path) = convertURI(uri, allowCreate = false)
      env.isValid
    } catch {
      case x: DatabaseException =>
        false
      case x: UnsupportedOperationException =>
        false
      case x: IllegalArgumentException =>
        false
      case y: Throwable =>
        throw y
    }
  }

  def addStore(uri: URI)(implicit ec: ExecutionContext): Future[Store] = ???

  override def storeExists(uri: URI): Boolean = ???

  def open(uri: URI, create: Boolean)(implicit ec: ExecutionContext): Future[Store] = Future {
    val (env, name) = convertURI(uri, allowCreate = create)
    BDBJEStore(this, uri, env, name)
  }

  def id: Symbol = 'BDBJE

  override def close(): Unit = {
    super.close()
  }

  def exceptionThrown (event: ExceptionEvent) : Unit = {
    log.error(s"Asynchronous exception thrown from Berkeley DB Java Edition: ${event.toString}", event.getException)
  }
}
