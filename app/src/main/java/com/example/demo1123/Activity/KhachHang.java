package com.example.demo1123.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo1123.R;
import com.example.demo1123.Ultil.CheckPermission;
import com.example.demo1123.Ultil.Url_Path;

import java.util.HashMap;
import java.util.Map;

public class KhachHang extends AppCompatActivity {

    EditText edtTenKH, edtSdtKH, edtEmailKH;
    Button btnCancel, btnXacNhan;
    ImageView btnBack, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khach_hang);
        Map();
        Back();
        MuaHang();
    }

    private void MuaHang() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenkh = edtTenKH.getText().toString().trim();
                final String sdt = edtSdtKH.getText().toString().trim();
                final String email = edtEmailKH.getText().toString().trim();
                if (tenkh.length() > 0 && sdt.length() > 0 && email.length() >0){
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Url_Path.urlThongTinKhachHang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d("ASD",response);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", tenkh);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);

                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }
            }
        });
    }
    private void Back() {
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
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

    private void Map() {
        edtTenKH = findViewById(R.id.edt_khachhang_tenkh);
        edtSdtKH = findViewById(R.id.edt_khachhang_esdt);
        edtEmailKH = findViewById(R.id.edt_khachhang_email);

        btnCancel = findViewById(R.id.btn_khachhang_cancel);
        btnBack = findViewById(R.id.btn_khachhang_back);
        btnXacNhan = findViewById(R.id.btn_khachhang_xacnhan);
        btnHome = findViewById(R.id.btn_khachhang_home);
    }
}
