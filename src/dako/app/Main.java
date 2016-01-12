package dako.app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Main extends Activity{
    final static INIT_V = 9;
    
    SequenceDisplay disp;
    
    protected void onCreate(Bundle bndl) {
        super.onCreate(bndl);
        LinearLayout ll = new LinearLayout(this);
        ll.setBackgroundColor(Color.BLACK);
        
        int n = getIntent().getIntExtra("N_VAL", 5);
        int dur = getIntent().getIntExtra("DUR", 1000);
        disp = new SequenceDisplay(this,n,dur);
        
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
    
    protected static class SequenceDisplay extends View {
        final static  TRIES_AMOUNT = 4;
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
        
        
        private int[] seq;
        private int pos;
        
        private int tries;
        private boolean first;
        private boolean started;
        
        public SequenceDisplay(
          Context context, int nValue, int duration
        ) {
            super(context);
            
            started = false;
            seq = new int[nValue];
            
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
            progressPaint.setColor(Color.parseColor("#FCFD59"));

            textPaint = new TextPaint();
            textPaint.setTextSize(radius / 2);
            textPaint.setColor(0xFFFCFD59);
            textPaint.setTextAlign(Paint.Align.CENTER);

            textHeight = textPaint.descent() - textPaint.ascent();
            textOffset = (textHeight / 2) - textPaint.descent();

            viewHandler = new Handler();
        }
        
        protected void startAnimation() {
            started = true;
            first = true;
            updateView = new Runnable(){
                @Override
                public void run(){
                    if (first) {
                        try {
                            Thread.sleep(300);
                            tries = TRIES_AMOUNT;
                            startTime = System.currentTimeMillis();
                            first = false;
                            progress = -1;
							
                        } catch (InterruptedException e){
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }

                    currentTime = System.currentTimeMillis();
                    progressMillisecond = (currentTime - startTime) % maxTime;
                    double tmp = (double) progressMillisecond / maxTime;
                    
                    if (tmp < progress) {
						pos = ++pos == seq.length?0:pos;
                        if (tries == 0) {
                            tries = TRIES_AMOUNT;
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
                String text = started?""+seq[pos]:" ";
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
    
    /*
    if (Math.abs(rng.nextInt())%(v--)==0) {
        // set next value equal to popped
        v = INIT_V;
    }
    else {
        // set next value to any value not equal to popped.
    } 
    
    */
}
