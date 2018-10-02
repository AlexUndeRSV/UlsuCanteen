package com.lynx.formi.ulsucanteen.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynx.formi.ulsucanteen.data.db.DbHelper;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.domain.dataclass.Eat;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.CategoriesTable;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.EatTable;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.LootTable;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {

    private static final String DB_NAME = "eat_db";
    private static final int DB_VERSION = 7;

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
                category.setTitle(cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_TITLE)));
                category.setImgUrl(cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_IMG_URL)));
                category.setId(cursor.getString(cursor.getColumnIndex(CategoriesTable.Columns.COLUMN_ID)));
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
        cv.put(CategoriesTable.Columns.COLUMN_TITLE, category.getTitle());
        cv.put(CategoriesTable.Columns.COLUMN_ID, category.getTitle());
        cv.put(CategoriesTable.Columns.COLUMN_IMG_URL, category.getTitle());

        db.insert(CategoriesTable.TABLE_NAME, null, cv);
        db.close();
    }

    public void saveEatList(List<Eat> eatList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        //delete
        db.execSQL(String.format("DELETE FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_ID + " NOT IN (%s)", toIds(eatList)) + ";");

        // update
        ContentValues cv = new ContentValues();
        for (int i = 0; i < eatList.size(); i++) {
            cv.put(EatTable.Columns.COLUMN_TITLE, eatList.get(i).title);
            cv.put(EatTable.Columns.COLUMN_ID, eatList.get(i).id);
            cv.put(EatTable.Columns.COLUMN_IMG_URL, eatList.get(i).imgUrl);
            cv.put(EatTable.Columns.COLUMN_PRICE, eatList.get(i).price);
            cv.put(EatTable.Columns.COLUMN_CATEGORY_ID, eatList.get(i).categoryId);
            cv.put(EatTable.Columns.COLUMN_DESCRIPTION, eatList.get(i).description);
            db.update(EatTable.TABLE_NAME, cv, EatTable.Columns.COLUMN_ID + " = ?", new String[]{eatList.get(i).id});
        }

        //insert or ignore
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < eatList.size(); i++) {
            if (i != 0) values.append(",");
            values.append("('")
                    .append(eatList.get(i).categoryId).append("','")
                    .append(eatList.get(i).id).append("','")
                    .append(eatList.get(i).description).append("','")
                    .append(eatList.get(i).imgUrl).append("','")
                    .append(eatList.get(i).price).append("','")
                    .append(eatList.get(i).title)
                    .append("')");
        }
        db.execSQL("INSERT OR IGNORE INTO " + EatTable.TABLE_NAME +
                "(" + EatTable.Columns.COLUMN_CATEGORY_ID + ","
                + EatTable.Columns.COLUMN_ID + ","
                + EatTable.Columns.COLUMN_DESCRIPTION + ","
                + EatTable.Columns.COLUMN_IMG_URL + ","
                + EatTable.Columns.COLUMN_PRICE + ","
                + EatTable.Columns.COLUMN_TITLE
                + ")" +
                " VALUES " + values + ";");

        db.setTransactionSuccessful();
        db.endTransaction();

        db.close();
    }

    private String toIds(List<Eat> eatList) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < eatList.size(); i++) {
            if (i != 0) sb.append(",");
            sb.append("'");
            sb.append(eatList.get(i).id);
            sb.append("'");
        }
        return sb.toString();
    }

    /*public void addEat(Eat eat) {
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
    }*/

    public List<Eat> getLootEatList() {
        List<Eat> eatList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + LootTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
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
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eatList;
    }

    public void addEatToLoot(Eat eat) {
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

    public Eat getEatById(String id) {
        Eat eat = new Eat();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_ID + " = ?", new String[]{id});

        if (cursor.moveToFirst()) {
            do {
                eat.id = id;
                eat.title = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_TITLE));
                eat.price = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_PRICE));
                eat.description = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_DESCRIPTION));
                eat.categoryId = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_CATEGORY_ID));
                eat.imgUrl = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_IMG_URL));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return eat;
    }
}
