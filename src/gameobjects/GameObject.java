package gameobjects;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	
	protected Image image;
	
	public GameObject(int x, int y, int width, int height) {
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	
	public abstract void update();
	
	public void draw(Graphics g) {
		if (image != null) {
			g.drawImage(image, x, y, width, height, null);
		}
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public Rectangle getBounds() { return new Rectangle(x, y, width, height); }
}