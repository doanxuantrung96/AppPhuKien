package com.example.demo1123.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.demo1123.Adapter.LoaiSPAdapter;
import com.example.demo1123.Adapter.SanPhamAdapter;
import com.example.demo1123.Model.GioHang;
import com.example.demo1123.Model.LoaiSP;
import com.example.demo1123.Model.SanPham;
import com.example.demo1123.R;
import com.example.demo1123.Ultil.CheckPermission;
import com.example.demo1123.Ultil.Url_Path;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;

    ArrayList<LoaiSP> arrayListLoaiSP;
    LoaiSPAdapter loaiSPAdapter;

    ArrayList<SanPham> arrayListSanPham;
    SanPhamAdapter sanPhamAdapter;

    public static ArrayList<GioHang> arrayList_giohang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Map();

        if (CheckPermission.haveNetworkConnection(getApplicationContext())) {
            Actionbar();
            ActionFlipper();
            GetDataLoaiSP();
            GetDataSanPhamMoi();
            clickItemMenu();
            ClickSanPhan();
        } else {
            CheckPermission.ToastMessage(getApplicationContext(), "Connect fail!");
            finish();
        }
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void ClickSanPhan() {
        sanPhamAdapter.setOnItemClickListener(new SanPhamAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                Intent intent = new Intent(MainActivity.this, ThongthinSPActivity.class);
                intent.putExtra("chitietsanpham", arrayListSanPham.get(position));
                startActivity(intent);
            }
        });
    }

    private void clickItemMenu() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckPermission.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ListTaiNghe_Activity.class);
                            intent.putExtra("idsanpham", arrayListLoaiSP.get(position).getId());
                            startActivity(intent);
                            drawerLayout.closeDrawers();
                        } else {
                            CheckPermission.ToastMessage(getApplicationContext(), "Connect fail!");
                            finish();
                        }
                        break;
                    case 1:
//                        if (CheckPermission.haveNetworkConnection(getApplicationContext())) {
//                            Intent intent = new Intent(MainActivity.this, ListPinDuPhong.class);
////                            intent.putExtra("idsanpham", arrayListLoaiSP.get(position+1).getId());
//                            startActivity(intent);
//                            drawerLayout.closeDrawers();
//                        } else {
//                            CheckPermission.ToastMessage(getApplicationContext(), "Connect fail!");
//                            finish();
//                        }
                        break;
                }
            }
        });
    }

    private void GetDataSanPhamMoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest_spm = new JsonArrayRequest(Url_Path.urlSanPhamMoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String tensp = "";
                    Integer giasp = 0;
                    String anhsp = "";
                    String motasp = "";
                    int idsp = 0;

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                            id = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensanpham");
                            giasp = jsonObject.getInt("giasanpham");
                            anhsp = jsonObject.getString("hinhanhsanpham");
                            motasp = jsonObject.getString("motasanpham");
                            idsp = jsonObject.getInt("idsanpham");

                            arrayListSanPham.add(new SanPham(id, tensp, giasp, anhsp, motasp, idsp));
                            sanPhamAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckPermission.ToastMessage(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(arrayRequest_spm);
    }

    private void GetDataLoaiSP() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest arrayRequest_lsp = new JsonArrayRequest(Url_Path.urlLoaiSP, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int id = 0;
                String tenloaisp = "";
                String hinhloaisp = "";

                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisanpham");
                            hinhloaisp = jsonObject.getString("hinhanhloaisanpham");

                            arrayListLoaiSP.add(new LoaiSP(id, tenloaisp, hinhloaisp));
                            loaiSPAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckPermission.ToastMessage(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(arrayRequest_lsp);
    }

    private void ActionFlipper() {
        ArrayList<String> arrFliper = new ArrayList<>();
        arrFliper.add("https://manager.remaxvietnam.vn/asset/images/SanPham/phukiendienthoai/tainghe/RB-S26/slide/slide-tai-nghe-bluetooth-the-thao-remax-rb-m26-3-01062019.jpg");
        arrFliper.add("https://cdn.tgdd.vn/Products/Images/54/187374/tai-nghe-bluetooth-kanen-k6-den-2-org.jpg");
        for (int i = 0; i < arrFliper.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get()
                    .load(arrFliper.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(3000);
        viewFlipper.setAutoStart(true);
        Animation animatin_star = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flipper_view);
        Animation animatin_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.flipper_view_out);
        viewFlipper.setInAnimation(animatin_star);
        viewFlipper.setOutAnimation(animatin_out);

    }

    private void Actionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Map() {
        toolbar = findViewById(R.id.toolbar_home);
        viewFlipper = findViewById(R.id.viewfliper_home);
        recyclerView = findViewById(R.id.rcv_sanphamhot);
        navigationView = findViewById(R.id.navigation_home);
        listView = findViewById(R.id.lv_navigation);
        drawerLayout = findViewById(R.id.drawable_home);

        arrayListLoaiSP = new ArrayList<>();
        loaiSPAdapter = new LoaiSPAdapter(arrayListLoaiSP, getApplicationContext());
        listView.setAdapter(loaiSPAdapter);


        arrayListSanPham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), arrayListSanPham);
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(sanPhamAdapter);

        if (arrayList_giohang != null){

        }else {
            arrayList_giohang = new ArrayList<>();
        }
    }
}
