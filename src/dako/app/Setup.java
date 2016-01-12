package dako.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Setup extends Activity {
    
    final static int N_MAX = 10;
    final static int N_DEFAULT = 2;
    final static int DUR_MAX = 16;
    final static int DUR_OFFSET = 5;
    final static int DUR_DEFAULT = 3;
    
    private static int nVal = N_DEFAULT;
    private static int dur = DUR_DEFAULT; 
    
    private NumberPicker nPicker;
    private NumberPicker durPicker; 
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setPadding(10, 30, 10, 30);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        ll.setLayoutParams(params);
        
        LinearLayout lengthSpan1 = new LinearLayout(this);
        lengthSpan1.setGravity(Gravity.CENTER_HORIZONTAL);
        lengthSpan1.setPadding(10, 10, 10, 10);     
        
        LinearLayout col1 = new LinearLayout(this);
        col1.setOrientation(LinearLayout.VERTICAL);
        col1.setPadding(0, 0, 25, 0);
        TextView a1 = new TextView(this);
        a1.setText("N-value:");
        col1.addView(a1);
        
        nPicker = new NumberPicker(this);
        nPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        nPicker.setMinValue(1);
        nPicker.setMaxValue(N_MAX);
        nPicker.setValue(nVal);
        col1.addView(nPicker);

        LinearLayout col2 = new LinearLayout(this);
        col2.setOrientation(LinearLayout.VERTICAL);
        col2.setPadding(25,0,0,0);
        TextView a2 = new TextView(this);
        a2.setText("Interval duration");
        col2.addView(a2);
        
        durPicker = new NumberPicker(this);
        durPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        durPicker.setMinValue(1);
        durPicker.setMaxValue(DUR_MAX);
        durPicker.setValue(dur);
        String[] vals = new String[DUR_MAX];
        for (int i = 0; i < vals.length; i++) {
            vals[i] = ""+(i+DUR_OFFSET)/10.0;
        }
        durPicker.setDisplayedValues(vals);
        col2.addView(durPicker);
        
        lengthSpan1.addView(col1);
        lengthSpan1.addView(col2);
        ll.addView(lengthSpan1);
        
        Button startButton = new Button(this);
        startButton.setText("Start");
        startButton.setOnClickListener(new View.OnClickListener() {         
            @Override
            public void onClick(View v) {
                startTest();
            }
        });
        RelativeLayout rl = new RelativeLayout(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        rl.addView(ll, lp);

        lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rl.addView(startButton, lp);
        setContentView(rl);
    }
    protected void startTest() {
        
        nVal = nPicker.getValue();
        dur = durPicker.getValue();
        
        Intent inte = new Intent(Setup.this, Main.class);
        inte.putExtra("N_VAL",nVal);
        inte.putExtra("DUR",dur*100);
        
        startActivityForResult(inte, 0);
    }
    protected void onActivityResult(int req, int res, Intent data) {
    }
}
