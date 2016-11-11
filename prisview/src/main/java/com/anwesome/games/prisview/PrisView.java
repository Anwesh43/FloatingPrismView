package com.anwesome.games.prisview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by anweshmishra on 10/11/16.
 */
public class PrisView extends View {
    private int color = Color.parseColor("#673AB7");
    private int fixedDistanceFromCenter,distanceFromCenter = 0,radiusOfButtons = 100,toggleSpeed = 10;
    private ConcurrentLinkedQueue<PrisButton> buttons = new ConcurrentLinkedQueue<>();
    private int time = 0,collapseDir = -1,moveTime = 0;
    private final static int  NUMBER_OF_MOVES = 5;
    private boolean startMoving = false,invalidatedAtFirst = false,shallInvalidate = false;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private PrisButton midButton = PrisButton.newInstance();
    public PrisView(Context context) {
        super(context);
    }
    public PrisView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }
    public PrisView(Context context,int color) {
        super(context);
        this.color = color;

    }
    public void onLayout(boolean changed,int a,int b,int w,int h) {
        if(changed) {
            invalidate();
            int smallerDimension = (w>h)?h:w;
            radiusOfButtons = smallerDimension/PrismConstants.RADIUS_SCALE;
            fixedDistanceFromCenter = smallerDimension/PrismConstants.DISTANCE_FROM_CENTER_SCALE-radiusOfButtons;
        }
        toggleSpeed = (fixedDistanceFromCenter)/NUMBER_OF_MOVES;

    }
    public void onDraw(Canvas canvas) {

        if(time == 0) {
            init(canvas.getWidth(),canvas.getHeight());
        }
        if(!invalidatedAtFirst) {
            invalidatedAtFirst = true;
        }
        if(startMoving) {
            distanceFromCenter+=collapseDir*toggleSpeed;
            moveTime++;
            if(moveTime == NUMBER_OF_MOVES) {
                moveTime = 0;
                startMoving = false;
                midButton.setRotSpeed(0);
                if(collapseDir==1 && distanceFromCenter >= fixedDistanceFromCenter) {
                    distanceFromCenter = fixedDistanceFromCenter;
                }
                else if(collapseDir == -1 && distanceFromCenter <= 0) {
                    distanceFromCenter = 0;
                }
                shallInvalidate = false;

            }
        }
        float deg = 0;
        if(buttons.size()!=0) {
                deg = 360/(buttons.size());
            }
        int index = 0;
        for(PrisButton button:buttons) {
            paint.setColor(color);
            float x = canvas.getWidth()/2+(float)(distanceFromCenter*Math.cos(index*deg*Math.PI/180));
            float y = canvas.getWidth()/2+(float)(distanceFromCenter*Math.sin(index*deg*Math.PI/180));
            button.setX(x);
            button.setY(y);
            button.draw(canvas,paint);
            index++;
        }
        paint.setColor(color);
        midButton.draw(canvas,paint);
        time++;

        if(shallInvalidate) {
            try {
                Thread.sleep(PrismConstants.DELAY);

            }
            catch (Exception ex) {

            }
            invalidate();
        }
    }
    private void init(int w,int h) {
        midButton.setX(w/2);
        midButton.setY(h/2);
        midButton.setIcon(BitmapFactory.decodeResource(getResources(),R.drawable.icon_white_thick));
        midButton.setR(radiusOfButtons);
        for(PrisButton button:buttons) {
            button.setX(w/2);
            button.setY(h/2);
            button.setR(radiusOfButtons);
        }
    }
    public void addButton(PrisButton...buttons){
        for(PrisButton button:buttons) {
            this.buttons.add(button);
        }
    }
    private void toggle() {
        if(!startMoving) {
            collapseDir*=-1;
            startMoving = true;
            midButton.setRotSpeed(collapseDir);
            shallInvalidate = true;
            postInvalidate();
        }
    }
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (midButton.contains(event.getX(), event.getY())) {
                toggle();
            } else {
                for (PrisButton button : buttons) {
                    if (button.contains(event.getX(), event.getY())) {
                        shallInvalidate = true;
                        button.click(this);
                        postInvalidate();
                        break;
                    }
                }
            }
        }
        return true;
    }
}
