package com.effone.machinetest;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.effone.machinetest.common.AppPreferene;
import com.effone.machinetest.common.Validation;
import com.effone.machinetest.model.UserDataBase;
import com.effone.machinetest.view.Activity.NavigationActivity;
import com.effone.machinetest.view.Activity.RegistrationActivity;
import com.effone.machinetest.common.StaticConfig;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private FloatingActionButton fab;
    private EditText mETInputName, mEtInputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;
    private Button mBtGo;
    private Realm mRealm;
    private AppPreferene mAppPreference;
    private  StaticConfig staticConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Realm.init(this);
        mAppPreference=new AppPreferene(this);
        staticConfig=new StaticConfig();
        if(AppPreferene.with(this).getEmail() != ""){
            staticConfig.openActivity(this,NavigationActivity.class);
        }
        mRealm=Realm.getDefaultInstance();
        declaration();


    //    mListView.setOnItemClickListener(this);
    }

    private void declaration() {

        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        mETInputName = (EditText) findViewById(R.id.et_username);
        mEtInputPassword = (EditText) findViewById(R.id.et_password);
        mBtGo=(Button)findViewById(R.id.bt_go);
        mBtGo.setOnClickListener(this);
        fab=(FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:
             clickRegisterationLayout(view);break;
            case R.id.bt_go:
                String userName=mETInputName.getText().toString().trim();
                String password=mEtInputPassword.getText().toString().trim();
                Validation validation = new Validation();
                if (validation.isValidEmail(userName) && validation.isValidPassword(password))
                validationOfData(userName,password);
                else
                    staticConfig.toastMessages(this,getString(R.string.inValidUserNamePassword));
                break;
        }

    }

    private void validationOfData(String userName, String password) {
        UserDataBase userDetails=mRealm.where(UserDataBase.class).equalTo("userName",userName).equalTo("password",password).findFirst();
        if(userDetails != null) {
            if(userDetails.getUserName()!= null) {
                AppPreferene.with(this).setEmail(userDetails.getUserName());
                staticConfig.openActivity(this, NavigationActivity.class);
            }
        }else {
            staticConfig.toastMessages(this,getString(R.string.doesnotexits));

        }
    }

    @SuppressLint("RestrictedApi")
    private void clickRegisterationLayout(View view) {
        getWindow().setExitTransition(null);
        getWindow().setEnterTransition(null);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options =
                    ActivityOptions.makeSceneTransitionAnimation(this, fab, fab.getTransitionName());
            startActivityForResult(new Intent(this, RegistrationActivity.class), StaticConfig.REQUEST_CODE_REGISTER, options.toBundle());
        } else {
            startActivityForResult(new Intent(this, RegistrationActivity.class), StaticConfig.REQUEST_CODE_REGISTER);
        }
    }


}
