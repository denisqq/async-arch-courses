plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "billing-system"
include("billing-system-modules")
include("billing-system-modules:billing-system-application")
//findProject(":service-template-modules:service-template-application")?.name = "service-template-application"
