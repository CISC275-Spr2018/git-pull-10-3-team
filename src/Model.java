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
	OrcState state;
	int stateTime;
	
	public void setDirection(Direction d) {
		direction = d;
	}
	public void setState(OrcState s){
		state = s;
		stateTime = 0;
	}
	
	public Orc(int x, int y, Direction dir, OrcState s) {
		this.xPos = x;
		this.yPos = y;
		this.direction = dir;
		this.state = s;
		stateTime = 0;
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
	final OrcState startState = OrcState.FORWARD;
	Orc charles;
	public Model(int w, int h, int iw, int ih){
		charles = new Orc(startxPos, startyPos, startDir, startState);
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
	public OrcState getState(){
		return charles.state;
	}
	public void setState(OrcState s){
		charles.setState(s);
	}
	public void updateLocationAndDirection(){
		
		charles.stateTime++;
		switch(charles.state){
		case JUMP:
			if(charles.stateTime > 8){
				charles.setState(OrcState.FORWARD);
			}
		case FORWARD:
		//check right side collision	
    	if (charles.xPos + (imgWidth*.85) + xIncr > width && charles.direction.getName().contains("east")) {
    		if (charles.direction == Direction.SOUTHEAST)
    			charles.direction = Direction.SOUTHWEST;
    		else if (charles.direction == Direction.NORTHEAST)
    			charles.direction = Direction.NORTHWEST;
    		else
    			charles.direction = Direction.WEST;
    	}
    	//check left side collision
    	else if (charles.xPos - xIncr + imgWidth*0.15< 0 && charles.direction.getName().contains("west")) {
    		if (charles.direction == Direction.SOUTHWEST)
    			charles.direction = Direction.SOUTHEAST;
    		else if (charles.direction == Direction.NORTHWEST)
    			charles.direction = Direction.NORTHEAST;
    		else
    			charles.direction = Direction.EAST;
    	}
    	//check bottom collision
    	if (charles.yPos + imgHeight*.9 + yIncr > height && charles.direction.getName().contains("south")) {
    		if (charles.direction == Direction.SOUTHEAST)
    			charles.direction = Direction.NORTHEAST;
    		else if(charles.direction == Direction.SOUTHWEST)
    			charles.direction = Direction.NORTHWEST;
    		else
    			charles.direction = Direction.NORTH;
    	}
    	//check top collision
     	else if (charles.yPos - yIncr + imgHeight*0.15< 0 && charles.direction.getName().contains("north")) {
     		if (charles.direction == Direction.NORTHEAST)
    			charles.direction = Direction.SOUTHEAST;
    		else if(charles.direction == Direction.NORTHWEST)
    			charles.direction = Direction.SOUTHWEST;
    		else
    			charles.direction = Direction.SOUTH;
    	}
    	
		
		if (charles.direction.getName().contains("east"))
			charles.xPos += xIncr;
		else if (charles.direction.getName().contains("west"))
			charles.xPos -= xIncr;
		
		if (charles.direction.getName().contains("south"))
			charles.yPos += yIncr;
		else if (charles.direction.getName().contains("north"))
			charles.yPos -= yIncr;
		break;
		case HALT:
		break;
		case FIRE:
			if(charles.stateTime > 4){
				charles.setState(OrcState.FORWARD);
			}
		break;
		default:
				charles.setState(OrcState.HALT);
		break;
		}
	}
}