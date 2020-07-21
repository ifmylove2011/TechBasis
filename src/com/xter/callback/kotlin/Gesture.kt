package com.xter.callback.kotlin

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
interface Gesture {
    fun onMove()

    fun setGestureListener(listener: GestureListener<out Gesture>)
}
