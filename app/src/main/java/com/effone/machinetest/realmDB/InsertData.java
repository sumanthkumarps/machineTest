package com.effone.machinetest.realmDB;

import android.content.Context;

import com.effone.machinetest.model.User;
import com.effone.machinetest.model.UserDataBase;

import io.realm.Realm;

/**
 * Created by sumanth.peddinti on 1/18/2018.
 */

public class InsertData {
    public Realm realm;
    public InsertData(Context context) {
        realm=Realm.getDefaultInstance();
    }

    public User gettingDataFromForm(String userName, String password) {
        User uesr=new User();
        uesr.setUserName(userName);
        uesr.setFirstName("");
        uesr.setLastName("");
        uesr.setDateOfBirth("");
        uesr.setPassword(password);
        return uesr;
    }

    public  void insertDataIntoDataBase(User mUser) {
        realm.beginTransaction();
        UserDataBase userDataBase= realm.createObject(UserDataBase.class);
        userDataBase.setFirstName(mUser.getFirstName());
        userDataBase.setLastName(mUser.getLastName());
        userDataBase.setPhoneNumber(mUser.getPhoneNumber());
        userDataBase.setDateOfBirth(mUser.getDateOfBirth());
        userDataBase.setUserName(mUser.getUserName());
        userDataBase.setPassword(mUser.getPassword());
        realm.insert(userDataBase);
        realm.commitTransaction();
    }


}
