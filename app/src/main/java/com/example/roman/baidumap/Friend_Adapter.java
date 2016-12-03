package com.example.roman.baidumap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Roman on 2016/10/17.
 */
public class Friend_Adapter extends BaseAdapter{
    private static final int TYPE_FRIEND = 0;
    private static final int TYPE_ENEMY = 1;
    private Context mContext;
    private ArrayList<Object> mList = null;

    public Friend_Adapter(){}

    public Friend_Adapter(ArrayList<Object> mList,Context mContext){
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public Object getItem(int position){
        return null;
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public int getItemViewType(int position){
        if (mList.get(position) instanceof friend){
            return TYPE_FRIEND;
        }else if (mList.get(position) instanceof enemy){
            return TYPE_ENEMY;
        }else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        int type = getItemViewType(position);
        ViewHolder holder = null;
        if (convertView == null){
            switch (type){
                case TYPE_FRIEND:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.friends_list_item, parent, false);
                    break;
                case TYPE_ENEMY:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.enemies_list_item, parent, false);
                    break;
            }
            holder = new ViewHolder();
            holder.name_cell = (TextView) convertView.findViewById(R.id.name_cell);
            holder.delete_btn = (Button) convertView.findViewById(R.id.delete_button_cell);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }
        Object obj = mList.get(position);
        switch (type){
            case TYPE_FRIEND:
                friend f = (friend) obj;
                if (f!=null)
                    holder.name_cell.setText(f.getName());
                break;
            case TYPE_ENEMY:
                enemy e = (enemy) obj;
                if (e!=null)
                    holder.name_cell.setText(e.getName());
                break;
        }
        holder.delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
                MainActivity.FriendNum--;
            }
        });
        return convertView;
    }

    private class ViewHolder{
        TextView name_cell;
        Button delete_btn;
    }

}
