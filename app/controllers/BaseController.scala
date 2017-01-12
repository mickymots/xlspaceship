package controllers


import play.api.mvc._

import connectors.{GameFormatter, GameRequestFormatter}



trait BaseController extends Controller with GameFormatter with GameRequestFormatter{


}
