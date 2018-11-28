package com.example.day01_register.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.day01_register.Bean.RegisterBean;
import com.example.day01_register.R;
import com.example.day01_register.Util.NetUtil;

public class MainActivity extends AppCompatActivity {
    private String registerUrl = "http://120.27.23.105/user/login?mobile=%s&password=%s";
    private EditText name;
    private EditText password;
    private Button btn_register;
    private Button btn_begin;
    //http://120.27.23.105/user/reg?mobile=18525398551&password=123456
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initData(String names,String passwords) {
        NetUtil.getIntance().getRequest(String.format(registerUrl,names,passwords), RegisterBean.class, new NetUtil.CallBack<RegisterBean>() {
            @Override
            public void onSuccess(RegisterBean o) {
                if(o.getMsg().equals("登录成功")){
                    Intent intent = new Intent(MainActivity.this,ProductActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //初始化view
    private void initView() {
        //获取资源id
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);
        btn_begin = findViewById(R.id.btn_begin);
        //登录按钮
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框的值
                String names = name.getText().toString();
                String passwords = password.getText().toString();
                //String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";
               // String passRegex = "c =  /^[a-zA-Z0-9]{6,8}$/;";
                if(names.equals("") || passwords.equals("")){
                    Toast.makeText(MainActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    initData(names,passwords);
                    Toast.makeText(MainActivity.this,"成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //注册
        btn_begin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BeginActivity.class);
                startActivity(intent);
            }
        });
    }
}
