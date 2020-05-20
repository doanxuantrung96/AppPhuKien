package com.example.demo1123.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo1123.Adapter.GioHangAdapter;
import com.example.demo1123.Model.GioHang;
import com.example.demo1123.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHang_Activity extends AppCompatActivity {

    ListView listView;
    GioHangAdapter gioHangAdapter;
    Button btnmuathem, btnthanhtoan;
    static TextView txttongtien;
    ImageView btnback, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang_);
        Map();
        DataGioHang();
        TongTien();
        XoaItemGioHang();
        back();
        MuaThem();
        ThanhToan();
    }

    private void ThanhToan() {
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), KhachHang.class);
                startActivity(intent);
            }
        });
    }

    private void MuaThem() {
        btnmuathem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListTaiNghe_Activity.class);
                startActivity(intent);
            }
        });
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

    private void XoaItemGioHang() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang_Activity.this);
                builder.setTitle("Xóa sản phẩm này khỏi giỏ hàng");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.arrayList_giohang.size() <= 0) {
                        } else {
                            MainActivity.arrayList_giohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            TongTien();
                            if (MainActivity.arrayList_giohang.size() <= 0) {
                            } else {
                                TongTien();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void TongTien() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.arrayList_giohang.size(); i++) {
            tongtien += MainActivity.arrayList_giohang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien) + "vnđ");
    }

    private void DataGioHang() {
        gioHangAdapter.notifyDataSetChanged();
    }

    private void Map() {
        listView = findViewById(R.id.lv_giohang_listhangthanhtoan);

        txttongtien = findViewById(R.id.txt_giohang_tongtien);

        btnback = findViewById(R.id.btn_back_gh);
        btnHome = findViewById(R.id.btn_giohang_home);
        btnmuathem = findViewById(R.id.btn_giohang_muathem);
        btnthanhtoan = findViewById(R.id.btn_giohang_thanhtoan);

        gioHangAdapter = new GioHangAdapter(MainActivity.arrayList_giohang, getApplicationContext());
        listView.setAdapter(gioHangAdapter);
    }
}
