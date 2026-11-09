package engine;


import audio.SoundManager;
import gui.GameWindow;
import gameobjects.*;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable {
    
    final int playAreaWidth = 800;
    final int hudWidth = 150;
    final int screenWidth = playAreaWidth + hudWidth;
    final int screenHeight = 600;
    final int FPS = 60;
    
    public static List<GameObject> walls = new ArrayList<>();
    public static List<GameObject> bushes = new ArrayList<>(); 
    public static List<GameObject> waters = new ArrayList<>();
    public static List<GameObject> steels = new ArrayList<>();
    public static List<GameObject> enemies = new ArrayList<>();
    
    public static List<TankMermisi> mermiler = new ArrayList<>();
    public static List<PowerUp> powerUps = new ArrayList<>(); 
    
    Thread gameThread;
    boolean running = false;
    boolean canShoot = true;
    public boolean isPaused = false; 
    
    int currentLevel = 1;
    boolean gameOver = false;
    boolean gameWon = false;
    boolean scoreSaved = false;
    
    int playerLives = 3;
    int enemiesToSpawn = 20;
    int activeEnemiesLimit = 4;
    int score = 0;
    
    int playerStars = 0;
    long freezeTimeEnd = 0;
    long shovelTimeEnd = 0;
    long shieldTimeEnd = 0;
    
    Random rand = new Random();
    KeyHandler keyH = new KeyHandler();
    PlayerTank player;
    Base base;
    
    int[][] level1 = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,0,0,1,1,4,4,0,0,0,0,1,1,0,0,1,1,0},
        {0,1,1,0,0,3,3,4,4,0,0,0,0,3,3,0,0,1,1,0},
        {0,2,2,0,0,1,1,0,1,1,1,1,0,1,1,0,0,2,2,0},
        {0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0},
        {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
        {0,0,0,4,4,0,0,0,0,0,0,0,0,0,0,4,4,0,0,0},
        {0,1,1,0,0,1,1,0,2,2,2,2,0,1,1,0,0,1,1,0},
        {0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0},
        {0,1,1,0,0,1,1,0,0,0,0,0,0,1,1,0,0,1,1,0},
        {0,1,1,0,0,1,1,0,3,3,3,3,0,1,1,0,0,1,1,0},
        {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
        {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
        {0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0} 
    };

    int[][] level2 = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,2,2,2,0,4,4,4,4,0,0,4,4,4,4,0,2,2,2,0},
        {0,2,2,2,0,4,4,4,4,0,0,4,4,4,4,0,2,2,2,0},
        {0,0,0,0,0,1,1,1,1,0,0,1,1,1,1,0,0,0,0,0},
        {0,3,3,0,0,0,0,0,0,0,0,0,0,0,0,0,0,3,3,0},
        {0,3,3,0,1,1,1,1,2,2,2,2,1,1,1,1,0,3,3,0},
        {0,0,0,0,1,1,1,1,2,2,2,2,1,1,1,1,0,0,0,0},
        {0,4,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,4,0},
        {0,4,4,0,3,3,3,0,1,1,1,1,0,3,3,3,0,4,4,0},
        {0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0},
        {0,1,1,1,1,0,2,2,2,2,2,2,2,2,0,1,1,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,4,4,4,4,0,0,0,0,0,0,0,0,0,0,4,4,4,4,0},
        {0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0}
    };

    int[][] level3 = {
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,3,3,0,0,3,3,0,0,3,3,0,0,3,3,0,0,3,3,0},
        {0,3,3,0,0,3,3,0,0,3,3,0,0,3,3,0,0,3,3,0},
        {0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0},
        {0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0,0,1,1,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,3,3,3,3,0,1,1,1,1,1,1,1,1,0,3,3,3,3,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,1,1,0,0,3,3,3,3,0,0,3,3,3,3,0,0,1,1,0},
        {0,1,1,0,0,3,3,3,3,0,0,3,3,3,3,0,0,1,1,0},
        {0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0},
        {0,3,3,0,2,2,2,2,2,2,2,2,2,2,2,2,0,3,3,0},
        {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0,1,0,1,0,0,0,0,0,0,0,0,0}
    };

    public GamePanel() {
    	this.currentLevel = GameWindow.selectedLevel;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK); 
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        loadMap();        
        player = new PlayerTank(240, 560);
    }
    
    public void loadMap() {
        walls.clear(); bushes.clear(); waters.clear(); steels.clear(); enemies.clear(); mermiler.clear(); powerUps.clear();
        enemiesToSpawn = 20;
        
        if (currentLevel == 1) activeEnemiesLimit = 4;
        else if (currentLevel == 2) activeEnemiesLimit = 6;
        else if (currentLevel == 3) activeEnemiesLimit = 8;
        
        base = new Base(9 * 40, 14 * 40); 
        
        int[][] currentMap = level1; 
        if (currentLevel == 2) currentMap = level2;
        else if (currentLevel == 3) currentMap = level3;
        
        for (int row = 0; row < currentMap.length; row++) {
            for (int col = 0; col < currentMap[row].length; col++) {
                if (row == 14 && col == 9) continue; 
                int val = currentMap[row][col];
                if (val == 1) walls.add(new BrickWall(col * 40, row * 40));
                else if (val == 2) waters.add(new Water(col * 40, row * 40));
                else if (val == 3) steels.add(new SteelWall(col * 40, row * 40));
                else if (val == 4) bushes.add(new Bush(col * 40, row * 40));
            }
        }
        if (player != null) { player.setX(240); player.setY(560); }
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        running = true;
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        while (running) {
            update(); repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1000000;
                if (remainingTime < 0) remainingTime = 0;
                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
    
    public void update() {
        if (isPaused) return;
        if (gameWon || gameOver) { saveScoreHandler(); return; }
        
        long currentTime = System.currentTimeMillis();
        
        if (enemies.isEmpty() && enemiesToSpawn == 0) {
            if (currentLevel < 3) { currentLevel++; loadMap(); return; } 
            else { gameWon = true; }
        }
        
        
        if (enemiesToSpawn > 0 && enemies.size() < activeEnemiesLimit) {
            enemies.add(new EnemyTank(rand.nextInt(3) * 360, 0, currentLevel));
            enemiesToSpawn--;
        }
        
        player.upPressed = keyH.upPressed;
        player.downPressed = keyH.downPressed;
        player.leftPressed = keyH.leftPressed;
        player.rightPressed = keyH.rightPressed;
        player.update();
        
        for (int i = 0; i < powerUps.size(); i++) {
            if (player.getBounds().intersects(powerUps.get(i).getBounds())) {
                applyPowerUp(powerUps.get(i).type);
                SoundManager.playSound("src/sounds/powerup.wav");
                powerUps.remove(i);
                break;
            }
        }
        
        int maxPlayerBullets = (playerStars >= 2) ? 2 : 1;
        long activePlayerBullets = mermiler.stream().filter(m -> m.isPlayer).count();
        
        if (keyH.spacePressed && canShoot && activePlayerBullets < maxPlayerBullets) {
            TankMermisi yeniMermi = new TankMermisi(player.getX() + 16, player.getY() + 16, player.direction, true);
            
            if (playerStars >= 1) yeniMermi.speed = 14; 
            
            mermiler.add(yeniMermi);
            SoundManager.playSound("src/sounds/shoot.wav");
            canShoot = false;
        }
        if (!keyH.spacePressed) canShoot = true;
        
        for (int i = 0; i < mermiler.size(); i++) {
            TankMermisi m = mermiler.get(i);
            m.update();
            boolean hit = false;
            
            if (m.getX() < 0 || m.getX() > playAreaWidth || m.getY() < 0 || m.getY() > screenHeight) hit = true;
            
            if (!hit) {
                for (int j = 0; j < walls.size(); j++) {
                    if (m.getBounds().intersects(walls.get(j).getBounds())) {
                        hit = true; 
                        if (walls.get(j) instanceof BrickWall) walls.remove(j); 
                        SoundManager.playSound("src/sounds/explosion.wav");
                        break;
                    }
                }
            }
            
            if (!hit) {
                for (int j = 0; j < steels.size(); j++) {
                    if (m.getBounds().intersects(steels.get(j).getBounds())) {
                        hit = true;
                        if (m.isPlayer && playerStars >= 3) steels.remove(j); 
                        SoundManager.playSound("src/sounds/explosion.wav");
                        break;
                    }
                }
            }
            
            if (!hit && currentTime < shovelTimeEnd) {
                Rectangle baseProtect1 = new Rectangle(8 * 40, 14 * 40, 40, 40);
                Rectangle baseProtect2 = new Rectangle(9 * 40, 13 * 40, 40, 40);
                Rectangle baseProtect3 = new Rectangle(10 * 40, 14 * 40, 40, 40);
                if (m.getBounds().intersects(baseProtect1) || m.getBounds().intersects(baseProtect2) || m.getBounds().intersects(baseProtect3)) {
                    hit = true;
                }
            }
            
            if (!hit && m.isPlayer) {
                for (int j = 0; j < enemies.size(); j++) {
                    if (m.getBounds().intersects(enemies.get(j).getBounds())) {
                        hit = true; 
                        
                        if (rand.nextInt(100) < 100) {
                            powerUps.add(new PowerUp(enemies.get(j).getX(), enemies.get(j).getY(), rand.nextInt(6)));
                        }
                        
                        enemies.remove(j); 
                        SoundManager.playSound("src/sounds/explosion.wav");
                        score += 100; 
                        break;
                    }
                }
            } 
            else if (!hit && !m.isPlayer && m.getBounds().intersects(player.getBounds())) {
                hit = true; 
                if (currentTime > shieldTimeEnd) { 
                    playerLives--; 
                    SoundManager.playSound("src/sounds/explosion.wav");
                    if (playerLives <= 0) gameOver = true;
                    else { player.setX(240); player.setY(560); }
                }
            }
            
            if (!hit && base != null && !base.isDestroyed && m.getBounds().intersects(base.getBounds())) {
                hit = true; 
                base.destroy(); 
                SoundManager.playSound("src/sounds/explosion.wav");
                gameOver = true;
            }
            
            if (hit) { mermiler.remove(i); i--; }
        }
        
        if (currentTime > freezeTimeEnd) { 
            for (GameObject enemy : enemies) enemy.update();
        }
    } 
    
    private void applyPowerUp(int type) {
        long currentTime = System.currentTimeMillis();
        switch(type) {
            case 0: playerLives++; break; 
            case 1: if (playerStars < 3) playerStars++; break; 
            case 2: score += enemies.size() * 100; enemies.clear(); break; 
            case 3: freezeTimeEnd = currentTime + 5000; break; 
            case 4: shovelTimeEnd = currentTime + 10000; break; 
            case 5: shieldTimeEnd = currentTime + 8000; break; 
        }
    }
    
    private void saveScoreHandler() {
        if (!scoreSaved) {
            scoreSaved = true;
            SwingUtilities.invokeLater(() -> {
                String name = JOptionPane.showInputDialog(this, 
                    "Your Score: " + score + "\nPlease enter your name:", 
                    (gameWon ? "You Completed All Levels!" : "Game Over!"), 
                    JOptionPane.PLAIN_MESSAGE);
                    
                if (name != null && !name.trim().isEmpty()) {
                    LocalDateTime now = LocalDateTime.now();
                    DateTimeFormatter dateForm = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeForm = DateTimeFormatter.ofPattern("HH:mm:ss");
                    
                    try (FileWriter fw = new FileWriter("scores.csv", true);
                         PrintWriter pw = new PrintWriter(fw)) {
                        pw.println(name.trim() + "," + now.format(dateForm) + "," + now.format(timeForm) + "," + score);
                    } catch (IOException e) { e.printStackTrace(); }
                }
            });
        }
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long currentTime = System.currentTimeMillis();
        
        g.setColor(Color.BLACK); 
        g.fillRect(0, 0, playAreaWidth, screenHeight);
        
        for (GameObject wall : walls) wall.draw(g);
        for (GameObject water : waters) water.draw(g);
        for (GameObject steel : steels) steel.draw(g); 
        
        if (currentTime < shovelTimeEnd) {
            g.setColor(Color.LIGHT_GRAY); 
            g.fillRect(8 * 40, 14 * 40, 40, 40); 
            g.fillRect(9 * 40, 13 * 40, 40, 40); 
            g.fillRect(10 * 40, 14 * 40, 40, 40); 
        }
        
        if (base != null) base.draw(g);
        
        for (PowerUp p : powerUps) p.draw(g);
        for (GameObject enemy : enemies) enemy.draw(g);
        
        if (currentTime < shieldTimeEnd) {
            g.setColor(new Color(0, 100, 255, 100)); 
            g.fillOval(player.getX() - 5, player.getY() - 5, 50, 50);
        }
        player.draw(g);
        
        for (TankMermisi m : mermiler) m.draw(g);
        for (GameObject bush : bushes) bush.draw(g);
        
        g.setColor(Color.GRAY); 
        g.fillRect(playAreaWidth, 0, hudWidth, screenHeight);
        g.setColor(Color.BLACK); 
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString("LEVEL " + currentLevel, playAreaWidth + 10, 50);
        g.drawString("LIVES " + playerLives, playAreaWidth + 10, 100);
        g.drawString("ENEMIES", playAreaWidth + 10, 160);
        g.drawString("  " + (enemiesToSpawn + enemies.size()), playAreaWidth + 10, 190);
        g.drawString("SCORE", playAreaWidth + 10, 260);
        g.drawString("  " + score, playAreaWidth + 10, 290);
        
        g.setColor(new Color(153, 0, 0)); 
        g.drawString("STARS: " + playerStars, playAreaWidth + 10, 330);
        
        if (isPaused && !gameOver && !gameWon) {
            g.setColor(new Color(0, 0, 0, 150)); 
            g.fillRect(0, 0, playAreaWidth, screenHeight);
            g.setColor(Color.YELLOW); 
            g.setFont(new Font("Monospaced", Font.BOLD, 70));
            g.drawString("PAUSED", 260, 300);
        }
        
        if (gameOver) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, playAreaWidth, screenHeight);
            g.setColor(Color.RED); 
            g.setFont(new Font("Monospaced", Font.BOLD, 80));
            g.drawString("GAME OVER", 180, 300);
        } else if (gameWon) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, playAreaWidth, screenHeight);
            g.setColor(Color.GREEN); 
            g.setFont(new Font("Monospaced", Font.BOLD, 70));
            g.drawString("YOU WIN!", 230, 300);
        }
    }
}