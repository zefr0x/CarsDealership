> [!Warning]
> This repository/project is a univertiy project. It has no quality standards and is no longer maintained. If you are interested in the project, it's free software under the GPL-3.0 license.

The `gradle` build tool is used to manage the application.

To run the application from your IDE, configure the `app:run` gradle task in your run button.

To build a .jar package, use the `shadowJar` gradle task:

```
$ gradle shadowJar
```

To run the .jar package use:

```
$ java -jar app/build/gradle/libs/CarsDealership-0.1.0.jar
```
