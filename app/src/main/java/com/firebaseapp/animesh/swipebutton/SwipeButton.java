package com.firebaseapp.animesh.swipebutton;

import android.content.Context;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SwipeButton extends android.support.v7.widget.AppCompatButton {
    private final int gradientWidth = 50;
    //default values for button configuration
    private int negativeBackgroundColor = 0xFFFF0000;
    private int positiveBackgroundColor = 0xFF006300;
    private int textColor = 0xFFFFFFFF;
    private double actionConfirmDistanceFraction = 0.5;
    private String positiveText = "yes";
    private String negativeText = "no";

    private float x1; //x coordinate of where user first touches the button
    private boolean negative = true;//boolean checking whether the button is currently positive or negative
    private boolean textAltered; //boolean indicating whether the text was already changed during motion

    public SwipeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setButtonTextColor(textColor);
        setBackgroundColor(negativeBackgroundColor);
        setNegativeText(negativeText);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: { // when user first touches the screen get x coordinate
                x1 = event.getX();
                textAltered = false;
                break;
            }
            case MotionEvent.ACTION_MOVE: {//update background color while swiping
                float x2 = event.getX();

                int[] backGroundColors =
                        x1 < x2 == negative ?
                                new int[]{negativeBackgroundColor, positiveBackgroundColor}
                                : new int[]{positiveBackgroundColor, negativeBackgroundColor};
                Shader shader = new LinearGradient(x2, 0, x2 - gradientWidth, 0, backGroundColors,
                        new float[]{0, 1}, Shader.TileMode.CLAMP);

                ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
                mDrawable.getPaint().setShader(shader);
                this.setBackgroundDrawable(mDrawable);

                if (Math.abs(x1 - x2) >= this.getWidth() * actionConfirmDistanceFraction && !textAltered) {
                    textAltered = true;
                    this.setText(negative ? positiveText : negativeText);
                }

                break;
            }
            case MotionEvent.ACTION_UP: { //when the user releases finalize button design
                negative = !negative;
                this.setBackgroundColor(negative ? negativeBackgroundColor : positiveBackgroundColor);
                if (!textAltered) {
                    this.setText(negative ? negativeText : positiveText);
                }
                break;
            }
        }
        return super.onTouchEvent(event);
    }

    public void setNegativeBackgroundColor(int negativeBackgroundColor) {
        this.negativeBackgroundColor = negativeBackgroundColor;
        setBackgroundColor(negativeBackgroundColor);
    }

    public void setPositiveBackgroundColor(int positiveBackgroundColor) {
        this.positiveBackgroundColor = positiveBackgroundColor;
    }

    public void setButtonTextColor(int textColor) {
        this.textColor = textColor;
        setTextColor(textColor);
    }

    public void setActionConfirmDistanceFraction(double actionConfirmDistanceFraction) {
        this.actionConfirmDistanceFraction = actionConfirmDistanceFraction;
    }

    public void setPositiveText(String positiveText) {
        this.positiveText = positiveText;
    }

    public void setNegativeText(String negativeText) {
        this.negativeText = negativeText;
        setText(negativeText);
    }
}
