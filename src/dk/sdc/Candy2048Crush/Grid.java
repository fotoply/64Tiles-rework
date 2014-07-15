package dk.sdc.Candy2048Crush;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sdc on 7/15/14.
 */
public class Grid {
    ArrayList<Tile> tileList = new ArrayList<Tile>();
    int[] tileValues = {2,4,8,16};

    /**
     * Checks if a tile is adjacent to another
     * @param tile1 First tile
     * @param tile2 Second tile
     * @return Returns true if tiles are adjacent
     */
    public boolean isAdjacentTile(Tile tile1, Tile tile2) {
        if((Math.abs(tile1.getX()-tile2.getX()) == 1 && tile1.getY() == tile2.getY()) || (Math.abs(tile1.getY()-tile2.getY()) == 1 && tile1.getX() == tile2.getX())) {
            return true;
        }
        return false;
    }

    /**
     * Generates a tile at the specified x and y coordinate. Will remove any previous tile at the x and y before creating the new one. Will return the tile but will also add it to the grids tilelist.
     * @param x
     * @param y
     * @return The newly created tile
     */
    public Tile generateNewTile(int x, int y) {
        if(getTileAt(x,y).getValue() != -1) {
            removeTileAt(x,y);
        }
        Tile tempTile = new Tile(this,x,y,-1);
        Random rng = new Random();
        tempTile.setValue(tileValues[rng.nextInt(tileValues.length)]);
        tileList.add(tempTile);
        return tempTile;
    }

    /**
     * Gets the tile from the grid at the x and y coordinates
     * @param x
     * @param y
     * @return Returns the tile found. If no tile is found will return a tile with a value of -1
     */
    public Tile getTileAt(int x, int y) {
        for(int i = 0; i < tileList.size(); i++) {
            Tile temp = tileList.get(i);
            if(temp.getX() == x && temp.getY() == y) {
                return tileList.get(i);
            }
        }

        return new Tile(null,-1,-1,-1);
    }

    /**
     * Removes a tile at the specified x and y coordinates
     * @param x
     * @param y
     */
    public void removeTileAt(int x, int y) {
        tileList.remove(getTileAt(x,y));
    }
}
