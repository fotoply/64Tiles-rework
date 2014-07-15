package dk.sdc.Candy2048Crush;

/**
 * Created by sdc on 7/15/14.
 */
public class Tile {
    private Grid parent;
    private int x;
    private int y;
    private int value;
    private boolean selected;

    public Tile(Grid parent, int x, int y, int value) {
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.value = value;
    }

    /**
     * Will detect whether the tile and adjacent tiles are in the correct position for a combo
     * @return Returns true if a combo is found, otherwise returns false
     */
    public boolean isComboAvailable() {
        int tilesFound = 1;
        if(parent.getTileAt(x,y-1).value == value) {
            tilesFound++;
            if(parent.getTileAt(x,y-2).value == value) {
                tilesFound++;
            }
        }
        if(parent.getTileAt(x,y+1).value == value) {
            tilesFound++;
            if(parent.getTileAt(x,y+2).value == value) {
                tilesFound++;
            }
        }
        if(tilesFound >= 3) {
            return true;
        }

        tilesFound = 1;

        if(parent.getTileAt(x-1,y).value == value) {
            tilesFound++;
            if(parent.getTileAt(x-2,y).value == value) {
                tilesFound++;
            }
        }
        if(parent.getTileAt(x+1,y).value == value) {
            tilesFound++;
            if(parent.getTileAt(x+2,y).value == value) {
                tilesFound++;
            }
        }
        if(tilesFound >= 3) {
            return true;
        }

        return false;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
