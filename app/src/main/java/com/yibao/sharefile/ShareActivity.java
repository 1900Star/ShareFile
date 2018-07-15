package com.yibao.sharefile;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

public class ShareActivity extends AppCompatActivity {

    private ImageView mIv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }

    private void initData() {
        Intent intent = getIntent();
        String action = intent.getAction();//action
        String type = intent.getType();//类型

        //类型
        if (Intent.ACTION_SEND.equals(action) && type != null /*&& "video/mp4".equals(type)*/) {
            Uri uri = intent.getParcelableExtra(Intent.EXTRA_STREAM);
            //如果是媒体类型需要从数据库获取路径
            String filePath = getRealPathFromURI(uri);
            mIv.setImageBitmap(BitmapFactory.decodeFile(filePath));
//        Glide.with(this).load(uri).into(mIv);
//        mIv.setImageURI(uri);
            LogUtil.d("文件路径:" + filePath);
        }

    }

    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor != null) {

            if (cursor.moveToNext()) {
                return cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            }
            cursor.close();
        }

        return null;
    }


    private void initView() {
        mIv = findViewById(R.id.iv);
    }
}
