package com.epf.museo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners, MuseeCalls.Callbacks {

    // DESIGN

    @BindView(R.id.fragment_main_textview) TextView textView;

    public MainFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,view);
        return view;
    }


    // ACTIONS

    @OnClick(R.id.fragment_main_button)
    public void submit(View view){
        this.executeHttpRequestWithRetrofit();
    }
    // HTTP REQUEST

    private void executeHttpRequestWithRetrofit(){
        new NetworkAsyncTask(this).execute("http://vps449928.ovh.net");
    }

    @Override
    public void onResponse(@Nullable List<Musee> id) {

    }

    @Override
    public void onFailure() {

    }

    @Override
    public void onPreExecute() {
        this.updateUIWhenStartingHTTPRequest();
    }

    @Override
    public void doInBackground() {

    }

    @Override
    public void onPostExecute(String json) {
        this.updateUIWhenStopingHTTPRequest(json);
    }

    // UPDATE UI

    private void updateUIWhenStartingHTTPRequest(){
        this.textView.setText("Downloading...");
    }

    private void updateUIWhenStopingHTTPRequest(String response){
        this.textView.setText(response);
    }

    private void updateUIWithListOfUsers(List<Musee> musees){
        StringBuilder stringBuilder = new StringBuilder();
        for(Musee musee : musees){
            stringBuilder.append("-"+musee.getId()+"\n");
        }
        updateUIWhenStopingHTTPRequest(stringBuilder.toString());
    }
}
