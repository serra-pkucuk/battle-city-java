package gameobjects;

import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Base extends GameObject {
    
    public boolean isDestroyed = false;
    private BufferedImage destroyedImage;

    public Base(int x, int y) {
        super(x, y, 40, 40);
        try {
            
            this.image = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png")).getSubimage(304, 32, 16, 16);
            
            
            this.destroyedImage = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png")).getSubimage(320, 32, 16, 16);
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }

    public void destroy() {
        if (!isDestroyed) {
            isDestroyed = true;
            this.image = destroyedImage; 
        }
    }

    @Override
    public void update() {
        
    }
}