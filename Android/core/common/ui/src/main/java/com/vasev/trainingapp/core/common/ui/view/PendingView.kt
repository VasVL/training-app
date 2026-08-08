package com.vasev.trainingapp.core.common.ui.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.vasev.trainingapp.core.common.ui.R

/**
 * Reusable skeleton placeholder that slowly pulses while content is loading.
 * Переиспользуемый skeleton-плейсхолдер, который медленно мерцает во время загрузки контента.
 *
 * The default shape is [R.drawable.bg_pending]. Callers may provide another shape through
 * `android:background`; this view still enforces the shared color and pulse animation.
 * Форма по умолчанию — [R.drawable.bg_pending]. Вызывающий код может передать другую форму через
 * `android:background`; общий цвет и анимация мерцания всё равно задаются этой view.
 */
class PendingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

    private var pulseAnimator: ObjectAnimator? = null

    init {
        background = background ?: ContextCompat.getDrawable(
            context,
            R.drawable.bg_pending,
        )
        backgroundTintList = ContextCompat.getColorStateList(
            context,
            R.color.common_pending_background,
        )
        importantForAccessibility = IMPORTANT_FOR_ACCESSIBILITY_NO
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        startPulseAnimation()
    }

    override fun onDetachedFromWindow() {
        stopPulseAnimation()
        super.onDetachedFromWindow()
    }

    private fun startPulseAnimation() {
        val animator = pulseAnimator ?: ObjectAnimator.ofFloat(
            this,
            View.ALPHA,
            MIN_ALPHA,
            MAX_ALPHA,
        ).also { createdAnimator ->
            createdAnimator.duration = PULSE_DURATION_MILLIS
            createdAnimator.interpolator = LinearInterpolator()
            createdAnimator.repeatCount = ValueAnimator.INFINITE
            createdAnimator.repeatMode = ValueAnimator.REVERSE
            pulseAnimator = createdAnimator
        }
        animator.start()
    }

    private fun stopPulseAnimation() {
        pulseAnimator?.cancel()
        alpha = MAX_ALPHA
    }

    private companion object {
        const val MAX_ALPHA = 1F
        const val MIN_ALPHA = 0.55F
        const val PULSE_DURATION_MILLIS = 1_200L
    }
}
