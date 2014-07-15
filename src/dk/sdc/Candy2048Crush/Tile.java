package dk.sdc.Candy2048Crush;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    double drawWidth;
    Paint standardTile;
    Paint selectedTile;


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
        standardTile = new Paint();
        selectedTile = new Paint();
        drawWidth = (double) ((canvas.getWidth()-40-8*5)/8);
        standardTile.setColor(Color.LTGRAY);
        selectedTile.setColor(Color.BLUE);

        canvas.drawRect((float)20+drawWidth*xPos+5*xPos, (float)20+drawWidth*yPos+5*yPos, (float)(20+drawWidth*xPos+5*xPos)+drawWidth, (float)(20+drawWidth*yPos+5*yPos)+drawWidth, standardTile );

    }

    /**
     * Will detect whether the tile and adjacent tiles are in the correct position for a combo
     *
     * @return Returns true if a combo is found, otherwise returns false
     */
    public boolean isComboAvailable() {
        int tilesFound = 1;
        if (parent.getTileAt(xPos, yPos - 1).value == value) {
            tilesFound++;
            if (parent.getTileAt(xPos, yPos - 2).value == value) {
                tilesFound++;
            }
        }
        if (parent.getTileAt(xPos, yPos + 1).value == value) {
            tilesFound++;
            if (parent.getTileAt(xPos, yPos + 2).value == value) {
                tilesFound++;
            }
        }
        if (tilesFound >= 3) {
            return true;
        }

        tilesFound = 1;

        if (parent.getTileAt(xPos - 1, yPos).value == value) {
            tilesFound++;
            if (parent.getTileAt(xPos - 2, yPos).value == value) {
                tilesFound++;
            }
        }
        if (parent.getTileAt(xPos + 1, yPos).value == value) {
            tilesFound++;
            if (parent.getTileAt(xPos + 2, yPos).value == value) {
                tilesFound++;
            }
        }
        if (tilesFound >= 3) {
            return true;
        }

        return false;
    }

    /**
     * Will execute the combo for a tile if any is available.
     */
    public void updateTile() {
        if (isComboAvailable()) {
            executeCombo();
        }
    }

    /**
     * Finds and executes combos for the current tile and its surroundings. Will propagate into nwe tiles to avoid non-executed combo leftovers.
     */
    public void executeCombo() {
        if (parent.getTileAt(xPos - 1, yPos).value == value) {
            if (parent.getTileAt(xPos - 2, yPos).value == value) {
                if (parent.getTileAt(xPos + 1, yPos).value == value) {
                    if (parent.getTileAt(xPos + 2, yPos).value == value) { // Fem ens!
                        parent.removeTileAt(xPos - 1, yPos);
                        parent.removeTileAt(xPos - 2, yPos);
                        parent.removeTileAt(xPos + 1, yPos);
                        parent.removeTileAt(xPos + 2, yPos);
                        parent.removeTileAt(xPos, yPos);

                        parent.tileList.add(new Tile(parent, xPos, yPos, value * 8));
                        parent.generateNewTile(xPos - 1, yPos);
                        parent.generateNewTile(xPos - 2, yPos);

                        parent.generateNewTile(xPos + 1, yPos);
                        parent.generateNewTile(xPos + 2, yPos);
                    } else { // FIRE ENS
                        parent.removeTileAt(xPos - 1, yPos);
                        parent.removeTileAt(xPos - 2, yPos);
                        parent.removeTileAt(xPos + 1, yPos);
                        parent.removeTileAt(xPos, yPos);

                        parent.tileList.add(new Tile(parent, xPos, yPos, value * 4));
                        parent.generateNewTile(xPos - 1, yPos);
                        parent.generateNewTile(xPos - 2, yPos);
                        parent.generateNewTile(xPos + 1, yPos);
                    }
                } else { // TRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos - 2, yPos);
                    parent.removeTileAt(xPos, yPos);

                    parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos - 2, yPos);
                }
            } else if (parent.getTileAt(xPos + 1, yPos).value == value) {
                if (parent.getTileAt(xPos + 2, yPos).value == value) { // FIRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos + 1, yPos);
                    parent.removeTileAt(xPos + 2, yPos);
                    parent.removeTileAt(xPos, yPos);

                    parent.tileList.add(new Tile(parent, xPos, yPos, value * 4));
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos + 1, yPos);
                    parent.generateNewTile(xPos + 2, yPos);
                } else { // TRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos + 1, yPos);
                    parent.removeTileAt(xPos, yPos);

                    parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos + 1, yPos);
                }
            }
        } else if (parent.getTileAt(xPos + 1, yPos).value == value) {
            if (parent.getTileAt(xPos + 2, yPos).value == value) { // TRE ENS
                parent.removeTileAt(xPos + 2, yPos);
                parent.removeTileAt(xPos + 1, yPos);
                parent.removeTileAt(xPos, yPos);

                parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                parent.generateNewTile(xPos + 1, yPos);
                parent.generateNewTile(xPos + 2, yPos);
            }
        } else if (parent.getTileAt(xPos, yPos - 1).value == value) {
            if (parent.getTileAt(xPos, yPos - 2).value == value) {
                if (parent.getTileAt(xPos, yPos + 1).value == value) {
                    if (parent.getTileAt(xPos, yPos + 2).value == value) { // FEM ens LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos - 2);
                        parent.removeTileAt(xPos, yPos + 1);
                        parent.removeTileAt(xPos, yPos + 2);
                        parent.removeTileAt(xPos, yPos);

                        parent.tileList.add(new Tile(parent, xPos, yPos, value * 8));
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos - 2);
                        parent.generateNewTile(xPos, yPos + 1);
                        parent.generateNewTile(xPos, yPos + 2);
                    } else { // FIRE ENS LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos - 2);
                        parent.removeTileAt(xPos, yPos + 1);
                        parent.removeTileAt(xPos, yPos);

                        parent.tileList.add(new Tile(parent, xPos, yPos, value * 4));
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos - 2);
                        parent.generateNewTile(xPos, yPos + 1);
                    }
                } else { // TRE ENS LODRET
                    parent.removeTileAt(xPos, yPos - 1);
                    parent.removeTileAt(xPos, yPos - 2);
                    parent.removeTileAt(xPos, yPos);

                    parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                    parent.generateNewTile(xPos, yPos - 1);
                    parent.generateNewTile(xPos, yPos - 2);
                }
            } else {
                if (parent.getTileAt(xPos, yPos + 1).value == value) {
                    if (parent.getTileAt(xPos, yPos + 2).value == value) { // FIRE ENS LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos + 2);
                        parent.removeTileAt(xPos, yPos + 1);
                        parent.removeTileAt(xPos, yPos);

                        parent.tileList.add(new Tile(parent, xPos, yPos, value * 4));
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos + 1);
                        parent.generateNewTile(xPos, yPos + 2);
                    }
                } else { // TRE ENS LODRET
                    parent.removeTileAt(xPos, yPos - 1);
                    parent.removeTileAt(xPos, yPos + 1);
                    parent.removeTileAt(xPos, yPos);

                    parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                    parent.generateNewTile(xPos, yPos - 1);
                    parent.generateNewTile(xPos, yPos + 1);
                }
            }
        } else if (parent.getTileAt(xPos, yPos + 1).value == value) {
            if (parent.getTileAt(xPos, yPos + 2).value == value) { // TRE ENS LODRET
                parent.removeTileAt(xPos, yPos + 1);
                parent.removeTileAt(xPos, yPos + 2);
                parent.removeTileAt(xPos, yPos);

                parent.tileList.add(new Tile(parent, xPos, yPos, value * 2));
                parent.generateNewTile(xPos, yPos + 1);
                parent.generateNewTile(xPos, yPos + 2);
            }
        }
    }

/*
        public void executeCombo() {
        int tilesFound = 1;
        if (parent.getTileAt(xPos, yPos - 1).value == value) {
            tilesFound++;
            parent.generateNewTile(xPos, yPos - 1);
            if (parent.getTileAt(xPos, yPos - 2).value == value) {
                tilesFound++;
                parent.generateNewTile(xPos, yPos - 2);
            }
        }
        if (parent.getTileAt(xPos, yPos + 1).value == value) {
            tilesFound++;
            parent.generateNewTile(xPos, yPos + 1);
            if (parent.getTileAt(xPos, yPos + 2).value == value) {
                tilesFound++;
                parent.generateNewTile(xPos, yPos + 2);
            }
        }
        if (tilesFound >= 3) {
            setValue(value*(int)(Math.pow(2, tilesFound-2)));
        } else {
            tilesFound = 1;
            if (parent.getTileAt(xPos - 1, yPos).value == value) {
                tilesFound++;
                parent.generateNewTile(xPos - 1, yPos);
                if (parent.getTileAt(xPos - 2, yPos).value == value) {
                    tilesFound++;
                    parent.generateNewTile(xPos - 2, yPos);
                }
            }
            if (parent.getTileAt(xPos + 1, yPos).value == value) {
                tilesFound++;
                parent.generateNewTile(xPos + 1, yPos);
                if (parent.getTileAt(xPos + 2, yPos).value == value) {
                    tilesFound++;
                    parent.generateNewTile(xPos + 2, yPos);
                }
            }
            if (tilesFound >= 3) {
                setValue(value * (int) (Math.pow(2, tilesFound - 2)));
            }
        }

    }*/

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
        ArrayList<Tile> selectedTiles = parent.getSelectedTiles();
        if (selectedTiles.size() > 1) {
            parent.deselectAll();
            Toast.makeText(getContext(), "Error: too many selected, deselecting all", Toast.LENGTH_LONG).show();
        } else if (selectedTiles.size() == 1) {
            if (parent.isAdjacentTile(this, selectedTiles.get(0))){
                parent.swapTiles(this,selectedTiles.get(0),false);
            } else {
                parent.deselectAll();
            }
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
