package dk.sdc.Candy2048Crush;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created at UNF SDC on 7/15/14. Full sourcecode is aviable on github at https://github.com/sdc2014/Candy2048Crush
 */
public class GameActivity extends Activity {
    private Grid grid;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        getActionBar().hide();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();

        grid = (Grid) findViewById(R.id.cvGameGrid);
        grid.parent = this;
        grid.gameOver = false;
        grid.tileList.clear();
        grid.generateGrid(8, 8);
        grid.autofixTiles();

        getSavedHighscore();

        Button buNewGame = (Button) findViewById(R.id.buttonNewGame);
        buNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grid.gameOver) {
                    grid.tileList.clear();
                    grid.gameOver = false;
                    grid.generateGrid(8, 8);
                } else {
                    new AlertDialog.Builder(GameActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("New game?")
                            .setMessage("Are you sure you want to start over?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    grid.tileList.clear();
                                    grid.gameOver = false;
                                    grid.generateGrid(8, 8);
                                }

                            })
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });

        Button buBack = (Button) findViewById(R.id.buttonBack);

        buBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grid.gameOver) {
                    finish();
                } else {
                    new AlertDialog.Builder(GameActivity.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Quit?")
                            .setMessage("Are you sure you want to quit?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    //Stop the activity
                                    finish();
                                }

                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    grid.printAllTiles();
                                }
                            })
                            .show();
                }
            }
        });

    }

    /**
     * Sets the saved high score.
     */
    public void setSavedHighscore() {
        if (sp.getInt("highscore", 0) < grid.getHighscore()) {
            editor.putInt("highscore", grid.getHighscore());
        }
        TextView tv = (TextView) findViewById(R.id.textViewHighScore);
        tv.setText("Highscore: " + sp.getInt("highscore", 0));
        editor.commit();
    }

    /**
     * Sets the score to score
     *
     * @param score The score you want to set.
     */
    public void setScore(int score) {
        TextView tv = (TextView) findViewById(R.id.textViewScore);
        tv.setText("Score: " + score);
    }

    /**
     * Gets the high score form shared preferences.
     *
     * @return The high score that was saved in shared preferences.
     */
    public int getSavedHighscore() {
        TextView tv = (TextView) findViewById(R.id.textViewHighScore);
        tv.setText("Highscore: " + sp.getInt("highscore", 0));
        return sp.getInt(("hihgscore"), 0);
    }
}