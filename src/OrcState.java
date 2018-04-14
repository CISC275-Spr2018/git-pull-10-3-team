public enum OrcState {

	HALT("idle_ewns"),
	FORWARD("forward"),
	FIRE("fire"),
	JUMP("jump");
	
	private String name = null;
	
	private OrcState(String s){
		name = s;
	}
	public String getName() {
		return name;
	}

}
