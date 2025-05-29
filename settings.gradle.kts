plugins {
    id("com.gradle.develocity") version("4.0.2")
}

rootProject.name = "advent-of-code-2019"

include("aoc2019")
include("aoc2019:aoc2019-java")
include("aoc2019:aoc2019-kotlin")

include("utils")
include("utils:utils-java")
include("utils:utils-kotlin")
