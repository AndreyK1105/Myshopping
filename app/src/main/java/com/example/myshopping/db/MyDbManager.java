package com.example.myshopping.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myshopping.adapter.ListItem;

import java.util.ArrayList;
import java.util.List;

public class MyDbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;

    public MyDbManager(Context context) {
        this.context = context;
        myDbHelper= new MyDbHelper(context);
    }
    public void openDb(){
db= myDbHelper.getWritableDatabase();
    }
    public void delete(int id){
        String selection= MyConstants._ID+"="+id;
        db.delete(MyConstants.TABLE_NAME, selection,null);
    }
    public void insertToDb(String title, String  disc, String uri){
        ContentValues contentValues= new ContentValues();
        contentValues.put(MyConstants.TITLE,title);
        contentValues.put(MyConstants.DISCRIPTION,disc);
        contentValues.put(MyConstants.URI,uri);
        db.insert(MyConstants.TABLE_NAME,null,contentValues);
    }
    public List<ListItem> getFromDb(String search_text){
        List<ListItem> tempList = new ArrayList<>();
        String selection = MyConstants.TITLE + " like ?";
        Cursor cursor= db.query(MyConstants.TABLE_NAME,null,selection,new String[]{"%"+search_text+"%"},null,null,null);
        while (cursor.moveToNext()){
            ListItem listItem= new ListItem();
            String title = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
            String desc = cursor.getString(cursor.getColumnIndex(MyConstants.DISCRIPTION));
            String uri = cursor.getString(cursor.getColumnIndex(MyConstants.URI));
            Integer _id = cursor.getInt(cursor.getColumnIndex(MyConstants._ID));

            listItem.setTitle(title);
            listItem.setDesc(desc);
            listItem.setUri(uri);
            listItem.setId(_id);
            tempList.add(listItem);
        }
        cursor.close();
        return tempList;
    }

    public List<String> getFromDbDiscr() {
        List<String> tempList = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MyConstants.DISCRIPTION));
            tempList.add(title);
        }
        cursor.close();
        return tempList;
    }

    public void closeDb (){
        myDbHelper.close();
    }
}
