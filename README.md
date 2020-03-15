# HyperProperties Plugin
Manage build parameters from a *.property file or an environment variables

The purpose of this library is to handle build parameters via a specific formatting of a `.properties` file.
Features:
* the entries defined in the `.properties` file can be overridden by an environment variable, for example for CI integration
* the entries can automatically create entries in:
  * BUILDCONFIG.java
  * resources
  * gradle rootProject ext property
  * gradle sub project ext property
  
  
# Example
Given this sample file `hyper.properties` in the root folder of the project:
```groovy
property01.BuildConfig=prop01
property02.Resources=prop02
property03.ProjectExt.BuildConfig=prop03
property04.RootProjExt=prop04
```
Will:

1 **BUILDCONFIG**: generate this code in the sub-project BuildConfig
```java
[...]
public final class BuildConfig {
[...]
  public static final String property01 = "prop01";
  public static final String property03 = "prop03";
}
``` 

2  **Shared resources**: make available these shared resources
```
@string/property02
``` 

3 **Project ext**: make available an ext property in the gradle sub project   
```groovy
    if (project.ext.has("property03")) {
        println "property03 found in sub project: $property03"
    }
```

4 **RootProject ext**: make available an ext property in the gradle root project   
   ```groovy
       if (project.rootProject.ext.has("property04")) {
           println "property04 found in root project: $property04"
       }
   ```


# Usage
Add the plugin to your root project using the new plugin DSL:
```groovy
plugins {
  id "com.guidovezzoni.hyperprop" version "0.2.0-beta"
}
```
or the leagacy plugin application:
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.guidovezzoni.hyperprop:hyper-properties:0.2.0-beta"
  }
}
```
Load the plugin in your `app` subproject and configure it
```groovy
apply plugin: 'com.guidovezzoni.hyperprop'


    hyperPropertiesPlugin {
        sourceFile = file("$rootDir/external.properties")
    }
```

**PLEASE NOTE**: Android flavours are not supported yet



# Future Feature List

## Major features
* environment variables
* ~~support gradle ext~~
* support flavours
* fabric https://github.com/plastiv/CrashlyticsDemo/

## Minor features/tech improvements
* logger
* unit test https://guides.gradle.org/testing-gradle-plugins/
* uppercase/underscores for buildconfig

## History

| Version     | Date       | Issues        | Notes                                      |
|:------------|:-----------|:--------------|:-------------------------------------------|
| 0.2.0_bets  | 15/03/2020 |               | First beta release                        |


# Licence
```
   Copyright 2019 Guido Vezzoni

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
