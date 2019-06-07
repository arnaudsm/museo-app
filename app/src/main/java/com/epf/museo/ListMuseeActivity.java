package com.epf.museo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.models.Musee;

import org.w3c.dom.Text;

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
        adapter = new RecyclerViewAdapter(listData);
        recyclerView.setAdapter(adapter);

        // create views
        LinearLayout listMuseeLayout = findViewById(R.id.list_musee_layout);
        //TextView txtViewNomMusee = findViewById(R.id.txtDescription);
        //ImageView imgViewMusee = findViewById(R.id.imageView);

        // set the view OnClickListener
        listMuseeLayout.setOnClickListener(new ClikListMuseeActivity());
        //txtViewNomMusee.setOnClickListener(new ClikListMuseeActivity());
        //imgViewMusee.setOnClickListener(new ClikListMuseeActivity());


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

    public class ClikListMuseeActivity implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            v.findViewById(R.id.list_musee_layout);
            Toast.makeText(getApplicationContext(), "Ouverture des infos du Musee", Toast.LENGTH_SHORT).show();
            launchActivity(MuseeActivity.class);
        }
    }

    private void launchActivity(Class<MuseeActivity> museeActivity) {
        Intent intent = new Intent(this, museeActivity);
        startActivity(intent);
    }
}
