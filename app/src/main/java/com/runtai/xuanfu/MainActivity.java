package com.runtai.xuanfu;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button bt_xf, bt_st;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_xf = (Button) findViewById(R.id.bt_xf);
        bt_xf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showButton();
            }
        });

        bt_st = (Button) findViewById(R.id.bt_st);
        bt_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetting();
            }
        });
    }

    /**
     * 设置
     */
    private void showSetting(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                Log.e("setting", "setting");
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));//加上这句话后的效果是直接跳到该应用界面让用户授权
                startActivity(intent);
            }
        } else {
            Log.e("setting", "setting");
        }
    }

    /**
     * 应用权限监测
     */
    public void showButton(){
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {
                showFloatView();
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));//加上这句话后的效果是直接跳到该应用界面让用户授权
                startActivity(intent);
            }
        } else {
            showFloatView();
        }
    }

    /**
     * 构造悬浮按钮
     */
    public void showFloatView() {
        WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        FloatView floatView = new FloatView(getApplicationContext());
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;//控件对齐
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.width = 150;//控件宽度
        params.height = 150;//控件高度
        params.x = 10;//X轴偏移
        params.y = 10;//Y轴偏移
        params.alpha = 50;//透明度
        windowManager.addView(floatView, params);

        floatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                Log.e("floatView", "floatView");
                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
