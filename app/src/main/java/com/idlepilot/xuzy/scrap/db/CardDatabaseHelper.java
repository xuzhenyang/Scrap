package com.idlepilot.xuzy.scrap.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by xuzywozz on 2015/12/30.
 */
public class CardDatabaseHelper extends SQLiteOpenHelper
{

    private static final String DB_NAME = "scrap.db"; //数据库名称
    private static final int version = 1; //数据库版本

    public CardDatabaseHelper(Context context)
    {
        super(context, DB_NAME, null, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "create table card(id INTEGER PRIMARY KEY, imagePath text, content text, date text)";
        db.execSQL(sql);
        String sql1 = "insert into card(imagepath, content, date) values('assets://wall01.jpg', 'test1', '2015-12-30')";
        String sql2 = "insert into card(imagepath, content, date) values('assets://wall02.jpg', 'test2', '2015-12-31')";
        String sql3 = "insert into card(imagepath, content, date) values('assets://wall03.jpg', 'test3', '2016-01-01')";
        String sql4 = "insert into card(imagepath, content, date) values('assets://wall04.jpg', 'test4', '2016-01-02')";
        db.execSQL(sql1);
        db.execSQL(sql2);
        db.execSQL(sql3);
        db.execSQL(sql4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub
//        if (oldVersion < 2)
//        {
//            db.beginTransaction();
//            try
//            {
//                String sql1 = "insert into card(imagepath, content, date) values('assets://wall01.jpg', 'test1', '2015-12-30')";
//                String sql2 = "insert into card(imagepath, content, date) values('assets://wall02.jpg', 'test2', '2015-12-30')";
//                String sql3 = "insert into card(imagepath, content, date) values('assets://wall03.jpg', 'test3', '2015-12-30')";
//                String sql4 = "insert into card(imagepath, content, date) values('assets://wall04.jpg', 'test4', '2015-12-30')";
//                db.execSQL(sql1);
//                db.execSQL(sql2);
//                db.execSQL(sql3);
//                db.execSQL(sql4);
//                db.setTransactionSuccessful();
//            } finally
//            {
//                db.endTransaction();
//            }
//        }

    }

}
