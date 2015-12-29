package com.idlepilot.xuzy.scrap;

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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // TODO Auto-generated method stub

    }

}
