package com.example.day01_01.ui.activity;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.day01_01.Bean.ShopBean;
import com.example.day01_01.R;
import com.example.day01_01.Util.NetUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoaderInterface;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Banner banner;
    private TextView title;
    private TextView original;
    private TextView discounts;
    private String url = "http://www.zhaoapi.cn/product/getProductDetail?pid=1";
    private int mPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPage = 1;
        //获取资源id
        banner = findViewById(R.id.view_pager);
        title = findViewById(R.id.title);
        original = findViewById(R.id.original);
        discounts = findViewById(R.id.discounts);
        original.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        //设置banner样式
        //banner.setBannerStyle(BannerConfig.)
        //设置图片适配器
        banner.setImageLoader(new ImageLoaderInterface<ImageView>() {

            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(
                        (String) path,imageView
                );
            }

            @Override
            public ImageView createImageView(Context context) {
                ImageView imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                return imageView;
            }
        });
        initData();
    }

    private void initData() {
        NetUtil.getIntance().getRequest(url, ShopBean.class, new NetUtil.CallBack<ShopBean>() {
            @Override
            public void onSuccess(ShopBean o) {
                List<String> list = new ArrayList<>();
                String images = o.getData().getImages();
                String[] split = images.split("\\|");
                for(String s:split){
                    list.add(s);
                }
                banner.setImages(list);
                banner.start();
                title.setText(o.getData().getTitle());
                original.setText("原价："+o.getData().getPrice());
                discounts.setText("优惠价："+o.getData().getBargainPrice());
            }
        });
    }
}
