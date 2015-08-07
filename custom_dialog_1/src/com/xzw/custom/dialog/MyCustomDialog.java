package com.xzw.custom.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
/**
 *
 * @author Mr.Xu
 *
 */
public class MyCustomDialog extends Dialog {
	public interface OnCustomDialogListener{
		public void back(String name);
	}
	
	private String name;
	private OnCustomDialogListener customDialogListener;
	EditText etName;
 
	public MyCustomDialog(Context context,String name,OnCustomDialogListener customDialogListener) {
		super(context,R.style.MyDialog);
		this.name = name;
		this.customDialogListener = customDialogListener;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_orderpop);
		//etName = (EditText)findViewById(R.id.edit);
		//Button clickBtn = (Button) findViewById(R.id.clickbtn);
		//clickBtn.setOnClickListener(clickListener);
	}
	
	private View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			customDialogListener.back(String.valueOf(etName.getText()));
		    MyCustomDialog.this.dismiss();
		}
	};

}
