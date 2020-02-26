package com.sambarboza.orbitview

import android.animation.TimeInterpolator
import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.*

/**
 * @author Samuel Barbosa
 */

class PlanetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private enum class Interpolator {
        LINEAR, ACCELERATE, ACCELERATE_DECELERATE, ANTICIPATE, ANTICIPATE_OVERSHOOT, BOUNCE,
        DECELERATE, OVERSHOOT
    }

    private val planetDrawable: Drawable?
    var fromAngle = 0
    var toAngle = 360
    var orbitDuration = 600
    var animStartDelay = 0
    var animInterpolator: TimeInterpolator = LinearInterpolator()
    var repeatCount: Int = 0

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.PlanetView, 0, 0).apply {
            try {
                planetDrawable = getDrawable(R.styleable.PlanetView_drawable)
                orbitDuration = getInt(R.styleable.PlanetView_orbitDuration, orbitDuration)
                fromAngle = getInt(R.styleable.PlanetView_fromAngle, fromAngle)
                toAngle = getInt(R.styleable.PlanetView_toAngle, toAngle)
                animStartDelay = getInteger(R.styleable.PlanetView_animStartDelay, animStartDelay)

                val interpolators = Interpolator.values()
                animInterpolator =
                    when (interpolators[getInt(R.styleable.PlanetView_interpolator, 0)]) {
                        Interpolator.LINEAR -> LinearInterpolator()
                        Interpolator.ACCELERATE -> AccelerateInterpolator()
                        Interpolator.ACCELERATE_DECELERATE -> AccelerateDecelerateInterpolator()
                        Interpolator.ANTICIPATE -> AnticipateInterpolator()
                        Interpolator.ANTICIPATE_OVERSHOOT -> AnticipateOvershootInterpolator()
                        Interpolator.BOUNCE -> BounceInterpolator()
                        Interpolator.DECELERATE -> DecelerateInterpolator()
                        Interpolator.OVERSHOOT -> OvershootInterpolator()
                    }
                repeatCount = getInt(R.styleable.PlanetView_repeatCount, 0)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        planetDrawable?.setBounds(0, 0, measuredWidth, measuredHeight)
        planetDrawable?.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        val desiredWidth = (suggestedMinimumWidth + paddingLeft + paddingRight) / 2
        val desiredHeight = (suggestedMinimumHeight + paddingTop + paddingBottom) / 2

        setMeasuredDimension(
            desiredWidth + parentWidth,
            desiredHeight + parentHeight
        )
    }

}