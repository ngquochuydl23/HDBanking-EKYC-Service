package com.socialv2.ewallet.singleton;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.socialv2.ewallet.dtos.users.UserDto;

public class UserSingleton {
    private static final UserSingleton instance = new UserSingleton();


    public static UserSingleton getInstance() {
        return instance;
    }

    private MutableLiveData<UserDto> resultLiveData = new MutableLiveData<>();

    public LiveData<UserDto> getData() {
        return resultLiveData;
    }

    public void setData(UserDto newData) {
        resultLiveData.postValue(newData);
    }

    public void clear() {
        resultLiveData.setValue(null);
    }
}
