package com.example.demo1123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.demo1123.Activity.GioHang_Activity;
import com.example.demo1123.Activity.MainActivity;
import com.example.demo1123.Model.GioHang;
import com.example.demo1123.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    ArrayList<GioHang> arrayListlgh;
    Context context;

    public GioHangAdapter(ArrayList<GioHang> arrayListlgh, Context context) {
        this.arrayListlgh = arrayListlgh;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListlgh.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListlgh.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttensp, txtgiasp, txtsoluongsp;
        public ImageView imghinhsp, btnminus, btnplus;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listgiohang, null);
            viewHolder.txttensp = convertView.findViewById(R.id.txt_itemgiohang_tensanpham);
            viewHolder.txtgiasp = convertView.findViewById(R.id.txt_itemgiohang_giasp);
            viewHolder.txtsoluongsp = convertView.findViewById(R.id.txt_itemgh_soluong);
            viewHolder.imghinhsp = convertView.findViewById(R.id.img_itemgh_anhsp);
            viewHolder.btnminus = convertView.findViewById(R.id.btn_itemgh_minus);
            viewHolder.btnplus = convertView.findViewById(R.id.btn_itemgh_plus);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        GioHang gioHang = (GioHang) getItem(position);
        viewHolder.txttensp.setText(gioHang.getTensp());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiasp.setText(decimalFormat.format(gioHang.getGiasp()) + "vnđ");

        Picasso.get().load(gioHang.getAnhsp()).into(viewHolder.imghinhsp);
        viewHolder.txtsoluongsp.setText(gioHang.getSoluongsp()+"");

        int slsp = Integer.parseInt(viewHolder.txtsoluongsp.getText().toString()) ;
        if (slsp >= 10){
            viewHolder.btnplus.setEnabled(false);
            viewHolder.btnminus.setEnabled(true);
        }else if (slsp <= 1){
            viewHolder.btnplus.setEnabled(true);
            viewHolder.btnminus.setEnabled(false);
        }else {
            viewHolder.btnplus.setEnabled(true);
            viewHolder.btnminus.setEnabled(true);
        }


        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(finalViewHolder.txtsoluongsp.getText().toString()) + 1;
                int slht = MainActivity.arrayList_giohang.get(position).getSoluongsp();
                long giaht = MainActivity.arrayList_giohang.get(position).getGiasp();

                MainActivity.arrayList_giohang.get(position).setSoluongsp(slm);
                long giamoi = giaht * slm / slht;
                MainActivity.arrayList_giohang.get(position).setGiasp(giamoi);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiasp.setText(decimalFormat.format(giamoi) + "vnđ");

                GioHang_Activity.TongTien();
                if (slm > 9){
                    finalViewHolder.btnplus.setEnabled(false);
                    finalViewHolder.btnminus.setEnabled(true);

                    finalViewHolder.txtsoluongsp.setText(String.valueOf(slm));
                }else {
                    finalViewHolder.btnplus.setEnabled(true);
                    finalViewHolder.btnminus.setEnabled(true);

                    finalViewHolder.txtsoluongsp.setText(String.valueOf(slm));
                }
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slm = Integer.parseInt(finalViewHolder.txtsoluongsp.getText().toString()) - 1;
                int slht = MainActivity.arrayList_giohang.get(position).getSoluongsp();
                long giaht = MainActivity.arrayList_giohang.get(position).getGiasp();

                MainActivity.arrayList_giohang.get(position).setSoluongsp(slm);
                long giamoi = giaht * slm / slht;
                MainActivity.arrayList_giohang.get(position).setGiasp(giamoi);

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiasp.setText(decimalFormat.format(giamoi) + "vnđ");

                GioHang_Activity.TongTien();
                if (slm < 2){
                    finalViewHolder.btnplus.setEnabled(true);
                    finalViewHolder.btnminus.setEnabled(false);

                    finalViewHolder.txtsoluongsp.setText(String.valueOf(slm));
                }else {
                    finalViewHolder.btnplus.setEnabled(true);
                    finalViewHolder.btnminus.setEnabled(true);

                    finalViewHolder.txtsoluongsp.setText(String.valueOf(slm));
                }
            }
        });

        return convertView;
    }
}
