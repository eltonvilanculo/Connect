package com.example.connect;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {


    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        Button btnLout = view.findViewById(R.id.btn_logout_fragment);
        btnLout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performLout();
            }
        });
        return view;
    }

    private void performLout() {

    }

}
