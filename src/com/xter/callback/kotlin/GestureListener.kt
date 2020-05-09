package com.xter.callback.kotlin

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
interface GestureListener<F:Gesture> {
    fun done(gesture: F)
}
