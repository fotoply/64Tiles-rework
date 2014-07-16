package dk.sdc.Candy2048Crush;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sdc on 7/15/14.
 */
public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        getActionBar().hide();

        Grid grid = (Grid)findViewById(R.id.cvGameGrid);
        grid.generateGrid(8,8);
    }
}