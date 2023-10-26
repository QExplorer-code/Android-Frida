package com.example.myapp1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE = 101;
    private TextView contactsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 设置页面控件：文本框和按钮
        contactsTextView = findViewById(R.id.contactsTextView);
        Button queryButton = findViewById(R.id.queryButton);

        // 点击按钮，查询联系人信息
        queryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检查是否有读取联系人权限
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    // 如果没有权限，请求权限
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_CONTACTS}, REQUEST_CODE);
                } else {
                    // 如果有权限，执行查询联系人的操作
                    queryContacts();
                }
            }
        }
        );

    }

    // 查询联系人信息
    private void queryContacts() {
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            StringBuilder stringBuilder = new StringBuilder();
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                stringBuilder.append("Name: ").append(name).append(", Number: ").append(number).append("\n");
            }
            cursor.close();
            contactsTextView.setText(stringBuilder.toString());
        }
    }

    // 处理权限请求的结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户授予了读取联系人的权限，执行查询联系人的操作
                queryContacts();
            } else {
                // 用户拒绝了读取联系人的权限，可以显示一个提示，或者做其他处理
                contactsTextView.setText("Permission denied. Cannot query contacts.");
            }
        }
    }
}