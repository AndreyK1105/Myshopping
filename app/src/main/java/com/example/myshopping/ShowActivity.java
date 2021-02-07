package com.example.myshopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ShowActivity extends AppCompatActivity {
    private TextView showTitle, showDescr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_layout);
        init();
        getIntentShow();
    }
    private void init(){
        showTitle = findViewById(R.id.showTitle);
        showDescr = findViewById(R.id.showDescr);
    }
    private void getIntentShow (){
        Intent i = getIntent();
        if (i != null){
            showTitle.setText(i.getStringExtra("user_title"));
            showDescr.setText(i.getStringExtra("user_descr"));
        }
    }
}
