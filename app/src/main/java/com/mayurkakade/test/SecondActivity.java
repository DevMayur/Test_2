package com.mayurkakade.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonObject;
import com.mayurkakade.test.retrofit.Methods;
import com.mayurkakade.test.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondActivity extends FragmentActivity implements OnMapReadyCallback {

    List<UserModel> usersList;
    RecyclerView recyclerView;
    UsersAdapter adapter;

    GoogleMap map;
    int position = 0;
    Double latitude = 0.00;
    Double longitude = 0.00;
    String markerTitle = "Marker";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
        .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        usersList = new ArrayList<>();
        adapter = new UsersAdapter(this,usersList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(adapter);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findFirstVisibleItemPosition() != position) {
                    setAdapterPosition(((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition());
                }
            }
        });

        getData();
    }

    private void getData() {
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<List<UserModel>> call = methods.getUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserModel>> call,@NonNull Response<List<UserModel>> response) {
                if (response.body() != null) {
                    SecondActivity.this.usersList.addAll(response.body());
                    SecondActivity.this.adapter.notifyDataSetChanged();
                    setAdapterPosition(0);
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<UserModel>> call,@NonNull Throwable t) {
            }
        });
    }

    public void setAdapterPosition(int position) {
        this.position = position;
        setLatLang(Double.parseDouble(usersList.get(position).getAddress().getGeo().getLat().replace("\"","")), Double.parseDouble(usersList.get(position).getAddress().getGeo().getLng().replace("\"","")));
        map.clear();
        markerTitle = usersList.get(position).getAddress().getCity();
        onMapReady(map);
    }


    public void setLatLang(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        LatLng destination = new LatLng(latitude,longitude);
        map.addMarker(new MarkerOptions().position(destination).title(markerTitle));
        map.animateCamera(CameraUpdateFactory.newLatLng(destination));
        Log.d("mapReadyLogs", "latitude : "+ latitude+ "\tlongitude : " + longitude + "\t position : " + position);

    }
}