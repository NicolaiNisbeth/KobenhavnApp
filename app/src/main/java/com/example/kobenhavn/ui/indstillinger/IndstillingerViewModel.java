package com.example.kobenhavn.ui.indstillinger;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class IndstillingerViewModel extends ViewModel {

    private MutableLiveData<String> text;

    public IndstillingerViewModel() {
        text = new MutableLiveData<>();
        text.setValue("This is indstillinger fragment");
    }

    public LiveData<String> getText() {
        return text;
    }
}