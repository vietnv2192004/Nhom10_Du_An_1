package com.example.nhom10_duan1.Frag;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.nhom10_duan1.HoadonActivity;
import com.example.nhom10_duan1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class Frag_hoadon extends Fragment {
    FloatingActionButton flb_hoadon;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_fraghoadon,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flb_hoadon = (FloatingActionButton) view.findViewById(R.id.fbl_adhoadon);

        flb_hoadon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HoadonActivity.class);
                startActivity(intent);
            }

        });
    }
}
