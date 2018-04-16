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
		int numDir, numState;
		numDir = Direction.values().length;
		numState = OrcState.values().length;
		images = new BufferedImage[numState * numDir];
		//animation is a 2D array; each row is a different animation, each column is a frame
		//the # of rows is equal to the number of states * the number of directions each state can be in
		animation = new BufferedImage[Direction.values().length*OrcState.values().length][0];
		for(int i = 0; i < numState; i++){
			for(int j = 0; j < numDir; j++){
				if(i == 0){//halt is loaded differently than the other states
					if(j==0)
						loadHaltImg(animation[i],animation[i].length);//load all at once
					//this is done because the halt animation file is different than the others
				}else{
					images[i*numDir + j] = createImage(OrcState.values()[i], Direction.values()[j]);
					animation[i*numDir + j] = new BufferedImage[images[i*numDir + j].getWidth()/imgWidth];
					loadImg(images[i*numDir + j], (i*numDir+j), animation[i*numDir + j].length);
				}
			}
		}
		pics = new BufferedImage[animation[getIndex(OrcState.FORWARD, Direction.EAST)].length];
		pics = animation[getIndex(OrcState.FORWARD, Direction.EAST)]; 
		//get array postion of forward south
	}
	private void loadHaltImg(BufferedImage[] an ,int frames){
		for(int i = 0; i < Direction.values().length; i++){
			images[i] = createImage(Direction.values()[i]);
			animation[i] = new BufferedImage[images[i].getWidth()/imgWidth];
			loadImg(images[i], i, animation[i].length);
		}
	}
	private void loadImg(BufferedImage img, int index ,int frames){
		for(int i = 0; i < frames; i++){
			animation[index][i] = img.getSubimage(imgWidth * i, 0, imgWidth, imgHeight);
		}
	}
	private BufferedImage createImage(Direction dir) {//***THIS IS FOR HALT ANIMATIONS ONLY***
		BufferedImage bufferedImage = null;
		try {
			switch((dir.ordinal() % 2)){
			case 0: 
				bufferedImage = ImageIO.read(new File("images/orc/orc_idle_ewns.png"));
				break;
			default:
				bufferedImage = ImageIO.read(new File("images/orc/orc_idle_nwneswse.png"));
				break;
			}
			switch(dir){
			case NORTH:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight*2, bufferedImage.getWidth(), imgHeight);
				break;
			case SOUTH:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight*3, bufferedImage.getWidth(), imgHeight);
				break;
			case EAST:
				bufferedImage = bufferedImage.getSubimage(0, 0, bufferedImage.getWidth(), imgHeight);
				break;
			case WEST:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight, bufferedImage.getWidth(), imgHeight);
				break;
			case NORTHEAST:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight, bufferedImage.getWidth(), imgHeight);
				break;
			case SOUTHEAST:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight*3, bufferedImage.getWidth(), imgHeight);
				break;
			case NORTHWEST:
				bufferedImage = bufferedImage.getSubimage(0, 0, bufferedImage.getWidth(), imgHeight);
				break;
			case SOUTHWEST:
				bufferedImage = bufferedImage.getSubimage(0, imgHeight*2, bufferedImage.getWidth(), imgHeight);
				break;
			}
			return bufferedImage;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private BufferedImage createImage(OrcState state, Direction direction) {
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(new File("images/orc/orc_"+state.getName()+direction.getName()+".png"));
			return bufferedImage;
		} catch (IOException e) {
			System.out.println("images/orc/orc_"+state.getName()+direction.getName()+".png");
			e.printStackTrace();
		}
		return null;
	}
	
	private static int getIndex(OrcState s, Direction dir){
		return s.ordinal()*Direction.values().length + dir.ordinal();
	}
	public void update(int x, int y, Direction direct, OrcState state){
		xloc = x;
		yloc = y;
		frameCount = animation[getIndex(state, direct)].length;
		pics = animation[getIndex(state, direct)];
		if(picNum >= (frameCount-2) && state == OrcState.FORWARD){
			pics = animation[getIndex(OrcState.FORWARD, direct)];
			frameCount = pics.length;
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