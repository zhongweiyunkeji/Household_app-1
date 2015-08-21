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
    private Activity ActivityForgetPassword;
    private Button restart_passworld_id;
    private int img;

    public TimeCountUtil(Activity ActivityForgetPassword, long millisInFuture, long countDownInterval, Button restart_passworld_id, int img) {
        super(millisInFuture, countDownInterval);
        this.ActivityForgetPassword = ActivityForgetPassword;
        this.restart_passworld_id = restart_passworld_id;
        this.img = img;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        restart_passworld_id.setClickable(false);
        restart_passworld_id.setText(millisUntilFinished / 1000 + ActivityForgetPassword.getResources().getString(R.string.phone_send_number));
        restart_passworld_id.setBackgroundColor(ActivityForgetPassword.getResources().getColor(R.color.transition_bg));
        Spannable span = new SpannableString(restart_passworld_id.getText().toString());
        span.setSpan(new ForegroundColorSpan(Color.RED), 0, 2, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        restart_passworld_id.setText(span);
    }

    @Override
    public void onFinish() {
        restart_passworld_id.setText(ActivityForgetPassword.getResources().getString(R.string.phone_get_number));
        restart_passworld_id.setClickable(true);
        if(img != 0) {
            restart_passworld_id.setBackgroundResource(img);
        }
    }
}
