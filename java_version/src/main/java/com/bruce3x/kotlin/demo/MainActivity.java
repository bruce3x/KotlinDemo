package com.bruce3x.kotlin.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private FoodOrderService service = new FoodOrderService(false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    private void initViews() {
        RadioGroup options = findViewById(R.id.rg_options);
        RadioButton foodA = findViewById(R.id.rb_food_a);
        RadioButton foodB = findViewById(R.id.rb_food_b);
        RadioButton foodC = findViewById(R.id.rb_food_c);
        RadioButton foodD = findViewById(R.id.rb_food_d);
        foodA.setText(FoodOrderService.FOOD_A);
        foodB.setText(FoodOrderService.FOOD_B);
        foodC.setText(FoodOrderService.FOOD_C);
        foodD.setText("山珍海味");

        View btnSelect = findViewById(R.id.btn_select);
        View btnReset = findViewById(R.id.btn_reset);

        TextView tvDetail = findViewById(R.id.tv_detail);
        tvDetail.setText(service.getOrderDetail());

        btnSelect.setOnClickListener(v -> {
            // 获取选中的菜名
            RadioButton selected = findViewById(options.getCheckedRadioButtonId());
            String food = selected.getText().toString();

            try {
                service.select(food);
            } catch (FoodOrderService.FoodUnavailableException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            } finally {
                tvDetail.setText(service.getOrderDetail());
            }
        });

        btnReset.setOnClickListener(v -> {
            service.reset();
            tvDetail.setText(service.getOrderDetail());
        });
    }
}
