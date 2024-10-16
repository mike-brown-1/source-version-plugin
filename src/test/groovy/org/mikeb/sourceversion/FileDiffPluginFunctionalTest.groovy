package org.mikeb.sourceversion

import org.gradle.testkit.runner.GradleRunner
import spock.lang.Specification
import spock.lang.TempDir

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS

class FileDiffPluginFunctionalTest extends Specification {
    @TempDir
    File testProjectDir
    File buildFile

    def setup() {
        buildFile = new File(testProjectDir, 'build.gradle')
        buildFile << """
            plugins {
                id 'org.mikeb.source-version'
            }
            repositories {
                mavenLocal()
                mavenCentral()
            }
            version = '20241014'
        """
    }

    def "can  diff 2 files of same length"() {
        given:
        File testFile1 = new File(testProjectDir, 'testFile1.txt')
        testFile1.createNewFile()

        buildFile << """
            sourceVersion {
                sourceFile = file("${testFile1.getName()}")
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('sourceVersion')
                .withPluginClasspath()
                .build()

        then:
        result.task(":sourceVersion").outcome == SUCCESS
        println "output: ${result.output}"
    }

    def "can  diff 2 files of differing length"() {
        println "tmp dir: ${testProjectDir.absolutePath}"
        given:
        File testFile1 = new File(testProjectDir, 'testFile1.txt')
        testFile1 << 'Short text'

        buildFile << """
            sourceVersion {
                sourceFile = file("${testFile1.getName()}")
                variableName = 'appVersion'
            }
        """

        when:
        def result = GradleRunner.create()
                .withProjectDir(testProjectDir)
                .withArguments('sourceVersion')
                .withPluginClasspath()
                .build()

        then:
        result.task(":sourceVersion").outcome == SUCCESS
        println result.output
    }
}