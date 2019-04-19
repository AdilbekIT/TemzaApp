package com.example.asus.mobiletracker.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asus.mobiletracker.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);


//        list = (ListView) view.findViewById(R.id.recycler_view);
//
//        adapter = new ListOfProductsAdapter.OnNoteListener();
//        list.setAdapter(adapter);

        return view;
    }
}
