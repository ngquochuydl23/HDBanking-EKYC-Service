package com.socialv2.ewallet.singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.socialv2.ewallet.dtos.RequestSignUpDto;
import com.socialv2.ewallet.dtos.users.UserDto;

public class RegisterDataSingleton {
    private static final RegisterDataSingleton instance = new RegisterDataSingleton();


    public static RegisterDataSingleton getInstance() {
        return instance;
    }

    private MutableLiveData<RequestSignUpDto> resultLiveData = new MutableLiveData<>();

    public LiveData<RequestSignUpDto> getData() {
        return resultLiveData;
    }

    public void setData(RequestSignUpDto newData) {
        resultLiveData.postValue(newData);
    }

    public void clear() {
        resultLiveData.setValue(null);
    }
}
