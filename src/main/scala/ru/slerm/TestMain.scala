package ru.slerm

import scala.util.{Failure, Success, Try}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._

import io.scalaland.chimney.dsl._

object TestMain extends App {

/*
* 1) Using +
* 2) Jsoniter vs Circe +
* 3) Chimney +
* 4) Scopt
*
* */
  println("Hello, world from package!")

//  Parsing JSON
  object in {
    case class Device(id: Int, model: String)
    case class User(name: Option[String], devices: List[Device])
  }

  object out {
    case class Device(id: Int, model: String)
    case class User(fullName: Option[String], devices: List[Device], date: String)
  }


//  Jsoniter
  import com.github.plokhotnyuk.jsoniter_scala.macros._
  import com.github.plokhotnyuk.jsoniter_scala.core._

  import in._

  implicit val userCodec: JsonValueCodec[User]= JsonCodecMaker.make

  val validValue: String = """{"name":"John","devices":[{"id":1,"model":"HTC One X"}]}"""
  val invalidValue2: String = """{"devices":[{"id":1,"model":"HTC One X"}]}"""


  Try(readFromString[User](invalidValue2))
    .map {e: User => e}
    .recover {
    case e: JsonReaderException => e.getMessage
  }

  Try(readFromString[User](invalidValue2)) match {
    case Failure(exception) => ???
    case Success(value) => ???
  }

  Try(readFromString[User](invalidValue2)).toOption.foreach(println)

  Try(readFromString[User](invalidValue2)).toEither match {
    case Left(exception) => ???
    case Right(value) => ???
  }


  // Circe (https://circe.github.io/circe/)
  val json: String = writeToString(User(Some("John"), List(Device(2, "iPhone X"))))

  decode[User](validValue) match {
    case Left(value) => ???
    case Right(value) => ???
  }


//  Chimney (https://scalalandio.github.io/chimney/)
val source: in.User = in.User(Some("John"), List(in.Device(2, "iPhone X")))

  val target: out.User = out.User(
    fullName = Some("John"),
    devices = List(out.Device(2, "iPhone X")),
    date = "2020-01-01"
    //   + 100 fields
  )

  val targetWithChimney: out.User = source.into[out.User]
    .withFieldComputed(_.date, _ => "2020-01-01")
    .transform

//  Play Json (https://www.playframework.com/documentation/2.8.x/ScalaJson)

  // Java MapStruct


  //  1) Change field name
  //  2) Change field type
  //  3) Add cons field
  //  4) Remove field
  //  5) Change field order
  //  Java => MapsTruth




//  Scopt (https://github.com/scopt/scopt)




}
