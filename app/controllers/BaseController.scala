package controllers


import play.api.mvc._

import connectors.{GameFormatter, GameRequestFormatter, SalvoStatusFormatter}



trait BaseController extends Controller with GameFormatter with GameRequestFormatter with SalvoStatusFormatter{


}
