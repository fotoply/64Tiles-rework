package dk.sdc.Candy2048Crush;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.reflect.Field;

/**
 * Created by sdc on 7/15/14.
 */
public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        getActionBar().hide();

        final Grid grid = (Grid)findViewById(R.id.cvGameGrid);
        grid.generateGrid(8,8);

        Button buNewGame = (Button)findViewById(R.id.buttonNewGame);
        Button buBack = (Button) findViewById(R.id.buttonBack);

        buBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(grid.gameOver) {
                    finish();
                } else {
                    new AlertDialog.Builder(getApplicationContext())
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
                            .setNegativeButton("No", null)
                            .show();
                }
            }
        });
    }
}