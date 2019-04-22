package com.bruce3x.kotlin.demo;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 点餐机
 */
public class FoodOrderService {
    public static final String FOOD_A = "宫保鸡丁";
    public static final String FOOD_B = "鱼香肉丝";
    public static final String FOOD_C = "小炒肉";

    private List<Food> foods = new ArrayList<>();
    private boolean isVip;

    /**
     * @param isVip 是否为 VIP 客户（可以打折😏）
     */
    public FoodOrderService(boolean isVip) {
        this.isVip = isVip;
    }

    /**
     * 选择想吃的菜名
     *
     * @param food 菜名
     * @throws FoodUnavailableException 如果厨师做不了这个菜，会抛出该异常
     */
    public void select(String food) throws FoodUnavailableException {
        switch (food) {
            case FOOD_A:
                foods.add(new Food(FOOD_A, 15F));
                break;
            case FOOD_B:
                foods.add(new Food(FOOD_B, 16.5F));
                break;
            case FOOD_C:
                foods.add(new Food(FOOD_C, 20.0F));
                break;
            default:
                throw new FoodUnavailableException(food);
        }
    }

    /**
     * 获取当前订单的详情
     */
    public String getOrderDetail() {
        List<String> lines = new ArrayList<>();
        lines.add("欢迎来点餐！！！");
        lines.add("");

        int index = 1;
        float total = 0;
        for (Food item : foods) {
            String line = String.format(Locale.getDefault(), "#%d  菜名：%s      价格：￥%.2f", index, item.name, item.price);
            lines.add(line);
            total += item.price;
            index++;
        }

        float amount;
        if (isVip) {
            // 88折哟
            amount = 0.88F * total;
        } else {
            amount = total;
        }

        lines.add("");
        lines.add(String.format(Locale.getDefault(), "总计：￥%.2f", amount));
        return TextUtils.join("\n", lines);
    }

    /**
     * 重置当前订单
     */
    public void reset() {
        foods.clear();
    }

    private static class Food {
        public String name;
        public float price;

        public Food(String name, float price) {
            this.name = name;
            this.price = price;
        }
    }

    public static class FoodUnavailableException extends Exception {
        public FoodUnavailableException(String food) {
            super("厨师做不了这个菜哦：" + food);
        }
    }
}
