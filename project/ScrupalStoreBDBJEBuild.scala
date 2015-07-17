/**********************************************************************************************************************
  * This file is part of Scrupal, a Scalable Reactive Web Application Framework for Content Management                 *
  *                                                                                                                    *
  * Copyright (c) 2015, Reactific Software LLC. All Rights Reserved.                                                   *
  *                                                                                                                    *
  * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance     *
  * with the License. You may obtain a copy of the License at                                                          *
  *                                                                                                                    *
  *     http://www.apache.org/licenses/LICENSE-2.0                                                                     *
  *                                                                                                                    *
  * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed   *
  * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for  *
  * the specific language governing permissions and limitations under the License.                                     *
  **********************************************************************************************************************/

import sbt.Keys._
import sbt._
import play.sbt.PlayLayoutPlugin
import scrupal.sbt.ScrupalPlugin
import scrupal.sbt.ScrupalPlugin.autoImport._
import scoverage.ScoverageSbtPlugin

object ScrupalStoreBDBJEBuild extends Build {


  lazy val root_proj = Project("scrupal_store_bdbje", file("."))
    .enablePlugins(ScrupalPlugin)
    .disablePlugins(PlayLayoutPlugin)
    .settings(
      organization := "org.scrupal",
      version := "0.2.1-SNAPSHOT",
      maxErrors := 50,
      scrupalTitle := "Scrupal Utils",
      scrupalPackage := "scrupal.utils",
      scrupalCopyrightHolder := "Reactific Software LLC",
      scrupalCopyrightYears := Seq(2015),
      scrupalDeveloperUrl := url("http://reactific.com/"),
      ScoverageSbtPlugin.ScoverageKeys.coverageFailOnMinimum := true,
      ScoverageSbtPlugin.ScoverageKeys.coverageMinimum := 95,
      resolvers += "Oracle Maven" at "http://download.oracle.com/maven/",
      libraryDependencies ++= Seq(
        "org.scrupal" %% "scrupal-api" % "0.2.1-SNAPSHOT",
        "org.scrupal" %% "scrupal-storage" % "0.2.1-SNAPSHOT",
        "com.sleepycat" % "je" % "6.3.8"
      )
    )

  override def rootProject = Some(root_proj)
}

