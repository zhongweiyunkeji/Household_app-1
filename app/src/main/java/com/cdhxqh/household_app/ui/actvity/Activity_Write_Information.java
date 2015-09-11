package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.AlramProcessMsg;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.widget.CartoonDisplay;
import com.cdhxqh.household_app.ui.widget.Photo.PhotoUtil;
import com.cdhxqh.household_app.ui.widget.SwitchButtonIs;
import com.cdhxqh.household_app.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/9/8.
 */
public class Activity_Write_Information extends BaseActivity {
    /**
     * 拍摄照片
     */
    private RelativeLayout shot_photo;

    /**
     * 第一栏拍照
     */
    private LinearLayout create_user;

    /**
     * 第二栏从相册中选择
     */
    private LinearLayout address_book;

    /**
     * 取消
     */
    private LinearLayout cancel;

    /**
     * 灰色屏幕
     */
    private RelativeLayout select_p;

    /**
     * 拍照
     */
    private static final int ITEM_1 = 1;

    /**
     * 从相册中查找
     */
    private static final int ITEM_2 = 2;

    private static final int QRCODE = 3;

    /**
     * 显示相片
     */
    private ImageView view_photo;

    /**
     * 拍摄或选择图片
     */
    private TextView site_photo;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    Button submit; // 提交按钮

    /**
     * 返回按钮*
     */
    private ImageView back_imageview_id;
    /**
     * 标题*
     */
    private TextView titleTextView;
    /**
     * 搜索*
     */
    private ImageView title_add_id;

    SwitchButtonIs wiperSwitch1;  // 是否已协助核查
    SwitchButtonIs wiperSwitch2;  // 是否存在安全隐患
    SwitchButtonIs wiperSwitch3; // 是否已处理
    // SwitchButtonIs wiperSwitch4; // 关闭

    AlramProcessMsg msg;

    EditText  processmsg;
    int alram_uid; // 报警记录的用户id

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_information);
        findViewById();
        getData();
        initView();
    }

    public void findViewById() {
        shot_photo = (RelativeLayout) findViewById(R.id.shot_photo);
        cancel = (LinearLayout) findViewById(R.id.cancel);
        create_user = (LinearLayout) findViewById(R.id.create_user);
        address_book = (LinearLayout) findViewById(R.id.address_book);
        select_p = (RelativeLayout) findViewById(R.id.select_p);
        view_photo = (ImageView) findViewById(R.id.view_photo);
        site_photo = (TextView) findViewById(R.id.site_photo);

        wiperSwitch1 = (SwitchButtonIs) findViewById(R.id.wiperSwitch1);
        wiperSwitch2 = (SwitchButtonIs) findViewById(R.id.wiperSwitch2);
        wiperSwitch3 = (SwitchButtonIs) findViewById(R.id.wiperSwitch3);
        // wiperSwitch4 = (SwitchButtonIs) findViewById(R.id.wiperSwitch4);

        processmsg = (EditText)findViewById(R.id.processmsg);
        submit = (Button) findViewById(R.id.restart_passworld_id);
        /**
         * 标题标签相关id
         */
        back_imageview_id = (ImageView) findViewById(R.id.back_imageview_id);
        titleTextView = (TextView) findViewById(R.id.title_text_id);
        title_add_id = (ImageView) findViewById(R.id.title_add_id);
    }

    public void getData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            msg =  (AlramProcessMsg)bundle.getSerializable("MPROCESSMSG");
            alram_uid = bundle.getInt("alram_uid");
        }
    }

    public void initView() {
        /**
         * 拍摄照片监听
         */
        shot_photo.setOnClickListener(shotPhotoOnClickListener);
        /**
         * 取消
         */
        cancel.setOnClickListener(cancelOnClickListener);
        /**
         * 第一栏拍照
         */
        create_user.setOnClickListener(createOnClickListener);
        /**
         * 第二栏从相册中选择
         */
        address_book.setOnClickListener(addressOnClickListener);

        /**
         *灰色屏幕
         */
        select_p.setOnClickListener(selectOnClickListener);

        back_imageview_id.setOnTouchListener(backImageViewOnTouchListener);

        //返回至登录界面事件
        back_imageview_id.setOnClickListener(backImageViewOnClickListener);

        //设置标签页显示方式
        back_imageview_id.setVisibility(View.VISIBLE);
        title_add_id.setVisibility(View.GONE);
        titleTextView.setText("填写反馈信息");

        if(msg!=null){
            // 是否已协助核查
            wiperSwitch1.setState(msg.getHelpcheck()==1 ? true : false);
            // 是否存在安全隐患
            wiperSwitch2.setState(msg.getHelpcheck() ==1 ? true : false);
            // 是否已处理
            wiperSwitch3.setState(msg.getIsprocess() ==1 ? true : false);
            // 处理信息
            processmsg.setText(msg.getProcessResult());
        }

        wiperSwitch1.setOnChangeListener(new SwitchButtonIs.OnChangeListener() {
            @Override
            public void onChange(SwitchButtonIs sb, boolean state) {
                if(alram_uid == msg.getUid() || Constants.USER_ID == msg.getUid()){  // 户主可以修改所有的报警记录, 同时其他协助人员只能修改自己的记录

                } else { // 提示用户不能修改记录
                    ToastUtil.showMessage(Activity_Write_Information.this, "您当前不能修改该记录");
                }
            }
        });

        wiperSwitch2.setOnChangeListener(new SwitchButtonIs.OnChangeListener() {
            @Override
            public void onChange(SwitchButtonIs sb, boolean state) {
                if(alram_uid == msg.getUid() || Constants.USER_ID == msg.getUid()){  // 户主可以修改所有的报警记录, 同时其他协助人员只能修改自己的记录

                } else { // 提示用户不能修改记录
                    ToastUtil.showMessage(Activity_Write_Information.this, "您当前不能修改该记录");
                }
            }
        });

        wiperSwitch3.setOnChangeListener(new SwitchButtonIs.OnChangeListener() {
            @Override
            public void onChange(SwitchButtonIs sb, boolean state) {
                if (alram_uid == msg.getUid() || Constants.USER_ID == msg.getUid()) {  // 户主可以修改所有的报警记录, 同时其他协助人员只能修改自己的记录

                } else { // 提示用户不能修改记录
                    ToastUtil.showMessage(Activity_Write_Information.this, "您当前不能修改该记录");
                }
            }
        });

        if(alram_uid == msg.getUid() || Constants.USER_ID == msg.getUid()){  // 户主可以修改所有的报警记录, 同时其他协助人员只能修改自己的记录

        } else { // 提示用户不能修改记录
            processmsg.setEnabled(false);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alram_uid == msg.getUid() || Constants.USER_ID == msg.getUid()){  // 户主可以修改所有的报警记录, 同时其他协助人员只能修改自己的记录
                    String processResult = processmsg.getText().toString();
                    if(processResult==null){
                        processResult = "";
                    }
                    final String procResult = processResult;
                    RequestParams maps = new RequestParams();
                    maps.put("alarm_id", msg.getAlramid());
                    maps.put("helpcheck", wiperSwitch1.getState() ? 1 : 0);
                    maps.put("hasdanger", wiperSwitch2.getState() ? 1 : 0);
                    maps.put("isprocess", wiperSwitch3.getState() ? 1 : 0);
                    maps.put("processResult", processResult);
                    HttpManager.sendHttpRequest(Activity_Write_Information.this, Constants.GET_ALARM_PROCESS, maps, "post", false, new HttpCallBackHandle() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                            // {"errcode":"SECURITY-GLOBAL-S-0","errmsg":"请求成功","result":null}
                            if(responseBody!=null){
                                try {
                                    JSONObject object = new JSONObject(responseBody);
                                    String errcode = object.getString("errcode");
                                    if("SECURITY-GLOBAL-S-0".equals(errcode)){
                                        msg.setHasdanger(wiperSwitch1.getState() ? 1 : 0);
                                        msg.setHasdanger(wiperSwitch2.getState() ? 1 : 0);
                                        msg.setIsprocess(wiperSwitch3.getState() ? 1 : 0);
                                        msg.setProcessResult(procResult);
                                        msg.setProcesstimeStr(sdf.format(new Date()));
                                        ToastUtil.showMessage(Activity_Write_Information.this, "更新成功");
                                    } else {
                                        ToastUtil.showMessage(Activity_Write_Information.this, "更新失败");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, String responseBody, Throwable error) {
                            Log.i("TAG", "TAG");
                            Log.i("TAG", "TAG");
                        }
                    });
                } else { // 提示用户不能修改记录
                    ToastUtil.showMessage(Activity_Write_Information.this, "您当前不能修改该记录");
                }
            }
        });

    }

    private View.OnTouchListener backImageViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(getResources().getColor(R.color.list_item_read));
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                v.setBackgroundColor(getResources().getColor(R.color.clarity));
            }
            return false;
        }
    };

    /**
     * 返回事件的监听*
     */
    private View.OnClickListener backImageViewOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activity_Write_Information.this, Activity_Alarm_Process.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", msg);
            //设置返回数据
            Activity_Write_Information.this.setResult(1000, intent);
            finish();
        }
    };


    /**
     * 拍摄照片监听
     */
    final Activity activity = this;
    private View.OnClickListener shotPhotoOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };

    /**
     * 取消
     */
    private View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };

    /**
     * 取消灰色屏幕
     */
    private View.OnClickListener selectOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
        }
    };

    /**
     * 第一栏拍照
     */
    private View.OnClickListener createOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
            PhotoUtil.startCamearPicCut();
        }
    };

    /**
     * 第二栏从相册中选择
     */
    private View.OnClickListener addressOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            new CartoonDisplay(activity, 2, new String[]{"拍照", "从相册选择"}).display();
            PhotoUtil.startImageCaptrue();
        }
    };

    /**
     * 相册的回调方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_TAKEPHOTO:
                PhotoUtil.startPhotoZoom(Uri.fromFile(PhotoUtil.tempFile), 150);
                break;

            case PHOTO_REQUEST_GALLERY:
                if (data != null) {
                    PhotoUtil.startPhotoZoom(data.getData(), 150);
                }
                break;

            case PHOTO_REQUEST_CUT:
                if (data != null) {
                    PhotoUtil.setPicToView(data);
                }
                break;
        }
    }
}

