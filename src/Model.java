/**
 * Model: Contains all the state and logic
 * Does not contain anything about images or graphics, must ask view for that
 *
 * has methods to
 *  detect collision with boundaries
 * decide next direction
 * provide direction
 * provide location
 **/

class Orc{
	int xPos;
	int yPos;
	Direction direction;
	
	public void setDirection(Direction d) {
		direction = d;
	}
	
	public Orc(int x, int y, Direction dir) {
		this.xPos = x;
		this.yPos = y;
		this.direction = dir;
	}
}

public class Model{
	final int xIncr = 8;
    final int yIncr = 8; //originally 2
	final int startxPos = 0;
	final int startyPos = 0;
	final int imgWidth;
	final int imgHeight;
	final int width;
	final int height;
	boolean xTurn = false;
	boolean yTurn = false;
	final Direction startDir = Direction.SOUTH;
	Orc charles;
	public Model(int w, int h, int iw, int ih){
		charles = new Orc(startxPos, startyPos, startDir);
		this.width = w;
		this.height = h;
		this.imgHeight = ih;
		this.imgWidth = iw;
	}
	public int getX(){
		return charles.xPos;
	}
	public int getY(){
		return charles.yPos; 
	}
	public Direction getDirect(){
		return charles.direction;
	}
	public void setDirect(Direction d) {
		charles.setDirection(d);
	}
	public void updateLocationAndDirection(){
		//check right side collision
    	if (charles.xPos + (imgWidth*.85) + xIncr > width && charles.direction.getName().contains("forward_east")) {
    		if (charles.direction == Direction.SOUTHEAST)
    			charles.direction = Direction.SOUTHWEST;
    		else if (charles.direction == Direction.NORTHEAST)
    			charles.direction = Direction.NORTHWEST;
    		else
    			charles.direction = Direction.WEST;
    	}
    	//check left side collision
    	else if (charles.xPos - xIncr + imgWidth*0.15< 0 && charles.direction.getName().contains("forward_west")) {
    		if (charles.direction == Direction.SOUTHWEST)
    			charles.direction = Direction.SOUTHEAST;
    		else if (charles.direction == Direction.NORTHWEST)
    			charles.direction = Direction.NORTHEAST;
    		else
    			charles.direction = Direction.EAST;
    	}
    	//check bottom collision
    	if (charles.yPos + imgHeight*.9 + yIncr > height && charles.direction.getName().contains("forward_south")) {
    		if (charles.direction == Direction.SOUTHEAST)
    			charles.direction = Direction.NORTHEAST;
    		else if(charles.direction == Direction.SOUTHWEST)
    			charles.direction = Direction.NORTHWEST;
    		else
    			charles.direction = Direction.NORTH;
    	}
    	//check top collision
     	else if (charles.yPos - yIncr + imgHeight*0.15< 0 && charles.direction.getName().contains("forward_north")) {
     		if (charles.direction == Direction.NORTHEAST)
    			charles.direction = Direction.SOUTHEAST;
    		else if(charles.direction == Direction.NORTHWEST)
    			charles.direction = Direction.SOUTHWEST;
    		else
    			charles.direction = Direction.SOUTH;
    	}
    	
		
		if (charles.direction.getName().contains("forward_east"))
			charles.xPos += xIncr;
		else if (charles.direction.getName().contains("forward_west"))
			charles.xPos -= xIncr;
		
		if (charles.direction.getName().contains("forward_south"))
			charles.yPos += yIncr;
		else if (charles.direction.getName().contains("forward_north"))
			charles.yPos -= yIncr;
		
		if (charles.direction.getName().contains("idle_ewns"))
			charles.yPos = charles.yPos;
			charles.xPos = charles.xPos;
		
	}
}