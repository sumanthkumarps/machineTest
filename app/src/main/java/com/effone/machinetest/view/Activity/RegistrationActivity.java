package com.effone.machinetest.view.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.effone.machinetest.MainActivity;
import com.effone.machinetest.R;
import com.effone.machinetest.common.StaticConfig;
import com.effone.machinetest.common.Validation;
import com.effone.machinetest.realmDB.InsertData;

import io.realm.Realm;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private  CardView cvAdd;
    private  FloatingActionButton fab;
    private EditText mETInputName, mEtInputPassword,mEtInputConfPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword,inputLayoutPassword1;
    private Button mBtGo;
    private StaticConfig mStaticConfig;
    private Realm mRealm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mRealm=Realm.getDefaultInstance();
        mStaticConfig=new StaticConfig();
        declarations();
        ShowEnterAnimation();
    }

    private void declarations() {
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutPassword1 = (TextInputLayout) findViewById(R.id.tv_cong_password);
        mEtInputConfPassword = (EditText) findViewById(R.id.et_repeatpassword);
        mEtInputPassword = (EditText) findViewById(R.id.et_password);
        mBtGo=(Button)findViewById(R.id.bt_go);
        mBtGo.setOnClickListener(this);
        cvAdd = (CardView) findViewById(R.id.cv_add);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(100);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fab:
                animateRevealClose();
                break;
            case R.id.bt_go:
                String userName=mETInputName.getText().toString().trim();
                String password=mEtInputPassword.getText().toString().trim();
                String configPassword=mEtInputConfPassword.getText().toString().trim();
                    validationCheck(userName,password,configPassword);

                break;
        }
    }

    private void validationCheck(String userName, String password, String configPassword) {
        Validation validation = new Validation();

        if (!validation.isValidEmail(userName)) {
            inputLayoutName.setError(getString(R.string.emailerror));
        }else if(!validation.isValidPassword(password)){
            inputLayoutPassword.setError(getString(R.string.passworderror));
        }else
        if (!validation.isValidPassword(configPassword)) {
            inputLayoutPassword1.setError(getString(R.string.emailerror));
        } else if(!password.equals(configPassword)){
            inputLayoutPassword1.setError(getString(R.string.didnotmatch));
        }else{
            insertingDataIntoDataBase(userName,password);
        }
    }

    private void insertingDataIntoDataBase(String userName, String password) {
        InsertData insertData=new InsertData(this);
        insertData.insertDataIntoDataBase(insertData.gettingDataFromForm(userName,password));
        mStaticConfig.openActivity(this, MainActivity.class);
    }


    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.ic_signup);
                RegistrationActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }
}
