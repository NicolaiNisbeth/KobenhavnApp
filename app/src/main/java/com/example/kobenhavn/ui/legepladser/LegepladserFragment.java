package com.example.kobenhavn.ui.legepladser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;

public class LegepladserFragment extends Fragment {

    private LegepladserViewModel legepladserViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.legepladser_fragment, container, false);

        return root;
    }
}
