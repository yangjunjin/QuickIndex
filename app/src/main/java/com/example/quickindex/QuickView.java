package com.example.quickindex;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.example.quickindexdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author : yangjunjin
 * date : 2019/8/17 0:10
 */
public class QuickView extends View {
    private static final String TAG = "QuickView";
    private String[] indexArr = {"#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z"};
    private Paint paint;
    private float cellHeight;
    private int currentIndex;
    private TextView tvDialog;
    private Handler handler = new Handler();
    private List<Friend> mList = new ArrayList<>();
    private boolean isTouch = false;//是否在触摸

    public QuickView(Context context) {
        this(context, null);
    }

    public QuickView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QuickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        /**通过dimens设置文字的规格*/
        paint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textSize));
        paint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        cellHeight = getMeasuredHeight() * 1f / indexArr.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < indexArr.length; ++i) {
            float x = getMeasuredWidth() / 2;
            float y = cellHeight / 2 + cellHeight * i + getTextHeight(indexArr[i]) / 2;
            paint.setColor(i == currentIndex ? Color.BLACK : Color.WHITE);
            canvas.drawText(indexArr[i], x, y, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                isTouch = true;
//                break;
            case MotionEvent.ACTION_MOVE:
                isTouch = true;
                int index = (int) (event.getY() / cellHeight);
                if (currentIndex != index) {
                    if (index >= 0 && index < indexArr.length) {
                        Log.i(TAG, "onTouchEvent: " + indexArr[index]);
                        if (mListener != null) {
                            textChange(indexArr[index]);
                        }
                    }
                    currentIndex = index;
                }
                break;
            case MotionEvent.ACTION_UP:
                currentIndex = -1;
                isTouch = false;
                break;
        }
        invalidate();
        return true;
    }

    private void textChange(String text) {
        for (int i = 0; i < mList.size(); ++i) {
            String firstChar = String.valueOf(mList.get(i).pinyin.charAt(0));
            if (firstChar.equalsIgnoreCase(text)) {
                mListener.select(i);
                break;
            }
        }
        showDialog(text);
    }

    /**
     * 显示选中的字母
     *
     * @param text
     */
    private boolean isAnimaing = false;

    private void showDialog(String text) {
//        handler.removeCallbacksAndMessages(null);
//        tvDialog.setText(text);
//        if (tvDialog.getVisibility() == View.GONE)
//            tvDialog.setVisibility(View.VISIBLE);
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (tvDialog.getVisibility() == View.VISIBLE)
//                    tvDialog.setVisibility(View.GONE);
//            }
//        }, 2000);

        tvDialog.setText(text);
        if (!isAnimaing) {
            showAnimateDialog(isAnimaing);
        }
        handler.removeCallbacksAndMessages(null);
        if (!isTouch) {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    showAnimateDialog(isAnimaing);
                }
            }, 1000);
        }
    }

    /**
     * 动画显示隐藏对话框
     *
     * @param animate
     */
    private void showAnimateDialog(boolean animate) {
        if (tvDialog == null) return;
        isAnimaing = animate;
        float start = 0f, end = 0f;
        if (animate) {
            start = 0.0f;
            end = 1.0f;
        } else {
            start = 1.0f;
            end = 0.0f;
        }
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(tvDialog, "scaleX", start, end);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(tvDialog, "scaleY", start, end);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animatorX, animatorY);
        set.setDuration(1000);
//        set.setInterpolator(new OvershootInterpolator());
        set.start();
    }

    private float getTextHeight(String s) {
        /**
         * start end 表示的是文字的起始位置，如果文字有一大串，表示从第start个到end个
         * bounds 文字的举行边框
         */
        Rect bounds = new Rect();
        paint.getTextBounds(s, 0, s.length(), bounds);
        return bounds.height();
    }

    /**
     * 设置数据
     *
     * @param textView
     * @param list
     */
    public void setData(TextView textView, List<Friend> list, OnSelectListener listener) {
        mListener = listener;
        tvDialog = textView;
        mList.addAll(list);
        showAnimateDialog(false);
    }

    OnSelectListener mListener;

    public interface OnSelectListener {
        void select(int position);
    }
}
