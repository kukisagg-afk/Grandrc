package com.hanks.htextview.evaporate;

import android.content.Context;
import android.graphics.Canvas;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.hanks.htextview.base.AnimationListener;
import com.hanks.htextview.base.HTextView;

public class EvaporateTextView extends HTextView {
    private EvaporateText evaporateText;

    public EvaporateTextView(Context context) {
        this(context, (AttributeSet) null);
    }

    public EvaporateTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EvaporateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    public void setAnimationListener(AnimationListener listener) {
        this.evaporateText.setAnimationListener(listener);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        EvaporateText evaporateText2 = new EvaporateText();
        this.evaporateText = evaporateText2;
        evaporateText2.init(this, attrs, defStyleAttr);
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
    }

    public void setProgress(float progress) {
        this.evaporateText.setProgress(progress);
    }

    public void animateText(CharSequence text) {
        this.evaporateText.animateText(text);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.evaporateText.onDraw(canvas);
    }
}
