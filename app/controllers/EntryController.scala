package controllers

import javax.inject.Inject

import models.{LateEntry, Gender, Race}
import play.api.data._
import play.api.mvc._

import scala.collection.mutable.ArrayBuffer

/**
  *
 * Instead of MessagesAbstractController, you can use the I18nSupport trait,
 * which provides implicits that create a Messages instances from
 * a request using implicit conversion.
 */
class EntryController @Inject()(cc: MessagesControllerComponents) extends MessagesAbstractController(cc) {
  import LateEntryForm._

  private val entries = ArrayBuffer(

    LateEntry("Paul", "Scott", Gender.Male, Race.`10km`, "0848346899", "scottdbsd@gmail.com", vitality = "Yes", Some("7311115303087"), "541"),

    LateEntry("John", "Collins", Gender.Male, Race.`5km`, "0848002322", "jc@gmail.com", vitality = "No", None, "333")

  )

  private val postUrl = routes.EntryController.createEntry()

  def index = Action {
    Ok(views.html.index())
  }

  def listEntries = Action { implicit request: MessagesRequest[AnyContent] =>
    // Pass an unpopulated form to the template
    Ok(views.html.listEntries(entries, form, postUrl))
  }

  // This will be the action that handles our form post
  def createEntry = Action { implicit request: MessagesRequest[AnyContent] =>

    val errorFunction = { formWithErrors: Form[Data] =>

      // This is the bad case, where the form had validation errors.
      BadRequest(views.html.listEntries(entries, formWithErrors, postUrl))
    }

    val successFunction = { data: Data =>
      // This is the good case, where the form was successfully parsed as a Data.
      val entry = LateEntry(
        fname = data.fname,
        lname = data.lname,
        gender = Gender.withName(data.gender.toString),
        race = Race.withName(data.race.toString),
        cell = data.cell,
        email = data.email,
        idnum = data.idnum,
        vitality = data.vitality,
        timingTag = data.timingTag
      )
      entries.append(entry)
      Redirect(routes.EntryController.listEntries()).flashing("info" -> "Entry added!")
    }

    val formValidationResult = form.bindFromRequest
    formValidationResult.fold(errorFunction, successFunction)
  }

}