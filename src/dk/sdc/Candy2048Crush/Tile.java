package dk.sdc.Candy2048Crush;

/**
 * Created by sdc on 7/15/14.
 */
public class Tile {
    private Grid parent;
    private int x;
    private int y;
    private int value;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
