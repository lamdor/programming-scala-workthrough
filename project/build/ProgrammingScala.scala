import sbt._

class ProgrammingScalaProject(info: ProjectInfo) extends DefaultProject(info) {
  val slf4j = "org.slf4j" % "slf4j-jdk14" % "1.5.2"
  val mina = "org.apache.mina" % "mina-core" % "2.0.0-M6"
  val specs = "org.scala-tools.testing" % "specs" % "1.6.0" % "test"
  val junit = "junit" % "junit" % "4.7" % "testn"
}
