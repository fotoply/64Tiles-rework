package dk.sdc.Candy2048Crush;

import java.util.ArrayList;

/**
 * Created by sdc on 7/15/14.
 */
public class Grid {
    ArrayList<Tile> tileList = new ArrayList<Tile>();

    public boolean isAdjacentTile(Tile tile1, Tile tile2) {
        if((Math.abs(tile1.getX()-tile2.getX()) == 1 && tile1.getY() == tile2.getY()) || (Math.abs(tile1.getY()-tile2.getY()) == 1 && tile1.getX() == tile2.getX())) {
            return true;
        }
        return false;
    }
}
