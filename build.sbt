name := "geotrellis-ingest-example"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.11.11"
organization := "com.example"
scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:reflectiveCalls",
  "-language:higherKinds",
  "-language:postfixOps",
  "-language:existentials",
  "-language:experimental.macros",
  "-feature")
publishMavenStyle := true
publishArtifact in Test := false
pomIncludeRepository := { _ => false}

addCompilerPlugin("org.spire-math" % "kind-projector" % "0.9.3" cross CrossVersion.binary)
addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)

shellPrompt := { s => Project.extract(s).currentProject.id + " > " }
dependencyUpdatesExclusions := moduleFilter(organization = "org.scala-lang")

libraryDependencies ++= Seq(
  "org.locationtech.geotrellis" %% "geotrellis-spark-etl" % "1.2.0",
  "org.apache.spark" %% "spark-core" % "2.2.0",
  "org.apache.hadoop" % "hadoop-client" % "2.7.3",
  "com.typesafe.akka" %% "akka-actor"  % "2.4.17",
  "com.typesafe.akka" %% "akka-http" % "10.0.10"
)

assemblyMergeStrategy in assembly := {
  case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$") => MergeStrategy.discard
  case "reference.conf" | "application.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

