# Battle City — Java Desktop Game

**CSE 212 — Software Development Methodologies | Yeditepe University**

A Java desktop implementation of a retro, top-down tank game built with Swing and AWT. The project demonstrates object-oriented design, a real-time game loop, tile-based levels, user input handling, enemy behavior, collision detection, and score persistence.

## Features

- Three built-in levels with varying enemy counts and difficulty
- Keyboard-controlled tank movement and shooting
- Enemy movement and shooting logic
- Brick, steel, water, and bush terrain with collision handling
- Power-ups for lives, weapon upgrades, enemy clearing, freezing, base protection, and shielding
- Menus, pause/resume, difficulty selection, and high-score screen
- Grid-based map editor that exports a Java array to the console
- Shooting, explosion, and power-up sound effects
- High scores stored locally in a CSV file

## Technologies

Java, Swing, AWT, Java Sound API, collections, object-oriented programming, and file I/O.

## Code structure

```text
src/
├── Main.java
├── audio/
│   └── SoundManager.java
├── engine/
│   ├── GamePanel.java
│   └── KeyHandler.java
├── gameobjects/
│   └── ... (tanks, terrain, projectiles, and power-ups)
├── gui/
│   └── ... (menus, options, map editor, and scores)
└── sounds/
    └── ... (WAV sound effects)
```

## Controls

| Key | Function |
| --- | --- |
| W / A / S / D | Move the player tank |
| Space | Shoot |
| Game menu | Start, pause, or open game options |

## Build and run

The project uses Java's standard desktop libraries and was verified with JDK 17. Run the following commands from the repository root.

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out:resources Main
```

On Windows, run the build commands in Git Bash and use `java -cp 'out;resources' Main` to launch the game.

### Assets

- [Sprite sheet](resources/resim/sprites.png). The game loads `/resim/sprites.png` from the classpath, which includes the `resources` directory.
- [Sound effects](src/sounds/). The game loads `src/sounds/shoot.wav`, `src/sounds/explosion.wav`, and `src/sounds/powerup.wav` relative to the working directory. Launch the game from the repository root so these paths resolve.
- Reference images in `docs/`. [Stage 1](docs/stage1.png), [Stage 2](docs/stage2.png), [Stage 3](docs/stage3.png), and [additional artwork](docs/misc-original.png).

## Implementation notes

- The game loop targets 60 FPS.
- Game entities derive from a common `GameObject` base class.
- Collision checks use rectangular hitboxes.
- The map editor currently prints level arrays to the console; it does not directly inject custom levels into gameplay.
- Scores are appended to `scores.csv` and displayed by the high-score view.

## Author

**Serra Pehlivanküçük**, Yeditepe University.

## Status

All Java sources compile with JDK 17. Headless verification covered sprite loading, off-screen rendering of all three built-in levels, and decoding of all three WAV files. Interactive gameplay and audible playback still require a desktop test.
