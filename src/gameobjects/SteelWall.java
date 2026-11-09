package gameobjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class SteelWall extends Wall {
    public SteelWall(int x, int y) {
        super(x, y); 
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png")).getSubimage(256, 16, 16, 16);
        } catch (IOException e) { 
            e.printStackTrace(); 
        }
    }
}