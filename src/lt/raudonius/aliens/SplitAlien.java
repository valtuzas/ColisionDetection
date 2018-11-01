package lt.raudonius.aliens;

public class SplitAlien extends Alien {
	private int lives = 2;


	public SplitAlien(int x, int y) {
		super(x, y);
		initSplitAlien();
	}

	private void initSplitAlien() {
		 loadImage("src/resources/zigzagalian.png");
	        getImageDimensions();
	}
	@Override
	public boolean toSplit() {
		if(lives==0) {
			return true;
		}
		return false;
	}
	
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
}
