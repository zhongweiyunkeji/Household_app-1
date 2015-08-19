package com.cdhxqh.household_app.ui.widget;

import android.app.Activity;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;

import com.cdhxqh.household_app.R;


/**
 * Created by Administrator on 2015/7/24.
 */
public class TimeCountUtil extends CountDownTimer {
    private Activity phoneActivity;
    private Button info_button_id;
    private int img;

    public TimeCountUtil(Activity phoneActivity, long millisInFuture, long countDownInterval, Button info_button_id, int img) {
        super(millisInFuture, countDownInterval);
        this.phoneActivity = phoneActivity;
        this.info_button_id = info_button_id;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        info_button_id.setClickable(false);
        info_button_id.setText(millisUntilFinished / 1000 + phoneActivity.getResources().getString(R.string.phone_send_number));
        info_button_id.setBackgroundColor(phoneActivity.getResources().getColor(R.color.transition_bg));
        Spannable span = new SpannableString(info_button_id.getText().toString());
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        info_button_id.setText(span);
    }

    @Override
    public void onFinish() {
        info_button_id.setText(phoneActivity.getResources().getString(R.string.phone_get_number));
        info_button_id.setClickable(true);
        if(img != 0) {
            info_button_id.setBackgroundResource(img);
        }
    }
}
