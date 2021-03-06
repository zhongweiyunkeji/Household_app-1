package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.app.HttpManager;
import com.cdhxqh.household_app.config.Constants;
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.model.SaceConfig;
import com.cdhxqh.household_app.ui.action.HttpCallBackHandle;
import com.cdhxqh.household_app.ui.actvity.SafeActivity;
import com.cdhxqh.household_app.ui.actvity.SafeCenterActivity;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/14.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<ProductModel> productModel;

    public ProductAdapter(Context paramContext, ArrayList<ProductModel> productModel) {
        this.mContext = paramContext;
        this.productModel = productModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_product_row, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ProductModel model = productModel.get(i);
        viewHolder.position_id.setText(model.getPosition_id());
        viewHolder.product_id.setText(model.getProduct_id());
        // 设置监控
        viewHolder.safe_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送网络请求
                RequestParams maps = new RequestParams();
                maps.put("ca_id", model.getCaid());
                HttpManager.sendHttpRequest(mContext, Constants.GET_SAFE_SETTING, maps, "get", false, new HttpCallBackHandle() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseBody) {
                        if(responseBody!=null){
                            try {
                                JSONObject object = new JSONObject(responseBody);
                                JSONObject result = object.getJSONObject("result");
                                if(result!=null){
                                    int acid = result.getInt("ac_id");  // 主键id
                                    int caid = result.getInt("ca_id");  // 设备id
                                    int uid = result.getInt("uid");    // 用户id
                                    String alarmType = result.getString("alarmtype");  // 报警类型
                                    int email = result.getInt("email");    // email
                                    int sms = result.getInt("sms");    // 短信
                                    int app = result.getInt("app");    // ??????
                                    int groupid = result.getInt("groupid");    // ??????
                                    String mobile = result.getString("mobile");  // ???
                                    int help = result.getInt("help");       // ???
                                    int feedback = result.getInt("feedback");  // ????
                                    String helpaddress = result.getString("helpaddress");  //  ?????
                                    String uids = result.getString("uids");  // ?????

                                    SaceConfig config = new SaceConfig(acid, caid, feedback, help, uid, uids, helpaddress);
                                    Intent intent = new Intent(mContext, SafeCenterActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("PRODUCTMODEL", config);
                                    intent.putExtras(bundle);
                                    mContext.startActivity(intent);
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    public void update(ArrayList<ProductModel> data, boolean merge) {
        if (merge) {
            for (int i = 0; i < data.size(); i++) {
                ProductModel device = data.get(i);
                boolean exist = false;
                for (int j = 0; j < productModel.size(); j++) {
                    if (productModel.get(j).getDeviceSerial().equals(device.getDeviceSerial())) {
                        exist = true;
                        break;
                    }
                }
                if (exist) continue;
                productModel.add(0, device);
            }
        }

        data = productModel;

        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // 图标
        private ImageView icon_id;
        // 安装位置
        private TextView product_id;
        // 家庭住址
        private TextView position_id;
        //进入箭头
        private ImageView safe_manage;

        private LinearLayout safe_line;

        public ViewHolder(View itemView) {
            super(itemView);
            safe_line = (LinearLayout) itemView.findViewById(R.id.ssfe_line);
            icon_id = (ImageView) itemView.findViewById(R.id.icon_id);
            product_id = (TextView) itemView.findViewById(R.id.product_id);
            position_id = (TextView) itemView.findViewById(R.id.position_id);
            safe_manage = (ImageView) itemView.findViewById(R.id.safe_manage);
        }
    }
}
