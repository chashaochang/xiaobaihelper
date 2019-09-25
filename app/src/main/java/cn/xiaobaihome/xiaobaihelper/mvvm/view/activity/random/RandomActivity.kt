package cn.xiaobaihome.xiaobaihelper.mvvm.view.activity.random

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import cn.xiaobaihome.xiaobaihelper.R
import cn.xiaobaihome.xiaobaihelper.adapter.FoodListAdapter
import cn.xiaobaihome.xiaobaihelper.base.BaseActivity
import cn.xiaobaihome.xiaobaihelper.databinding.ActivityRandomBinding
import cn.xiaobaihome.xiaobaihelper.entity.MapFoodListItem
import cn.xiaobaihome.xiaobaihelper.widget.RotationPanelView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class RandomActivity : BaseActivity<ActivityRandomBinding>() {

    private var rotationPanelView: RotationPanelView? = null
    private var bonusBeans: ArrayList<Int> = ArrayList()
    private var randomBonusResult: Int = 0 //所中的奖等
    private var randomBonusRadio: Float = 0f//随机角度
    private var foodList: MutableList<MapFoodListItem>? = null
    private var foodListAdapter: FoodListAdapter? = null

    override fun initView() {
        binding.activityRandomToolbar.setNavigationOnClickListener {
            finish()
        }
        val listStr = intent.getStringExtra("list")
        foodList = Gson().fromJson(listStr, object : TypeToken<MutableList<MapFoodListItem>>() {}.type)
        for (index in 1..foodList!!.size) {
            foodList!![index - 1].name = "$index." + foodList!![index - 1].name
        }
        foodListAdapter = FoodListAdapter(foodList!!)

        binding.activityRandomListview.adapter = foodListAdapter

        rotationPanelView = binding.activityRandomRotationPanelView
        initData()
        binding.activityRandomHelpBtn.setOnClickListener {
            initAnimation()
        }
    }

    private fun initData() {
        //初始化中奖数据，只需要让中奖数据相加等于1即可
        for (i in 1..(foodList?.size ?: 0)) {
            bonusBeans.add(i)
        }

        rotationPanelView?.bonusList = bonusBeans
    }

    //开放仿真转圈度数，要不转一下，只有一圈就尴尬了。所以想先转多少圈在显示中奖结果，自己定。
    @SuppressLint("ObjectAnimatorBinding")
    private fun initAnimation() {
        val count = 10
        val random = Random()
        //第一次随机产生中奖结果
        randomBonusResult = random.nextInt(bonusBeans.size - 1) + 1
        //二次随机产生一个停在目标中奖区域的随机度数
        randomBonusRadio = random.nextFloat()
        var tempFloat = 0.0f
        for (i in 0..randomBonusResult) {
            tempFloat += (1.0f / bonusBeans.size)
        }
        tempFloat -= (1.0f / bonusBeans.size) * randomBonusRadio
        tempFloat *= 360.0f
        //Android对象动画 设置我们的View  动画类型。由于自定义View是顺时针画的，所以为了停在我们的中奖区域使用逆时针旋转。即中奖度数取反。
        val animator = ObjectAnimator.ofFloat(rotationPanelView, "rotation", 0f, -(360f * count + tempFloat))
        animator.duration = (500 * count).toLong()
        animator.start()
        rotationPanelView?.setOnClickListener(null)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                var msg = ""
                msg += foodList?.get(randomBonusResult)?.name ?: ""
                AlertDialog.Builder(this@RandomActivity)
                        .setTitle("就吃它了！")
                        .setMessage(msg)
                        .show()
                foodListAdapter?.selectedItem = randomBonusResult
                binding.activityRandomListview.setSelection(randomBonusResult)
            }
        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_random
    }
}