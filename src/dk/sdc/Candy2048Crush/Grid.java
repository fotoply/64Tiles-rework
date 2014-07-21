package dk.sdc.Candy2048Crush;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created at UNF SDC on 7/15/14. Full sourcecode is available on github at https://github.com/sdc2014/Candy2048Crush
 */

public class Grid extends GridLayout {
    ArrayList<Tile> tileList = new ArrayList<Tile>();
    private final int[] tileValues = {2, 4, 8, 16};

    public Grid(Context context) {
        this(context, null);
    }

    public Grid(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Grid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    boolean gameOver = false;
    public GameActivity parent;

    /**
     * Generates a grid of tiles, with width*height amount of tiles.
     *
     * @param width
     * @param height
     */
    public void generateGrid(int width, int height) {
        this.setRowCount(width);
        this.setColumnCount(height);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                generateNewTile(i, j, false);
            }
        }
        for (int i = 0; i < tileList.size(); i++) {
            tileList.get(i).updateTile();
            if (tileList.get(i).getValue() == -1 || tileList.get(i).getText() != tileList.get(i).getValue() + "") {
                Log.w("TILE", "INVALID TILE FOUND; REGENERATING");
                generateNewTile(tileList.get(i).getxPos(), tileList.get(i).getyPos());
            }
        }
        /*respawnAllTiles();
        respawnAllTiles();
        respawnAllTiles();*/
    }

    /**
     * Generates a tile at the specified x and y coordinate. Will remove any previous tile at the x and y before creating the new one. Will return the tile but will also add it to the grids tileList.  Will update the tile and execute combos.
     *
     * @param x
     * @param y
     * @return The newly created tile
     */
    public Tile generateNewTile(int x, int y) {
        return generateNewTile(x, y, true);
    }

    /**
     * Generates a tile at the specified x and y coordinate. Will remove any previous tile at the x and y before creating the new one. Will return the tile but will also add it to the grids tileList. Will update the tile and execute combos if update is set to true.
     *
     * @param x
     * @param y
     * @param update
     * @return The newly created tile
     */
    public Tile generateNewTile(int x, int y, boolean update) {
        if (getTileAt(x, y).getValue() != -1) {
            removeTileAt(x, y);
        }
        Tile t = new Tile(this, x, y, -1);
        tileList.add(t);
        Log.w("Regenerate tile", "Regenerated tile at: " + x + "," + y);
        int cuTile = tileList.size() - 1;
        double random = Math.random();
        if (random < 0.5) {
            t.setValue(tileValues[0]);
        } else if (random < 0.75) {
            t.setValue(tileValues[1]);
        } else if (random < 0.9) {
            t.setValue(tileValues[2]);
        } else {
            t.setValue(tileValues[3]);
        }

        if (update) {
            t.updateTile();
        }
        Spec row = GridLayout.spec(y, 1);
        Spec colspan = GridLayout.spec(x, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
        gridLayoutParam.setMargins(5, 5, 5, 5);
        //tileList.get(cuTile).layout(0, 0, 125, 125);
//        try {
            addView(t, gridLayoutParam);
//        } catch (Exception ignored) {
//
//        }
        return tileList.get(cuTile);
    }

    /**
     * Runs through all tiles and finds the highest value.
     *
     * @return The value of the highest tile
     */
    public int getHighscore() {
        int highscore = 0;
        for (int i = 0; i < tileList.size(); i++) {
            highscore += tileList.get(i).getValue();
        }
        return highscore;
    }

    /**
     * Respawns a tile from scratch from coordinates and a value.
     *
     * @param x     coordinate of the tile.
     * @param y     coordinate of the tile.
     * @param value of the tile.
     * @return the new tile.
     */
    public Tile respawnTile(int x, int y, int value) {
        if (getTileAt(x, y).getValue() != -1) {
            removeTileAt(x, y);
        }
        Tile t = new Tile(this, x, y, value);
        tileList.add(t);
        Log.w("Regenerate tile", "Regenerated tile at: " + x + "," + y);
        int cuTile = tileList.size() - 1;
        Spec row = GridLayout.spec(y, 1);
        Spec colspan = GridLayout.spec(x, 1);
        GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row, colspan);
        gridLayoutParam.setMargins(5, 5, 5, 5);
        //tileList.get(cuTile).layout(0, 0, 125, 125);
//        try {
        addView(t, gridLayoutParam);
//        } catch (Exception ignored) {
//
//        }
        return tileList.get(cuTile);
    }

    /**
     * Checks if a tile is adjacent to another
     *
     * @param tile1 First tile
     * @param tile2 Second tile
     * @return Returns true if tiles are adjacent
     */
    public boolean isAdjacentTile(Tile tile1, Tile tile2) {
        if ((Math.abs(tile1.getxPos() - tile2.getxPos()) == 1 && tile1.getyPos() == tile2.getyPos()) || (Math.abs(tile1.getyPos() - tile2.getyPos()) == 1 && tile1.getxPos() == tile2.getxPos())) {
            return true;
        }
        return false;
    }

    /**
     * Removes a tile at the specified x and y coordinates
     *
     * @param x
     * @param y
     */
    public void removeTileAt(int x, int y) {
        for(int i = 0; i < tileList.size(); i++) {
            if(tileList.get(i).getxPos() == x && tileList.get(i).getyPos() == y) {
                removeView(tileList.get(i));
                tileList.remove(i);
            }
        }
        //System.gc();
    }

    /**
     * Gets the tile from the grid at the x and y coordinates
     *
     * @param x
     * @param y
     * @return Returns the tile found. If no tile is found will return a tile with a value of -1
     */
    public Tile getTileAt(int x, int y) {
        for (int i = 0; i < tileList.size(); i++) {
            if (tileList.get(i).getxPos() == x && tileList.get(i).getyPos() == y) {
                return tileList.get(i);
            }
        }
        return new Tile(this, -1, -1, -1);
    }

    /**
     * Goes through all tiles and detects selected ones
     *
     * @return a list of selected tiles
     */
    public ArrayList<Tile> getSelectedTiles() {
        ArrayList<Tile> selectedTile = new ArrayList<Tile>();
        for (int i = 0; i < tileList.size(); i++) {
            Tile temp = tileList.get(i);
            if (temp.isSelected()) {
                selectedTile.add(tileList.get(i));
            }
        }
        return selectedTile;
    }

    /**
     * Deselects all tiles
     */
    public void deselectAll() {
        for (int i = 0; i < tileList.size(); i++) {
            tileList.get(i).setSelected(false);
            tileList.get(i).invalidate();
        }
    }

    /**
     * Swaps two tiles, and checks if it creates a combo, if not it deselects all and calls isGameOver
     *
     * @param a       First tile to be swapped
     * @param b       Second tile to be swapped
     * @param restore if the method restores deselects all and checks for game over after re-swapping the tiles
     */
    public void swapTiles(Tile a, Tile b, boolean restore) {
        /*if (gameOver) {
            return;
        }*/

        int tempValue = a.getValue();
        a.setValue(b.getValue());
        b.setValue(tempValue);

        if (restore) {
            a.updateTile();
            b.updateTile();
            if (isGameOver()) {
                Toast.makeText(getContext(), "Game Over, no combos left", Toast.LENGTH_LONG).show();
                gameOver = true;
            }
        } else {
            Log.w("TEST", "A1=" + a.isComboAvailableHorisontal() + "; A2=" + a.isComboAvailableVertical() + "; B1=" + b.isComboAvailableHorisontal() + "; B2=" + b.isComboAvailableVertical());

            if (!a.isComboAvailableHorisontal() && !a.isComboAvailableVertical() && !b.isComboAvailableHorisontal() && !b.isComboAvailableVertical()) {
                Toast.makeText(getContext(), "No combos found", Toast.LENGTH_SHORT).show();
                swapTiles(a, b, true);
                deselectAll();
            }
            if (a.isComboAvailableHorisontal()) {
                a.executeComboHorizontal();
                deselectAll();
            }
            if (a.isComboAvailableVertical()) {
                a.executeComboVertical();
                deselectAll();
            }
            if (b.isComboAvailableHorisontal()) {
                b.executeComboHorizontal();
                deselectAll();
            }
            if (b.isComboAvailableVertical()) {
                b.executeComboVertical();
                deselectAll();
            }
        }
        parent.setSavedHighscore();
        parent.setScore(this.getHighscore());
    }

    /**
     * calls updateTile on all tiles
     */
    public void updateAllTiles() {
        for (int i = 0; i < tileList.size(); i++) {
            tileList.get(i).updateTile();
        }
    }

    /**
     * Checks if swapping two tiles can cause a combo
     *
     * @param a first tile to swap
     * @param b second tile to swap
     * @return if swapping the tiles will cause a combo
     */
    public boolean swapCheck(Tile a, Tile b) {
        int tempValue = a.getValue();
        a.setValue(b.getValue());
        b.setValue(tempValue);
        this.invalidate();

        if (!a.isComboAvailableHorisontal() && !a.isComboAvailableVertical() && !b.isComboAvailableHorisontal() && !b.isComboAvailableVertical()) {
            tempValue = a.getValue();
            a.setValue(b.getValue());
            b.setValue(tempValue);
            return false;
        } else {
            Log.w("swap", "swapCheck on " + a.getxPos() + "," + a.getyPos() + "and" + b.getxPos() + "," + b.getyPos() + "succesFull");
            tempValue = a.getValue();
            a.setValue(b.getValue());
            b.setValue(tempValue);
            return true;
        }
    }


    /**
     * Respawns all tiles
     */
    public void respawnAllTiles() {
        Tile temp;
        for (int i = 0; i < tileList.size(); i++) {
            temp = tileList.get(i);
            tileList.get(i).invalidate();
            respawnTile(temp.getxPos(), temp.getyPos(), temp.getValue());
        }
        System.gc();
    }

    /**
     * Checks if there is any combo you can do with remaining tiles.
     *
     * @return true if there is any possible move that can cause a combo.
     */
    public boolean isGameOver() {
        for (int x = 0; x < 7; x++) {
            for (int y = 0; y < 7; y++) {
                if (swapCheck(getTileAt(x, y), getTileAt(x + 1, y)) || swapCheck(getTileAt(x, y), getTileAt(x, y + 1))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Prints all tiles to Log.w
     */
    public void printAllTiles() {
        Log.w("TILE ALL", "Total tiles:" + tileList.size());
        String line = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                try {
                    line += getTileAt(i, j).getValue() + "-" + this.getTileAt(i, j).getText() + ":";
                } catch (Exception e) {

                }
            }
            Log.w("TILE ALL", line);
            line = "";
        }
    }

    /**
     * Clears the tileList of all tiles and removes associated views.
     */
    public void clearTiles() {
        for (int i = 0; i < tileList.size(); i++) {
            removeView(tileList.get(i));
        }
        tileList.clear();
    }
}
