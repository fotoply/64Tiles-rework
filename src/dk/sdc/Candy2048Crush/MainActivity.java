package dk.sdc.Candy2048Crush;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Log.w("MADEBY", "Made by 3 people on UNF SDC 2014. All source code can be found on https://github.com/sdc2014/Candy2048Crush. It will not be updated by any of the original developers after the 18th July 2014. All art and code was developed and written in 1 week.");

        getActionBar().hide();

        Button buStartGame = (Button) findViewById(R.id.buGameStart);
        buStartGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(intent);
            }
        });
    }
}
