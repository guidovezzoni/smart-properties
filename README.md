# SmartPropertiesPlugin
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
Given this sample file `smart.properties` in the root folder of the project:
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

Additionally, any of the above properties can be easily overridden by defining two types of environment variables:
* [propertyName]=[new value] -  this can simply be defined on any machine 
* [prefix_propertyName]=[new value] - same as above, the prefix is a general parameter, this is typically used on CI integration

Setting up appropriately `prefix`, the property can be overridden on local machines or CI.

Please note: the prefixed variable always has a higher priority.


# Usage

## Setup
Add the plugin to your root project using the new plugin DSL:
```groovy
plugins {
  id "com.guidovezzoni.smartproperties" version "0.2.0-beta"
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
    classpath "gradle.plugin.com.guidovezzoni.smartproperties:smart-properties:0.2.0-beta"
  }
}
```
Load the plugin in your `app` subproject and configure it
```groovy
apply plugin: 'com.guidovezzoni.smartproperties'
```

**PLEASE NOTE**: Android flavours are not supported yet

## Configuration

Here are the available settings:

```groovy
    smartPropertiesPlugin {
        sourceFile = file("$rootDir/external.properties")
        ciEnvironmentPrefix = "build_"
    }
```


# Enhancement List

## Major features
* ~~environment variables~~
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
| 0.2.0_beta  | 15/03/2020 |               | First beta release                         |
| 0.2.1_beta  | 17/03/2020 |               | env var support                            |


# Licence
```
   Copyright 2020 Guido Vezzoni

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
