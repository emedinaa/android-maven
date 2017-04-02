# android-maven
Ejemplo de como crear una dependencia de un módulo Android en un repositorio local  usando el plugin de Maven para Android.

Lo primero , es crear un proyecto Android y agregar un módulo(librería) llamado "module-data"

- app
- module-data

Luego , agregamos el plugin de Maven en el "build.gradle" de nuestro módulo . 

```
apply plugin: 'com.android.library'
apply plugin: 'maven'
```
* No olvidar sincronizar 

Definimos el grupo , nombre y versión de nuestra librería
```
group = "com.emedinaa"
archivesBaseName = "module-data"
version = "1.0.1"  //customize this for new release
```
El build.gradle del módulo quedaría de la siguiente manera
```
apply plugin: 'com.android.library'
apply plugin: 'maven'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"

    defaultConfig {
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"

    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

group = "com.emedinaa"
archivesBaseName = "module-data"
version = "1.0.1"  //customize this for new release

uploadArchives {
    repositories {
        mavenDeployer {
            repository(url: 'file://' + new File(System.getProperty('user.home'),'LocalRepository').absolutePath) { }
            //repository(url: "file://localhost/tmp/myRepo/")
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.1.0'
    testCompile 'junit:junit:4.12'
}

```

Para poder deployar el módulo a nuestro repositorio local  ejecutamos el comando 

```
./gradlew uploadArchives
```
Si salio todo bien, en el siguiente directorio tendremos plublicada nuestra librería.

```
/home/emedinaa/LocalRepository

/home/emedinaa/LocalRepository/com/emedinaa/module-data/1.0.0
```
```
- LocalRepository
  - com
    - emedinaa
      - module-data
        - 1.0.0
          - module-data-1.0.0.aar
```

Finalmente, para poder agregar la dependencia al proyecto realizamos lo siguiente :

- Agregar el repositorio local

```
allprojects {
    repositories {
        jcenter()
        mavenCentral()
        maven {url localMavenRepository}
    }
}
```

- Agregar la dependencia 

```
  compile 'com.emedinaa:module-data:1.0.1@aar'
```

```
apply plugin: 'com.android.application'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"
    defaultConfig {
        applicationId "com.emedinaa.mavenapp"
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    compile 'com.android.support:appcompat-v7:25.1.0'
    compile 'com.emedinaa:module-data:1.0.1@aar'
    compile 'com.android.support:design:25.1.0'
    testCompile 'junit:junit:4.12'
}

```

# Referencias

http://stackoverflow.com/questions/28361416/gradle-how-to-publish-a-android-library-to-local-repository

https://www.mkyong.com/maven/how-to-install-maven-in-ubuntu/

http://stackoverflow.com/questions/40406613/gradle-dependency-from-maven-local

https://docs.gradle.org/current/userguide/maven_plugin.html

# Commands
- ./gradlew uploadArchives
- ./gradlew install

- mkdir
- nautilus ./

