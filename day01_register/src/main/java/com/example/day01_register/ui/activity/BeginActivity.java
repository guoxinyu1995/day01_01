package com.example.day01_register.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.day01_register.Bean.RegisterBean;
import com.example.day01_register.R;
import com.example.day01_register.Util.NetUtil;

public class BeginActivity extends AppCompatActivity {
    private String beginUrl = "http://120.27.23.105/user/reg?mobile=%s&password=%s";
    private EditText name;
    private EditText password;
    private Button btn_register;
    private ImageButton imageButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);
        initView();
    }
    private void initData(String names, String passwords) {
        NetUtil.getIntance().getRequest(String.format(beginUrl,names,passwords), RegisterBean.class, new NetUtil.CallBack<RegisterBean>() {
            @Override
            public void onSuccess(RegisterBean o) {
                if(o.getMsg().equals("天呢！用户已注册")){
                    Toast.makeText(BeginActivity.this,"天呢！用户已注册",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(BeginActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(BeginActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void initView() {
        imageButton = findViewById(R.id.btn_return);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        //注册
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的值
                String names = name.getText().toString();
                String passwords = password.getText().toString();
                //String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
                // String passRegex = "c =  /^[a-zA-Z0-9]{6,8}$/;";
                if(names.equals("") || passwords.equals("")){
                    Toast.makeText(BeginActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    initData(names,passwords);
                }
            }
        });
    }
}
