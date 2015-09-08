package com.cdhxqh.household_app.ui.actvity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.ui.widget.CartoonDisplay;
import com.cdhxqh.household_app.ui.widget.Photo.PhotoUtil;

/**
 * Created by Administrator on 2015/9/8.
 */
public class Activity_Write_Information extends BaseActivity{
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
     *灰色屏幕
     */
    private RelativeLayout select_p;

    /**
     *拍照
     */
    private static final int ITEM_1 = 1;

    /**
     *从相册中查找
     */
    private static final int ITEM_2 = 2;

    private static final int QRCODE = 3;

    /**
     * 显示相片
     */
    private ImageView view_photo;

    /**
     *拍摄或选择图片
     */
    private TextView site_photo;
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_information);
        findViewById();
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
    }

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

