# NeoForge 1.21.1 Kotlin Templates

A collection of Kotlin-based **NeoForge 1.21.1** project templates.

Each template lives on its own branch, making it easy to start with exactly the setup you need without removing unnecessary code.

## Available Templates

| Branch | Description |
|--------|-------------|
| `both_simple` | Common + client + dedicated server mod template. |
| `client_simple` | Client-side only mod template. |
| `server_simple` | Dedicated server-side only mod template. |
| `both_sqlite` | Common + client + dedicated server template with SQLite (JDBC) support built in. |
| `client_sqlite` | Client-side only template with SQLite (JDBC) support built in. |
| `server_sqlite` | Dedicated server-side only template with SQLite (JDBC) support built in. |

## Features

All templates include:

- NeoForge **1.21.1**
- Kotlin
- Gradle with ModDevGradle
- Java 21 toolchain
- Kotlin for Forge
- Metadata templating through `gradle.properties`
- Datagen-ready project layout
- Clean, minimal project structure
- No Mixins by default

SQLite variants additionally include:

- SQLite JDBC dependency
- Ready-to-use database provider
- Basic database initialization

## Choosing a Template

### Simple Templates

Choose one of the `_simple` branches if your mod doesn't need a built-in database.

- `both_simple` - common mods supporting both client and dedicated server.
- `client_simple` - client-only mods.
- `server_simple` - dedicated server mods.

### SQLite Templates

Choose one of the `_sqlite` branches if your mod stores persistent data using SQLite.

These templates include the SQLite JDBC driver and a configured provider so you can start using a database immediately.

- `both_sqlite`
- `client_sqlite`
- `server_sqlite`

## Getting Started

Clone the branch you want to use.

```bash
git clone --branch both_simple https://github.com/thenolle/neoforge-templates.git
```

or

```bash
git clone --branch client_sqlite https://github.com/thenolle/neoforge-templates.git
```

Then:

1. Rename the values in `gradle.properties`.
2. Change the package name.
3. Rename the main mod class/object.
4. Reload the Gradle project.
5. Run the development environment.

## Gradle Tasks

```bash
./gradlew runClient # client or both
./gradlew runServer # server or both
./gradlew build # both
./gradlew clean # both
```

## Requirements

- Java 21
- Git
- IntelliJ IDEA (recommended) or another Gradle-compatible IDE

## Documentation

- NeoForged Documentation
- ModDevGradle Documentation
- Kotlin for Forge

## License

[WTFPL 2](https://www.wtfpl.net/) ([LICENSE](./LICENSE))
