# Zen [![](https://jitpack.io/v/forax/zen.svg)](https://jitpack.io/#forax/zen)
A bare bone graphic library in Java (on top of AWT) for teaching purpose, use at your own risk.

Download [zen-6.0.jar](https://jitpack.io/com/github/forax/zen/6.0/zen-6.0.jar),
[zen-6.0-sources.jar](https://jitpack.io/com/github/forax/zen/6.0/zen-6.0-sources.jar) or/and
[zen-6.0-javadoc.jar](https://jitpack.io/com/github/forax/zen/6.0/zen-6.0-javadoc.jar).

Zen provides
- a way to open a drawing area in full screen mode
  ```java
  Application.run(backgroundColor, context -> {
    ...
  });
  ```
  The context object (`ApplicationContext`) let you interact with the drawing area.
  
- a way to get keyboard and mouse pointer events
  ```java
  Event event = context.pollEvent();
  ```
  The `event` is either a keyboard event (`KeyboardEvent`) or a mouse pointer event (`PointerEvent`)
  
- a way to draw on the drawing area
  ```java
  context.renderFrame(graphics2D -> {
    // use the Graphics2D object to draw
    ...
  });
  ```

### How to use Zen with Eclipse ?
  Download the jar file [zen-6.0.jar](https://jitpack.io/com/github/forax/zen/6.0/zen-6.0.jar)
  then follow https://stackoverflow.com/questions/11463354/how-to-put-a-jar-in-classpath-in-eclipse.

### How to use Zen with Ant ?
  Download the jar file [zen-6.0.jar](https://jitpack.io/com/github/forax/zen/6.0/zen-6.0.jar)
  then follow https://stackoverflow.com/questions/7057229/how-to-include-classpath-jars-into-a-jar-in-ant. 

### How to use Zen with Maven ?
  The latest binary distribution of Zen is available on JitPack repository.

  So first, you need to add Jitpack as a repository in the POM file,
  ```xml
  ...
  <repositories>
      ...
      <repository>
          <id>jitpack.io</id>
          <url>https://jitpack.io</url>
      </repository>
  </repositories>
  ```

  and then add Zen as a dependency
  ```xml
  <dependencies>
      ...
      <dependency>
          <groupId>com.github.forax</groupId>
          <artifactId>zen</artifactId>
          <version>6.0</version>
      </dependency>
  </dependencies>
  ```

### How to use Zen with Gradle ?
  The latest binary distribution of Zen is available on JitPack repository. 

  ```gradle
  repositories {
      ...
      maven { url 'https://jitpack.io' }
  }
  dependencies {
      compile 'com.github.forax:zen:6.0'
  }
```

### How to use the Graphics2D object ?
  Oracle has is a nice tutorial explaining how to use the class `Graphics2D`,
  https://docs.oracle.com/javase/tutorial/2d/

### How to load images (PNG, JPEG, etc.) ?
  Images can be loaded using the package
  [imageio](https://docs.oracle.com/en/java/javase/21/docs/api/java.desktop/javax/imageio/package-summary.html).
  Here is an example
  ```java
  String imageName = ...
  BufferedImage image;
  try(InputStream input = MyClass.class.getResourceAsStream("/images/" + imageName)) {
    image = ImageIO.read(input);
  }
  ```
  With the images stored in a sub folder `images` of the jar file.

  If you are using `eclipse`, if you create the folder `images` in `src`, it will be copied into `bin` automatically.
  If you are using `maven` or `gradle`, if you create the folder `images` in `src/main/resources`, it will be copied
  into `target/classes` automatically. If you are using `ant`, you have to copy the folder using the task `copy`.

  The `BufferedImage` can later be drawn to the screen buffer using the method
  [graphics2D.drawImage(image, x, y, null)](https://docs.oracle.com/en/java/javase/21/docs/api/java.desktop/java/awt/Graphics.html#drawImage(java.awt.Image,int,int,java.awt.image.ImageObserver)).

### How can i create buttons and other graphics components
  The idea of Zen is to provide just the bare minimum to draw something on screen, exposing an instance of `Graphics2D`.
  If you want graphics components, you have to implement them by yourself, on top of Zen, which is a nice exercice BTW.

### Where is the javadoc of Zen ?
  The javadoc is [available online](https://jitpack.io/com/github/forax/zen/6.0/javadoc/).
  
### Is there a demo file of Zen ?
  Yes, see [Demo.java](src/test/java/com/github/forax/zen/demo/Demo.java)
