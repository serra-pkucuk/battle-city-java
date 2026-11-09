package gameobjects;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class PlayerTank extends Tank {
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public PlayerTank(int x, int y) {
        super(x, y, 40, 40); 
        this.speed = 4;
        this.direction = "UP";
        loadImages();
    }

    private void loadImages() {
        try {
            BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png"));
            upImage    = spriteSheet.getSubimage(0, 0, 16, 16);
            leftImage  = spriteSheet.getSubimage(32, 0, 16, 16);
            downImage  = spriteSheet.getSubimage(64, 0, 16, 16);
            rightImage = spriteSheet.getSubimage(96, 0, 16, 16);
            this.image = upImage; 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        int nextX = x;
        int nextY = y;

        if (upPressed) { direction = "UP"; this.image = upImage; nextY -= speed; } 
        else if (downPressed) { direction = "DOWN"; this.image = downImage; nextY += speed; } 
        else if (leftPressed) { direction = "LEFT"; this.image = leftImage; nextX -= speed; } 
        else if (rightPressed) { direction = "RIGHT"; this.image = rightImage; nextX += speed; }
        
        boolean canMove = true;
        
        if (nextX < 0 || nextX > 800 - width || nextY < 0 || nextY > 600 - height) canMove = false;
        
        if (canMove) {
            Rectangle futureBounds = new Rectangle(nextX, nextY, width, height);
            
            for (GameObject wall : engine.GamePanel.walls) {
                if (futureBounds.intersects(wall.getBounds())) { canMove = false; break; }
            }
            if (canMove) {
                for (GameObject steel : engine.GamePanel.steels) {
                    if (futureBounds.intersects(steel.getBounds())) { canMove = false; break; }
                }
            }
            if (canMove) {
                for (GameObject water : engine.GamePanel.waters) {
                    if (futureBounds.intersects(water.getBounds())) { canMove = false; break; }
                }
            }
        }

        if (canMove) { x = nextX; y = nextY; }
    }
}