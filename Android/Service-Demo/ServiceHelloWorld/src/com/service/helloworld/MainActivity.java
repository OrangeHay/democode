package com.service.helloworld;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;  
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.service.helloworld.TimerService.MyBinder;

public class MainActivity extends Activity implements OnClickListener {
	
	public static final String RECEIVER_ACTION = "com.service.counter.receiver_ACTION";

	private TimerInterface counter;

	private TextView count_text;
	private Button startBtn;
	private Button stopBtn;
	private Button endBtn;

	private ServiceConnection connection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			counter = null;
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			android.util.Log.e("hanyang","service="+service);
			counter = (MyBinder) service;
		}
	};
	
	BroadcastReceiver receiver = new MyBroadcastReceiver();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		count_text = (TextView) findViewById(R.id.count_text);
		startBtn = (Button) findViewById(R.id.startButton);
		stopBtn = (Button) findViewById(R.id.stopButton);
		endBtn = (Button) findViewById(R.id.endButton);

		startBtn.setOnClickListener(this);
		stopBtn.setOnClickListener(this);
		endBtn.setOnClickListener(this);

		Intent bindIntent = new Intent(this, TimerService.class);
		boolean b = bindService(bindIntent, connection, BIND_AUTO_CREATE);		
		android.util.Log.e("hanyang","bindService="+b);       
	}
	public void onStart(){
		super.onStart();
		IntentFilter filter = new IntentFilter(RECEIVER_ACTION);   
		registerReceiver(receiver, filter);  
	}
	public void onStop(){
		super.onStop();
		unregisterReceiver(receiver);
	}
	@Override
	public void onDestroy() {
		if(counter!=null)counter.endCount();
		unbindService(connection);		 
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		if(counter == null)return;
		switch (v.getId()) {
		case R.id.startButton:
			counter.startCount();
			break;
		case R.id.stopButton:
			counter.stopCount();
			break;
		case R.id.endButton:
			counter.endCount();
			break;
		default:
			break;
		}
	}

	public class MyBroadcastReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(RECEIVER_ACTION.equals(intent.getAction())){
				int count = intent.getIntExtra("count", -1);
				count_text.setText(count+"");
			}
		}
	}
}


