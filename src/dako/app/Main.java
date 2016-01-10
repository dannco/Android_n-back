package dako.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity{
	protected void onCreate(Bundle bndl) {
		super.onCreate(bndl);	
		LinearLayout ll = new LinearLayout(this);
		TextView tv = new TextView(this);
		tv.setText("TEST");
		ll.addView(tv);
		setContentView(ll);
	}
}
