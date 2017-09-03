package com.webber.mcorelibspace.demo.demos.primary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.webber.mcorelibspace.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReflectActivity extends AppCompatActivity {

    @Bind(R.id.m_reflect_bt)
    Button mReflectBt;
    @Bind(R.id.m_reflect_tv)
    TextView mReflectTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reflect);
        ButterKnife.bind(this);

        mReflectBt.setOnClickListener(v -> {
            reflect1();
        });
    }

    private void reflect1() {
        Class mClass = SonClass.class;
        //1.1 获取父类，子类所有变量,不含权限
        Field[] allFields = mClass.getFields();
        Method[] allMethods = mClass.getMethods();
        //1.2 获取本类中所有变量，不含权限
        Field[] selfFields = mClass.getDeclaredFields();
        Method[] selfMethods = mClass.getDeclaredMethods();
        for (Field field : allFields) {
            Log.d("reflect", "类型：" + field.getType() + "名字：" + field.getName() + "权限:" + field.getModifiers());
        }
        for (Method method : allMethods) {
            Log.d("reflect", "返回值类型：" + method.getReturnType() + "名字：" + method.getName() + "权限:" + method.getModifiers());
        }
        //1.3 访问私有方法
        ParentClass parentClass = new ParentClass();
        Class parentClazz = parentClass.getClass();
        try {
            Method mPrivateMethod = parentClazz.getDeclaredMethod("getPrivateFiled", String.class);
            mPrivateMethod.setAccessible(true);
            String name = (String) mPrivateMethod.invoke(parentClass, "添加的");
            Log.d("reflect", name);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //1.4 修改变量值
        try {
            Field fatherName = parentClazz.getDeclaredField("mFatherName");
            System.out.println("Before Modify：MSG = " + parentClass.getFatherName());
            fatherName.set(parentClass, "a");
            System.out.println("After Modify：MSG = " + parentClass.getFatherName());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //1.5 修改常量值
    }
}
