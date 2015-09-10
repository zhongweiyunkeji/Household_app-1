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
import com.cdhxqh.household_app.model.MyDevice;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.ui.actvity.SafeActivity;
import com.cdhxqh.household_app.ui.actvity.SafeCenterActivity;

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
        viewHolder.safe_manage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, SafeCenterActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCTMODEL", model);
                mContext.startActivity(intent);
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

        public ViewHolder(View itemView) {
            super(itemView);
            icon_id = (ImageView) itemView.findViewById(R.id.icon_id);
            product_id = (TextView) itemView.findViewById(R.id.product_id);
            position_id = (TextView) itemView.findViewById(R.id.position_id);
            safe_manage = (ImageView) itemView.findViewById(R.id.safe_manage);
        }
    }
}
