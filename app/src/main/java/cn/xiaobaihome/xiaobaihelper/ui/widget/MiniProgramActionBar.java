package cn.xiaobaihome.xiaobaihelper.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleObserver;

import java.util.ArrayList;
import java.util.List;

import cn.xiaobaihome.xiaobaihelper.R;
import cn.xiaobaihome.xiaobaihelper.databinding.ComponentActionbarMiniprogramBinding;
import cn.xiaobaihome.xiaobaihelper.helper.DensityUtils;

public class MiniProgramActionBar extends Toolbar implements LifecycleObserver {

    ComponentActionbarMiniprogramBinding binding;
    String title;
    private OnRightBtnClickListener onRightBtnClickListener;
    Context context;

    public MiniProgramActionBar(Context context) {
        this(context, null, 0);
    }

    public MiniProgramActionBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint({"CustomViewStyleable", "PrivateResource"})
    public MiniProgramActionBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        binding = ComponentActionbarMiniprogramBinding.inflate(LayoutInflater.from(context), this, false);
        TypedArray a = context.obtainStyledAttributes(attrs, androidx.appcompat.R.styleable.Toolbar);
        title = a.getString(androidx.appcompat.R.styleable.Toolbar_title);
        a.recycle();
        if (super.getNavigationIcon() == null) {
            setNavigationIcon(R.mipmap.icon_back);
        }
        init();
    }

    private void init() {
        addView(binding.getRoot());
        if (title != null) {
            setTitle(title);
        }
        binding.ivBack.setOnClickListener(v -> {
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).onBackPressed();
            }
        });
        binding.ivClose.setOnClickListener(v->{
            if (context instanceof Activity) {
                ((Activity) context).finish();
            }
        });
    }

    public void setEnableCloseBtn(boolean b) {
        if (b) {
            binding.ivClose.setVisibility(VISIBLE);
        } else {
            binding.ivClose.setVisibility(GONE);
        }
    }

    public void setOnCloseBtnClickListener(OnClickListener listener) {
        if (listener != null) {
            binding.ivClose.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        binding.tvTitle.setText(title);
    }

    public TextView getTitleView() {
        return binding.tvTitle;
    }

    @Override
    public void setNavigationIcon(int resId) {
        if (binding != null) {
            //super.setNavigationIcon(0);
            binding.ivBack.setImageResource(resId);
        } else {
            super.setNavigationIcon(resId);
        }
    }

    public void setCloseBtnIcon(int resId) {
        binding.ivClose.setImageResource(resId);
    }

    @Nullable
    @Override
    public Drawable getNavigationIcon() {
        if (binding != null) {
            return binding.ivBack.getDrawable();
        } else {
            return super.getNavigationIcon();
        }
    }

    @Override
    public void setNavigationIcon(@Nullable Drawable icon) {
        if (binding != null) {
            //super.setNavigationIcon(null);
            binding.ivBack.setImageDrawable(icon);
        } else {
            super.setNavigationIcon(icon);
        }
    }


    @Override
    public void setNavigationOnClickListener(OnClickListener listener) {
        binding.ivBack.setOnClickListener(listener);
    }

    public ImageView getNavigationView() {
        return binding.ivBack;
    }

    public void setOnRightBtnClickListener(OnRightBtnClickListener listener) {
        onRightBtnClickListener = listener;
    }

    public interface OnRightBtnClickListener {
        void onRightBtnClick(int position);
    }

    public static class RightBtn {

        public static final int IMAGE = 0;
        public static final int TEXT = 1;

        private final Context context;
        private final int type;
        private int icon;
        private String text;
        private int textColor = 0xFF0F0F0F;
        private View view;

        public RightBtn(Context context, int type, @DrawableRes int icon, String text) {
            this.context = context;
            this.type = type;
            this.icon = icon;
            this.text = text;
            initView();
        }

        public RightBtn(Context context, @DrawableRes int icon) {
            this.context = context;
            this.type = IMAGE;
            this.icon = icon;
            initView();
        }

        public RightBtn(Context context, String text) {
            this.context = context;
            this.type = TEXT;
            this.text = text;
            initView();
        }

        public RightBtn(Context context, int type, String text, int textColor) {
            this.context = context;
            this.type = type;
            this.text = text;
            this.textColor = textColor;
            initView();
        }

        private void initView() {
            if (type == IMAGE) {
                ImageView imageView = new ImageView(context);
                imageView.setImageResource(icon);
                imageView.setPadding(DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 8), DensityUtils.dp2px(context, 8));
                view = imageView;
            } else {
                TextView textView = new TextView(context);
                textView.setText(text);
                textView.setTextSize(16);
                textView.setTextColor(textColor);
                view = textView;
            }
        }

        public void setImageResource(@DrawableRes int resource) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageResource(resource);
            }
        }

        public View getView() {
            return view;
        }
    }

}
