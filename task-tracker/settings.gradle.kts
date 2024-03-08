plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "task-tracker"
include("task-tracker-modules")
include("task-tracker-modules:task-tracker-application")
//findProject(":service-template-modules:service-template-application")?.name = "service-template-application"
