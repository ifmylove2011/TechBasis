package com.xter.kot

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/6/30
 * 描述:
 */
import kotlinx.coroutines.*
fun main() {
    GlobalScope.launch { // 在后台启动⼀个新的协程并继续
        delay(1000L) // ⾮阻塞的等待 1 秒钟（默认时间单位是毫秒）
        println("World!") // 在延迟后打印输出
    }
    println("Hello,") // 协程已在等待时主线程还在继续
    Thread.sleep(2000L) // 阻塞主线程 2 秒钟来保证 JVM 存活
}