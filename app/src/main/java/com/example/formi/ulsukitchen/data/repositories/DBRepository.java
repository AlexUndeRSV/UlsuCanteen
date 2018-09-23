package com.example.formi.ulsukitchen.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.formi.ulsukitchen.data.db.DbHelper;
import com.example.formi.ulsukitchen.domain.dataclass.Category;
import com.example.formi.ulsukitchen.domain.dataclass.Eat;
import com.example.formi.ulsukitchen.other.Constants;
import com.example.formi.ulsukitchen.other.Constants.UlsuDatabase;
import com.example.formi.ulsukitchen.other.Constants.UlsuDatabase.CategoriesTable;
import com.example.formi.ulsukitchen.other.Constants.UlsuDatabase.EatTable;
import com.example.formi.ulsukitchen.other.Constants.UlsuDatabase.LootTable;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {

    private static final String DB_NAME = "eat_db";
    private static final int DB_VERSION = 5;

    private DbHelper dbHelper;

    public DBRepository(Context ctx) {
        dbHelper = new DbHelper(ctx, DB_NAME, null, DB_VERSION);
    }

    public List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + CategoriesTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.title = cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_TITLE));
                category.imgUrl = cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_IMG_URL));
                category.id = cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_ID));
                categoryList.add(category);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return categoryList;
    }

    public List<Eat> getEatList(String categoryId) {
        List<Eat> eatList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_CATEGORY_ID + "=" + categoryId, null);

        if (cursor.moveToFirst()) {
            do {
                Eat eat = new Eat();
                eat.title = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_TITLE));
                eat.imgUrl = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_IMG_URL));
                eat.id = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_ID));
                eat.categoryId = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_CATEGORY_ID));
                eat.description = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_DESCRIPTION));
                eat.price = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_PRICE));
                eatList.add(eat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eatList;

    }

    public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.Columns.COLUMN_TITLE, category.title);
        cv.put(CategoriesTable.Columns.COLUMN_ID, category.id);
        cv.put(CategoriesTable.Columns.COLUMN_IMG_URL, category.imgUrl);

        db.insert(CategoriesTable.TABLE_NAME, null, cv);
        db.close();
    }

    public void addEat(Eat eat) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EatTable.Columns.COLUMN_TITLE, eat.title);
        cv.put(EatTable.Columns.COLUMN_ID, eat.id);
        cv.put(EatTable.Columns.COLUMN_IMG_URL, eat.imgUrl);
        cv.put(EatTable.Columns.COLUMN_PRICE, eat.price);
        cv.put(EatTable.Columns.COLUMN_CATEGORY_ID, eat.categoryId);
        cv.put(EatTable.Columns.COLUMN_DESCRIPTION, eat.description);

        db.insert(EatTable.TABLE_NAME, null, cv);
        db.close();
    }

    public List<Eat> getLootEatList(){
        List<Eat> eatList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LootTable.TABLE_NAME, null);

        if(cursor.moveToFirst()){
            do {
                Eat eat = new Eat();
                eat.title = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_TITLE));
                eat.imgUrl = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_IMG_URL));
                eat.id = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_ID));
                eat.categoryId = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_CATEGORY_ID));
                eat.description = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_DESCRIPTION));
                eat.price = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_PRICE));
                eat.count = cursor.getString(cursor.getColumnIndex(LootTable.Columns.COLUMN_COUNT));
                eatList.add(eat);
            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eatList;
    }

    public void addEatToLoot(Eat eat){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(LootTable.Columns.COLUMN_TITLE, eat.title);
        cv.put(LootTable.Columns.COLUMN_ID, eat.id);
        cv.put(LootTable.Columns.COLUMN_IMG_URL, eat.imgUrl);
        cv.put(LootTable.Columns.COLUMN_PRICE, eat.price);
        cv.put(LootTable.Columns.COLUMN_COUNT, eat.count);
        cv.put(LootTable.Columns.COLUMN_CATEGORY_ID, eat.categoryId);
        cv.put(LootTable.Columns.COLUMN_DESCRIPTION, eat.description);

        db.insert(LootTable.TABLE_NAME, null, cv);
        db.close();
    }

    public void deleteFromLoot(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(LootTable.TABLE_NAME, LootTable.Columns.COLUMN_ID + " = ?", new String[]{id});

        db.close();
    }
}
