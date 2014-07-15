package dk.sdc.Candy2048Crush;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by sdc on 7/15/14.
 */
public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        getActionBar().hide();

    }
}