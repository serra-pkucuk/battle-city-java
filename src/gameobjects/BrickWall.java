package gameobjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class BrickWall extends Wall {
    public BrickWall(int x, int y) {
        super(x, y); 
        loadImages();
    }

    private void loadImages() {
        try {
            java.awt.image.BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png"));
            this.image = spriteSheet.getSubimage(256, 0, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}