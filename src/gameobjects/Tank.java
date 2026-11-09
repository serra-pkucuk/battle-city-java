package gameobjects;

import java.awt.image.BufferedImage;

public abstract class Tank extends GameObject {
    public int speed;
    public String direction;
    protected BufferedImage upImage, downImage, leftImage, rightImage;

    public Tank(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
}