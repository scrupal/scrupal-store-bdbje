package scrupal.store.bdbje

import java.net.URI

import org.specs2.mutable.Specification
import scrupal.storage.api.StorageDriver

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global

class BDBJEDriverSpec extends Specification  {

  "BDBJEDriver" should {
    "have a scheme of scrupal-bdbje" in {
      val driver = new BDBJEDriver
      driver.scheme must beEqualTo("scrupal-bdbje")
    }

    "identify itself as the driver for scrupal-bdbje scheme" in {
      val uri = new URI("scrupal-bdbje://localhost/tmp/bdbje")
      val future = StorageDriver(uri) map { driver =>
        driver.isInstanceOf[BDBJEDriver] must beTrue
      }
      Await.result(future, Duration(2, "seconds"))
    }

    "return false for canOpen(non-existent-uri)" in {
      val uri = new URI("scrupal-bdbje://localhost/tmp/bdbje")
      val future = StorageDriver(uri) map { driver =>
        driver.canOpen(uri) must beFalse
      }
      Await.result(future, Duration(2, "seconds"))
    }
  }

}
