package com.example.testactivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class timerTask extends Activity{  
    private int recLen = 15;  
    private TextView txtView;  
  
    public void onCreate(Bundle savedInstanceState){  
        super.onCreate(savedInstanceState);  
  
        setContentView(R.layout.timertask);  
        txtView = (TextView)findViewById(R.id.jjj);  
          
        handler.postDelayed(runnable, 1000);  
    }     
  
    Handler handler = new Handler();  
    Runnable runnable = new Runnable() {  
        @Override  
        public void run() {  
            recLen--;  
            if(recLen>=0){
            	txtView.setText("" + recLen);  
            	handler.postDelayed(this, 1000);  
            }
            else{
            	txtView.setText("结束了");  
            }
            
            
        }  
    };  
}  