# Pixel Sky Jump Pro v3

## Repositorios
La versión anterior tenía **1 repositorio genérico** (`InMemoryCrudRepository`). Esta versión añade **5 repositorios concretos**: `PlayerRepository`, `EnemyRepository`, `CoinRepository`, `FloatingIslandRepository` y `LevelRepository`, además de `RepositoryRegistry`.

En total: **1 repositorio base + 5 repositorios de entidades + 1 registro de repositorios**.

## Estructura
```text
pixel-sky-jump-pro/
├── pom.xml
├── game-core/
│   └── src/main/java/.../core/
│       ├── model/
│       ├── crud/
│       ├── repository/
│       ├── service/
│       └── levels/
└── game-app/
    └── src/main/java/.../app/
        ├── GameApp.java
        ├── controller/
        ├── engine/
        ├── render/
        ├── service/
        └── ui/
```

## Capas
- **model:** Player, Enemy, Coin, FloatingIsland, Level.
- **repository:** acceso CRUD por entidad.
- **service:** reglas de aplicación/dominio.
- **controller:** teclado, niveles y ventanas.
- **engine:** física, colisiones, puntuación, vidas y cámara.
- **render:** dibujo del mundo en JavaFX Canvas.
- **ui:** entorno CRUD JavaFX.
- **GameApp:** punto de entrada y ensamblaje.

## CRUD
El contrato genérico está en `CrudRepository<T,ID>` con `create`, `read`, `readAll`, `update`, `delete`. `InMemoryCrudRepository` es la implementación base. Los repositorios concretos reutilizan esa implementación. F2 abre el CRUD visual de niveles.

## Maven XML
El `pom.xml` padre es `packaging=pom` y declara los módulos `game-core` y `game-app`. También centraliza Java 21, JavaFX 21, Lombok 1.18.42 y JUnit 5. `game-app/pom.xml` depende de `game-core` y usa `javafx-maven-plugin`.

## Ejecutar
```powershell
mvn clean install
mvn -pl game-app javafx:run
```

Controles: A/D o flechas, ESPACIO/W/↑, ENTER, R, N, F2, F9, F10 y F11.
