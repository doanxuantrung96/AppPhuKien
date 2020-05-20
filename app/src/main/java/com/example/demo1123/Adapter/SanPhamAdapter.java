package com.example.demo1123.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1123.Model.SanPham;
import com.example.demo1123.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {

    Context context;
    ArrayList<SanPham> arrayListsanpham;

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public SanPhamAdapter(Context context, ArrayList<SanPham> arrayListsanpham) {
        this.context = context;
        this.arrayListsanpham = arrayListsanpham;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_sanphammoi, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        SanPham sanPham = arrayListsanpham.get(position);
        holder.txttensanpham.setText(sanPham.getTensanpham());

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText(decimalFormat.format(sanPham.getGiasanpham()) + " vnđ");

        Picasso.get().load(sanPham.getAnhsanpham()).into(holder.imgsanpham);

    }
    @Override
    public int getItemCount() {
        return arrayListsanpham.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgsanpham;
        public TextView txttensanpham, txtgiasanpham;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            imgsanpham = itemView.findViewById(R.id.img_item_listsanpham);
            txttensanpham = itemView.findViewById(R.id.txtTensp_item_listsanpham);
            txtgiasanpham = itemView.findViewById(R.id.txtGia_item_listsanpham);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(itemView, getLayoutPosition());
                    }
                }
            });
        }
    }

}
