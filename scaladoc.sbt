
scalacOptions in (Compile, doc) ++= Seq(
  "-sourcepath", baseDirectory.value.getAbsolutePath,
  "-doc-source-url", s"https://github.com/dwhjames/play-cors/tree/v${version.value}€{FILE_PATH}.scala"
)

apiURL := Some(url("https://dwhjames.github.io/play-cors/api/current/"))

autoAPIMappings := true

apiMappings ++= {
  val cp: Seq[Attributed[File]] = (fullClasspath in Compile).value
  def findManagedDependency(organization: String, name: String): Seq[File] = {
    for {
      entry <- cp
      module <- entry.get(moduleID.key)
      if module.organization == organization
      if module.name.startsWith(name)
      jarFile = entry.data
    } yield jarFile
  }
  findManagedDependency("com.typesafe.play", "play").map(f => f -> url("https://playframework.com/documentation/2.3.x/api/scala/")).toMap
}
