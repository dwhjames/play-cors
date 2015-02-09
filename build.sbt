
organization in ThisBuild := "com.github.dwhjames"

scalaVersion in ThisBuild := "2.11.5"

crossScalaVersions := Seq("2.10.4", "2.11.5")

scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-unchecked",
  "-Xfuture",
  "-Xlint"
)

resolvers in ThisBuild += Resolver.typesafeRepo("releases")



name := "play-cors"

description := "Cross-Origin Resource Sharing (CORS) support for Play"

licenses := Seq("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github/dwhjames/play-cors"))


libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play"      % "2.3.7" % "provided",
  "com.typesafe.play" %% "play-test" % "2.3.7" % "test"
)
