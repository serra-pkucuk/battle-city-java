package gameobjects;

import java.awt.Color;
import java.awt.Graphics;

public class TankMermisi extends GameObject {
    public int direction; 
    public boolean isPlayer; 
    public int speed = 7; 

   
    public TankMermisi(int x, int y, int direction, boolean isPlayer) {
        super(x, y, 6, 6);
        this.direction = direction;
        this.isPlayer = isPlayer;
    }

        public TankMermisi(int x, int y, String dirStr, boolean isPlayer) {
        super(x, y, 6, 6);
        this.isPlayer = isPlayer;
        
    
        if (dirStr.equals("UP")) this.direction = 0;
        else if (dirStr.equals("DOWN")) this.direction = 1;
        else if (dirStr.equals("LEFT")) this.direction = 2;
        else if (dirStr.equals("RIGHT")) this.direction = 3;
        else this.direction = 0; 
    }

    @Override
    public void update() {
        if (direction == 0) y -= speed;      
        else if (direction == 1) y += speed; 
        else if (direction == 2) x -= speed; 
        else if (direction == 3) x += speed; 
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(isPlayer ? Color.YELLOW : Color.WHITE);
        g.fillRect((int)x, (int)y, width, height);
    }
}