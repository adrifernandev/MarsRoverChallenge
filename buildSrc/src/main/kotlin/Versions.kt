import org.gradle.api.JavaVersion

object Versions {
    private const val MAJOR_VERSION = 0
    private const val MINOR_VERSION = 1
    private const val BUGFIX_VERSION = 0

    const val APPLICATION_ID = "com.adrifernandev.marsroverchallenge"
    const val COMPILE_SDK = 35
    const val MIN_SDK = 28
    const val APP_VERSION_CODE = MAJOR_VERSION * 1000 + MINOR_VERSION * 100 + BUGFIX_VERSION
    const val APP_VERSION_NAME = "$MAJOR_VERSION.$MINOR_VERSION.$BUGFIX_VERSION"
    const val JVM_TARGET = "11"
    val javaVersion = JavaVersion.VERSION_11

}