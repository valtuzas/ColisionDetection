package lt.raudonius.aliens;

public class StrongAlien extends Alien {
	protected int lives = 5;
	private boolean ableToMove = true;
	private int points = 5;
	private String png = "src/resources/strongalien.png";

	public StrongAlien(int x, int y) {
		super(x, y);
		super.initAlien(this.png);

	}

	@Override
	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}
		if (CanMove()) {
			x -= 1;
		}
		int sk = rnd.nextInt(100);
		if (sk == 1) {
			fire();
		}
	}

	private boolean CanMove() {
		ableToMove = !ableToMove;
		return ableToMove;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	@Override
	public int getPoints() {
		return points;
	}

}
