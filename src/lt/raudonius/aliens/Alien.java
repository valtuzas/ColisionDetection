package lt.raudonius.aliens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Alien extends Sprite {

	protected final int INITIAL_X = 400;
	protected int lives = 1;
	private int points = 1;
	private List<AlienShot> shot;
	protected Random rnd = new Random();
	private String png = "src/resources/images/alien.png";

	public Alien(int x, int y) {
		super(x, y);

		initAlien(png);
	}

	protected void initAlien(String png) {
		shot = new ArrayList<>();
		loadImage(png);
		getImageDimensions();
	}

	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}
		int sk = rnd.nextInt(150);
		if (sk == 1) {
			fire();
		}
		x -= 1;
	}

	public List<AlienShot> getMissiles() {
		return shot;
	}

	public void fire() {
		shot.add(new AlienShot(x + width, y + height / 2));
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public boolean toSplit() {
		return false;
	}

	public int getPoints() {
		return points;
	}

}