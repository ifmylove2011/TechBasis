package com.xter.callback.kotlin

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/5/8
 * 描述:
 */
object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val fling = FlingImpl()
        fling.setGestureListener(object : GestureListener<Fling> {
            override fun done(gesture: Fling) {
                gesture.onFling()
            }
        })
        fling.onMove()
    }
}
