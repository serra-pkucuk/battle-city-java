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
└── gui/
    └── ... (menus, options, map editor, and scores)
```

## Controls

| Key | Function |
| --- | --- |
| W / A / S / D | Move the player tank |
| Space | Shoot |
| Game menu | Start, pause, or open game options |

## Build and run

The project uses Java's standard desktop libraries. Once the required binary assets are included in their expected locations, compile from the repository root:

```bash
mkdir -p out
javac -d out $(find src -name '*.java')
java -cp out:resources Main
```

On Windows, use `;` instead of `:` as the classpath separator.

### Asset requirements

**The Java sources have been uploaded, but the original binary resources are not yet included in this repository.** The game expects a sprite sheet at `/resim/sprites.png` on the Java classpath (for example `resources/resim/sprites.png`), and WAV files at `src/sounds/shoot.wav`, `src/sounds/explosion.wav`, and `src/sounds/powerup.wav` relative to the working directory.

Without these files, the game cannot be demonstrated as intended. A verified working release requires the original assets and a build/runtime check.

## Implementation notes

- The game loop targets 60 FPS.
- Game entities derive from a common `GameObject` base class.
- Collision checks use rectangular hitboxes.
- The map editor currently prints level arrays to the console; it does not directly inject custom levels into gameplay.
- Scores are appended to `scores.csv` and displayed by the high-score view.

## Author

**Serra Pehlivanküçük**, Yeditepe University.

## Status

Java source code uploaded. Original sprite and audio resources still need to be added, and the game has not been independently built or tested from the GitHub snapshot.
