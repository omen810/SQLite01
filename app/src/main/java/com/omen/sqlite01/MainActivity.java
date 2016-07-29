package com.omen.sqlite01;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.StringDef;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyDatabaseHelper dbHelper;
    private Button mButtonCreate,mButtonAdd,mButtonUpdate,mButtonDelete,mButtonQuery;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new MyDatabaseHelper(this, "BookStore.db", null, 2);

        mButtonCreate= (Button) findViewById(R.id.activity_main_btn_create);
        mButtonAdd = (Button) findViewById(R.id.activity_main_btn_add);
        mButtonUpdate = (Button) findViewById(R.id.activity_main_btn_update);
        mButtonDelete= (Button) findViewById(R.id.activity_main_btn_delete);
        mButtonQuery = (Button) findViewById(R.id.activity_main_btn_query);



        mButtonCreate.setOnClickListener(this);
        mButtonAdd.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mButtonDelete.setOnClickListener(this);
        mButtonQuery.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        switch (view.getId()){
            case R.id.activity_main_btn_create:
                createDatabase();
                break;
            case R.id.activity_main_btn_add:
                addData();
                break;
            case R.id.activity_main_btn_update:
//                SQLiteDatabase db=dbHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",99);
                db.update("Book",values,"name=?",new String[]{"第一行代码"});
                break;
            case R.id.activity_main_btn_delete:
//                SQLiteDatabase db=dbHelper.getWritableDatabase();
                /*db.delete("Book", "pages>?", new String[]{"400"});*/
                db.delete("Book", "pages>? and price>?", new String[]{"400","40"});
                break;
            case R.id.activity_main_btn_query:
                Cursor cursor = db.query("Book", null, null, null, null, null, null);
                if(cursor==null){
                    return;
                }
                while (cursor.moveToNext()){
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    String author = cursor.getString(cursor.getColumnIndex("author"));
                    int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                    double prices = cursor.getDouble(cursor.getColumnIndex("price"));
                    Log.d("omen", "书名：《 "+name+"》，作者："+author+"，页数："+pages+"，价格"+prices);
                }
                cursor.close();
                break;
            default:
                break;
        }
    }

    private void addData() {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("name","第一行代码");
        values.put("author","郭霖");
        values.put("pages",500);
        values.put("price",88);
        db.insert("Book", null, values);
        values.put("name","Android疯狂讲义");
        values.put("author","张三");
        values.put("pages",400);
        values.put("price",66);
        db.insert("Book", null, values);
    }

    private void createDatabase() {
        dbHelper.getWritableDatabase();
    }
}
