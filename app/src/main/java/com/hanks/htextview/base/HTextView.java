package com.hanks.htextview.base;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public abstract class HTextView extends TextView {
    public abstract void animateText(CharSequence charSequence);

    public abstract void setAnimationListener(AnimationListener animationListener);

    public abstract void setProgress(float f);

    public HTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public HTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
