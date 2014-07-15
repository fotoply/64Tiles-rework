package dk.sdc.Candy2048Crush;

import android.graphics.Canvas;
import android.view.View;

/**
 * Created by sdc on 7/15/14.
 */
public class Tile extends View implements View.OnClickListener {
    private Grid parent;
    private int x;
    private int y;
    private int value;
    private boolean selected;

    public Tile(Grid parent, int x, int y, int value) {
        super(parent.getContext());
        this.parent = parent;
        this.x = x;
        this.y = y;
        this.value = value;
        setOnClickListener(this);
    }

    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
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

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {

    }

//    public int getX() {
//        return x;
//    }
//
//    public void setX(int x) {
//        this.x = x;
//    }
//
//    public int getY() {
//        return y;
//    }
//
//    public void setY(int y) {
//        this.y = y;
//    }
}
