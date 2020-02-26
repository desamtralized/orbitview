package com.sambarboza.orbitview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.animation.BaseInterpolator
import android.view.animation.LinearInterpolator
import android.widget.FrameLayout
import androidx.core.view.children
import kotlin.math.cos
import kotlin.math.sin

/**
 * @author Samuel Barbosa
 */
class OrbitView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var paint: Paint? = null
    private var color: Int? = null
    private val startAnimationsDelay = 333
    private var animated = false

    init {
        setWillNotDraw(false)
        context.theme.obtainStyledAttributes(attrs, R.styleable.OrbitView, 0, 0).apply {
            try {
                color = getColor(R.styleable.OrbitView_orbitColor, Color.GREEN)
                color?.let {
                    paint = Paint(Paint.ANTI_ALIAS_FLAG)
                    paint?.apply {
                        style = Paint.Style.STROKE
                        color = it
                        strokeWidth = getDimensionPixelSize(
                            R.styleable.OrbitView_orbitWidth,
                            2
                        ).toFloat()
                    }
                }
            } finally {
                recycle()
            }
        }
    }

    private fun setViewAngle(view: PlanetView, angle: Int, centerX: Float, centerY: Float, r: Int) {
        view.x = centerX + cos(angle.toFloat() * Math.PI.toFloat() / 180f) * r
        view.y = centerY + sin(angle.toFloat() * Math.PI.toFloat() / 180f) * r
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        color?.let {
            canvas?.drawCircle(
                (width / 2).toFloat(),
                (height / 2).toFloat(), (width / 2).toFloat(), paint
            )
        }

        val r = width / 2


        val planets = children.filterIsInstance<PlanetView>()
        val anims = arrayListOf<ValueAnimator>()

        planets.forEach {
            val centerX = (width / 2).toFloat() - (it.width / 2)
            val centerY = (height / 2).toFloat() - (it.height / 2)

            it.x = centerX
            it.y = centerY

            if (!animated) {
                val anim = ValueAnimator.ofInt(it.fromAngle, it.toAngle)
                    .setDuration(it.orbitDuration.toLong())

                anim.addUpdateListener { animVal ->
                    val angle = animVal.animatedValue as Int
                    setViewAngle(it, angle, centerX, centerY, r)
                }

                anim.interpolator = it.animInterpolator
                anim.startDelay = it.animStartDelay.toLong() - startAnimationsDelay
                anim.repeatCount = it.repeatCount
                anims.add(anim)
            } else {
                setViewAngle(it, it.toAngle, centerX, centerY, r)
            }

        }

        if (!animated) {
            handler.postDelayed({
                anims.forEach { it.start() }
                animated = true
            }, startAnimationsDelay.toLong())
        }
    }

}