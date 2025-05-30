dependencies {
    api(project(":aoc-utils"))

    implementation(aoc2019Libs.commons.collections4)
    implementation(aoc2019Libs.commons.lang3)

    testImplementation(aoc2019Libs.equalsverifier)

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Jar>("jar") {
    manifest {
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
    }
}
