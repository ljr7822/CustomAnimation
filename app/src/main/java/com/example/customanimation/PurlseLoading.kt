package com.example.customanimation

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * 小球跳动动画类
 * @author iwen大大怪
 * Create to 2020/9/20 16:11
 *
 */
class PurlseLoading : View {
    private var radius = 0f
    private var space = 0f

    //第一个点的中心点cx cy
    private var cx = 0f
    private var cy = 0f

    //画笔
    private val mPaint = Paint().apply {
        color = Color.MAGENTA
        style = Paint.Style.FILL
    }

    //动画因子
    private var scale = 1f

    //延迟时间
    private val animDelays = arrayOf(120L, 240L, 360L)

    //每个圆缩放比例
    private val scales = arrayOf(1f, 1f, 1f)

    // 代码创建
    constructor(context: Context) : super(context) {}

    // xml创建
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        //确定半径
        if (width >= height) {
            radius = height / 2f
            if (7 * radius > width) {
                radius = width / 7f
            }
        } else {
            radius = width / 7f
        }
        space = radius / 2f

        cx = (width - 7 * radius) / 2 + radius
        cy = height / 2f
    }

    override fun onDraw(canvas: Canvas?) {
        //绘制圆
        canvas?.drawCircle(cx, cy, radius * scales[0], mPaint)
        canvas?.drawCircle(cx + 2.5f * radius, cy, radius * scales[1], mPaint)
        canvas?.drawCircle(cx + 2 * 2.5f * radius, cy, radius * scales[2], mPaint)
    }

    private var animators = AnimatorSet()

    private fun createAnim() {

        val lists = mutableListOf<ValueAnimator>()
        for (index in 0..2) {
            val anim = ValueAnimator.ofFloat(1f, 0.3f, 1f).apply {
                duration = 650
                startDelay = animDelays[index]
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    scales[index] = it.animatedValue as Float
                    invalidate()
                }
            }
            lists.add(anim)
        }

        for (anim in lists) {
            animators.play(anim)

        }

    }

    fun startAnim() {
        createAnim()
        animators.start()
    }

    fun stopAnim() {
        animators.end()
    }
}