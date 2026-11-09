package gameobjects;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Water extends GameObject {
    public Water(int x, int y) {
        super(x, y, 40, 40);
        try {
            this.image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/resim/sprites.png")).getSubimage(288, 32, 16, 16);
        } catch (IOException e) { e.printStackTrace(); }
    }
    @Override public void update() {}
}