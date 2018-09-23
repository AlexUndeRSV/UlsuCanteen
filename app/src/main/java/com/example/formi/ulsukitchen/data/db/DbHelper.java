package com.example.formi.ulsukitchen.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.formi.ulsukitchen.other.Constants.UlsuDatabase.*;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriesTable.Queries.CREATE_TABLE);
        db.execSQL(EatTable.Queries.CREATE_TABLE);
        db.execSQL(LootTable.Queries.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CategoriesTable.Queries.DROP_TABLE);
        db.execSQL(EatTable.Queries.DROP_TABLE);
        db.execSQL(LootTable.Queries.DROP_TABLE);
        onCreate(db);
    }
}
