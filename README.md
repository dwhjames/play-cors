[![Build Status](https://travis-ci.org/dwhjames/play-cors.svg?branch=master)](https://travis-ci.org/dwhjames/play-cors)

## Cross-Origin Resource Sharing (CORS) support for Play

A configurable filter and action builder that implement Cross-Origin Resource Sharing (CORS).

See the full [CORS specification](http://www.w3.org/TR/cors/).

See the [Scaladoc documentation](https://dwhjames.github.io/play-cors/index.html).

## Setup

play-cors is built for Play 2.3.x, and both Scala 2.10.x and 2.11.x. Binary releases are available from [Bintray]('https://bintray.com/dwhjames/maven/play-cors/view?source=watch').

<a href='https://bintray.com/dwhjames/maven/play-cors/view?source=watch' alt='Get automatic notifications about new "play-cors" versions'><img src='https://www.bintray.com/docs/images/bintray_badge_color.png'></a>

If you are using SBT, simply add the following to your `build.sbt` file:

```
resolvers += Resolver.bintrayRepo("dwhjames", "maven")

libraryDependencies += "com.github.dwhjames" %% "play-cors" % "0.1.0"
```

## Usage

The most basic use case is with the [CORSFilter](https://dwhjames.github.io/play-cors/api/current/index.html#play.filters.cors.CORSFilter$) object. It is configured from the global application configuration.

If no configuration is explicitly provided, then the filter will behave in the following way:
- all request paths are filtered
- all request origins are allowed (HTTP Origin Header)
- all HTTP methods are allowed
- all request headers that are requsted are allowed
- no response headers are exposed
- credentials support is enabled
- a max-age of 1 hour is set for the [preflight result cache](http://www.w3.org/TR/cors/#preflight-result-cache)

Any aspect of this default behavior can be changed by extending the application configuration according to the following configuration template:

```
cors {
    path.prefixes = ["/myresource", ...]  # If left undefined, all paths are filtered
    allowed {
        origins = ["http://...", ...]  # If left undefined, all origins are allowed
        http {
            methods = ["PATCH", ...]  # If left undefined, all methods are allowed
            headers = ["Custom-Header", ...]  # If left undefined, all headers are allowed
        }
    }
    exposed.headers = [...]  # empty by default
    supports.credentials = true  # true by default
    preflight.maxage = 3600  # 3600 by default
}
```

---

Alternatively, rather than use the [CORSFilter](https://dwhjames.github.io/play-cors/api/current/index.html#play.filters.cors.CORSFilter$) object and the global application configuration, one can use the [CORSFilter](https://dwhjames.github.io/play-cors/api/current/index.html#play.filters.cors.CORSFilter) class and instantiate it with a type-safe configuration built with the [CORSConfig](https://dwhjames.github.io/play-cors/api/current/index.html#play.filters.cors.CORSConfig) case class.

---

One has fairly coarse-grained control over the filter by restricting the request paths that the filter is applied to. If more fine-grained control is required over how CORS is implemented in your application, then the [CORSActionBuilder](https://dwhjames.github.io/play-cors/api/current/index.html#play.filters.cors.CORSActionBuilder$) object should be used instead.

There are three ways of constructing an action in a controller:

```scala
// an action that uses the application configuration
CORSActionBuilder { Ok }

// an action that uses a subtree of the application configuration
CORSActionBuilder("my-conf-path") { Ok }

// an action that uses a locally defined configuration
val corsConfig: CORSConfig = ...
CORSActionBuilder(conf) { Ok }
```

Note that you *must* have the appropriate `OPTIONS` routes defined in the application routes for [CORS preflight requests](http://www.w3.org/TR/cors/#resource-preflight-requests) to succeed.


## License

Copyright © 2015 Daniel W. H. James.

This software is distributed under the [Apache License, Version 2.0](LICENSE).
