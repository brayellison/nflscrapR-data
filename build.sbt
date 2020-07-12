name := "nflscrapR Data Grouping"

version := "1.0"

scalaVersion := "2.12.8"
resolvers += "Mvnrepository" at "https://mvnrepository.com/artifact/"
resolvers += "other maven" at "https://repo1.maven.org/maven2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "org.apache.spark" %% "spark-sql" % "2.4.6",
  "org.postgresql" % "postgresql" % "42.2.5",
  "net.postgis" % "postgis-jdbc" % "2.2.1"
)
