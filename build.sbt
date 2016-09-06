import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignParameters, true)
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)

name := "akka-api-gateway-example"

version := "0.1.0"

scalaVersion := "2.11.8"

organization := "jp.co.dzl"

resolvers += Resolver.bintrayRepo("hseeberger", "maven")

libraryDependencies ++= Seq(
  "com.typesafe.akka"      %% "akka-http-experimental"      % "2.4.9",
  "com.typesafe.akka"      %% "akka-stream"                 % "2.4.9",
  "io.circe"               %% "circe-generic"               % "0.4.1",
  "io.circe"               %% "circe-parser"                % "0.4.1",
  "de.heikoseeberger"      %% "akka-http-circe"             % "1.8.0",
  "org.scaldi"             %% "scaldi"                      % "0.5.7",
  "com.pauldijou"          %% "jwt-core"                    % "0.8.0",
  "org.scala-lang.modules" %% "scala-xml"                   % "1.0.4",
  "com.typesafe.akka"      %% "akka-http-testkit"           % "2.4.9"  % "test",
  "com.typesafe.akka"      %% "akka-stream-testkit"         % "2.4.9"  % "test",
  "org.scalatest"          %% "scalatest"                   % "2.2.6"  % "test",
  "org.scalamock"          %% "scalamock-scalatest-support" % "3.2.2"  % "test"
)

test in assembly := {}

mainClass in assembly := Some("jp.co.dzl.example.akka.api")

fork in Test := true
