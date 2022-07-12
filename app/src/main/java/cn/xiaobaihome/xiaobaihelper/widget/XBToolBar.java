package cn.xiaobaihome.xiaobaihelper.ui.widget;

import android.annotation.SuppressLint;
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
import cn.xiaobaihome.xiaobaihelper.databinding.ComponentToolbarSimpleBinding;
import cn.xiaobaihome.xiaobaihelper.helper.DensityUtils;

/**
 * wiki https://wiki.517cdn.com/index.php/%E8%8A%92%E6%9E%9C%E5%9C%A8%E7%BA%BFApp%EF%BC%88Android%E7%89%88%EF%BC%89/product/%E8%8A%92%E6%9E%9C%E5%9C%A8%E7%BA%BF2020/product/%E8%8A%92%E6%9E%9C%E5%9C%A8%E7%BA%BF2020_%E5%8A%9F%E8%83%BD%E6%96%87%E6%A1%A3/product/%E9%A1%B6%E9%83%A8%E5%AF%BC%E8%88%AA%E6%A0%8F%E5%B0%81%E8%A3%85(SimpleToolBar)
 */
public class XBToolBar extends Toolbar implements LifecycleObserver {

    ComponentToolbarSimpleBinding binding;
    String title;
    String subTitle;
    int navigationIcon;
    private final List<RightBtn> rightBtnList = new ArrayList<>();
    private OnRightBtnClickListener onRightBtnClickListener;
    Context context;
    private boolean enableSubTitle = true;

    public XBToolBar(Context context) {
        this(context, null, 0);
    }

    public XBToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint({"CustomViewStyleable", "PrivateResource"})
    public XBToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        binding = ComponentToolbarSimpleBinding.inflate(LayoutInflater.from(context), this, false);
        TypedArray a = context.obtainStyledAttributes(attrs, androidx.appcompat.R.styleable.Toolbar);
        title = a.getString(androidx.appcompat.R.styleable.Toolbar_title);
        subTitle = a.getString(androidx.appcompat.R.styleable.Toolbar_subtitle);
        navigationIcon = a.getResourceId(androidx.appcompat.R.styleable.Toolbar_navigationIcon, R.mipmap.icon_back);
        a.recycle();
        if (getBackground() == null) {
            setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        setNavigationIcon(navigationIcon);
        init();
    }

    private void init() {
        addView(binding.getRoot());
        if (title != null) {
            setTitle(title);
        }
        if (subTitle != null) {
            setSubTitle(subTitle);
        }
        binding.ivBack.setOnClickListener(v -> {
            if (context instanceof AppCompatActivity) {
                ((AppCompatActivity) context).onBackPressed();
            }
        });
        initialize();
    }

    public void setEnableCloseBtn(boolean b) {
        if (b) {
            binding.ivClose.setVisibility(VISIBLE);
        } else {
            binding.ivClose.setVisibility(GONE);
        }
        initialize();
    }

    public void setOnCloseBtnClickListener(OnClickListener listener) {
        if (listener != null) {
            binding.ivClose.setOnClickListener(listener);
        }
    }

    public void setTitle(String title) {
        binding.componentToolbarSimpleTitle.setText(title);
    }

    public void setTitleTextColor(int color) {
        binding.componentToolbarSimpleTitle.setTextColor(color);
    }

    public void setSubTitle(String title) {
        binding.componentToolbarSimpleSubtitle.setText(title);
        binding.componentToolbarSimpleSubtitle.setVisibility(VISIBLE);
    }

    public TextView getTitleView() {
        return binding.componentToolbarSimpleTitle;
    }

    public void setRightButtons(RightBtn... rightButtons) {
        rightBtnList.clear();
        binding.llRightBtns.removeAllViews();
        for (int i = 0; i < rightButtons.length; i++) {
            RightBtn btn = rightButtons[i];
            binding.llRightBtns.addView(btn.getView());
            int finalI = i;
            btn.getView().setOnClickListener(v -> {
                if (onRightBtnClickListener != null) {
                    onRightBtnClickListener.onRightBtnClick(finalI);
                }
            });
            rightBtnList.add(btn);
        }
        initialize();
    }

    public List<RightBtn> getRightButtons() {
        return rightBtnList;
    }

    @Override
    public void setNavigationIcon(int resId) {
        if (binding != null) {
            //super.setNavigationIcon(0);
            binding.ivBack.setImageResource(resId);
        } else {
            super.setNavigationIcon(resId);
        }
        initialize();
    }

    public void setCloseBtnIcon(int resId) {
        binding.ivClose.setImageResource(resId);
        initialize();
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
        initialize();
    }

    private void initialize() {
        if (binding != null) {
            int left = 0, right;
            if (binding.ivClose.getVisibility() == VISIBLE) {
                left = DensityUtils.dp2px(context, 8 + 44 + 44);//左padding+返回+关闭
            }
            right = binding.llRightBtns.getWidth() + DensityUtils.dp2px(context, 8);
            int maxMargin = Math.max(left, right);
            ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) binding.componentToolbarSimpleTitle.getLayoutParams();
            lp.leftMargin = maxMargin;
            lp.rightMargin = maxMargin;
            binding.componentToolbarSimpleTitle.setLayoutParams(lp);
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

        public void setText(String text) {
            if (view instanceof TextView) {
                ((TextView) view).setText(text);
            }
        }

        public View getView() {
            return view;
        }
    }

    public boolean isEnableSubTitle() {
        return enableSubTitle;
    }

    public void setEnableSubTitle(boolean enableSubTitle) {
        this.enableSubTitle = enableSubTitle;
        binding.componentToolbarSimpleSubtitle.setVisibility(enableSubTitle ? View.VISIBLE : View.GONE);
    }
}
