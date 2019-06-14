package com.epf.museo;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.Manifest;
import android.widget.Toast;

import com.epf.museo.adapter.RecyclerViewAdapter;
import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.models.Musee;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnMuseeListener {

    private Class<?> mClss;
    private static com.epf.museo.database.database database;
    private List<Musee> musees;

    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Musee> listData = new ArrayList<Musee>();
    private MuseumDatabase databaseBuilder;

    public void launchActivity(Class<?> clss) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            mClss = clss;
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            Intent intent = new Intent(this, clss);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(mClss != null) {
                        Intent intent = new Intent(this, mClss);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create views
        FloatingActionButton fab = findViewById(R.id.scan_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(ScannerActivity.class);
            }
        });


        // Action Bar
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.ic_museum_alone);
        menu.setTitle("  " + getResources().getString(R.string.app_name));

        // BDD
        databaseBuilder = Room.databaseBuilder(this, MuseumDatabase .class, "mydb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        database = databaseBuilder.getDatabase();

        musees = database.getItems();
        loadRecyclerView();
    }


    private void loadRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new RecyclerViewAdapter(listData,    this, database);
        recyclerView.setAdapter(adapter);

        for (Musee musee: musees) {
            listData.add(musee);
        }
    }

    @Override
    public void onMuseeClick(int position) {
        Musee musee = listData.get(position);
        Intent intent = new Intent(this, MuseeActivity.class);
        intent.putExtra("result", musee.getId());
        startActivity(intent);
    }

}
