package gameobjects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class PowerUp extends GameObject {
    
    public int type;
    

    public PowerUp(int x, int y, int type) {
        
        super(x + 10, y + 10, 20, 20); 
        this.type = type;
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics g) {
        
        if (type == 0) g.setColor(Color.RED);          // Tank (Can)
        else if (type == 1) g.setColor(Color.YELLOW);  // Yıldız
        else if (type == 2) g.setColor(Color.DARK_GRAY); // Bomba
        else if (type == 3) g.setColor(Color.CYAN);    // Saat
        else if (type == 4) g.setColor(Color.ORANGE);  // Kürek
        else if (type == 5) g.setColor(Color.BLUE);    // Kalkan
        
        g.fillRect(x, y, width, height);
        
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 12));
        
        String harf = "";
        if(type == 0) harf = "T";
        if(type == 1) harf = "Y";
        if(type == 2) harf = "B";
        if(type == 3) harf = "S";
        if(type == 4) harf = "K";
        if(type == 5) harf = "K+";
        
        g.drawString(harf, x + 4, y + 15);
    }
}