package com.programmingscala.smtpd

import java.nio.ByteOrder
import net.lag.naggati._
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.filterchain.IoFilter
import org.apache.mina.core.session.{DummySession, IoSession}
import org.apache.mina.filter.codec._
import org.specs._
import scala.collection.{immutable, mutable}

object SmtpDecoderSpecification extends Specification {
  var fakeSession: IoSession = null
  var fakeDecoderOutput: ProtocolDecoderOutput = null
  var written = new mutable.ListBuffer[Request]

    def quickDecode(s: String) {
      Codec.decoder.decode(fakeSession, IoBuffer.wrap(s.getBytes), fakeDecoderOutput)
    }
  "SmtpRequestDecoder" should {

    doBefore {
      written.clear
      fakeSession = new DummySession
      fakeDecoderOutput = new ProtocolDecoderOutput {
        override def flush(nextFilter: IoFilter.NextFilter, s: IoSession) = { }
        override def write(obj: AnyRef) = written += obj.asInstanceOf[Request]
      }
    }

    "parse HELO" in {
      quickDecode("HELO client.example.org\n")
      written.size must_== 1
      written(0).command must_== "HELO"
      written(0).data must_== "client.example.org"
    }

    "parse QUIT" in {
      quickDecode("QUIT\n")
      written.size must_== 1
      written(0).command must_== "QUIT"
      written(0).data must beNull
    }
  }
}

