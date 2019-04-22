package com.bruce3x.kotlin.demo

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val service = FoodOrderService(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        val options: RadioGroup = findViewById(R.id.rg_options)
        val foodA: RadioButton = findViewById(R.id.rb_food_a)
        val foodB: RadioButton = findViewById(R.id.rb_food_b)
        val foodC: RadioButton = findViewById(R.id.rb_food_c)
        val foodD: RadioButton = findViewById(R.id.rb_food_d)
        foodA.text = FoodOrderService.FOOD_A
        foodB.text = FoodOrderService.FOOD_B
        foodC.text = FoodOrderService.FOOD_C
        foodD.text = "山珍海味"

        val btnSelect = findViewById<View>(R.id.btn_select)
        val btnReset = findViewById<View>(R.id.btn_reset)

        val tvDetail = findViewById<TextView>(R.id.tv_detail)
        tvDetail.text = service.getOrderDetail()

        btnSelect.setOnClickListener {
            // 获取选中的菜名
            val selected: RadioButton = findViewById(options.checkedRadioButtonId)
            val food = selected.text.toString()

            try {
                service.select(food)
            } catch (e: FoodOrderService.FoodUnavailableException) {
                Toast.makeText(this@MainActivity, e.message, Toast.LENGTH_SHORT).show()
            } finally {
                tvDetail.text = service.getOrderDetail()
            }
        }

        btnReset.setOnClickListener {
            service.reset()
            tvDetail.text = service.getOrderDetail()
        }
    }
}