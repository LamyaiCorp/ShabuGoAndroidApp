package com.knott.navtab.loing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.knott.navtab.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        Button bt1 = (Button) findViewById(R.id.signUp);
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(it);
            }
        });

        Button bt2 = (Button) findViewById(R.id.login);
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(it);
            }
        });
    }
}
