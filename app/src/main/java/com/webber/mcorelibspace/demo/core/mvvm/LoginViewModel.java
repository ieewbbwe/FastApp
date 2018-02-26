package com.webber.mcorelibspace.demo.core.mvvm;

import android.databinding.Bindable;

import com.webber.mcorelibspace.demo.core.mvvm.wrapper.WrapperViewModel;

import cn.picher.sdk_mvvm.event.RxEvent;
import cn.picher.sdk_mvvm.event.RxEventBus;
import io.reactivex.functions.Consumer;

/**
 * Created by picher on 2018/1/11.
 * Describe：
 */

public class LoginViewModel extends WrapperViewModel<LoginContract.View>
        implements LoginContract.ViewModel {

    @Bindable
    private String emial;
    @Bindable
    private String passwod;

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
    }

    public String getPasswod() {
        return passwod;
    }

    public void setPasswod(String passwod) {
        this.passwod = passwod;
    }

    @Override
    public void onClickLogin() {
        EmailLoginRequestModelInternal emailLoginRequestModel = new EmailLoginRequestModelInternal.Builder()
                .email(getEmial())
                .password(getPasswod())
                .build();
        if(Validator.validateLogin(emailLoginRequestModel).isValid()){
            //singleBridge();
        }
    }

    @Override
    public void onAttach(LoginContract.View view) {
        super.onAttach(view);
        registerEvent();
    }

    private void registerEvent() {
        RxEventBus.getInstance().register(new Consumer<RxEvent>() {
            @Override
            public void accept(RxEvent rxEvent) throws Exception {
                if(rxEvent.getEventId() == RxEvent.RxEventId.RX_LOGIN_SUCCEED){

                }
            }
        });
    }
}
