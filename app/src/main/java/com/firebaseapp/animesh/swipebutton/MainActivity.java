package com.firebaseapp.animesh.swipebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SwipeButton mSwipeButton = (SwipeButton) findViewById(R.id.my_swipe_button);
    }
}
