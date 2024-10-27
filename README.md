# Installation

### Note: 
To use github packages registry you need to have an access token configured: https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-apache-maven-registry

## Gradle

Add repository:

```kotlin
repositories {
    maven("https://maven.pkg.github.com/Creeperface01/*")
}
```

Add dependency:

```kotlin
compileOnly("com.creeperface.nukkit.kformapi:kformapi:1.0")
```

## Maven

Add repository to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>creeperface-github</id>
        <url>https://maven.pkg.github.com/Creeperface01/*</url>
    </repository>
</repositories>
```

Add dependency:

```xml
<dependency>
    <groupId>com.creeperface.nukkit.kformapi</groupId>
    <artifactId>kformapi</artifactId>
    <version>1.0</version>
    <scope>provided</scope>
</dependency>
```

