package com.example.kobenhavn.ui.indstillinger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.kobenhavn.R;

public class IndstillingerFragment extends Fragment {

    private IndstillingerViewModel indstillingerViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.indstillinger_fragment, container, false);

        return root;
    }
}
