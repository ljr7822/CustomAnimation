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
 * 吃球动画类
 * @author iwen大大怪
 * Create to 2020/9/20 10:10
 */
class MouseLoading : View {

    // 画圆弧的画笔
//    private val mArcPaint:Paint = Paint().apply {
//        color = Color.MAGENTA
//        style = Paint.Style.FILL
//    }

    // 懒加载:画圆弧的画笔
    private val mPaint: Paint by lazy {
        Paint().apply {
            color = Color.CYAN
            style = Paint.Style.FILL
        }
    }

    // 懒加载:画小圆的画笔
    private val mPaintMin: Paint by lazy {
        Paint().apply {
            color = Color.CYAN
            style = Paint.Style.FILL
        }
    }

    // 小圆的半径
    private var ballRadius = 0f

    // 两个球的间距
    private var space = 0f

    // 嘴巴的半径
    private var mouseRadiud = 0f

    // 圆弧中心点坐标
    private var cx = 0f
    private var cy = 0f

    // 嘴巴的动画因子
    private var mouseAngle = 0f

    // 小球移动的变化因子
    private var ballTranslateX = 0f

    // 定义一个变量保存嘴巴动画对象
    private var mouseMoveAnim: ValueAnimator? = null

    // 定义一个变量保存小球动画对象
    private var ballMoveAnim: ValueAnimator? = null

    // 动画集
    private var animators = AnimatorSet()

    // 代码创建
    constructor(context: Context) : super(context) {}

    // xml创建
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 确定小球半径
        if (width >= height) {
            // 高度顶满
            ballRadius = height / 6f
            // 判断宽度能否容纳8.5个R
            if (8.5 * ballRadius > width) {
                // 宽度容纳不下
                ballRadius = width / 8.5f
            } else {
                // 宽度顶满
                ballRadius = width / 8.5f
                // 判断高度能否容纳6个R
                if (6 * ballRadius > height) {
                    // 高度容纳不下
                    ballRadius = height / 6f
                }
            }
            mouseRadiud = 3f * ballRadius
            space = 0.5f * ballRadius

            // 中心点坐标
            cx = ((width - 8.5 * ballRadius) / 2f + 3 * ballRadius).toFloat()
            cy = height / 2f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        // 绘制圆弧
        canvas?.drawArc(
            cx - mouseRadiud,
            cy - mouseRadiud,
            cx + mouseRadiud,
            cy + mouseRadiud, mouseAngle, 360f - 2 * mouseAngle, true, mPaint
        )
        // 绘制圆形
        canvas?.drawCircle(
            cx + 4.5f * ballRadius - ballTranslateX,
            cy, ballRadius, mPaintMin
        )
    }

    /**
     * 创建动画方法
     */
    private fun createAnim() {
        // 判断嘴巴动画对象是否存在
        if (mouseMoveAnim == null) {
            // 不存在，创建嘴巴动画
            mouseMoveAnim = ValueAnimator.ofFloat(0f, 45f, 0f).apply {
                duration = 650
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    mouseAngle = it.animatedValue as Float
                    invalidate()
                }
            }
        }
        // 判断小球动画对象是否存在
        if (ballMoveAnim == null) {
            // 不存在，创建小球动画
            ballMoveAnim = ValueAnimator.ofFloat(0f, 4.5f * ballRadius).apply {
                duration = 650
                repeatCount = ValueAnimator.INFINITE
                addUpdateListener {
                    ballTranslateX = it.animatedValue as Float
                    invalidate()
                }
            }
        }

//        AnimatorSet().apply {
//            // 顺序执行
//            // playSequentially(mouseMoveAnim, ballMoveAnim)
//            // 同步执行
//            playTogether(mouseMoveAnim,ballMoveAnim)
//        }
        animators.playTogether(mouseMoveAnim, ballMoveAnim)
    }

    /**
     * 提供给外部启动这个动画的接口
     */
    fun startAnim() {
        // 创建动画
        createAnim()
        // 判断动画状态
        if (animators.isPaused) {
            animators.resume()
        } else {
            animators.start()
        }
    }

    /**
     * 提供给外部关闭这个动画的接口
     */
    fun stopAnim() {
        animators.pause()
        //mouseMoveAnim?.end()
    }
}