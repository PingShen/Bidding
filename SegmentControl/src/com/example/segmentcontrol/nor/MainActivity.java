package com.example.segmentcontrol.nor;

import com.example.segmentcontrol.R;
import com.example.segmentcontrol.R.id;
import com.example.segmentcontrol.R.layout;
import com.example.segmentcontrol.adad.SegmentControl;
import com.example.segmentcontrol.adad.SegmentControl.OnSegmentChangedListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


public class MainActivity  extends Activity{
	SegmentControl segControl;
	private ViewFlipper	mFlipper;
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_login);
        
        mFlipper = (ViewFlipper)findViewById(R.id.flipper);
        //测试界面，实际开发中是从layout中读取的，下同。
        TextView tv=new TextView(this);
        tv.setText("index=0");
        mFlipper.addView(tv);
//      tv.requestFocus();
        
        segControl = (SegmentControl)findViewById(R.id.segcontrol);
        segControl.setStyle(SegmentControl.TAB);//试试SEGMENT
        segControl.newButton("服务", 0);
        segControl.newButton("休息", 1);
        //还可试试segControl.newButton(int drawableId, int id);
        
        segControl.setSelectedIndex(0);
        int width = this.px2dip(this, 80*segControl.getButtonCount());
        int height = this.px2dip(this, 38);
        segControl.setWidth(width, height, segControl.getButtonCount());
        
        segControl.setOnSegmentChangedListener(new OnSegmentChangedListener() {
			
			@Override
			public void onSegmentChanged(int index) {
				//
				onChangeView(index);
			}
		});
        
    }
    private int mIndex=0;
    private void changeViewAnimation(int index, View view) {
		if(index == mIndex)
			return;
		
		mFlipper.addView(view);
		
		Animation inAnim = null;
		Animation outAnim = null;

		if(index > mIndex) {
			inAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
			outAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		} else {
			inAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
			outAnim = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
		}
		inAnim.setDuration(1000);
		outAnim.setDuration(1000);
		
        mFlipper.setInAnimation(inAnim);
        mFlipper.setOutAnimation(outAnim);
        mFlipper.showNext();
        //mFlipper.startFlipping();
        mFlipper.removeViewAt(0);
        
        mIndex = index;
	}
    private void onChangeView(int index) 
	{
        //测试界面，实际开发中是从layout中读取的，下同。 
    	TextView tv=new TextView(this);
    	tv.setText("index="+index);
		switch(index){
		case 0:
			Toast.makeText(this, "VIEW_TLINE", Toast.LENGTH_SHORT).show();
			changeViewAnimation(index, tv);
			break;
		case 1:
			Toast.makeText(this, "VIEW_KLINE", Toast.LENGTH_SHORT).show();
			changeViewAnimation(index, tv);
			break;
		case 2:
			Toast.makeText(this, "VIEW_DETAIL", Toast.LENGTH_SHORT).show();
			changeViewAnimation(index, tv);
			break;
		case 3:
			Toast.makeText(this, "VIEW_F10", Toast.LENGTH_SHORT).show();
			changeViewAnimation(index, tv);
			break;
		case 4:
			Toast.makeText(this, "VIEW_RADAR", Toast.LENGTH_SHORT).show();
			changeViewAnimation(index, tv);
			break;
		}
	}
    
  //dip/px像素单位转换
	public static int dip2px(Context context, float dipValue){   
        final float scale = context.getResources().getDisplayMetrics().density;   
        return (int)(dipValue / scale + 0.5f);   
    }   
	public static int px2dip(Context context, float pxValue){   
		final float scale = context.getResources().getDisplayMetrics().density;   
	    return (int)(pxValue * scale + 0.5f);   
	}  
}
