# Source Version Plugin

This project provides a [Gradle](https://gradle.org/) plugin that provides a single task named sourceVersion.  This task will update the value of a "property" in a source file with the Gradle project
version number.

**NOTE** This plugin has not be published on the [Gradle Plugins](https://plugins.gradle.org/) site yet.

## Add the Plugin to your build.gradle

Add the following within your `plugins {}` block
in your build script.

`id ('org.mikeb.source-version') version '1.0.1-SNAPSHOT'`

## Configure the Plugin

You need to tell the plugin which file contains
the property that should have the project version as well as the name of the property.

The property type is assumed to be a String.

Sample configuration block:

```groovy
sourceVersion {
    sourceFile = file("src/main/groovy/org/example/Config.groovy")
    variableName = 'appVersion'
}
```

Sample source file:

```groovy
package org.example

class Config {
    String appVersion = "0.1.1"
    int num = 20
}
```

Given the above configuration block, the plugin will replace the value of the appVersion property.  If there is no change, a warning will 
be issued in case the developer forgot to update the project version.

When trying to match the pattern of the source code, spaces around the equals sign are ignored.
