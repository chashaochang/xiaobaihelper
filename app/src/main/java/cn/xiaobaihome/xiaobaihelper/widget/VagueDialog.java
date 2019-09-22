package cn.xiaobaihome.xiaobaihelper.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import cn.xiaobaihome.xiaobaihelper.R;

/**
 * Author: yubin
 * Time:2018/8/1 11:50
 * Tips: 高斯模糊背景对话框
 */
public class VagueDialog extends Dialog {

    private AdapterView.OnItemClickListener mItemClickListener;

    public VagueDialog setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
        return this;
    }

    public VagueDialog setOnDismiss(OnDismissListener dismissListener) {
        setOnDismissListener(dismissListener);
        return this;
    }

    /**
     * 全屏的模糊弹窗
     *
     * @param context
     * @param res
     */
    public VagueDialog(@NonNull Activity context, @LayoutRes int res) {
        this(context, context.getWindow().getDecorView(), -1, res);
    }

    /**
     * @param context
     * @param blurredView 模糊之前的View
     * @param vagueHeight 距离底部的高度 -1时全屏模糊
     * @param res         弹出的布局  需包含id blurring_view rl_button rl_button ll_release iv_close
     */
    public VagueDialog(@NonNull Context context, View blurredView, int vagueHeight, @LayoutRes int res) {
        super(context, R.style.circle_vague_dialog_style);
        //获取当前Activity所在的窗体
        try {
            Window dialogWindow = getWindow();
            //设置Dialog从窗体底部弹出
            dialogWindow.setGravity(Gravity.BOTTOM);
            dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            View view = View.inflate(context, res, null);
            //获取View 的id
            int bv = context.getResources().getIdentifier("blurring_view", "id", context.getPackageName());
            BlurringView mBlurringView = view.findViewById(bv);

            //给出了模糊视图并刷新模糊视图。
            if (blurredView != null) {
                mBlurringView.setBlurredView(blurredView, vagueHeight);
                mBlurringView.invalidate();
            }

            view.measure(0, 0);
            if (vagueHeight != -1) {
                lp.height = view.getMeasuredHeight();
            }
            dialogWindow.setAttributes(lp);
            if (vagueHeight == -1) {
                //获取View 的id
                int rl = context.getResources().getIdentifier("rl_button", "id", context.getPackageName());
                RelativeLayout rl_button = view.findViewById(rl);
                rl_button.setAnimation(AnimationUtils.loadAnimation(context, R.anim.pop_enter_anim));
            } else
                dialogWindow.setWindowAnimations(R.style.popupwindow);  //添加动画

            //获取View 的id
            int ll = context.getResources().getIdentifier("ll_release", "id", context.getPackageName());
            LinearLayout ll_release = view.findViewById(ll);
            for (int i = 0; i < ll_release.getChildCount(); i++) {
                final int finalI = i;
                View childAt = ll_release.getChildAt(i);
                childAt.setOnClickListener(view1 -> {
                    mItemClickListener.onItemClick(null, childAt, finalI, finalI);
                    dismiss();
                });
            }
            //获取View 的id
//            int iv_close = context.getResources().getIdentifier("iv_close", "id", context.getPackageName());
//            ImageView ivClose = view.findViewById(iv_close);
//            ivClose.setOnClickListener(view1 -> dismiss());

            setContentView(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}