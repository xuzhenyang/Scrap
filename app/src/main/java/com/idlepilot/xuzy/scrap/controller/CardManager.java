package com.idlepilot.xuzy.scrap.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.idlepilot.xuzy.scrap.db.CardDatabaseHelper;
import com.idlepilot.xuzy.scrap.model.CardDataItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by xuzywozz on 2015/12/30.
 */
public class CardManager
{
    private CardDatabaseHelper dbHelper = null;
    private SQLiteDatabase dbR = null;
    private SQLiteDatabase dbW = null;
    public Context context = null;

    public CardManager(Context context)
    {
        this.context = context;
        //DatabaseContext重写openOrCreateDatabase 将数据库保存在sd卡
//        DatabaseContext dbContext = new DatabaseContext(context);
        this.dbHelper = new CardDatabaseHelper(context);
        this.dbR = this.dbHelper.getReadableDatabase();
        this.dbW = this.dbHelper.getWritableDatabase();
    }

    public ArrayList<CardDataItem> loadAllCard()
    {
        ArrayList<CardDataItem> cardList = new ArrayList<CardDataItem>();
        Cursor c = dbR.query("card", null, null, null, null, null, null);//查询并获得游标
        while (c.moveToNext())
        {
            CardDataItem card = new CardDataItem();
            card.setImagePath(c.getString(c.getColumnIndex("imagePath")));
            card.setContent(c.getString(c.getColumnIndex("content")));
            card.setDate(c.getString(c.getColumnIndex("date")));
            Log.d("CardManager", "imagePath : " + card.getImagePath());
            Log.d("CardManager", "content : " + card.getContent());
            cardList.add(card);
        }
        //懒得改sql顺序 直接反向排列
        Collections.reverse(cardList);
        return cardList;
    }

    public void addCard(CardDataItem card)
    {
        ContentValues cv = new ContentValues();//实例化一个ContentValues用来装载待插入的数据
        cv.put("imagePath", card.getImagePath());
        cv.put("content", card.getContent());
        cv.put("date", card.getDate());
        Log.d("CardManager", "Add imagePath : " + card.getImagePath());
        Log.d("CardManager", "Add content : " + card.getContent());
        dbW.insert("card", null, cv);//执行插入操作
    }

    public void clearCardTable()
    {
        dbW.execSQL("delete from card");
    }

    public void printAllCardInfo()
    {
        ArrayList<CardDataItem> cardList = loadAllCard();
        for (int i = 0; i < cardList.size(); i++)
        {
            Log.d("CardManager", "card" + i + ":" + " imagePath:" + cardList.get(i).getImagePath() + " content:" + cardList.get(i).getContent() + " date:" + cardList.get(i).getDate());
        }
    }

    public boolean isThisDayHasCard(String date)
    {
        Cursor cursor = dbR.rawQuery("select * from card where date = ?", new String[]{date});
        if (cursor.moveToNext())
        {
            return true;
        }
        return false;
    }
}
