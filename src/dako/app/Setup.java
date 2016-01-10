package dako.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Setup extends Activity {
	protected void onCreate(Bundle bndl) {
		super.onCreate(bndl);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10, 30, 10, 30);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        
        
        TextView tv = new TextView(this);
        tv.setText("Setup menu");
        ll.addView(tv);
        
        Button startButton = new Button(this);
        startButton.setText("Start test");
        startButton.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				startTest();
			}
		});
        ll.addView(startButton);
		setContentView(ll);		
	}
	protected void startTest() {
		Intent inte = new Intent(Setup.this, Main.class);
		startActivityForResult(inte, 0);
	}
	protected void onActivityResult(int req, int res, Intent data) {
	}
}
