# android-maven
## Android Maven

En este ejemplo veremos  como crear una dependencia de un módulo Android en un repositorio local  usando el plugin de Maven para Android.

Lo primero , es crear un proyecto Android y agregar un para de módulos(librería) llamados "module-data" y "core"

- app
- module-data
- core

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
      - core
        - 1.0.0
          - core1.0.0.aar
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
** En este ejemplo , la app y los módulos están en el mismo proyecto por un tema de prácticidad pero los módulos pueden estar en un proyecto aparte.

# Commandos

Si estas en Linux o OSx puedes ejecutar directamente el commando.
```
 ./gradlew uploadArchives
```

* Tip : Si tu proyecto consta de varios módulos, puedes crear un pequeño script para que ejecute el comando en cada módulo. Por ejemplo "myScript.sh"

```
./gradlew :module-data:uploadArchives

./gradlew :core:uploadArchives

```

# Problemas comunes

Se pueden presentar algunos problemas cuando dentro de un módulo existen dependecias de librerías externas como Google Play services, Picasso o Retrofit , etc, etc. Esto tambien puede suceder si tu proyecto comience a crecer y supere el límite en la arquitecture de compilación , para esto puedes activar el multidex en tu proyecto.

```
apply plugin: 'com.android.library'

android {
    compileSdkVersion 25
    buildToolsVersion "25.0.2"

    defaultConfig {
        minSdkVersion 15
        targetSdkVersion 25
        versionCode 1
        versionName "1.0"
        multiDexEnabled true

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
    compile 'com.android.support:multidex:1.0.0'
    testCompile 'junit:junit:4.12'
}
```

# Updates 
- [Coming soon] Subir tu biblioteca a repositorio externo.

# Referencias

- Multidex [https://developer.android.com/studio/build/multidex.html](https://developer.android.com/studio/build/multidex.html)

- Maven Plugin [https://docs.gradle.org/current/userguide/maven_plugin.html](https://docs.gradle.org/current/userguide/maven_plugin.html)

# Issues

- Cualquier issue, duda o consulta lo puede dejar en este link [https://github.com/emedinaa/android-maven/issues](https://github.com/emedinaa/android-maven/issues) y lo atenderé a la brevedad.

