package com.example.helloworld;

import com.example.helloworld.jni_java.HelloWorld;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView = (TextView)findViewById(R.id.hello_world);
		int text = HelloWorld.getHelloWorld();
		textView.setText(text+"");
	}
}
