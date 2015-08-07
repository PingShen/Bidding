package com.xzw.custom.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
    private TextView resultText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        resultText = (TextView) findViewById(R.id.result);
        Button showDialogBtn = (Button) findViewById(R.id.showdialog);
        showDialogBtn.setOnClickListener(this);

    }

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		MyCustomDialog dialog = new MyCustomDialog(this,"Enter your name",new MyCustomDialog.OnCustomDialogListener() {

			@Override
			public void back(String name) {
				resultText.setText("Enter name is "+name);
				
			}
			
		});
		dialog.show();
		WindowManager windowManager = getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = (int)(display.getWidth()); //设置宽度
		lp.height = (int)(display.getHeight());
		dialog.getWindow().setAttributes(lp);
    }

}
