pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {repositories
        google()
        mavenCentral()
    }
}

rootProject.name = "CalamityCall"
include(":app")
include(":app:libs")
