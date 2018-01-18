package com.effone.machinetest.view.Fragment;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.effone.machinetest.R;
import com.effone.machinetest.common.AppPreferene;
import com.effone.machinetest.common.Validation;
import com.effone.machinetest.model.User;
import com.effone.machinetest.model.UserDataBase;
import com.effone.machinetest.realmDB.InsertData;

import io.realm.Realm;


public class EditProfileFragment extends Fragment implements View.OnClickListener {
    private TextInputLayout mTvUserName,mTvPhone,mTvPassword,mTvFirstName,mTvLastName,mTvdob;
    private TextInputEditText mEtUserName,mEtPhone,mEtPassword,mEtFirstName,mEtLastName,mEtdob;
    private Realm mRealm;
    private   UserDataBase userDetails;
    private Button mBtnSave;
    public static EditProfileFragment newInstance() {
        EditProfileFragment fragment = new EditProfileFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_edit_profile, container, false);

        mRealm=Realm.getDefaultInstance();
        userDetails  =mRealm.where(UserDataBase.class).equalTo("userName", AppPreferene.with(getActivity()).getEmail()).findFirst();
        init(view);
        return view;
    }

    private void init(View view) {
        mTvUserName=(TextInputLayout)view.findViewById(R.id.tv_error_userName);
        mTvFirstName=(TextInputLayout)view.findViewById(R.id.tv_error_first_name);
        mTvLastName=(TextInputLayout)view.findViewById(R.id.tv_error_last_name);
        mTvdob=(TextInputLayout)view.findViewById(R.id.tv_error_date_of_birth);
        mTvPhone=(TextInputLayout)view.findViewById(R.id.tv_error_phone_number);
        mTvPassword=(TextInputLayout)view.findViewById(R.id.tv_error_password);

        mEtdob=(TextInputEditText)view.findViewById(R.id.et_dob);
        mEtUserName=(TextInputEditText)view.findViewById(R.id.et_email);
        mEtPassword=(TextInputEditText)view.findViewById(R.id.et_password);
        mEtFirstName=(TextInputEditText)view.findViewById(R.id.et_firstName);
        mEtLastName=(TextInputEditText)view.findViewById(R.id.et_lastName);
        mEtPhone=(TextInputEditText)view.findViewById(R.id.et_phone_number);

        mBtnSave=(Button)view.findViewById(R.id.btn_save);
        mBtnSave.setOnClickListener(this);
        try {
            mEtUserName.setText(" "+userDetails.getUserName());
            mEtPassword.setText(userDetails.getPassword());
            mEtUserName.setEnabled(false);
            mEtPassword.setEnabled(false);
            if(!userDetails.getDateOfBirth().equals("")) {
                mEtdob.setText(userDetails.getDateOfBirth());
                mEtFirstName.setText(userDetails.getFirstName());
                mEtLastName.setText(userDetails.getLastName());
                mEtPhone.setText(userDetails.getPhoneNumber());
            }else {
                mEtFirstName.requestFocus();
            }

        }catch (Exception e){
            Log.e("EditProfile","this is due to null values inserted into the db"+e.getMessage());
        }




    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:
                User user=new User();
                user.setDateOfBirth(mEtdob.getText().toString().trim());
                user.setFirstName(mEtFirstName.getText().toString().trim());
                user.setLastName(mEtLastName.getText().toString().trim());
                user.setPhoneNumber(mEtPhone.getText().toString().trim());
                validationMethods(user);
                break;
        }

    }

    private void validationMethods(User user) {
        int count=0;
        Validation validate = new Validation();
        if (!validate.isValidFirstName(user.getFirstName())) {
            mTvFirstName.setError(getResources().getString(R.string.firstnamemsg));
            count++;
        }else{
            mTvFirstName.setErrorEnabled(false);
        }
        if (!validate.isValidFirstName(user.getLastName())) {
            mTvLastName.setError(getString(R.string.lastnamemsg));
            count++;
        }else{
            mTvLastName.setErrorEnabled(false);
        }
        if (!validate.isValidDate(user.getDateOfBirth())) {
            mTvdob.setError(getString(R.string.DateofBirthmsg));
            count++;
        }else{
            mTvdob.setErrorEnabled(false);
        }
        if (!validate.isValidPhone(user.getPhoneNumber())) {
            mTvPhone.setError(getString(R.string.Phonemsg));
            count++;
        }else{
            mTvPhone.setErrorEnabled(false);
        }


        if (count == 0) {
            mRealm.beginTransaction();
            userDetails.setDateOfBirth(user.getDateOfBirth());
            userDetails.setFirstName(user.getFirstName());
            userDetails.setLastName(user.getLastName());
            userDetails.setPhoneNumber(user.getPhoneNumber());
            mRealm.commitTransaction();
        }
    }
}
