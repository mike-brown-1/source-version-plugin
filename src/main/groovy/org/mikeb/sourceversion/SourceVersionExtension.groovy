package org.mikeb.sourceversion

import org.gradle.api.file.RegularFileProperty

abstract class SourceVersionExtension {
    abstract RegularFileProperty getSourceFile()
//    String sourceFile = ''
    String variableName = 'version'

}
