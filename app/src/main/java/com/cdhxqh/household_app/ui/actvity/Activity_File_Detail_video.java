package com.cdhxqh.household_app.ui.actvity;

import android.os.Bundle;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

/**
 * Created by think on 2015/8/19.
 */
public class Activity_File_Detail_video extends BaseActivity{
    private TextView titlename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acivity_file_video);
        titlename = (TextView) findViewById(R.id.title_text_id);
        getIntent().getStringExtra("name");
        titlename.setText(getIntent().getStringExtra("name"));
    }
}
