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

## License

Copyright © 2015 Daniel W. H. James.

This software is distributed under the [Apache License, Version 2.0](LICENSE).
