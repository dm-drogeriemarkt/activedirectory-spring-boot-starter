# Publishing artifacts on maven central

* Preparation guide: http://central.sonatype.org/pages/ossrh-guide.html
* Following process is used to release with travis: http://www.debonair.io/post/maven-cd/

## Release process

* Prepare release version in `pom.xml` with `x.y.z-SNAPSHOT`
* Generate (`mvn package`)and push updated docs
* Tag with this Version (without `-SNAPSHOT` Postfix).
