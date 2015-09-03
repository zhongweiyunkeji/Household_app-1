package com.cdhxqh.household_app.ui.widget.Photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/22.
 */
public class PhotoUtil {

    /** Called when the activity is first created. */
    /**
     * 相册框
     */


//    EditText view_et;
    public static Activity activity;
    // 线程通知上传成功
//    final Handler upLoadhand = new Handler(this);
//    String[] arrayString = { "拍照", "相册" };
//    String title = "上传照片";

    // 上传的地址
//    String uploadUrl = "http://192.168.1.101:8080/UploadServlet/UploadServlet?";
    String filename = "照片";
    private static final int PHOTO_REQUEST_TAKEPHOTO = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

//    @Override
//    public boolean handleMessage(Message msg) {
//
//        if (msg.obj != null) {
//            Drawable drawable = new BitmapDrawable((Bitmap) msg.obj);
//            contactser.setBackgroundDrawable(drawable);
//            Toast.makeText(activity, "获得图片并且头像上传成功", 3).show();
//        }else
//        {
//            Toast.makeText(activity, "获得图片，但是头像上传失败，请注意配置uploadUrl上传地址", 3).show();
//        }
//
//        return false;
//    }

    // 创建一个以当前时间为名称的文件
    public static File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    // 对话框
//    DialogInterface.OnClickListener onDialogClick = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            switch (which) {
//                case 0:
//                    startCamearPicCut(dialog);// 开启照相
//                    break;
//                case 1:
//                    startImageCaptrue(dialog);// 开启图库
//                    break;
//                default:
//                    break;
//            }
//        }

        public static void startCamearPicCut() {
            // TODO Auto-generated method stub
            // 调用系统的拍照功能
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            intent.putExtra("camerasensortype", 2);// 调用前置摄像头
            intent.putExtra("autofocus", true);// 自动对焦
            intent.putExtra("fullScreen", false);// 全屏
            intent.putExtra("showActionIcons", false);
            // 指定调用相机拍照后照片的储存路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
            activity.startActivityForResult(intent, PHOTO_REQUEST_TAKEPHOTO);
        }

        public static void startImageCaptrue() {
            // TODO Auto-generated method stub
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    "image/*");
            activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
        }
//    };


//    private void init() {
//        // TODO Auto-generated method stub
//        view_pic = (ImageView) findViewById(R.id.iv);
//        view_btn = (Button) findViewById(R.id.btn);
//        view_et = (EditText) findViewById(R.id.et);
//        view_btn.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // TODO Auto-generated method stub
//                AlertDialog.Builder dialog = AndroidClass.getListDialogBuilder(
//                        MainActivity.context, arrayString, title,
//                        onDialogClick);
//                dialog.show();
//            }
//        });
//    }

    // 使用系统当前日期加以调整作为照片的名称
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case PHOTO_REQUEST_TAKEPHOTO:
//                startPhotoZoom(Uri.fromFile(tempFile), 150);
//                break;
//
//            case PHOTO_REQUEST_GALLERY:
//                if (data != null) {
//                    startPhotoZoom(data.getData(), 150);
//                }
//                break;
//
//            case PHOTO_REQUEST_CUT:
//                if (data != null) {
//                    setPicToView(data);
//                }
//                break;
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    public static void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    // 将进行剪裁后的图片显示到UI界面上
    public static void  setPicToView(Intent picdata) {
        Bundle bundle = picdata.getExtras();
        if (bundle != null) {
            ImageView contactser = (ImageView) activity.findViewById(R.id.view_photo);
            contactser.setVisibility(View.VISIBLE);
            TextView site_photo = (TextView) activity.findViewById(R.id.site_photo);
            site_photo.setVisibility(View.GONE);
            final Bitmap photo = bundle.getParcelable("data");
            contactser.setImageBitmap(photo);
//            new Thread() {
//
//                @Override
//                public void run() {
//                    byte[] photodata = GraphicsBitmapUtils.Bitmap2Bytes(photo);
//                    UploadFile uploadFile = new UploadFile(uploadUrl);
//                    Map parameters = new HashMap();
//                    parameters.put("msg", view_et.getText().toString());
//
//                    boolean isUploadSuccess = false;
//
//                    try {
//                        isUploadSuccess = uploadFile.defaultUploadMethod(
//                                photodata, filename, parameters);
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//
//                    if (isUploadSuccess) {
//                        upLoadhand.obtainMessage(0, photo).sendToTarget();
//                    } else {
//                        upLoadhand.obtainMessage(-1, null).sendToTarget();
//                    }
//
//                }
//            }.start();

        }
    }

}
