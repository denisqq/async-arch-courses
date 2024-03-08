plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "auth-server"
include("auth-server-modules")
include("auth-server-modules:auth-server-application")
//findProject(":service-template-modules:service-template-application")?.name = "service-template-application"
