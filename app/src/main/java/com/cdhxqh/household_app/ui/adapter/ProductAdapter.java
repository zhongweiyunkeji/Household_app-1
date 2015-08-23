package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.ProductModel;
import com.cdhxqh.household_app.ui.actvity.SafeActivity;

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
//            viewHolder.icon_id.setImageDrawable();
//            viewHolder.isOpen_id.setText("开启");
//            viewHolder.isOpen_id.setTextColor(Color.RED);
            viewHolder.product_id.setText(productModel.get(i).getProduct_id());
            viewHolder.isOpen_id.setText(productModel.get(i).getPosition_id());
            viewHolder.isOpen_id.setText(productModel.get(i).getMonitor_time_id());
            /**
             * 设置监控
             */
            viewHolder.isVideo_listbg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent();
                    intent.setClass(mContext,SafeActivity.class);
                    mContext.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return productModel.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * 图标
         */
        private ImageView icon_id;

        /**
         * 名称
         */
        private TextView product_id;

        /**
         * 是否开启
         */
        private TextView isOpen_id;

        /**
         *位置
         */
        private TextView position_id;

        /**
         *监控时间
         */
        private TextView monitor_time_id;

        /**
         *进入箭头
         */
        private ImageView safe_manage;

        /**
         * 测试
         */
        private LinearLayout isVideo_listbg;


        public ViewHolder(View itemView) {
            super(itemView);
            icon_id = (ImageView) itemView.findViewById(R.id.icon_id);
            product_id = (TextView) itemView.findViewById(R.id.product_id);
            isOpen_id = (TextView) itemView.findViewById(R.id.isOpen_id);
            position_id = (TextView) itemView.findViewById(R.id.position_id);
            monitor_time_id = (TextView) itemView.findViewById(R.id.monitor_time_id);
            safe_manage = (ImageView) itemView.findViewById(R.id.safe_manage);
            isVideo_listbg = (LinearLayout) itemView.findViewById(R.id.isVideo_listbg);
        }
    }
}
