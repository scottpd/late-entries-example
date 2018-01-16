package models



object Gender extends Enumeration {
  val Male, Female = Value
  def get(sex: Value): String = Value.toString
}

object Race extends Enumeration {
  val `10km`, `5km` = Value
  def get(distance: Value): String = Value.toString
}

/**
  * Presentation object used for displaying data in a template.
  *
  * Note that it's a good practice to keep the presentation DTO,
  * which are used for reads, distinct from the form processing DTO,
  * which are used for writes.
  */
case class LateEntry(
                      fname: String,
                      lname: String,
                      gender: Gender.Value,
                      race: Race.Value,
                      cell: String,
                      email: String,
                      vitality: String,
                      idnum: Option[String],
                      timingTag: String
                    )

