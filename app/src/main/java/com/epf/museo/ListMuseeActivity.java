package com.epf.museo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListMuseeActivity extends AppCompatActivity {

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
        adapter =  new RecyclerViewAdapter(listData);
        recyclerView.setAdapter(adapter);


    }

    private void initData() {

        listData.add(new Data(R.drawable.ic_museum, "musee"));
        listData.add(new Data(R.drawable.barcares_2017, "Barcares 2017"));
/*        listData.add(new Data(R.drawable.before_gala_epf_2017, "Before Gala EPF 2017"));
        listData.add(new Data(R.drawable.chalet_quebec_aout_2018,"Chalet Quebec Aout 2018"));*/
        // copy again

        listData.add(new Data(R.drawable.ic_museum, "musee"));

        listData.add(new Data(R.drawable.barcares_2017, "Barcares 2017"));
/*        listData.add(new Data(R.drawable.before_gala_epf_2017, "Before Gala EPF 2017"));
        listData.add(new Data(R.drawable.chalet_quebec_aout_2018,"Chalet Quebec Aout 2018"));*/
    }
}
