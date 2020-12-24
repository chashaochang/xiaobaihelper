package cn.xiaobaihome.xiaobaihelper.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.abs

class RotationPanelView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defAttrStyle: Int = 0)
    : View(context, attributeSet, defAttrStyle) {
    //View的宽和高
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    //圆半径
    private var mRadio: Int = 0
    //圆心坐标
    private var mCircleX: Int = 0
    private var mCircleY: Int = 0
    private var mPaint: Paint? = null
    var bonusList: ArrayList<Int> = ArrayList()
        set(value) {
            field = value
            invalidate()
        }
    private var colorList: ArrayList<Int> = ArrayList()

    //设置画布抗锯齿
    private val pfd = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)

    init {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (checkData()) {
            canvas.drawFilter = pfd
            mPaint!!.color = Color.BLACK
            mPaint!!.style = Paint.Style.STROKE
            canvas.drawCircle(mCircleX.toFloat(), mCircleY.toFloat(), mRadio.toFloat(), mPaint!!)
            //设置浮点类型的矩形，也是我们所画扇形的区域。也就是四边与圆相切的正方形
            val rectF = RectF((mCircleX - mRadio).toFloat(), (mCircleY - mRadio).toFloat(), (mCircleX + mRadio).toFloat(), (mCircleY + mRadio).toFloat())

            var tempRadio = 0.0f
            //为每个扇形设置角度和颜色
            for (i in bonusList.indices) {
                //减90度 使扇形从12点方向开始
                val beginRadio = 360.0f * tempRadio - 90.0f
                tempRadio += 1.0f / bonusList.size
                val endRadio = 360 * (1.0f / bonusList.size)
                mPaint!!.color = colorList[i]
                mPaint!!.style = Paint.Style.FILL
                canvas.drawArc(rectF, beginRadio, endRadio, true, mPaint!!)
            }
            val rectF1 = RectF((mCircleX - mRadio).toFloat() + 100, (mCircleY - mRadio).toFloat() + 100, (mCircleX + mRadio).toFloat() - 100, (mCircleY + mRadio).toFloat() - 100)

            for (i in bonusList.indices) {
                val beginRadio = 360.0f * tempRadio - 90.0f
                tempRadio += 1.0f / bonusList.size
                val endRadio = 360 * (1.0f / bonusList.size)
                mPaint!!.color = Color.WHITE
                mPaint!!.textSize = 60f
                val path = Path()
                path.addArc(rectF1, beginRadio, endRadio)
                canvas.drawTextOnPath(bonusList[i].toString(), path, 0f, 0f, mPaint!!)
            }
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //测试当前View的宽和高 以方便后面设置我们扇形的区域
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measureHeight = MeasureSpec.getSize(heightMeasureSpec)
        mWidth = measureWidth
        mHeight = measureHeight
        mCircleX = mWidth / 2
        mCircleY = mHeight / 2
        mRadio = mCircleX - 100
    }

    //对输入的数据进行判断，如果数据错误，不会绘制圆周，
    private fun checkData(): Boolean {
        //println(bonusList.size)
        if (bonusList.size in 1..20) {
            var temp = 0.0f
            for (i in bonusList.indices) {
                temp += 1.0f / bonusList.size
            }
            //看看加入数据比例之和是否等于1 由于是浮点数可能产生误差，所以这里不能写"==1"
            return if (abs(temp - 1.0f) < 0.01f) {
                colorList = ArrayList()
                //设置颜色列表，我知道的颜色不多，可以自行改成你喜欢的颜色列表
                val myRandom = Random()
                for (color in 0..bonusList.size) {
                    val ranColor = (if (color % 2 > 0) 0xff00aeef else 0xff0080be).toInt()
                    colorList.add(ranColor)
                }
                true
            } else {
                false
            }
        } else {
            return false
        }
    }
}
