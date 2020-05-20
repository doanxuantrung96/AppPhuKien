package com.example.demo1123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo1123.Model.LoaiSP;
import com.example.demo1123.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LoaiSPAdapter extends BaseAdapter {
    ArrayList<LoaiSP> arrayListlsp;
    Context context;

    public LoaiSPAdapter(ArrayList<LoaiSP> arrayListlsp, Context context) {
        this.arrayListlsp = arrayListlsp;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListlsp.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListlsp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView txtTenloaisp;
        ImageView txtHinhloaisp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listmenu, null);
            viewHolder.txtTenloaisp = convertView.findViewById(R.id.txt_item_tenloaisp);
            viewHolder.txtHinhloaisp = convertView.findViewById(R.id.img_item_menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        LoaiSP loaiSP = (LoaiSP) getItem(position);
        viewHolder.txtTenloaisp.setText(loaiSP.getTenloaisp());

        Picasso.get().load(loaiSP.hinhanhloaiSP).into(viewHolder.txtHinhloaisp);

        return convertView;
    }
}
