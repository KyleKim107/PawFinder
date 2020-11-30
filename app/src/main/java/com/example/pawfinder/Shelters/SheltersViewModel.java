package com.example.pawfinder.Shelters;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SheltersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SheltersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the shelters fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
