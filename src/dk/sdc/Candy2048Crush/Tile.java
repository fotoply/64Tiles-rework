package dk.sdc.Candy2048Crush;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sdc on 7/15/14.
 */

public class Tile extends TextView implements View.OnClickListener {
    private Grid parent;
    private int xPos;
    private int yPos;
    private int value;
    private boolean selected;
    private int width = 125;
    private int height = 125;


    public Tile(Grid parent, int x, int y, int value) {
        super(parent.getContext());
        this.parent = parent;
        this.xPos = x;
        this.yPos = y;
        setValue(value);
        setOnClickListener(this);

        width = (parent.getWidth()-70)/8;
        height = width;

        this.setWidth(width);
        this.setHeight(height);
        this.setText(value + "");
        //this.setBackgroundColor(Color.BLUE);
        this.setGravity(Gravity.CENTER);
    }

    /*@Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        standardTile = new Paint();
        selectedTile = new Paint();
        drawWidth = (double) ((canvas.getWidth()-40-8*5)/8);
        standardTile.setColor(Color.LTGRAY);
        selectedTile.setColor(Color.BLUE);

        canvas.drawRect(0,0,125,125,standardTile);

        *//*if (isSelected()){
            canvas.drawRect((float)(20+drawWidth*xPos+5*xPos), (float)(20+drawWidth*yPos+5*yPos), (float)((20+drawWidth*xPos+5*xPos)+drawWidth), (float)((20+drawWidth*yPos+5*yPos)+drawWidth), selectedTile );
        } else {
            canvas.drawRect((float)(20+drawWidth*xPos+5*xPos), (float)(20+drawWidth*yPos+5*yPos), (float)((20+drawWidth*xPos+5*xPos)+drawWidth), (float)((20+drawWidth*yPos+5*yPos)+drawWidth), standardTile );
        }*//*

    }*/

    /**
     * Will detect whether the tile and adjacent tiles are in the correct position for a combo
     *
     * @return Returns true if a combo is found, otherwise returns false
     */
    public boolean isComboAvailableVertical() {
        Log.w("COMBO", "looking for vertical combo at:" + xPos + "," + yPos);
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
        if (tilesFound > 2) {
            Log.w("COMBO", "found vertical combo at:" + xPos + "," + yPos);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Will detect whether the tile and adjacent tiles are in the correct position for a combo
     *
     * @return Returns true if a combo is found, otherwise returns false
     */
    public boolean isComboAvailableHorisontal() {
        Log.w("COMBO", "looking for horizontal combo at:" + xPos + "," + yPos);
        int tilesFound = 1;

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
        if (tilesFound > 2) {
            Log.w("COMBO", "found horizontal combo at:" + xPos + "," + yPos);
            return true;
        } else {
            return false;
        }
    }


    /**
     * Will execute the combo for a tile if any is available.
     */
    public void updateTile() {
        this.setText(value + "");
        if (isComboAvailableHorisontal()) {
            executeComboHorizontal();
        }
        if (isComboAvailableVertical()) {
            executeComboVertical();
        }
        this.invalidate();
    }

    /**
     * Finds and executes combos for the current tile and its surroundings. Will propagate into nwe tiles to avoid non-executed combo leftovers.
     */
    public void executeComboHorizontal() {
        Log.w("COMBO", "executing horizontal combo at:" + xPos + "," + yPos);
        if (parent.getTileAt(xPos - 1, yPos).value == value) {
            if (parent.getTileAt(xPos - 2, yPos).value == value) {
                if (parent.getTileAt(xPos + 1, yPos).value == value) {
                    if (parent.getTileAt(xPos + 2, yPos).value == value) { // Fem ens!
                        parent.removeTileAt(xPos - 1, yPos);
                        parent.removeTileAt(xPos - 2, yPos);
                        parent.removeTileAt(xPos + 1, yPos);
                        parent.removeTileAt(xPos + 2, yPos);

                        setValue(value * 8);
                        parent.generateNewTile(xPos - 1, yPos);
                        parent.generateNewTile(xPos - 2, yPos);
                        parent.generateNewTile(xPos + 1, yPos);
                        parent.generateNewTile(xPos + 2, yPos);
                        updateTile();
                    } else { // FIRE ENS
                        parent.removeTileAt(xPos - 1, yPos);
                        parent.removeTileAt(xPos - 2, yPos);
                        parent.removeTileAt(xPos + 1, yPos);

                        setValue(value * 4);
                        parent.generateNewTile(xPos - 1, yPos);
                        parent.generateNewTile(xPos - 2, yPos);
                        parent.generateNewTile(xPos + 1, yPos);
                        updateTile();
                    }
                } else { // TRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos - 2, yPos);

                    setValue(value * 2);
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos - 2, yPos);
                    updateTile();
                }
            } else if (parent.getTileAt(xPos + 1, yPos).value == value) {
                if (parent.getTileAt(xPos + 2, yPos).value == value) { // FIRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos + 1, yPos);
                    parent.removeTileAt(xPos + 2, yPos);

                    setValue(value * 4);
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos + 1, yPos);
                    parent.generateNewTile(xPos + 2, yPos);
                    updateTile();
                } else { // TRE ENS
                    parent.removeTileAt(xPos - 1, yPos);
                    parent.removeTileAt(xPos + 1, yPos);

                    setValue(value * 2);
                    parent.generateNewTile(xPos - 1, yPos);
                    parent.generateNewTile(xPos + 1, yPos);
                    updateTile();
                }
            }
        } else if (parent.getTileAt(xPos + 1, yPos).value == value) {
            if (parent.getTileAt(xPos + 2, yPos).value == value) { // TRE ENS
                parent.removeTileAt(xPos + 2, yPos);
                parent.removeTileAt(xPos + 1, yPos);

                setValue(value * 2);
                parent.generateNewTile(xPos + 1, yPos);
                parent.generateNewTile(xPos + 2, yPos);
                updateTile();
            }
        }
        Log.w("COMBO", "finished horizontal combo at:" + xPos + "," + yPos);
    }

    public void executeComboVertical() {
        Log.w("COMBO", "executing vertical combo at:" + xPos + "," + yPos);
        if (parent.getTileAt(xPos, yPos - 1).value == value) {
            if (parent.getTileAt(xPos, yPos - 2).value == value) {
                if (parent.getTileAt(xPos, yPos + 1).value == value) {
                    if (parent.getTileAt(xPos, yPos + 2).value == value) { // FEM ens LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos - 2);
                        parent.removeTileAt(xPos, yPos + 1);
                        parent.removeTileAt(xPos, yPos + 2);

                        setValue(value * 8);
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos - 2);
                        parent.generateNewTile(xPos, yPos + 1);
                        parent.generateNewTile(xPos, yPos + 2);
                        updateTile();
                    } else { // FIRE ENS LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos - 2);
                        parent.removeTileAt(xPos, yPos + 1);

                        setValue(value * 4);
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos - 2);
                        parent.generateNewTile(xPos, yPos + 1);
                        updateTile();
                    }
                } else { // TRE ENS LODRET
                    parent.removeTileAt(xPos, yPos - 1);
                    parent.removeTileAt(xPos, yPos - 2);

                    setValue(value * 2);
                    parent.generateNewTile(xPos, yPos - 1);
                    parent.generateNewTile(xPos, yPos - 2);
                    updateTile();
                }
            } else {
                if (parent.getTileAt(xPos, yPos + 1).value == value) {
                    if (parent.getTileAt(xPos, yPos + 2).value == value) { // FIRE ENS LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos + 1);
                        parent.removeTileAt(xPos, yPos + 2);

                        setValue(value * 4);
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos + 1);
                        parent.generateNewTile(xPos, yPos + 2);
                        updateTile();
                    } else { // TRE ENS LODRET
                        parent.removeTileAt(xPos, yPos - 1);
                        parent.removeTileAt(xPos, yPos + 1);

                        setValue(value * 2);
                        parent.generateNewTile(xPos, yPos - 1);
                        parent.generateNewTile(xPos, yPos + 1);
                        updateTile();
                    }
                }
            }
        } else if (parent.getTileAt(xPos, yPos + 1).value == value) {
            if (parent.getTileAt(xPos, yPos + 2).value == value) { // TRE ENS LODRET
                parent.removeTileAt(xPos, yPos + 1);
                parent.removeTileAt(xPos, yPos + 2);

                setValue(value * 2);
                parent.generateNewTile(xPos, yPos + 1);
                parent.generateNewTile(xPos, yPos + 2);
                updateTile();
            }
        }
        Log.w("COMBO", "finished horizontal combo at:" + xPos + "," + yPos);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (value) {
            case 2:
                this.setBackgroundColor(Color.rgb(140,140,140));
                break;
            case 4:
                this.setBackgroundColor(Color.CYAN + Color.rgb(100, 100, 100));
                break;
            case 8:
                this.setBackgroundColor(Color.DKGRAY);
                break;
            case 16:
                this.setBackgroundColor(Color.YELLOW + Color.rgb(40, 20, 100));
                break;
            case 32:
                this.setBackgroundColor(Color.rgb(240, 130, 70));
                break;
            case 64:
                this.setBackgroundColor(Color.RED);
                break;
            case 128:
                this.setBackgroundColor(Color.rgb(125,15,30));
                break;
            case 256:
                this.setBackgroundColor(Color.rgb(200,25,150));
                break;
            case 512:
                this.setBackgroundColor(Color.rgb(100,25,170));
                break;
            case 1024:
                this.setBackgroundColor(Color.rgb(0,10,220));
                break;
            case 2048:
                this.setBackgroundColor(Color.rgb(30,250,220));
                break;
            case 4096:
                this.setBackgroundColor(Color.rgb(40,250,30));
                break;
            case 8192:
                this.setBackgroundColor(Color.rgb(30,80,25));
                break;
            default:
                this.setBackgroundColor(Color.rgb(150,150,20));
                break;
        }
        if(isSelected()) {
            this.setBackgroundColor(Color.YELLOW);
            //this.invalidate();

            /*Paint p = new Paint();
            p.setStrokeWidth(5f);
            p.setColor(Color.CYAN);
            canvas.drawLine(0, 0, width, 0, p);
            canvas.drawLine(0, 0, 0, height, p);
            canvas.drawLine(0, height, width, height, p);
            canvas.drawLine(width, 0, width, height, p);*/
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
        this.invalidate();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        //Log.w("OnClick",((Tile)v).toString());
        ArrayList<Tile> selectedTiles = parent.getSelectedTiles();

        if (selectedTiles.size() == 1 && selectedTiles.get(0) == parent.getTileAt(xPos,yPos)) {
            setSelected(false);
            Log.w("TILE ONCLICK", "X: " + ((Tile) v).getxPos() + "; Y: " + ((Tile) v).getyPos() + "; V: " + ((Tile) v).getValue() + "; Selected: " + ((Tile) v).isSelected());
            return;

        }

        if (selectedTiles.size() > 1) {
            parent.deselectAll();
            Toast.makeText(getContext(), "Error: too many selected, deselecting all", Toast.LENGTH_SHORT).show();
        } else if (selectedTiles.size() == 1) {
            if (parent.isAdjacentTile(this, selectedTiles.get(0))) {
                if (getValue() == selectedTiles.get(0).getValue()) {
                    Log.w("update", "respawned all tiles with value:" + getValue());
                    parent.respawnAllTiles();
                } else {
                    Toast.makeText(getContext(), "Swapping tiles", Toast.LENGTH_SHORT).show();
                    parent.swapTiles(this, selectedTiles.get(0), false);
                }
            } else {
                Log.w("TILE SWAP", "X1: " + this.getxPos() + ", Y1:" + this.getyPos() + "; X2: " + selectedTiles.get(0).getxPos() + ", Y2: " + selectedTiles.get(0).getyPos());
                Toast.makeText(getContext(), "Not adjacent", Toast.LENGTH_SHORT).show();
                parent.deselectAll();
            }
        } else {
            setSelected(true);
            //setBackgroundColor(Color.DKGRAY);
        }
        this.invalidate();
        Log.w("TILE ONCLICK", "X: " + ((Tile) v).getxPos() + "; Y: " + ((Tile) v).getyPos() + "; V: " + ((Tile) v).getValue() + "; V2: " + this.getValue() + "; V3: " + this.getText() + "; Selected: " + ((Tile) v).isSelected());
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
        this.setText("" + value);
        this.invalidate();
    }
}
