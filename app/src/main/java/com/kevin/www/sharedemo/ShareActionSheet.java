package com.kevin.www.sharedemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * author：wangzihang
 * date： 2017/7/20 15:46
 * desctiption：分享底部弹出popupwindow
 * e-mail：wangzihang@xiaohongchun.com
 */

public class ShareActionSheet extends PopupWindow implements View.OnClickListener {


    //吊起普通类型的回调
    public interface showNormalSharePopListener {
        void showNormalShareClick();
    }

    public void setNormalShareClick(showNormalSharePopListener shareClick) {
        this.normalSharePopListener = shareClick;
    }

    public interface OnShareSnsClickListener {
        void onSnsClick(int type);
    }

    public void setOnShareSnsClickListener(OnShareSnsClickListener l) {
        this.l = l;
    }

    private showNormalSharePopListener normalSharePopListener;
    private OnShareSnsClickListener l;
    private View root;
    private LinearLayout menuContainer;
    private ImageView bgImageView;
    private Context context;
    private TextView tv_share_towhere;

    private Button wechat, timeline, cancel;

    public ShareActionSheet(Activity context, ShareEntity shareEntity) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        root = inflater.inflate(R.layout.share_action_sheet, null);

        wechat = (Button) root.findViewById(R.id.share_action_sheet_wechat);
        timeline = (Button) root.findViewById(R.id.share_action_sheet_timeline);
        cancel = (Button) root.findViewById(R.id.share_action_sheet_cancel);
        tv_share_towhere = (TextView) root.findViewById(R.id.tv_share_towhere);
        tv_share_towhere.setText(shareEntity.shareTitle);
        wechat.setOnClickListener(this);
        timeline.setOnClickListener(this);
        cancel.setOnClickListener(this);
        menuContainer = (LinearLayout) root
                .findViewById(R.id.share_action_sheet_button_container);
        bgImageView = (ImageView) root.findViewById(R.id.share_action_sheet_bg);

        // 设置SelectPicPopupWindow的View
        this.setContentView(root);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(Color.argb(0, 0, 0, 0));
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        // mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        root.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = menuContainer.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        invokeStopAnim();
                    }
                }
                return true;
            }
        });
    }

    public boolean showNormalSharePop;

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        invokeStartAnim();
    }


    @Override
    public void onClick(View v) {
        if (!(context instanceof MainActivity) || l == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.share_action_sheet_timeline:
                l.onSnsClick(MainActivity.TIMELINE);
                this.dismiss();
                break;
            case R.id.share_action_sheet_wechat:
                l.onSnsClick(MainActivity.WECHAT);
                this.dismiss();
                break;
            case R.id.share_action_sheet_cancel:
                invokeStopAnim();
                break;
        }
    }


    private void invokeStartAnim() {
        menuContainer.setVisibility(View.VISIBLE);
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f);

        translateAnimation.setDuration(300);
        animationSet.addAnimation(translateAnimation);
        menuContainer.startAnimation(animationSet);

        bgImageView.setVisibility(View.VISIBLE);
        AnimationSet animationSet1 = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300);
        animationSet1.addAnimation(alphaAnimation);
        bgImageView.startAnimation(alphaAnimation);
    }

    private void invokeStopAnim() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f);

        translateAnimation.setDuration(300);
        animationSet.addAnimation(translateAnimation);
        menuContainer.startAnimation(animationSet);

        AnimationSet animationSet1 = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(300);
        animationSet1.addAnimation(alphaAnimation);
        bgImageView.startAnimation(alphaAnimation);

        mHandler.sendEmptyMessageDelayed(DISMISS, 300);
    }

    protected final int DISMISS = 1212;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case DISMISS:
                    dismiss();
                    if (showNormalSharePop && normalSharePopListener != null) {
                        normalSharePopListener.showNormalShareClick();
                    }
                    break;
            }
        }
    };
}
