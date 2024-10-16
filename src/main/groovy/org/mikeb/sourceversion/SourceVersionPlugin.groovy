package org.mikeb.sourceversion

import org.gradle.api.Plugin
import org.gradle.api.Project

class SourceVersionPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('sourceVersion', SourceVersionExtension)

        project.tasks.register('sourceVersion', SourceVersionTask) {
            sourceFile = project.sourceVersion.sourceFile
            variableName = project.sourceVersion.variableName
            description = 'Maintain build version number in source file.'
        }
    }
}
