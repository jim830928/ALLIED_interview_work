package com.example.allied_interview_work;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ItemPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);
        // Retrieve text(item content) from previous activity
        String ItemContent = getIntent().getStringExtra("ItemContent");
        TextView ItemContentDisplay = (TextView) findViewById(R.id.item_content_display);
        ItemContentDisplay.setText(ItemContent);
    }
}
