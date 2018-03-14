package com.service.helloworld;

import java.util.Timer;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

public class TimerService extends Service{
	
	Handler h = new Handler();	
	private boolean isStart = false;
	private boolean isStop = false;
	private int count = 0;
	
	MyBinder mBinder = new MyBinder();	
	@Override  
    public void onCreate() {  
        super.onCreate();  
        android.util.Log.e("hanyang","onCreate_service");
    }   
    @Override  
    public int onStartCommand(Intent intent, int flags, int startId) {  
    	android.util.Log.e("hanyang","onStartCommand_service");
        return super.onStartCommand(intent, flags, startId);          
    }  
    @Override  
    public void onDestroy() {  
    	android.util.Log.e("hanyang","onDestroy_service");
        super.onDestroy();  
    }  	
	@Override
	public IBinder onBind(Intent arg0) {
		return mBinder;
	}		
	public class MyBinder extends Binder implements TimerInterface{ 	  
		@Override
		public void startCount() {
			if(!isStart){
				isStart = true;
				h.postDelayed(runTimer, 1000);
			}
		}
		@Override
		public void endCount() {
			isStart = false;
			isStop = false;
			count = 0;
			Intent intent = new Intent(MainActivity.RECEIVER_ACTION);
			intent.putExtra("count", count);
			sendBroadcast(intent);
		}
		@Override
		public void stopCount() {
			if(!isStart)return;
			isStop = !isStop;
			if(!isStop){
				h.postDelayed(runTimer, 1000);
			}else{
				h.removeCallbacks(runTimer);
			}
		}    
    }  
	
	Runnable runTimer = new Runnable(){
		@Override
		public void run() {
			if(isStart){				
				count ++;
				Intent intent = new Intent(MainActivity.RECEIVER_ACTION);
				intent.putExtra("count", count);
				sendBroadcast(intent);
				if(!isStop){
					h.postDelayed(runTimer, 1000);
				}
			}
		}
	};
	
}
