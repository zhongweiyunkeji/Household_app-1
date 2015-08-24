package com.cdhxqh.household_app.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cdhxqh.household_app.R;
import com.cdhxqh.household_app.model.Contacters;
import com.cdhxqh.household_app.ui.actvity.AlarmActivity;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_view_user_row, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
//            holder.id.setText("*" + String.valueOf(position + 1));
        holder.name.setText(contactersList.get(position).getName());
        holder.phone.setText(contactersList.get(position).getPhone());
        holder.check_id.setChecked(contactersList.get(position).isFlag());

        final ViewHolder h = holder;
        holder.check_id.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                contactersList.get(position).setFlag(h.check_id.isChecked());
                AlarmActivity.update(contactersList);
            }
        });
//        final int i = position;
//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                contactersList.remove(i);
//                ViewUserActivity.update(contactersList);
//                dataChanged();
//            }
//        });
    }

    /**
     * 更新数据
     */
    public void update(ArrayList<Contacters> contactersList) {
        this.contactersList = contactersList;
    }
    // 刷新list
    public void dataChanged() {
        // 通知listView刷新
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return contactersList.size();
    }

    public void setIsSelected() {
        for(int i = 0; i<contactersList.size(); i++) {
            contactersList.get(i).setFlag(true);
        }
        dataChanged();
    }

    public void inverseSelected() {
        for(int i = 0; i<contactersList.size(); i++) {
            contactersList.get(i).setFlag(false);
        }
        dataChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * id
         */
        private CheckBox check_id;

        /**
         * name
         */
        private TextView name;

        /**
         *phone
         */
        private TextView phone;

        public ViewHolder(View itemView) {
            super(itemView);
            check_id = (CheckBox) itemView.findViewById(R.id.check_id);
            name = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.phone);
        }
    }
}
