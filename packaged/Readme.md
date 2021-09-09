This directory is used to package the different applications and generate the installers.

Here are the commands to package the different applications. 

#### Non-modular application 

You can create a JAR file with the following command, assuming that your code has been compiled in the `out` directory. 

```shell
> jar --create \
      --verbose \
      --main-class org.paumard.jpack.Main \
      --file dump-app-no-module.jar \
      -C ../out/production/dumb-app-no-module .
```

Creating the native installer for a non-modular application on a Windows machine uses the following command. 

```shell
> jpackage --input . \
           --name app-no-module \
           --app-version 1.0 \
           --win-dir-chooser \
           --win-console \
           --win-shortcut \
           --main-jar dump-app-no-module.jar
```

#### Modular application

Creating the native installer for a modular application on a Windows machine uses the following command. The name of the module you pass, here `dumb.app.module` must match the name of your module declared in your `module-info.java` file. 

```shell
> jpackage --name app-no-module \
           --app-version 1.0 \
           --win-dir-chooser \
           --win-console \
           --win-shortcut \
           --module-path . \
           --module dumb.app.module/org.paumard.jpack.Main
```

#### The TravelingJUGSpeaker application

Creating the native installer for this application can be done with the following command. You must be in the `traveling-jug-speaker` directory to execute it.

First, you need to create the JAR for this application. 

```shell
> mvn package
```

```shell
> jpackage @../traveling-jug-speaker/config.jpak
```


#### The DukeDisplayer application

Creating the native installer for this application can be done with the following command. You must be in the `duke-displayer` directory to execute it.

First, you need to create the JAR for this application.

```shell
> jar --create \
      --verbose \
      --main-class org.paumard.duke.DukeDisplayer \
      --file jar/duke-displayer.jar \
      -C ../out/production/duke-displayer .
```

```shell
> jpackage @config.jpak
```