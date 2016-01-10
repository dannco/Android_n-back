package dako.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class StartView extends Activity {
	
	final static int FONT_SIZE = 20;
	

	protected void onCreate(Bundle bndl) {
		super.onCreate(bndl);					
		RelativeLayout rl = new RelativeLayout(this);
        rl.setBackgroundColor(Color.BLACK);
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.CENTER);
        tv.setText("N-BACK TEST\n\nTAP SCREEN TO BEGIN");
        tv.setTextSize(FONT_SIZE);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextColor(Color.WHITE);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl.addView(tv,lp);        
		rl.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View arg0) {
				goToSetup();
			}
		});		
		setContentView(rl);		
	}
	protected void goToSetup() {
		Intent inte = new Intent(StartView.this,Setup.class);
		startActivityForResult(inte,1);
	}
    protected void onActivityResult(int req, int res, Intent data) {
        finish();
    }
}
