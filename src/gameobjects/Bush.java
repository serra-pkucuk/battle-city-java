package gameobjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bush extends GameObject {

    public Bush(int x, int y) {
        super(x, y, 40, 40);
        loadImages();
    }

    private void loadImages() {
        try {
            java.awt.image.BufferedImage spriteSheet = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png"));
            
            this.image = spriteSheet.getSubimage(272, 32, 16, 16);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
    
    }
}