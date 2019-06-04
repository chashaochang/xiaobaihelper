package cn.xiaobaihome.xiaobaihelper.helper.annotation;

import androidx.annotation.IntDef;

import static cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType.ERROR;
import static cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType.NORMAL;
import static cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType.SUCCESS;
import static cn.xiaobaihome.xiaobaihelper.helper.annotation.ToastType.WARNING;

/**
 * 页面描述：ToastType
 * <p>
 * Created by ditclear on 2017/10/11.
 */
@IntDef({ERROR, NORMAL, SUCCESS, WARNING})
public @interface ToastType {
    int ERROR = -2;
    int WARNING = -1;
    int NORMAL = 0;
    int SUCCESS = 1;
}
