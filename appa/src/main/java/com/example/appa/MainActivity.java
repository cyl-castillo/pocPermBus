package com.example.appa;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("AppA main activity");
        tv.setPadding(16, 16, 16, 16);
        setContentView(tv);
    }
}
