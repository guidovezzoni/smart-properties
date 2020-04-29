[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2e39b28f9cea49c28bdd3cfd8318b5c2)](https://www.codacy.com/manual/guidovezzoni/smart-properties?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guidovezzoni/smart-properties&amp;utm_campaign=Badge_Grade)

# SmartPropertiesPlugin
Manage build parameters from a *.property file or an environment variables

The purpose of this library is to handle build parameters via a specific formatting of a `.properties` file.
Features:
*  the entries defined in the `.properties` file can be overridden by an environment variable, for example for CI integration
*  the entries can automatically create entries in:
   *  BUILDCONFIG.java
   *  resources
  
# Example
Given this sample file `smart.properties` in the root folder of the project:
```groovy
property01.BuildConfig=prop01
property02.Resources=prop02
property03.Resources.BuildConfig=prop03
```
The plugin will perform the following actions:

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
<string name="property02" translatable="false">"prop02"</string>
<string name="property03" translatable="false">"prop03"</string>
``` 

Additionally, any of the above properties can be easily overridden by defining two types of environment variables:
*  (propertyName)=(new value) -  this can simply be defined on any machine 
*  (prefix_propertyName)=(new value) - same as above, the prefix is a general parameter, this is typically used on CI integration

Setting up appropriately `prefix`, the property can be overridden on local machines or CI.

Please note: the prefixed variable always has a higher priority.

# Usage

## Setup
Add the plugin to your root project using the new plugin DSL:
```groovy
plugins {
  id "com.guidovezzoni.smartproperties" version "<latest version>"
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
    classpath "gradle.plugin.com.guidovezzoni.smartproperties:smart-properties:<latest version>"
  }
}
```
Load the plugin in your `app` subproject and configure it
```groovy
apply plugin: 'com.guidovezzoni.smartproperties'
```

**PLEASE NOTE**: This plugin is meant to be used in a gradle Android sub-project, it hasn't been tested on the root project.

## Configuration

Here are the available settings:

```groovy
smartPropertiesPlugin {
    defaultConfig {
        sourceFile = file("$rootDir/smart.properties")
        ciEnvironmentPrefix = "build_"
    }

    productFlavors {
        alpha {
            sourceFile = file("$rootDir/smartprop-alpha.properties")
        }
        beta {
            sourceFile = file("$rootDir/smartprop-beta.properties")
        }
        prod {
            ciEnvironmentPrefix = "prod_"
        }
    }
}
```

## Known Issues
1.  generated resValue resources do not seem to be identified correctly by AndroidStudio IDE, however they are built correctly by both gradle and AndroidStudio

# Enhancement List

## Major features
*  ~~environment variables~~
*  support gradle ext - investigation in progress
*  ~~support flavours~~
*  fabric https://github.com/plastiv/CrashlyticsDemo/

## Minor features/tech improvements
*  ~~logger~~
*  ~~unit test https://guides.gradle.org/testing-gradle-plugins/~~
*   uppercase/underscores for buildconfig

# Version History

Latest version: 0.5.0-beta

| Version     | Date       | Issues        | Notes                                      |
|:------------|:-----------|:--------------|:-------------------------------------------|
| 0.5.0-beta  | 29/04/2020 | 003 (additional unit tests), 004 (keep property syntax) | |
| 0.4.0_beta  | 16/04/2020 | flavors support, logger, unit test | |
| 0.3.0_beta  | 28/03/2020 |  env var support, Project renamed | |
| 0.2.0_beta  | 15/03/2020 | | First beta release |

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
