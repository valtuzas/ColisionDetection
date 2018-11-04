package lt.raudonius.aliens;

import java.util.Random;

public class ZigZagAlien extends Alien {

	private Random rand = new Random();
	private int points = 3;
	private String png = "src/resources/images/zigzagalian.png";

	public ZigZagAlien(int x, int y) {
		super(x, y);
		super.initAlien(this.png);

	}

	@Override
	public void move() {

		if (x < 0) {
			x = INITIAL_X;
		}
		x -= 1;
		int random = rand.nextInt(2);
		if (random == 0) {
			y -= 3;
		} else if (random == 1) {
			y += 3;
		}
		if (y < 0) {
			y = 0;
		}
		if (y > 260) {
			y = 260;
		}
		int sk = rnd.nextInt(200);
		if (sk == 1) {
			fire();
		}
	}

	@Override
	public int getPoints() {
		return points;
	}

}
