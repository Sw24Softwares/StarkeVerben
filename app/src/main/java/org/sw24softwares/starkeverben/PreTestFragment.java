package org.sw24softwares.starkeverben;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.content.Intent;

import pl.bclogic.pulsator4droid.library.PulsatorLayout;

import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;

public class PreTestFragment extends Fragment {
    @Override
    public View onCreateView(
        LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pre_test, container, false);

        PulsatorLayout pulsator = (PulsatorLayout) view.findViewById(R.id.pulsator);
        pulsator.start();

        View button = (View) view.findViewById(R.id.begin_test);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), TestActivity.class);
                intent.putExtra("total", 0);
                intent.putExtra("marks", new int[0]);
                intent.putExtra("dialog", false);
                startActivity(intent);
            }
        });

        return view;
    }
}
