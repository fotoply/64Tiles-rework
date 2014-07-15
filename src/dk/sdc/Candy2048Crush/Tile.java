package dk.sdc.Candy2048Crush;

import android.graphics.Canvas;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sdc on 7/15/14.
 */

public class Tile extends View implements View.OnClickListener {
    private Grid parent;
    private int xPos;
    private int yPos;
    private int value;
    private boolean selected;

    public Tile(Grid parent, int x, int y, int value) {
        super(parent.getContext());
        this.parent = parent;
        this.xPos = x;
        this.yPos = y;
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
        if(parent.getTileAt(xPos,yPos-1).value == value) {
            tilesFound++;
            if(parent.getTileAt(xPos,yPos-2).value == value) {
                tilesFound++;
            }
        }
        if(parent.getTileAt(xPos,yPos+1).value == value) {
            tilesFound++;
            if(parent.getTileAt(xPos,yPos+2).value == value) {
                tilesFound++;
            }
        }
        if(tilesFound >= 3) {
            return true;
        }

        tilesFound = 1;

        if(parent.getTileAt(xPos-1,yPos).value == value) {
            tilesFound++;
            if(parent.getTileAt(xPos-2,yPos).value == value) {
                tilesFound++;
            }
        }
        if(parent.getTileAt(xPos+1,yPos).value == value) {
            tilesFound++;
            if(parent.getTileAt(xPos+2,yPos).value == value) {
                tilesFound++;
            }
        }
        if(tilesFound >= 3) {
            return true;
        }

        return false;
    }

    public void executeCombo() {
        int tilesFound = 1;
        if(parent.getTileAt(xPos,yPos-1).value == value) {
            tilesFound++;
            parent.
            if(parent.getTileAt(xPos,yPos-2).value == value) {
                tilesFound++;
            }
        }
        if(parent.getTileAt(xPos,yPos+1).value == value) {
            tilesFound++;
            if(parent.getTileAt(xPos,yPos+2).value == value) {
                tilesFound++;
            }

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
        ArrayList<Tile> selectedTile = parent.getSelectedTiles();
        if (selectedTile.size() > 1) {
            parent.deselectAll();
            Toast.makeText(getContext(),"Error: too many selected, deselecting all",Toast.LENGTH_LONG).show();
        } else if (selectedTile.size() == 1) {
            parent.isAdjacentTile(this, selectedTile.get(0));
        } else {
            setSelected(true);
        }
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
