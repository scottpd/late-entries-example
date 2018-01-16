package controllers

import play.api.data.FormError


object LateEntryForm {
  import play.api.data.Forms._
  import models.{Gender, Race}
  import play.api.data.Form
  import play.api.data.format.Formatter
  import play.api.data.format.Formats._
  implicit def genderFormat: Formatter[Gender.Value] = new Formatter[Gender.Value] {

    override def bind(key: String, data: Map[String, String]) =
      data.get(key)
        .map(Gender.withName(_))
        .toRight(Seq(FormError(key, "error.required", Nil)))

    override def unbind(key: String, value: Gender.Value) =
      Map(key -> value.toString)
  }
  implicit def raceFormat: Formatter[Race.Value] = new Formatter[Race.Value] {

    override def bind(key: String, data: Map[String, String]) =
      data.get(key)
        .map(Race.withName(_))
        .toRight(Seq(FormError(key, "error.required", Nil)))

    override def unbind(key: String, value: Race.Value) =
      Map(key -> value.toString)
  }

  /**
    * A form processing DTO that maps to the form below.
    *
    */
  case class Data(fname: String, lname: String, gender: Gender.Value, race: Race.Value, cell: String, email: String, vitality: String, idnum: Option[String], timingTag: String)

  /**
    * The form definition for the "create a late entry" form.
    */
  val form = Form(
    mapping(
      "fname" -> nonEmptyText,
      "lname" -> nonEmptyText,
      "gender" -> of[Gender.Value],
      "race" -> of[Race.Value],
      "cell" -> nonEmptyText(10, 15),
      "email" -> email,
      "vitality" -> text,
      "idnum" -> optional(text),
      "timingTag" -> nonEmptyText
    )(Data.apply _)(Data.unapply _)
  )
}
