package dk.sdc.Candy2048Crush;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

/**
 * Created by sdc on 7/16/14.
 */
public class TileTest extends TextView {
    public TileTest(Context context) {
        super(context);
        this.setText(""+(int)(Math.random()*20));
        this.setMaxHeight(120);
        this.setBackgroundColor(Color.BLUE);
        this.setHeight(120);
    }

    public void onTileClick(View v) {
        Log.w("Test",this.getText()+"");
    }
}