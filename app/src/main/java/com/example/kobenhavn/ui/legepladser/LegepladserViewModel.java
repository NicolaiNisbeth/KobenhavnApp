package com.example.kobenhavn.ui.legepladser;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LegepladserViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public LegepladserViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is legepladser fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}