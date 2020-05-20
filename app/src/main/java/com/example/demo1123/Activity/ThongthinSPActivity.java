package com.example.demo1123.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.demo1123.Model.GioHang;
import com.example.demo1123.Model.SanPham;
import com.example.demo1123.R;
import com.squareup.picasso.Picasso;
import java.text.DecimalFormat;

public class ThongthinSPActivity extends AppCompatActivity {

    TextView txtTensp, txtMotasp, txtGiasp, txttongtien;
    ImageView imgAnhsp;
    Spinner spinner;
    Button btnthemgiohang;
    ImageView btnback, btnHome;
    int id = 0;
    String tensp = "";
    Integer giasp = 0;
    String anhsp = "";
    String motasp = "";
    int idsp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongthin_sp);
        Map();
        ThongtinSP();
        SoLuongSanPham();
        ChonSoLuongSP();
        back();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mngiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang_Activity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void back() {
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ChonSoLuongSP() {

        final Integer[] listsoluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> SniperAdapter = new ArrayAdapter<Integer>(this, R.layout.item_spiner, listsoluong);
        SniperAdapter.setDropDownViewResource(R.layout.item_spiner);
        spinner.setAdapter(SniperAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int slm = Integer.parseInt(spinner.getSelectedItem().toString());

                long tonggiamua = slm * giasp;

                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txttongtien.setText(decimalFormat.format(tonggiamua) + "vnđ");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SoLuongSanPham() {
        btnthemgiohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean exits = false;
                if (MainActivity.arrayList_giohang.size() > 0) {
                    int slm = Integer.parseInt(spinner.getSelectedItem().toString());
                    for (int i = 0; i < MainActivity.arrayList_giohang.size(); i++) {
                        if (MainActivity.arrayList_giohang.get(i).getIdsp() == id) {
                            MainActivity.arrayList_giohang.get(i).setSoluongsp(MainActivity.arrayList_giohang.get(i).getSoluongsp() + slm);
                            if (MainActivity.arrayList_giohang.get(i).getSoluongsp() > 10) {
                                MainActivity.arrayList_giohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.arrayList_giohang.get(i).setGiasp(giasp * MainActivity.arrayList_giohang.get(i).getSoluongsp());
                            exits = true;
                        }
                    }
                    if (exits == false) {
                        int soluongmua = Integer.parseInt(spinner.getSelectedItem().toString());
                        long tonggiamua = soluongmua * giasp;
                        MainActivity.arrayList_giohang.add(new GioHang(id, tensp, tonggiamua, anhsp, soluongmua));
                    }
                } else {
                    int soluongmua = Integer.parseInt(spinner.getSelectedItem().toString());
                    long tonggiamua = soluongmua * giasp;
                    MainActivity.arrayList_giohang.add(new GioHang(id, tensp, tonggiamua, anhsp, soluongmua));
                }
                Intent intent = new Intent(ThongthinSPActivity.this, GioHang_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void ThongtinSP() {
        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("chitietsanpham");
        id = sanPham.getId();
        tensp = sanPham.getTensanpham();
        giasp = sanPham.getGiasanpham();
        anhsp = sanPham.getAnhsanpham();
        motasp = sanPham.getMotasanpham();
        idsp = sanPham.getIdsp();

        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        txtTensp.setText(tensp);
        txtGiasp.setText(decimalFormat.format(giasp) + " vnđ");
        txtMotasp.setText(motasp);
        Picasso.get()
                .load(anhsp).into(imgAnhsp);

    }

    private void Map() {
        txtTensp = findViewById(R.id.txtTenSP_chitiet);
        txtGiasp = findViewById(R.id.txtGiaSP_chitiet);
        txtMotasp = findViewById(R.id.txtMotaSP_chitiet);
        txttongtien = findViewById(R.id.txt_thongtin_tongtien);

        imgAnhsp = findViewById(R.id.imgAnhSP_chitiet);

        btnback = findViewById(R.id.btn_back_tt);
        btnHome = findViewById(R.id.btn_thongtin_home);

        spinner = findViewById(R.id.spinner);

        btnthemgiohang = findViewById(R.id.btn_themgiohang);
    }
}
