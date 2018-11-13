package com.lynx.formi.ulsucanteen.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.*;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CategoriesTable.Queries.CREATE_TABLE);
        db.execSQL(EatTable.Queries.CREATE_TABLE);
        db.execSQL(BucketTable.Queries.CREATE_TABLE);
    }

    // TODO Migration
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CategoriesTable.Queries.DROP_TABLE);
        db.execSQL(EatTable.Queries.DROP_TABLE);
        db.execSQL(BucketTable.Queries.DROP_TABLE);
        onCreate(db);
    }
}
