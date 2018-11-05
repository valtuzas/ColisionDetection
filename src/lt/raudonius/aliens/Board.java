package lt.raudonius.aliens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private SpaceShip spaceship;
	private List<Alien> aliens;
	private boolean ingame;
	private final int ICRAFT_X = 40;
	private final int ICRAFT_Y = 60;
	private final int B_WIDTH = 400;
	private final int B_HEIGHT = 300;
	private final int DELAY = 15;
	private int level = 1;
	private int sukurtaPriesu = 0;
	private int nusautaPriesu = -1;
	private Random rand = new Random();
	JButton nexLevelButton = new JButton("next level");
	JButton HighScoreButton = new JButton("High Scores");
	JButton quitButton = new JButton("Quit Game");
	JButton startGameButton = new JButton("Start Game");
	JButton restartLevelButton = new JButton("Restart level");
	private int points = 0;
	JTextField text = new JTextField("Name");
	// private String name;
	DataBaze baz = new DataBaze();

	public Board() {

		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);

		setLayout(null);
		startGameButton.setBounds(100, 50, 200, 30);
		nexLevelButton.setBounds(100, 50, 200, 30);
		restartLevelButton.setBounds(100, 50, 200, 30);
		HighScoreButton.setBounds(100, 100, 200, 30);
		quitButton.setBounds(100, 150, 200, 30);
		text.setBounds(100, 150, 200, 30);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);

		timer = new Timer(DELAY, this);

		// add(nexLevelButton);
		add(text);
		add(quitButton);
		add(startGameButton);
		
		text.setVisible(false);
		add(HighScoreButton);
		startGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				add(nexLevelButton);
				add(restartLevelButton);
				startGameButton.setVisible(false);
				restartLevelButton.setVisible(false);
				HighScoreButton.setVisible(false);
				nexLevelButton.setVisible(false);
				quitButton.setVisible(false);
				text.setVisible(false);
				spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
				setLevel(level);
				ingame = true;
				timer.start();
			}
		});
		quitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);

			}
		});
	

		HighScoreButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DataBaze a = new DataBaze();
				JTable table;
				try {
					table = new JTable(a.buildTableModel());

					JOptionPane.showMessageDialog(null, new JScrollPane(table));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

	}

	private void setLevel(int level) {
		if (level == 1) {
			initAliens(5, 2, 1);
		} else if (level == 2) {
			initAliens(5, 5, 2);
		} else if (level == 3) {
			initAliens(10, 8, 3);
		} else if (level == 4) {
			initAliens(15, 15, 5);
		} else if (level == 5) {
			initAliens(25, 20, 10);
		}

	}

	public void initAliens(int simpleAlien, int zigzagAlien, int strongAlien) {

		aliens = new ArrayList<>();

		for (int i = 0; i < simpleAlien; i++) {
			aliens.add(new Alien(rand.nextInt(600) + 500, rand.nextInt(230) + 30));
		}
		for (int i = 0; i < zigzagAlien; i++) {
			aliens.add(new ZigZagAlien(rand.nextInt(600) + 500, rand.nextInt(230) + 30));
		}
		for (int i = 0; i < strongAlien; i++) {
			aliens.add(new StrongAlien(rand.nextInt(600) + 500, rand.nextInt(230) + 30));
		}
		sukurtaPriesu = aliens.size();
		nusautaPriesu = 0;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (ingame) {

			drawObjects(g);

		} else {
			if (level <= 5) {
				if (!spaceship.isVisible()) {
					
					drawInsertName(g);
					nextLevel();
					
					this.points = 0;

				} else {
				
					nexLevelButton.setVisible(true);
					nextLevel();
				}
			} else {
				saveRezult();
				drawWinGame(g);
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}

	private void nextLevel() {

		nexLevelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				HighScoreButton.setVisible(false);
				nexLevelButton.setVisible(false);
				quitButton.setVisible(false);
				text.setVisible(false);
				restartLevelButton.setVisible(false);
				
					level++;
			
				spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
				setLevel(level);
				ingame = true;
				timer.start();
			}
		});
		restartLevelButton.addActionListener( new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				HighScoreButton.setVisible(false);
				nexLevelButton.setVisible(false);
				quitButton.setVisible(false);
				text.setVisible(false);
				restartLevelButton.setVisible(false);
				spaceship = new SpaceShip(ICRAFT_X, ICRAFT_Y);
				setLevel(level);
				ingame = true;
				timer.start();
			}
		});
		
	}

	private void drawObjects(Graphics g) {

		if (spaceship.isVisible()) {
			g.drawImage(spaceship.getImage(), spaceship.getX(), spaceship.getY(), this);
		}

		List<Missile> ms = spaceship.getMissiles();

		for (Missile missile : ms) {
			if (missile.isVisible()) {
				g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
			}
		}

		for (Alien alien : aliens) {
			if (alien.isVisible()) {
				g.drawImage(alien.getImage(), alien.getX(), alien.getY(), this);
				List<AlienShot> as = alien.getMissiles();
				for (Missile missile : as) {
					if (missile.isVisible()) {
						g.drawImage(missile.getImage(), missile.getX(), missile.getY(), this);
					}
				}
			}
		}

		g.setColor(Color.WHITE);
		g.drawString("Points: " + this.points, 5, 15);
	}

	private void drawWinGame(Graphics g) {

		String msg = "YOU SAVE THE WORLD";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}
	private void drawInsertName(Graphics g) {

		String msg = "Insert Name";
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics fm = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, 125);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		inGame();
		updateShip();
		updateMissiles();
		updateAliens();
		checkCollisions();
		repaint();
	}

	private void inGame() {

		if (!ingame) {
			timer.stop();
		}
	}

	private void updateShip() {

		if (spaceship.isVisible()) {
			spaceship.move();
		}
	}

	private void updateMissiles() {

		List<Missile> ms = spaceship.getMissiles();

		for (int i = 0; i < ms.size(); i++) {

			Missile m = ms.get(i);

			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}
		for (Alien alien : aliens) {

			List<AlienShot> as = alien.getMissiles();
			for (int i = 0; i < as.size(); i++) {

				Missile ss = as.get(i);

				if (ss.isVisible()) {
					ss.move();
				} else {
					as.remove(i);
				}
			}
		}
	}

	private void updateAliens() {

		if (aliens.isEmpty()) {

			ingame = false;
			return;
		}

		for (int i = 0; i < aliens.size(); i++) {

			Alien a = aliens.get(i);

			if (a.isVisible()) {
				a.move();
			} else {
				this.points += a.getPoints();
				aliens.remove(i);
				nusautaPriesu++;
			}
		}
	}

	public void checkCollisions() {

		Rectangle r3 = spaceship.getBounds();

		for (Alien alien : aliens) {

			Rectangle r2 = alien.getBounds();
			List<AlienShot> as = alien.getMissiles();
			for (Missile m : as) {
				Rectangle r4 = m.getBounds();
				if (r3.intersects(r4)) {

					spaceship.setVisible(false);
					m.setVisible(false);
					ingame = false;
					saveRezult();
				}
			}

			if (r3.intersects(r2)) {

				spaceship.setVisible(false);
				alien.setVisible(false);
				ingame = false;
				saveRezult();
			}
		}

		List<Missile> ms = spaceship.getMissiles();

		for (Missile m : ms) {

			Rectangle r1 = m.getBounds();

			for (Alien alien : aliens) {

				Rectangle r2 = alien.getBounds();

				if (r1.intersects(r2)) {

					m.setVisible(false);
					alien.setLives(alien.getLives() - 1);
					if (alien.getLives() == 0) {
						alien.setVisible(false);
					}
				}
			}
		}
	}

	private void saveRezult() {
		restartLevelButton.setVisible(false);
		//nexLevelButton.setVisible(false);
		HighScoreButton.setVisible(false);
		
		text.setVisible(true);
		baz.setPoints(this.points);
		text.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//nexLevelButton.setVisible(true);
				HighScoreButton.setVisible(true);
				quitButton.setVisible(true);
				restartLevelButton.setVisible(true);
				baz.setName(text.getText());
				text.setVisible(false);
				try {
					baz.incertHighScore(baz.getName(), baz.getPoints());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			spaceship.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			spaceship.keyPressed(e);
		}
	}
}
