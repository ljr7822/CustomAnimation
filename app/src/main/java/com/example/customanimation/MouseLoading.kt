package com.example.customanimation

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

/**
 * @author iwen大大怪
 * Create to 2020/9/20 10:10
 */
class MouseLoading: View {

    // 画圆弧的画笔
//    private val mArcPaint:Paint = Paint().apply {
//        color = Color.MAGENTA
//        style = Paint.Style.FILL
//    }

    // 懒加载:画圆弧的画笔
    private val mPaint:Paint by lazy {
       Paint().apply {
           color = Color.BLUE
           style = Paint.Style.FILL
       }
    }
    // 懒加载:画小圆的画笔
    private val mPaintMin:Paint by lazy {
        Paint().apply {
            color = Color.MAGENTA
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

    // 代码创建
    constructor(context: Context):super(context){}

    // xml创建
    constructor(context: Context, attrs:AttributeSet) : super(context,attrs){}

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 确定小球半径
        if (width >= height){
            // 高度顶满
            ballRadius = height / 6f
            // 判断宽度能否容纳8.5个R
            if (8.5*ballRadius >width){
                // 宽度容纳不下
                ballRadius = width / 8.5f
            }else{
                // 宽度顶满
                ballRadius = width / 8.5f
                // 判断高度能否容纳8.5个R
                if (6*ballRadius > height){
                    // 高度容纳不下
                    ballRadius = height / 6f
                }
            }
            mouseRadiud = 3f*ballRadius
            space = 0.5f*ballRadius

            // 中心点坐标
            cx = ((width - 8.5*ballRadius)/2f + 3*ballRadius).toFloat()
            cy = height / 2f
        }
    }

    override fun onDraw(canvas: Canvas?) {
        // 绘制圆弧
        canvas?.drawArc(
            cx - mouseRadiud,
            cy - mouseRadiud,
            cx + mouseRadiud,
            cy + mouseRadiud,0f,360f,true,mPaint
        )
        // 绘制圆形
        canvas?.drawCircle(
            cx + 4.5f*ballRadius,
            cy ,ballRadius,mPaintMin
        )
    }
}