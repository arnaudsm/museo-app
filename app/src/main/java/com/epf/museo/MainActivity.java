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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.Manifest;
import android.widget.Toast;

import com.epf.museo.database.MuseumDatabase;
import com.epf.museo.interfaces.ImageDownloader;
import com.epf.museo.models.Musee;
import com.epf.museo.models.MuseeImage;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{

    private Class<?> mClss;
    private static com.epf.museo.database.database database;
    private List<Musee> musees;

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
        FloatingActionButton fabscan = findViewById(R.id.scan_button);
        Button listmuseebutton = findViewById(R.id.afficher_list_musee_button);

        // now set the View with OnClickListener

        fabscan.setOnClickListener(new ClikMainActivity());
        listmuseebutton.setOnClickListener(new ClikMainActivity());



        // Action Bar
        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setIcon(R.drawable.ic_museum_alone);
        menu.setTitle("  " + getResources().getString(R.string.app_name));

        // BDD
        MuseumDatabase databaseBuilder = Room.databaseBuilder(this, MuseumDatabase .class, "mydb")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        database = databaseBuilder.getDatabase();
        update_list();
    }

    protected void update_list(){
        musees = database.getItems();
        TextView text_empty = (TextView) findViewById(R.id.text_empty);
        if(musees.size() == 0) {
            text_empty.setVisibility(View.VISIBLE);
        } else {
            text_empty.setVisibility(View.VISIBLE);
            text_empty.setText(musees.size()+" Museums already scanned");
        }
    }

    public class ClikMainActivity implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.afficher_list_musee_button:
                    Toast.makeText(getApplicationContext(), "Ouverture de la liste des musées", Toast.LENGTH_LONG).show();
                    launchActivity(ListMuseeActivity.class);
                    break;

                case R.id.scan_button:
                    Toast.makeText(getApplicationContext(), "Ouverture du scanner", Toast.LENGTH_LONG).show();
                    launchActivity(ScannerActivity.class);
                    break;

                default:
            }
        }
    }
}
