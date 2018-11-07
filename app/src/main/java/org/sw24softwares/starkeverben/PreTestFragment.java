package org.sw24softwares.starkeverben;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

public class PreTestFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pre_test, container, false);

        PulsatorLayout pulsator = view.findViewById(R.id.pulsator);
        pulsator.start();

        View button = view.findViewById(R.id.begin_test);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TestActivity.class);
            intent.putExtra("total", 0);
            intent.putExtra("marks", new int[0]);
            intent.putExtra("dialog", false);
            startActivity(intent);
        });

        return view;
    }
}
