package dako.app;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.view.View;
import android.widget.LinearLayout;
import android.graphics.*;

public class Main extends Activity{
    final static int INIT_V = 9;
    
    SequenceDisplay disp;
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.BLACK);
        
        int n = getIntent().getIntExtra("N_VAL", 5);
        int dur = getIntent().getIntExtra("DUR", 1000);
        
        disp = new SequenceDisplay(this,n,dur);
        disp.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				if (disp.tapped==0) disp.tapped = 1;
			}
		});
        
        ll.addView(disp);
        setContentView(ll);
        promptStart(n);
    }
    private void promptStart(int n) {
        new AlertDialog.Builder(this)
        .setTitle("Press \"GO\" when ready...")
        .setPositiveButton("GO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                startSequence();
            }
        })
        .show();
    }
    
    private void startSequence() {
        disp.startAnimation();
    }
    
    public void onBackPressed() {    
    	disp.running = false;
        finish();
    }
    
    
    protected static class SequenceDisplay extends View {
        final static int TRIES_AMOUNT = 3;
        final static int INIT_MOD = 10;
		
        protected boolean running;
		protected int tapped;
        static Random rng = new Random();
        
        private final Paint backgroundPaint;
        private final Paint progressPaint;
        private final Paint textPaint;

        private long startTime;
        private long currentTime;
        private long maxTime;

        private long progressMillisecond;
        private double progress;

        private RectF circleBounds;
        private float radius;
        private float handleRadius;
        private float textHeight;
        private float textOffset;
        
        private final Handler viewHandler;
        private Runnable updateView;
        
        static final int RED = 0xFFCC0000;       
        static final int GREEN = 0xFF00CC00;
        static final int YELLOW = 0xFFCCCC00;

        private int[] seq;
        private int pos;
        
        private int hits;
        private int tries;
        private int mod;
        
        private boolean first;
        private int current;
        
        public SequenceDisplay(
          Context context, int nValue, int duration
        ) {
            super(context);
            
            seq = new int[nValue];
            for (int i = 0; i < seq.length; i++) {
                seq[i] = -1;
            }
            mod = INIT_MOD;
            circleBounds = new RectF();

            radius = 150;
            handleRadius = 10;

            maxTime = duration;

            backgroundPaint = new Paint();
            backgroundPaint.setStyle(Paint.Style.STROKE);
            backgroundPaint.setAntiAlias(true);
            backgroundPaint.setStrokeWidth(10);
            backgroundPaint.setStrokeCap(Paint.Cap.SQUARE);
            backgroundPaint.setColor(Color.parseColor("#000000"));

            progressPaint = new Paint();
            progressPaint.setStyle(Paint.Style.STROKE);
            progressPaint.setAntiAlias(true);
            progressPaint.setStrokeWidth(10);
            progressPaint.setStrokeCap(Paint.Cap.SQUARE);
            progressPaint.setColor(YELLOW);            

            textPaint = new TextPaint();
            textPaint.setTextSize(radius / 2);
            textPaint.setColor(YELLOW);
            textPaint.setTextAlign(Paint.Align.CENTER);

            textHeight = textPaint.descent() - textPaint.ascent();
            textOffset = (textHeight / 2) - textPaint.descent();

            viewHandler = new Handler();
        }
        
        protected void startAnimation() {
            first = true;
            running = true;
            updateView = new Runnable(){
                @Override
                public void run(){
                	if (!running) return;
                    if (first) {
                        try {
                            Thread.sleep(300);
                            tries = TRIES_AMOUNT;
                            hits = 0;
                            startTime = System.currentTimeMillis();
                            first = false;
                            progress = -1;
							current = Math.abs(rng.nextInt()%10);                            
                        } catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    if (tapped==1) {
                    	tapped = 2;
                    	int color = current == seq[pos]?GREEN:RED;
                    	progressPaint.setColor(color);
                    	textPaint.setColor(color);
                    	if (color==RED) tries--;
                    }

                    currentTime = System.currentTimeMillis();
                    progressMillisecond = (currentTime - startTime) % maxTime;
                    double tmp = (double) progressMillisecond / maxTime;
                    
                    if (tmp < progress) {
                    	if (tapped == 0 && seq[pos]==current) tries--;                    
                    	tapped = 0;
                    	progressPaint.setColor(YELLOW);
                    	textPaint.setColor(YELLOW);
                    	
                    	seq[pos] = current;                    	                    	
                        pos = ++pos == seq.length?0:pos;
                        
                        if (Math.abs(rng.nextInt())%mod--==0) {
                        	current = seq[pos];
                        	mod = INIT_MOD;
                        } else {
                        	current = Math.abs(rng.nextInt()%10);
                        }                       
                        if (tries <= 0) {
                            running = false;
                            try {
                                Thread.sleep(200);
                                Activity act = ((Activity) getContext());
                                Intent inte = new Intent();
                                inte.putExtra("HITS",hits);
                                act.setResult(RESULT_OK,inte);
                                act.finish();
                                return;
                            } catch (InterruptedException e){
                                Thread.currentThread().interrupt();
                                return;
                            }
                        }
                    }

                    progress = tmp;
                    SequenceDisplay.this.invalidate();
                    viewHandler.postDelayed(updateView, 1000/60);
                    
                }
            };
            viewHandler.post(updateView);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            float centerWidth = canvas.getWidth() / 2;
            float centerHeight = canvas.getHeight() / 2;
            
            circleBounds.set(centerWidth - radius,
                centerHeight - radius,
                centerWidth + radius,
                centerHeight + radius);
            canvas.drawCircle(centerWidth, centerHeight, radius, backgroundPaint);
            canvas.drawArc(circleBounds, -90, (float)(progress*360), false, progressPaint);
            if (pos < seq.length) {
                String text = ""+current;
                canvas.drawText(text,
                    centerWidth,
                    centerHeight + textOffset,
                    textPaint);
            }
            canvas.drawCircle((float)(centerWidth  + (Math.sin(progress * 2 * Math.PI) * radius)),
                              (float)(centerHeight - (Math.cos(progress * 2 * Math.PI) * radius)),
                              handleRadius,
                              progressPaint);
        }
    }
}
