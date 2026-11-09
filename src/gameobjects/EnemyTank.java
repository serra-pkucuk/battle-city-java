package gameobjects;

import audio.SoundManager;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;
import engine.GamePanel;
import audio.SoundManager;
import audio.SoundManager;
import audio.SoundManager;

public class EnemyTank extends Tank {
    private int actionCounter = 0;
    private Random random = new Random();
    private int shootCooldown = 0; 

    public EnemyTank(int x, int y, int level) {
        super(x, y, 40, 40);
        
        
        this.speed = 1 + level; 
        
        this.direction = "DOWN";
        loadImages();
    }

    private void loadImages() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png"));
            upImage    = spriteSheet.getSubimage(128, 0, 16, 16);
            leftImage  = spriteSheet.getSubimage(160, 0, 16, 16);
            downImage  = spriteSheet.getSubimage(192, 0, 16, 16);
            rightImage = spriteSheet.getSubimage(224, 0, 16, 16);
            this.image = downImage;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        actionCounter++;

       
        if (actionCounter > 100) {
            int dir = random.nextInt(4);
            if (dir == 0) { direction = "UP"; this.image = upImage; }
            else if (dir == 1) { direction = "DOWN"; this.image = downImage; }
            else if (dir == 2) { direction = "LEFT"; this.image = leftImage; }
            else if (dir == 3) { direction = "RIGHT"; this.image = rightImage; }
            actionCounter = 0;
        }

    
        if (shootCooldown > 0) {
            shootCooldown--; 
        }
        
       
        if (shootCooldown <= 0 && random.nextInt(100) < 2) { 
            
            GamePanel.mermiler.add(new TankMermisi(this.x + 16, this.y + 16, this.direction, false));
            SoundManager.playSound("src/sounds/shoot.wav");
            shootCooldown = 80; 
        }

       
        int nextX = x;
        int nextY = y;

        if (direction.equals("UP")) nextY -= speed;
        else if (direction.equals("DOWN")) nextY += speed;
        else if (direction.equals("LEFT")) nextX -= speed;
        else if (direction.equals("RIGHT")) nextX += speed;

        boolean collision = false;
        
       
        if (nextX < 0 || nextX > 800 - width || nextY < 0 || nextY > 600 - height) {
            collision = true;
        }

        Rectangle futureBounds = new Rectangle(nextX, nextY, width, height);

        
        if (!collision) {
            for (GameObject wall : GamePanel.walls) {
                if (futureBounds.intersects(wall.getBounds())) { collision = true; break; }
            }
        }
        if (!collision) {
            for (GameObject steel : GamePanel.steels) {
                if (futureBounds.intersects(steel.getBounds())) { collision = true; break; }
            }
        }
        if (!collision) {
            for (GameObject water : GamePanel.waters) {
                if (futureBounds.intersects(water.getBounds())) { collision = true; break; }
            }
        }

        if (!collision) { 
            x = nextX; 
            y = nextY; 
        } else { 
            actionCounter = 101; 
        }
    }
}