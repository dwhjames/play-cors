/*
 *  Copyright 2015 Daniel W. H. James
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package play.filters.cors

import scala.concurrent.Future

import play.api.{ Configuration, Play }
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{ ActionBuilder, Request, Result }

/** An [[play.api.mvc.ActionBuilder ActionBuilder]] that implements Cross-Origin Resource Sharing (CORS)
  *
  * @see [[CORSFilter]]
  * @see [[http://www.w3.org/TR/cors/ CORS specification]]
  */
trait CORSActionBuilder extends ActionBuilder[Request] with AbstractCORSPolicy {

  override protected val logger = Play.logger

  override def invokeBlock[A](request: Request[A], block: Request[A] => Future[Result]): Future[Result] = {
    filterRequest(() => block(request), request)
  }
}

/** An [[play.api.mvc.ActionBuilder ActionBuilder]] that implements Cross-Origin Resource Sharing (CORS)
  *
  * It can be configured to...
  *
  *  - allow only requests with origins from a whitelist (by default all origins are allowed)
  *  - allow only HTTP methods from a whitelist for preflight requests (by default all methods are allowed)
  *  - allow only HTTP headers from a whitelist for preflight requests (by default all methods are allowed)
  *  - set custom HTTP headers to be exposed in the response (by default no headers are exposed)
  *  - disable/enable support for credentials (by default credentials support is enabled)
  *  - set how long (in seconds) the results of a preflight request can be cached in a preflight result cache (by default 3600 seconds, 1 hour)
  *
  * @example
  * {{{
  * CORSActionBuilder { Ok } // an action that uses the application configuration
  *
  * CORSActionBuilder("my-conf-path") { Ok } // an action that uses a subtree of the application configuration
  *
  * val corsConfig: CORSConfig = ...
  * CORSActionBuilder(conf) { Ok } // an action that uses a locally defined configuration
  * }}}
  *
  * @see [[CORSFilter]]
  * @see [[http://www.w3.org/TR/cors/ CORS specification]]
  */
object CORSActionBuilder extends CORSActionBuilder {

  private def globalConf =
    Play.maybeApplication.map(_.configuration).getOrElse(Configuration.empty)

  override protected def corsConfig =
    CORSConfig.fromConfiguration(globalConf)

  /** Construct an action builder that uses a subtree of the application configuration.
    *
    * @param  configPath  The path to the subtree of the application configuration.
    */
  def apply(configPath: String): CORSActionBuilder = new CORSActionBuilder {
    override protected def corsConfig =
      CORSConfig.fromConfiguration(
        Play.maybeApplication.flatMap(
          _.configuration.getConfig(configPath)).getOrElse(Configuration.empty))
  }

  /** Construct an action builder that uses locally defined configuration.
    *
    * @param  config  The local configuration to use in place of the global configuration.
    * @see [[CORSConfig]]
    */
  def apply(config: CORSConfig): CORSActionBuilder = new CORSActionBuilder {
    override protected val corsConfig = config
  }
}
