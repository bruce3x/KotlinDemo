package com.bruce3x.kotlin.demo

import android.text.TextUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * 点餐机
 * @param isVip 是否为 VIP 客户（可以打折😏）
 */
class FoodOrderService(private val isVip: Boolean) {

    private val foods = ArrayList<Food>()

    /**
     * 选择想吃的菜名
     *
     * @param food 菜名
     * @throws FoodUnavailableException 如果厨师做不了这个菜，会抛出该异常
     */
    @Throws(FoodUnavailableException::class)
    fun select(food: String) {
        when (food) {
            FOOD_A -> foods.add(Food(FOOD_A, 15f))
            FOOD_B -> foods.add(Food(FOOD_B, 16.5f))
            FOOD_C -> foods.add(Food(FOOD_C, 20.0f))
            else -> throw FoodUnavailableException(food)
        }
    }

    /**
     * 获取当前订单的详情
     */
    fun getOrderDetail(): String {
        val lines = ArrayList<String>()
        lines.add("欢迎来点餐！！！")
        lines.add("")

        var index = 1
        var total = 0f
        for (item in foods) {
            val line = "#$index   菜名：${item.name}      价格：￥${item.price}"
            lines.add(line)
            total += item.price
            index++
        }

        val amount: Float
        if (isVip) {
            // 88折哟
            amount = 0.88f * total
        } else {
            amount = total
        }

        lines.add("")
        lines.add(String.format(Locale.getDefault(), "总计：￥%.2f", amount))
        return TextUtils.join("\n", lines)
    }

    /**
     * 重置当前订单
     */
    fun reset() {
        foods.clear()
    }

    private data class Food(val name: String, val price: Float)

    class FoodUnavailableException(food: String) : Exception("厨师做不了这个菜哦：$food")

    companion object {
        const val FOOD_A = "宫保鸡丁"
        const val FOOD_B = "鱼香肉丝"
        const val FOOD_C = "小炒肉"
    }
}