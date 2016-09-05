package com.dinuscxj.refresh;

import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.swipe.toload.core.R;

/**
 * Created by 吴同 on 2016/9/5 0005.
 */
public class TestView extends LinearLayout implements NestedScrollingParent {

    private NestedScrollingParentHelper parentHelper;
    private LayoutInflater inflater;
    private View headerView;


    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.setOrientation(LinearLayout.VERTICAL);
        parentHelper = new NestedScrollingParentHelper(this);
        inflater = LayoutInflater.from(getContext());
        headerView = inflater.inflate(R.layout.headerview, null, false);
        this.addView(headerView);
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        parentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onStopNestedScroll(View child) {
        parentHelper.onStopNestedScroll(child);
    }

    private boolean addHeight;

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        Log.d("xxx",dx+" "+dy);
        //处理子view传上来的事件
        //头部高度
        int headerHeight = headerView.getHeight();

        if (dy > 0) {
            //向上滑动

            if (Math.abs(this.getTop() - dy) <= headerHeight) {
                //header 在向上滑动的过程
                this.layout(this.getLeft(), this.getTop() - dy, this.getRight(), this.getBottom() - dy);
                if (!addHeight) {
                    //只增加一次 高度 height
                    addHeight = true;
                    ViewGroup.LayoutParams params = this.getLayoutParams();
                    params.height = headerHeight + this.getHeight();
                    this.setLayoutParams(params);
                    requestLayout();
                }
                consumed[1] += dy;
            } else {
                //当用户滑动动作太大，一次位移太大就会把parentview滑动脱离底部屏幕
                if ((this.getTop() + headerHeight) > 0) {
                    int offsetY = headerHeight + this.getTop();
                    this.layout(this.getLeft(), this.getTop() - offsetY, this.getRight(), this.getBottom() - offsetY);
                    consumed[1] += offsetY;
                }
            }
        }
        if (dy < 0) {
            //向下滑动
            if ((this.getTop() + Math.abs(dy)) <= 0) {
                //header在向下滑动的过程
                //this.gettop是负数dy也是负数所以需要+dy的绝对值
                this.layout(this.getLeft(), this.getTop() + Math.abs(dy), this.getRight(), this.getBottom() + Math.abs(dy));
                consumed[1] += dy;
            } else {
                if (this.getTop() < 0) {
                    int offsetY = Math.abs(this.getTop());
                    this.layout(this.getLeft(), this.getTop() + offsetY, this.getRight(), this.getBottom() + offsetY);
                    consumed[1] += offsetY;
                }
            }
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
//        super.onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
//        return super.onNestedFling(target, velocityX, velocityY, consumed);
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
//        return super.onNestedPreFling(target, velocityX, velocityY);
    }


}
