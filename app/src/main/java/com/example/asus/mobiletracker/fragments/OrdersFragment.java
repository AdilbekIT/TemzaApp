package com.example.asus.mobiletracker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.mobiletracker.R;

public class OrdersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);


//        list = (ListView) view.findViewById(R.id.recycler_view);
//
//        adapter = new ListOfProductsAdapter.OnNoteListener();
//        list.setAdapter(adapter);

        return view;
    }
}


