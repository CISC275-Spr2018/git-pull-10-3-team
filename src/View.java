import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * View: Contains everything about graphics and images
 * Know size of world, which images to load etc
 *
 * has methods to
 * provide boundaries
 * use proper images for direction
 * load images for all direction (an image should only be loaded once!!! why?)
 **/

public class View{
	int frameCount;
	int picNum = 0;
	BufferedImage[] pics, images;
	BufferedImage[][] animation;
	final static int frameWidth = 500;
	final static int frameHeight = 300;
	final static int imgWidth = 165;
	final static int imgHeight = 165;
	JFrame frame;
	JButton button;
	
	DrawPanel panel = new DrawPanel();
	int xloc;
	int yloc;
	
	public int getWidth(){
		return frameWidth;
	}
	public int getHeight(){
		return frameHeight;
	}
	public int getImageWidth() {
		return imgWidth;
	}
	public int getImageHeight() {
		return imgHeight;
	}
	public JFrame getFrame() {
		return frame;
	}
	public JButton getButton() {
		return button;
	}
	
	public View(){
		frame = new JFrame();
		button = new JButton("Go/Stop");
		button.setSize(100, 100);
		button.setBounds(50, 150, 50, 50);
		panel.add(button);
		loadImages();
		frame.getContentPane().add(panel);
		frame.setBackground(Color.gray);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(frameWidth, frameHeight);
		frame.setVisible(true);	
	}
	@SuppressWarnings("serial")
	private class DrawPanel extends JPanel{
		@Override
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.setColor(Color.gray);
			setBackground(Color.gray);
			g.drawImage(pics[picNum], xloc, yloc, Color.gray, this);
		}
	}
	private void loadImages(){
		images = new BufferedImage[Direction.values().length];
		animation = new BufferedImage[Direction.values().length][0];
		for(int i = 0; i < Direction.values().length; i++) {
			images[i] = createImage(Direction.values()[i]);
			animation[i] = new BufferedImage[images[i].getWidth()/imgWidth];
			loadImg(images[i],animation[i],animation[i].length);
		}
		pics = animation[Direction.SOUTH.ordinal()];
	}
	private void loadImg(BufferedImage img, BufferedImage[] an ,int frames){
		for(int i = 0; i < frames; i++){
			an[i] = img.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
		}
	}
	private BufferedImage createImage(Direction direction) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File("images/orc/orc_"+ direction.getName()+".png"));
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
		// TODO: Change this method so you can load other orc animation bitmaps
	}
	public void update(int x, int y, Direction direct){
		xloc = x;
		yloc = y;
		switch(direct){
			case HALT:	
				frameCount = animation[Direction.HALT.ordinal()].length;	
				pics = animation[Direction.HALT.ordinal()];
				break;
			case NORTH:	
				frameCount = animation[Direction.NORTH.ordinal()].length;	
				pics = animation[Direction.NORTH.ordinal()];
				break;
			case EAST:
				frameCount = animation[Direction.EAST.ordinal()].length;	
				pics = animation[Direction.EAST.ordinal()];
				break;
			case WEST:	
				frameCount = animation[Direction.WEST.ordinal()].length;	
				pics = animation[Direction.WEST.ordinal()];
				break;
			case SOUTH:
				frameCount = animation[Direction.SOUTH.ordinal()].length;	
				pics = animation[Direction.SOUTH.ordinal()];
				break;
			case NORTHEAST:
				frameCount = animation[Direction.NORTHEAST.ordinal()].length;	
				pics = animation[Direction.NORTHEAST.ordinal()];
				break;
			case NORTHWEST:
				frameCount = animation[Direction.NORTHWEST.ordinal()].length;	
				pics = animation[Direction.NORTHWEST.ordinal()];
				break;
			case SOUTHEAST:
				frameCount = animation[Direction.SOUTHEAST.ordinal()].length;	
				pics = animation[Direction.SOUTHEAST.ordinal()];
				break;
			case SOUTHWEST:
				frameCount = animation[Direction.SOUTHWEST.ordinal()].length;	
				pics = animation[Direction.SOUTHWEST.ordinal()];
				break;
		}
		picNum = (picNum + 1) % frameCount;
		panel.repaint();
		try {
			Thread.sleep(80);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}