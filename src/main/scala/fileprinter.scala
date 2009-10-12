import java.io._

class ScalaIOException(cause: Throwable) extends RuntimeException(cause)

class ScalaLineNumberReader(in: Reader) extends LineNumberReader(in) {
  def inputLine() = readLine() match {
    case null => None
    case line => Some(line)
  }
}

object ScalaLineNumberReader {
  def apply(file: File) = try {
    new ScalaLineNumberReader(new FileReader(file))
  } catch {
    case ex: IOException => throw new ScalaIOException(ex)
  }
}

class FilePrinter(val file: File) {

  def print() = {
    val reader: ScalaLineNumberReader = ScalaLineNumberReader(file)
    try {
      loop(reader)
    } finally {
      if (reader != null)
        reader.close
    }
  }

  private def loop(reader: ScalaLineNumberReader) {
    reader.inputLine() match {
      case Some(line) => {
        format("%3d: %s\n", reader.getLineNumber, line)
        loop(reader)
      }
      case None =>
    }
  }
}
