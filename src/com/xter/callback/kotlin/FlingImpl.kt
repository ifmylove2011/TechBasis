package com.xter.callback.kotlin

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
class FlingImpl : Fling {
    private var listener: GestureListener<in Fling>? = null

    override fun onMove() {
        listener?.done(this)
    }

    override fun setGestureListener(listener: GestureListener<out Gesture>) {
        this.listener = listener as? GestureListener<in Fling>?
    }

    override fun onFling() {
        println("invoke onFling")
    }
}
