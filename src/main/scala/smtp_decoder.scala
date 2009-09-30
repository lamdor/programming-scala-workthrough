package com.programmingscala.smtpd

import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.{IdleStatus, IoSession}
import org.apache.mina.filter.codec._
import net.lag.naggati._
import net.lag.naggati.Steps._

case class Request(command: String, data: String)
case class Response(data: IoBuffer)

object Codec {
  val encoder = new ProtocolEncoder {
    def encode(session: IoSession, message: AnyRef, out: ProtocolEncoderOutput) = {
      val buffer = message.asInstanceOf[Response].data
      out.write(buffer)
    }

    def dispose(session: IoSession) = { } // noop
  }

  val decoder = new Decoder(readLine(true, "ISO-8859-1") { line =>
    line.split(' ').first match {
      case "HELO" => state.out.write(Request("HELO", line.split(' ')(1))); End
      case "QUIT" => state.out.write(Request("QUIT", null)); End
      case _ => throw new ProtocolError("Malformed request line: " + line)
    }
                                                        })
}

import net.lag.naggati.IoHandlerActorAdapter
import org.apache.mina.filter.codec.ProtocolCodecFilter
import org.apache.mina.transport.socket.SocketAcceptor
import org.apache.mina.transport.socket.nio.{NioProcessor, NioSocketAcceptor}
import java.net.InetSocketAddress
import java.util.concurrent.{Executors, ExecutorService}
import scala.actors.Actor._

object Main {
  val listenAddress = "0.0.0.0"
  val listenPort = 2525

  def setMaxThreads {
    val maxThreads = Runtime.getRuntime.availableProcessors * 2
    System.setProperty("actors.maxPoolSize", maxThreads.toString)
  }

  def initializeAcceptor {
    val acceptorExecutor = Executors.newCachedThreadPool
    val acceptor = new NioSocketAcceptor(acceptorExecutor,
                                         new NioProcessor(acceptorExecutor))
    acceptor.setBacklog(1000)
    acceptor.setReuseAddress(true)
    acceptor.getSessionConfig.setTcpNoDelay(true)
    acceptor.getFilterChain.addLast("codec",
                                    new ProtocolCodecFilter(Codec.encoder, Codec.decoder))
    acceptor.setHandler(new IoHandlerActorAdapter(session => new SmtpHandler(session)))
    acceptor.bind(new InetSocketAddress(listenAddress, listenPort))
  }

  def main(args: Array[String]) {
    setMaxThreads
    initializeAcceptor
    println("smtpd: up and running on " + listenAddress + ":" + listenPort)
  }
}

import net.lag.naggati.{IoHandlerActorAdapter, MinaMessage, ProtocolError}
import org.apache.mina.core.buffer.IoBuffer
import org.apache.mina.core.session.{IdleStatus, IoSession}
import java.io.IOException
import scala.actors.Actor
import scala.actors.Actor._
import scala.collection.{immutable, mutable}

class SmtpHandler(val session: IoSession) extends Actor {
  start

  def act {
    loop { react {
      case MinaMessage.MessageReceived(msg) => handle(msg.asInstanceOf[Request])
      case MinaMessage.SessionClosed => exit()
      case MinaMessage.SessionIdle(status) => session.close
      case MinaMessage.SessionOpened => reply("220 localhost Tapir SMPTd 0.1\n")
      case MinaMessage.ExceptionCaught(cause) => {
        cause.getCause match {
          case e: ProtocolError => reply("502 Error: " + e.getMessage + "\n")
          case i: IOException => reply("502 Error: " + i.getMessage + "\n")
          case _ => reply("502 error unknown\n")
        }
        session close
      }
    } }
  }

  def handle(request: Request) = request.command match {
    case "HELO" => reply("250 Hi there " + request.data + "\n")
    case "QUIT" => {
      reply("221 Peace out girl scout\n")
      session close
    }
  }

  def reply(s: String) =
    session.write(new Response(IoBuffer.wrap(s.getBytes)))

}
