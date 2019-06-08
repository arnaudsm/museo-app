package com.epf.museo;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.models.Musee;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListMuseeActivity extends AppCompatActivity implements RecyclerViewAdapter.OnMuseeListener {

    private static final String TAG = "MESSAGE: ";
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Data> listData = new ArrayList<Data>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_musee);
        
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter =  new RecyclerViewAdapter(listData, this);
        recyclerView.setAdapter(adapter);

/*        // create views
        ImageView imgViewMusee = findViewById(R.id.imageView);

        // set the view OnClickListener
        imgViewMusee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListMuseeActivity.this, MuseeActivity.class);
                startActivity(intent);
            }
        });*/
    }

    private void initData() {

        listData.add(new Data(R.drawable.ic_museum, "musee :"));
        listData.add(new Data(R.drawable.barcares_2017, "Barcares 2017"));
        listData.add(new Data(R.drawable.before_gala_epf_2017, "Before Gala EPF 2017"));
        //listData.add(new Data(R.drawable.chalet_quebec_aout_2018,"Chalet Quebec Aout 2018"));

        // copy again

        listData.add(new Data(R.drawable.ic_museum, "musee"));
        listData.add(new Data(R.drawable.barcares_2017, "Barcares 2017"));
        listData.add(new Data(R.drawable.before_gala_epf_2017, "Before Gala EPF 2017"));
        //listData.add(new Data(R.drawable.chalet_quebec_aout_2018,"Chalet Quebec Aout 2018"));
    }

    @Override
    public void onMuseeClick(int position) {
        //Toast.makeText(this, "click détecté", Toast.LENGTH_LONG).show();

        listData.get(position);
        Intent intent = new Intent(this, MuseeActivity.class);
        intent.putExtra("result", "00004a5b2c7a1370f9ec7d6fbe4ac259e92df614");
        startActivity(intent);
    }
}
