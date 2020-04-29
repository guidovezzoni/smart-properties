[![Codacy Badge](https://api.codacy.com/project/badge/Grade/2e39b28f9cea49c28bdd3cfd8318b5c2)](https://www.codacy.com/manual/guidovezzoni/smart-properties?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=guidovezzoni/smart-properties&amp;utm_campaign=Badge_Grade)

# SmartPropertiesPlugin
SmartPropertiesPlugin allow handling build parameters in a simpler way, removing some boiler plate code in gradle files.
It allows the project to:
* add a string entry in `BuildConfig.java`
* add a resource string to `gradleResValues.xml`
* override these values with an environment variables
* have variant specific set of values
   
# Usage Example
Here is a sample file `smart.properties` in the root folder of our project:
```groovy
property01.BuildConfig=prop01
property02.Resources=prop02
property03.Resources.BuildConfig=prop03
```
The `smart.property` file has the same structure as any `*.property` file: 
typically it's composed by pair `key=value` or a `#commented out line` or an empty line.
Keys have the usual *camelCase* naming convention followed by some extra tokens:
* the `.BuildConfig` token will generate a BuildConfig entry
* the `.Resources` token will generate a string resource entry

Given the file above, the plugin modifies the `BuildConfig.java` file adding these lines:
```java
public final class BuildConfig {
  //[...]
  public static final String PROPERTY_01 = "prop01";
  public static final String PROPERTY_03 = "prop03";
  //[...]
}
``` 
Please note the properties (key without any token) will be renamed to accommodate the BuildConfig.java naming convention.
This can be avoided using the `dontRenameProperty = true` setting.

`BuildConfig.java` file can usually be found at the following location - although depending on the setup this might be different.
```
<root project folder>/<android app module>/build/generated/source/buildConfig/<variant>/<build type>/<package folders>/BuildConfig.java
```

Also, the plugin makes available these resource strings:
```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!-- [...] -->
    <string name="property_02" translatable="false">"prop02"</string>
    <string name="property_03" translatable="false">"prop03"</string>
    <!-- [...] -->
</resources>
``` 
Please note the properties (key without any token) will be renamed to accommodate XML resources naming convention.
This can be avoided using the `dontRenameProperty = true` setting.

`gradleResValues.xml` file can usually be found at the following location - although depending on the setup this might be different.
```
<root project folder>/<android app module>/build/generated/res/resValues/<variant>/<build type>/values/gradleResValues.xml
```


Additionally, any of the above property values can be easily overridden by defining two types of environment variables:
*  (propertyName)=(new value) -  this can simply be defined on any machine 
*  (prefix_propertyName)=(new value) - same as above, the prefix is a general parameter, this is typically used on CI integration

Setting up appropriately `prefix`, the property can be overridden on a local machines or in a CI context - or both - in a flexible way.

Please note: the prefixed variable always has a higher priority.

So if we define a variable:
```
$ export property03=newvalue
```
the `newvalue` value will replace `prop03` in both BuildConfig.java and XML resources.

If we define:
```
$ export property03=newvalue
$ export prefix_property03=latestvalue
```
Then `latestvalue` will be the value used instead.

Please note the env var prefix needs to be set using the option `ciEnvironmentPrefix=prefix_`

## Setup
Add the plugin to your root project using the new plugin DSL:
```groovy
plugins {
  id "com.guidovezzoni.smartproperties" version "<latest version>"
}
```
or the legacy plugin application:
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

**PLEASE NOTE**: This plugin is meant to be used in a gradle Android sub-project, it hasn't been tested in a different configuration.

## Configuration

Here are the available settings:

```groovy
smartPropertiesPlugin {
    defaultConfig {
        sourceFile = file("$rootDir/smart.properties")
        ciEnvironmentPrefix = "build_"
        dontRenameProperty = true
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

Available settings:

| Name | Description | Default Value | Notes |
|:-----|:------------|:--------------|:------|
| sourceFile     | java file pointing at the location of the `*.properties` file       | `file("$rootDir/smart.properties")` | |
| ciEnvironmentPrefix | prefix used to check env variable values | `""` (empty string) | |
| dontRenameProperty | if `true` prevent the property name to be adapted to the host file naming convention. | false | This can be helpful in case of issues or conflicts with resources naming | 

## Known Issues
1.  Generated resValue resources do not seem to be identified correctly by AndroidStudio IDE, however they are built correctly by both gradle and AndroidStudio

# Enhancement List

## Major features
*  ~~environment variables~~
*  support gradle ext - investigation in progress
*  ~~support flavours~~
*  fabric https://github.com/plastiv/CrashlyticsDemo/

## Minor features/tech improvements
*  ~~logger~~
*  ~~unit test https://guides.gradle.org/testing-gradle-plugins/~~
*  ~~uppercase/underscores for buildconfig~~

# Version History

Latest version: 0.4.0-beta

| Version     | Date       | Issues        | Notes                                      |
|:------------|:-----------|:--------------|:-------------------------------------------|
| 0.4.0_beta  | 16/04/2020 |               | flavors support, logger, unit test         |
| 0.3.0_beta  | 28/03/2020 |               | env var support, Project renamed           |
| 0.2.0_beta  | 15/03/2020 |               | First beta release                         |

# Licence
```plaintext
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
