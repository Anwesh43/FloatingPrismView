package com.anwesome.games.prisview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by anweshmishra on 10/11/16.
 */
public class PrisButton {
    private float x,y,r = 100,rot=0,rotSpeed=0,opaqueRadius=0;
    private Bitmap icon = null;
    private PrisButtonClickListener buttonClickListener;
    private int color = 0;
    private boolean startOpaqueAnim = false;
    private View currentClickedView = null;
    private PrisButton() {

    }
    public static PrisButton newInstance() {
        return new PrisButton();
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y) {
        this.y = y;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public void setR(float r) {
        this.r = r;
    }
    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }
    public void draw(Canvas canvas, Paint paint) {
        if(color!=0) {
            paint.setAlpha(255);
            paint.setColor(color);

        }
        canvas.drawCircle(x,y,r,paint);
        if(startOpaqueAnim) {
            paint.setColor(Color.argb(PrismConstants.BUTTON_OPACITY,255,255,255));
            canvas.drawCircle(x,y,opaqueRadius,paint);
            opaqueRadius+=r/PrismConstants.OPAQUE_SCALE;
            if(opaqueRadius >= (PrismConstants.OPAQUE_LIMIT_SCALE)*(r/PrismConstants.OPAQUE_SCALE)) {
                startOpaqueAnim = false;
                opaqueRadius = 0;
                buttonClickListener.onClick(currentClickedView);
            }

        }
        if(icon!=null) {
            canvas.save();
            canvas.translate(x,y);
            canvas.rotate(rot);
            canvas.drawBitmap(icon, new Rect(0, 0, icon.getWidth(), icon.getHeight()), new RectF(-r / 2, -r / 2, r / 2, r / 2), paint);
            canvas.restore();
            rot+=PrismConstants.ROTATION_SPEED*rotSpeed;
        }
    }
    public void setRotSpeed(float rotSpeed) {
        this.rotSpeed = rotSpeed;
    }
    public void setOnClickListener(PrisButtonClickListener listener) {
        this.buttonClickListener = listener;
    }
    public void click(View view) {
        if(view instanceof PrisView && buttonClickListener != null && !startOpaqueAnim) {
            currentClickedView = view;
            startOpaqueAnim = true;

        }
    }
    public boolean contains(float x,float y) {
        return this.x>=x-r && this.x<=x+r && this.y>=y-r && this.y<=y+r;
    }
    public int hashCode() {
        return (icon!=null?icon.hashCode():0)+(int)x+(int)y+(int)r+color;
    }
}
