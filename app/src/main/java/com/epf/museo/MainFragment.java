package com.epf.museo;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.List;

public class MainFragment extends Fragment implements NetworkAsyncTask.Listeners, MuseeCalls.Callbacks {
    @Override
    public void onResponse(@Nullable List<Musee> id) {

    }

    @Override
    public void onFailure() {

    }
}
