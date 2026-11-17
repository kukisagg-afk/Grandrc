package com.hanks.htextview.evaporate;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import com.hanks.htextview.base.CharacterDiffResult;
import com.hanks.htextview.base.CharacterUtils;
import com.hanks.htextview.base.DefaultAnimatorListener;
import com.hanks.htextview.base.HText;
import com.hanks.htextview.base.HTextView;
import java.util.ArrayList;
import java.util.List;

public class EvaporateText extends HText {
    private ValueAnimator animator;
    float charTime = 300.0f;
    private List<CharacterDiffResult> differentList = new ArrayList();
    private long duration;
    private int mTextHeight;
    int mostCount = 20;

    public void init(HTextView hTextView, AttributeSet attrs, int defStyle) {
        super.init(hTextView, attrs, defStyle);
        ValueAnimator valueAnimator = new ValueAnimator();
        this.animator = valueAnimator;
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.animator.addListener(new DefaultAnimatorListener() {
            public void onAnimationEnd(Animator animation) {
                if (EvaporateText.this.animationListener != null) {
                    EvaporateText.this.animationListener.onAnimationEnd(EvaporateText.this.mHTextView);
                }
            }
        });
        this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float unused = EvaporateText.this.progress = ((Float) animation.getAnimatedValue()).floatValue();
                EvaporateText.this.mHTextView.invalidate();
            }
        });
        int n = this.mText.length();
        int n2 = n <= 0 ? 1 : n;
        float f = this.charTime;
        this.duration = (long) (f + ((f / ((float) this.mostCount)) * ((float) (n2 - 1))));
    }

    public void animateText(final CharSequence text) {
        this.mHTextView.post(new Runnable() {
            public void run() {
                EvaporateText evaporateText = EvaporateText.this;
                float unused = evaporateText.oldStartX = evaporateText.mHTextView.getLayout().getLineLeft(0);
                EvaporateText.super.animateText(text);
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initVariables() {
    }

    /* access modifiers changed from: protected */
    public void animateStart(CharSequence text) {
        int n = this.mText.length();
        int n2 = n <= 0 ? 1 : n;
        float f = this.charTime;
        this.duration = (long) (f + ((f / ((float) this.mostCount)) * ((float) (n2 - 1))));
        this.animator.cancel();
        this.animator.setFloatValues(new float[]{0.0f, 1.0f});
        this.animator.setDuration(this.duration);
        this.animator.start();
    }

    /* access modifiers changed from: protected */
    public void animatePrepare(CharSequence text) {
        this.differentList.clear();
        this.differentList.addAll(CharacterUtils.diff(this.mOldText, this.mText));
        Rect bounds = new Rect();
        this.mPaint.getTextBounds(this.mText.toString(), 0, this.mText.length(), bounds);
        this.mTextHeight = bounds.height();
    }

    /* access modifiers changed from: protected */
    public void drawFrame(Canvas canvas) {
        float startX;
        String str;
        int i;
        float startX2 = this.mHTextView.getLayout().getLineLeft(0);
        float startY = (float) this.mHTextView.getBaseline();
        float oldOffset = this.oldStartX;
        int maxLength = Math.max(this.mText.length(), this.mOldText.length());
        float offset = startX2;
        float oldOffset2 = oldOffset;
        int i2 = 0;
        while (i2 < maxLength) {
            if (i2 < this.mOldText.length()) {
                float f = this.progress * ((float) this.duration);
                float f2 = this.charTime;
                float pp = f / (f2 + ((f2 / ((float) this.mostCount)) * ((float) (this.mText.length() - 1))));
                this.mOldPaint.setTextSize(this.mTextSize);
                int move = CharacterUtils.needMove(i2, this.differentList);
                if (move != -1) {
                    this.mOldPaint.setAlpha(255);
                    float p = pp * 2.0f;
                    int i3 = move;
                    float distX = CharacterUtils.getOffset(i2, move, p > 1.0f ? 1.0f : p, startX2, this.oldStartX, this.gapList, this.oldGapList);
                    str = "";
                    startX = startX2;
                    i = i2;
                    canvas.drawText(this.mOldText.charAt(i2) + "", 0, 1, distX, startY, this.mOldPaint);
                } else {
                    startX = startX2;
                    int i4 = move;
                    str = "";
                    i = i2;
                    this.mOldPaint.setAlpha((int) ((1.0f - pp) * 255.0f));
                    canvas.drawText(this.mOldText.charAt(i) + str, 0, 1, oldOffset2 + ((((Float) this.oldGapList.get(i)).floatValue() - this.mOldPaint.measureText(this.mOldText.charAt(i) + str)) / 2.0f), startY - (((float) this.mTextHeight) * pp), this.mOldPaint);
                }
                oldOffset2 += ((Float) this.oldGapList.get(i)).floatValue();
            } else {
                startX = startX2;
                str = "";
                i = i2;
            }
            if (i < this.mText.length()) {
                if (!CharacterUtils.stayHere(i, this.differentList)) {
                    int alpha = (int) (((this.progress * ((float) this.duration)) - ((this.charTime * ((float) i)) / ((float) this.mostCount))) * (255.0f / this.charTime));
                    int alpha2 = 255;
                    if (alpha <= 255) {
                        alpha2 = alpha;
                    }
                    this.mPaint.setAlpha(alpha2 < 0 ? 0 : alpha2);
                    this.mPaint.setTextSize(this.mTextSize);
                    float f3 = this.progress * ((float) this.duration);
                    float f4 = this.charTime;
                    float pp2 = f3 / (f4 + ((f4 / ((float) this.mostCount)) * ((float) (this.mText.length() - 1))));
                    int i5 = this.mTextHeight;
                    canvas.drawText(this.mText.charAt(i) + str, 0, 1, offset + ((((Float) this.gapList.get(i)).floatValue() - this.mPaint.measureText(this.mText.charAt(i) + str)) / 2.0f), (((float) i5) + startY) - (((float) i5) * pp2), this.mPaint);
                }
                offset += ((Float) this.gapList.get(i)).floatValue();
            }
            i2 = i + 1;
            startX2 = startX;
        }
    }
}
