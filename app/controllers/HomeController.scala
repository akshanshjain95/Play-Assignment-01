package controllers

import javax.inject._

import akka.util.ByteString
import play.api._
import play.api.http.HttpEntity
import play.api.libs.iteratee.{Enumeratee, Enumerator}
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController extends Controller{

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok("Hi! Enter username and password in the above URL separated with a '/'")
  }

  def setSessionAction(name:String, password: String) = Action {implicit request: Request[AnyContent] =>

    Redirect(routes.HomeController.getSessionAction()).withSession("name" -> s"$name", "password" -> s"$password")

  }

  def getSessionAction = Action{ implicit request: Request[AnyContent] =>
    val name = request.session.get("name")
    val password = request.session.get("password")
    val (keyMessage, message) = if(name.exists(_ == "Akshansh") && password.exists(_ == "1234")) ("Success",s"Welcome, Akshansh!") else ("Error", "I don't recognize you!")
    Redirect(routes.HomeController.displayMessageAction()).flashing(keyMessage -> message)
  }

  def displayMessageAction = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(request.flash))
  }

}
