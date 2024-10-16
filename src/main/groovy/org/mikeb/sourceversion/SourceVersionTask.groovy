package org.mikeb.sourceversion

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

abstract class SourceVersionTask extends DefaultTask {
    @InputFile
    abstract RegularFileProperty getSourceFile()
    @Input
    String variableName = getVariableName()

    SourceVersionTask() {
        description = 'Maintain build version number in source file.'
    }

    @TaskAction
    def setVersion() {
        File theSourceFile = sourceFile.get().asFile
        if (!theSourceFile.exists() || !theSourceFile.isFile()) {
            throw GradleException("Source file: ${theSourceFile.name} does not exist or is not a file")
        }
        String contents = theSourceFile.text
//        println "source file: ${theSourceFile.name}"
//        println "contents:"
//        println contents
//        println "project version: ${project.version}"
//        println "project name: ${project.name}"
//        println "variable: ${variableName}"

        def pattern = ~/${variableName}\s*=\s*"([^"]*)"/
        def modifiedContent = contents.replaceAll(pattern) { match, oldValue ->
            "${variableName} = \"${project.version}\""
        }
        if (contents.equals(modifiedContent)) {
            println ""
            println "************************************************************"
            println "sourceVersion: File contents NOT CHANGED"
            println "************************************************************"
            println ""
        } else {
            theSourceFile.text = modifiedContent
        }
    }
}
