package com.lynx.formi.ulsucanteen.data.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynx.formi.ulsucanteen.data.db.DbHelper;
import com.lynx.formi.ulsucanteen.domain.dataclass.Category;
import com.lynx.formi.ulsucanteen.domain.dataclass.Food;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.BucketTable;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.CategoriesTable;
import com.lynx.formi.ulsucanteen.other.Constants.UlsuDatabase.EatTable;

import java.util.ArrayList;
import java.util.List;

public class DBRepository {

    private static final String DB_NAME = "eat_db";
    private static final int DB_VERSION = 8;

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

    public List<Food> getFoodList(String categoryId) {
        List<Food> foodList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_CATEGORY_ID + "=" + categoryId, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.title = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_TITLE));
                food.imgUrl = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_IMG_URL));
                food.id = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_ID));
                food.categoryId = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_CATEGORY_ID));
                food.description = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_DESCRIPTION));
                food.price = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_PRICE));
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;

    }

    /*public void addCategory(Category category) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.Columns.COLUMN_TITLE, category.getTitle());
        cv.put(CategoriesTable.Columns.COLUMN_ID, category.getTitle());
        cv.put(CategoriesTable.Columns.COLUMN_IMG_URL, category.getTitle());

        db.insert(CategoriesTable.TABLE_NAME, null, cv);
        db.close();
    }*/

    public void saveFoodList(List<Food> foodList) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        //delete
        db.execSQL(String.format("DELETE FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_ID + " NOT IN (%s)", toIds(foodList)) + ";");

        // update
        ContentValues cv = new ContentValues();
        for (int i = 0; i < foodList.size(); i++) {
            cv.put(EatTable.Columns.COLUMN_TITLE, foodList.get(i).title);
            cv.put(EatTable.Columns.COLUMN_ID, foodList.get(i).id);
            cv.put(EatTable.Columns.COLUMN_IMG_URL, foodList.get(i).imgUrl);
            cv.put(EatTable.Columns.COLUMN_PRICE, foodList.get(i).price);
            cv.put(EatTable.Columns.COLUMN_CATEGORY_ID, foodList.get(i).categoryId);
            cv.put(EatTable.Columns.COLUMN_DESCRIPTION, foodList.get(i).description);
            db.update(EatTable.TABLE_NAME, cv, EatTable.Columns.COLUMN_ID + " = ?", new String[]{foodList.get(i).id});
        }

        //insert or ignore
        StringBuilder values = new StringBuilder();

        for (int i = 0; i < foodList.size(); i++) {
            if (i != 0) values.append(",");
            values.append("('")
                    .append(foodList.get(i).categoryId).append("','")
                    .append(foodList.get(i).id).append("','")
                    .append(foodList.get(i).description).append("','")
                    .append(foodList.get(i).imgUrl).append("','")
                    .append(foodList.get(i).price).append("','")
                    .append(foodList.get(i).title)
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

    private String toIds(List<Food> foodList) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < foodList.size(); i++) {
            if (i != 0) sb.append(",");
            sb.append("'");
            sb.append(foodList.get(i).id);
            sb.append("'");
        }
        return sb.toString();
    }

    public List<Food> getBucketFoodList() {
        List<Food> foodList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BucketTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.title = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_TITLE));
                food.imgUrl = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_IMG_URL));
                food.id = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_ID));
                food.categoryId = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_CATEGORY_ID));
                food.description = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_DESCRIPTION));
                food.price = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_PRICE));
                food.count = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT));
                foodList.add(food);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return foodList;
    }

    public boolean addFoodToBucket(Food food) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int bufCount;
        String id = null;

        boolean isMax = false;

        Cursor cursor = db.rawQuery("SELECT * FROM " + BucketTable.TABLE_NAME + " WHERE " + BucketTable.Columns.COLUMN_ID + "=" + food.id, null);

        if (cursor.moveToFirst()) {
            if(Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT))) > 8){
                isMax = true;
            }
            bufCount = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT))) + Integer.valueOf(food.count);
            if (bufCount > 9) {
                food.count = "9";
            } else {
                food.count = String.valueOf(bufCount);
            }
            id = cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_ID));
        }
        cursor.close();

        ContentValues cv = new ContentValues();
        cv.put(BucketTable.Columns.COLUMN_TITLE, food.title);
        cv.put(BucketTable.Columns.COLUMN_ID, food.id);
        cv.put(BucketTable.Columns.COLUMN_IMG_URL, food.imgUrl);
        cv.put(BucketTable.Columns.COLUMN_PRICE, food.price);
        cv.put(BucketTable.Columns.COLUMN_COUNT, food.count);
        cv.put(BucketTable.Columns.COLUMN_CATEGORY_ID, food.categoryId);
        cv.put(BucketTable.Columns.COLUMN_DESCRIPTION, food.description);

        if (id != null) {
            db.update(BucketTable.TABLE_NAME, cv, BucketTable.Columns.COLUMN_ID + " = " + id, null);
        } else {
            db.insert(BucketTable.TABLE_NAME, null, cv);
        }
        db.close();

        return isMax;
    }

    public void deleteFromBucket(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(BucketTable.TABLE_NAME, BucketTable.Columns.COLUMN_ID + " = ?", new String[]{id});

        db.close();
    }

    public Food getFoodById(String id) {
        Food food = new Food();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + EatTable.TABLE_NAME + " WHERE " + EatTable.Columns.COLUMN_ID + " = ?", new String[]{id});

        if (cursor.moveToFirst()) {
            do {
                food.id = id;
                food.title = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_TITLE));
                food.price = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_PRICE));
                food.description = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_DESCRIPTION));
                food.categoryId = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_CATEGORY_ID));
                food.imgUrl = cursor.getString(cursor.getColumnIndex(EatTable.Columns.COLUMN_IMG_URL));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return food;
    }

    public int getTotalBucketPrice() {
        int totalPrice = 0;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + BucketTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                totalPrice += Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT))) * Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_PRICE)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return totalPrice;
    }

    public void clearLootTable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        db.delete(BucketTable.TABLE_NAME, null, null);

        db.close();
    }

    public void incrementCount(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int count = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM " + BucketTable.TABLE_NAME + " WHERE " + BucketTable.Columns.COLUMN_ID + "=" + id, null);


        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT)));
        }

        ContentValues cv = new ContentValues();
        cv.put(BucketTable.Columns.COLUMN_COUNT, String.valueOf(count + 1));
        db.update(BucketTable.TABLE_NAME, cv, BucketTable.Columns.COLUMN_ID + "=" + id, null);

        db.close();
    }

    public void decrementCount(String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        int count = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM " + BucketTable.TABLE_NAME + " WHERE " + BucketTable.Columns.COLUMN_ID + "=" + id, null);


        if (cursor.moveToFirst()) {
            count = Integer.valueOf(cursor.getString(cursor.getColumnIndex(BucketTable.Columns.COLUMN_COUNT)));
        }

        ContentValues cv = new ContentValues();
        cv.put(BucketTable.Columns.COLUMN_COUNT, String.valueOf(count - 1));
        db.update(BucketTable.TABLE_NAME, cv, BucketTable.Columns.COLUMN_ID + "=" + id, null);

        db.close();
    }
}
