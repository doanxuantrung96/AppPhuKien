package com.example.demo1123.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo1123.Adapter.ListSPAdapter;
import com.example.demo1123.Adapter.SanPhamAdapter;
import com.example.demo1123.Model.SanPham;
import com.example.demo1123.R;
import com.example.demo1123.Ultil.CheckPermission;
import com.example.demo1123.Ultil.Url_Path;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListTaiNghe_Activity extends AppCompatActivity {

    RecyclerView rcv_tainghe;
    ImageView btnback, btnHome;
    ArrayList<SanPham> arrayListSanPham;
    ListSPAdapter listSPAdapter;
    View loadmore_view;
    int idtainghe = 0;
    int page = 1;
    boolean isScrolling = false;
    int currenItems, scrolloutItems, totalItems;
    LinearLayoutManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__loai_sp_);

        Map();
        Gettainghe(page);
        LoadMore();
        back();
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

    private void LoadMore() {
        manager = new LinearLayoutManager(this);

        rcv_tainghe.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                currenItems = manager.getChildCount();
                totalItems = manager.getItemCount();
                scrolloutItems = manager.findFirstCompletelyVisibleItemPosition();

                if (isScrolling && (currenItems + scrolloutItems == totalItems) && totalItems != 0) {

                    isScrolling = false;
                    mHandler();
                }
            }
        });

        listSPAdapter.setOnItemClickListener(new ListSPAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(ListTaiNghe_Activity.this, ThongthinSPActivity.class);
                intent.putExtra("chitietsanpham", arrayListSanPham.get(position));
                startActivity(intent);
            }
        });

    }

    private void mHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    listSPAdapter.notifyDataSetChanged();
                }
            }
        }, 5000);
    }

    private void Gettainghe(final int page) {

        idtainghe = getIntent().getIntExtra("idsanpham", 1);
        Log.d("DSA", idtainghe + "");

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        String linktainghe = Url_Path.urlTaiNghe + String.valueOf(page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, linktainghe, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    int id = 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String anhsp = "";
                    String motasp = "";
                    int idsp = 0;

                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensanpham");
                            giasp = jsonObject.getInt("giasanpham");
                            anhsp = jsonObject.getString("hinhanhsanpham");
                            motasp = jsonObject.getString("motasanpham");
                            idsp = jsonObject.getInt("idsanpham");

                            arrayListSanPham.add(new SanPham(id, tensp, giasp, anhsp, motasp, idsp));
                            listSPAdapter.notifyDataSetChanged();
                            Log.d("DSA",id+ tensp+ giasp+ anhsp+ motasp+ idsp );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckPermission.ToastMessage(getApplicationContext(), error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(idtainghe));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void Map() {
        btnback = findViewById(R.id.btn_back_lsp);
        btnHome = findViewById(R.id.btn_loaisp_home);

        rcv_tainghe = findViewById(R.id.rcv_list_tainghe_menu);
        arrayListSanPham = new ArrayList<>();
        listSPAdapter = new ListSPAdapter(getApplicationContext(), arrayListSanPham);
        rcv_tainghe.setHasFixedSize(true);
        rcv_tainghe.setLayoutManager(new GridLayoutManager(ListTaiNghe_Activity.this, 2));
        rcv_tainghe.setAdapter(listSPAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

        loadmore_view = inflater.inflate(R.layout.progressbar, null);
    }
}
