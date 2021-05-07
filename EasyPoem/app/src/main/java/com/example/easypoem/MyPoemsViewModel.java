package com.example.easypoem;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyPoemsViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public MyPoemsViewModel() {
    }

}
