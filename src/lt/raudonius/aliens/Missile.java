package lt.raudonius.aliens;

public class Missile extends Sprite {

    private final int BOARD_WIDTH = 390;
    private final int MISSILE_SPEED = 2;
    private String png = "src/resources/images/missile.png";

    public Missile(int x, int y) {
        super(x, y);

        initMissile(png);
    }
    
    protected void initMissile(String png) {
        
        loadImage(png);
        getImageDimensions();        
    }

    public void move() {
        
        x += MISSILE_SPEED;
        
        if (x > BOARD_WIDTH)
            visible = false;
    }
}