plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "service-template"
include("service-template-modules")
include("service-template-modules:service-template-application")
//findProject(":service-template-modules:service-template-application")?.name = "service-template-application"
