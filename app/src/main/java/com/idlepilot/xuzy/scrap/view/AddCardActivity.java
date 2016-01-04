package com.idlepilot.xuzy.scrap.view;


import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.idlepilot.xuzy.scrap.controller.CardManager;
import com.idlepilot.xuzy.scrap.R;
import com.idlepilot.xuzy.scrap.model.CardDataItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;


public class AddCardActivity extends FragmentActivity
{
    private Button addButton;
    private Button confirmButton;
    private Button cancelButton;
    private ImageView imageView;
    private TextView date;
    private EditText content;
    private File file;
    private String fileName;
    private Uri uri;
    private Bitmap cutbitmap;
    private Uri urismallFile;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        //点击Photo Button按钮照相
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_add_layout);
        addButton = (Button) findViewById(R.id.card_add_button);
        confirmButton = (Button) findViewById(R.id.card_confirm_btn);
        cancelButton =  (Button) findViewById(R.id.card_cancel_btn);
        imageView = (ImageView) findViewById(R.id.cardadd_imageView);
        date = (TextView) findViewById(R.id.card_date);
        content = (EditText) findViewById(R.id.card_content);
        date.setText(new DateFormat().format("yyyy-MM-dd", Calendar.getInstance(Locale.CHINA)));
        //gallery = (Button) findViewById(R.id.gallery);

        confirmButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
                fileName = "/sdcard/myImage/" + name;
                String fileName2 = "file:///mnt/sdcard/myImage/" + name;
                file = new File(fileName);
                FileOutputStream fOut = null;
                try
                {
                    fOut = new FileOutputStream(file);
                } catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                }
                cutbitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
                try
                {
                    fOut.flush();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    fOut.close();
                } catch (IOException e)
                {
                    e.printStackTrace();

                }

                CardDataItem card = new CardDataItem();
                card.setImagePath(fileName2);
                card.setDate(date.getText().toString());
                card.setContent(content.getText().toString());
                CardManager cm = new CardManager(AddCardActivity.this);
                cm.addCard(card);
                startActivity(new Intent(AddCardActivity.this, MainActivity.class));
                finish();

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                startActivity(new Intent(AddCardActivity.this, MainActivity.class));
                finish();

            }
        });

        final View.OnClickListener mylistener = new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED))
                {
                    try
                    {

                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        String name = new DateFormat().format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";

                        File filepack = new File("/sdcard/myImage/");
                        filepack.mkdirs();// 创建文件夹

                        fileName = "/sdcard/myImage/" + name;
                        file = new File(fileName);
                        uri = Uri.fromFile(file);
                        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 1);
                    } catch (ActivityNotFoundException e)
                    {
                        e.printStackTrace();
                    }
                }
                addButton.setVisibility(View.GONE);
            }
        };
        addButton.setOnClickListener(mylistener);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // TODO Auto-generated method stub
        if (resultCode == RESULT_OK)
        {

            if (requestCode == 1)
            {
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
                sendBroadcast(intent);
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                Uri systemImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = getContentResolver();
                Cursor cursor = contentResolver.query(systemImageUri, null,
                        MediaStore.Images.Media.DISPLAY_NAME + "='"
                                + file.getName() + "'", null, null);
                Uri photoUriInMedia = null;
                if (cursor != null && cursor.getCount() > 0)
                {
                    cursor.moveToLast();
                    long id = cursor.getLong(0);
                    photoUriInMedia = ContentUris.withAppendedId(systemImageUri, id);
                }
                cursor.close();
                /*Intent in = new Intent("com.android.camera.action.CROP");
                //需要裁减的图片格式
                in.setDataAndType(photoUriInMedia, "image");
                //允许裁减
                in.putExtra("crop", "true");
                //剪裁后ImageView显时图片的宽
                in.putExtra("outputX", 390);
                //剪裁后ImageView显时图片的高
                in.putExtra("outputY", 280);
                //设置剪裁框的宽高比例
                in.putExtra("aspectX", 3);
                in.putExtra("aspectY", 2);
               *//* in.putExtra("return-data", true);*//*

                urismallFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, urismallFile);
                intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                startActivityForResult(intent, 2);*/

                Bitmap bitmap = null;
                try
                {
                    // 读取uri所在的图片
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUriInMedia);

                } catch (Exception e)
                {
                    Log.e("[Android]", e.getMessage());

                }
                cutbitmap = bitmap;
                imageView.setImageBitmap(bitmap);
            }
            if (requestCode == 2)
            {

                if (data != null)
                {
                   /* Bitmap bitmap = null;
                    try
                    {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(urismallFile));
                    } catch (Exception e)
                    {
                        Log.d("123", "not  found small photo");
                    }
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    cutbitmap = bitmap;
                    imageView.setImageBitmap(bitmap);*/

                }
                // imageView.setImageURI(uri);// 将图片显示在ImageView里
            }

        }
    }


}
