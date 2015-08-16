package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.actvity.ViewUserActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/8/15.
 */
public class ViewUserAdapter extends RecyclerView.Adapter<ViewUserAdapter.ViewHolder> {
    private Context mContext;
    ArrayList<Contacters> contactersList;

    public ViewUserAdapter(Context paramContext, ArrayList<Contacters> contactersList) {
        this.mContext = paramContext;
        this.contactersList = contactersList;
    }

    @Override
    public ViewUserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_user_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewUserAdapter.ViewHolder holder, int position) {
        if(contactersList.get(position).isFlag() == true) {
            holder.id.setText("*" + String.valueOf(position + 1));
            holder.name.setText(contactersList.get(position).getName());
            holder.phone.setText(contactersList.get(position).getPhone());
        }

        final int i = position;
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactersList.remove(i);
                ViewUserActivity.update(contactersList);
                dataChanged();
            }
        });
    }

    // 刷新list
    private void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactersList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * id
         */
        private TextView id;

        /**
         * name
         */
        private TextView name;

        /**
         *phone
         */
        private TextView phone;

        /**
         *删除
         */
        private ImageView delete;

        public ViewHolder(View itemView) {
            super(itemView);
            id = (TextView) itemView.findViewById(R.id.id);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
            delete = (ImageView) itemView.findViewById(R.id.delete);
        }
    }
}
