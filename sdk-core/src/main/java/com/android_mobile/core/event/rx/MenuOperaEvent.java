package com.android_mobile.core.event.rx;

import com.nextmedia.adapter.category.NewCategory;

/**
 * Created by picher on 2018/3/8.
 * Describe：
 */

public class MenuOperaEvent extends BaseRxBusEvent{

    //調到指定的menu
    public static final int MENU_OPERA_TYPE_JUMP = 1;

    private NewCategory newCategory;
    private int operaType;

    public MenuOperaEvent() {
    }

    public MenuOperaEvent(NewCategory newCategory, int operaType){
        this.newCategory = newCategory;
        this.operaType = operaType;
    }

    public void setNewCategory(NewCategory newCategory) {
        this.newCategory = newCategory;
    }

    public int getOperaType() {
        return operaType;
    }

    public void setOperaType(int operaType) {
        this.operaType = operaType;
    }

    public NewCategory getNewCategory(){
        return newCategory;
    }

}
