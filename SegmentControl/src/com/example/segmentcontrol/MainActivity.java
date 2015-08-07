package com.example.segmentcontrol;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.example.segmentcontrol.R;

public class MainActivity extends Activity {

    private SegmentControl mSegmentControl ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init () ;
    }

    private void init() {

        mSegmentControl = (SegmentControl) findViewById(R.id.segment_control);
        mSegmentControl.setmOnSegmentControlClickListener(new SegmentControl.OnSegmentControlClickListener() {
            @Override
            public void onSegmentControlClick(int index) {
                Toast.makeText(getApplicationContext(),"index " +index+ " is clicked!",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
