package lt.raudonius.aliens;

public class AlienShot extends Missile {
	
	private final int MISSILE_SPEED = -2;
	private String png = "src/resources/shot.png";

	public AlienShot(int x, int y) {
		super(x, y);
		 super.initMissile(png);
	}
	//@Override
	/*  protected void initMissile() {
	        
	        loadImage("src/resources/shot.png");
	        getImageDimensions();        
	    }*/
	@Override
	 public void move() {
	        
	        x += MISSILE_SPEED;
	        
	        if (x < 0)
	            visible = false;
	    }
}
