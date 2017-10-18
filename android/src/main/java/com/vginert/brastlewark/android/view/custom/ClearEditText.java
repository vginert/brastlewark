package com.vginert.brastlewark.android.view.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * EditText tha use the right drawable as clear button
 *
 * @author Vicente Giner Tendero
 */

public class ClearEditText extends android.support.v7.widget.AppCompatEditText {

    int actionX, actionY;

    private EditTextClearListener editTextClearListener;

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable clearDrawable = this.getCompoundDrawables()[2];

        if (clearDrawable != null) {
            if (this.hasFocus()) {
                clearDrawable.setAlpha(255);
            } else {
                clearDrawable.setAlpha(0);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();

            Drawable drawableRight = this.getCompoundDrawables()[2];

            if (drawableRight != null && this.hasFocus()) {

                bounds = drawableRight.getBounds();

                int x, y;
                int extraTapArea = 13;

                x = actionX + extraTapArea;
                y = actionY - extraTapArea;

                x = getWidth() - x;

                if(x <= 0){
                    x += extraTapArea;
                }

                if (y <= 0)
                    y = actionY;

                if (bounds.contains(x, y)) {
                    this.clear();
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return false;
                }
                return super.onTouchEvent(event);
            }

        }
        return super.onTouchEvent(event);
    }

    public void clear() {
        this.setText("");
        this.clearFocus();
        ((InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE))
                .toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        if(editTextClearListener != null) {
            editTextClearListener
                    .onClear(this);
        }
    }

    public void setEditTextClearListener(EditTextClearListener editTextClearListener) {
        this.editTextClearListener = editTextClearListener;
    }

    public interface EditTextClearListener {
        void onClear(View view);
    }
}
