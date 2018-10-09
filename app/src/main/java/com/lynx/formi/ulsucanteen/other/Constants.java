package com.lynx.formi.ulsucanteen.other;

public class Constants {

    public static class UlsuDatabase {

        public static class CategoriesTable {

            public static final String TABLE_NAME = "categories_table";

            public static class Columns {
                public static final String COLUMN_TITLE = "title";
                public static final String COLUMN_ID = "id";
                public static final String COLUMN_IMG_URL = "img_url";
            }

            public static class Queries {
                public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                        + Columns.COLUMN_ID + " TEXT PRIMARY KEY not null, "
                        + Columns.COLUMN_TITLE + " TEXT not null, "
                        + Columns.COLUMN_IMG_URL + " TEXT not null "
                        + ");";
                public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
            }
        }

        public static class EatTable {
            public static final String TABLE_NAME = "eat_table";

            public static class Columns {
                public static final String COLUMN_TITLE = "title";
                public static final String COLUMN_ID = "id";
                public static final String COLUMN_DESCRIPTION = "description";
                public static final String COLUMN_IMG_URL = "img_url";
                public static final String COLUMN_CATEGORY_ID = "category_id";
                public static final String COLUMN_PRICE = "price";
            }

            public static class Queries {
                public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                        + Columns.COLUMN_ID + " TEXT PRIMARY KEY not null, "
                        + Columns.COLUMN_TITLE + " TEXT not null, "
                        + Columns.COLUMN_DESCRIPTION + " TEXT, "
                        + Columns.COLUMN_CATEGORY_ID + " TEXT not null, "
                        + Columns.COLUMN_PRICE + " TEXT not null, "
                        + Columns.COLUMN_IMG_URL + " TEXT not null "
                        + ");";
                public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
            }
        }

        public static class BucketTable {
            public static final String TABLE_NAME = "bucket_table";

            public static class Columns {
                public static final String COLUMN_TITLE = "title";
                public static final String COLUMN_ID = "id";
                public static final String COLUMN_DESCRIPTION = "description";
                public static final String COLUMN_IMG_URL = "img_url";
                public static final String COLUMN_COUNT = "count";
                public static final String COLUMN_CATEGORY_ID = "category_id";
                public static final String COLUMN_PRICE = "price";
            }

            public static class Queries {
                public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                        + Columns.COLUMN_ID + " TEXT PRIMARY KEY not null, "
                        + Columns.COLUMN_TITLE + " TEXT not null, "
                        + Columns.COLUMN_DESCRIPTION + " TEXT, "
                        + Columns.COLUMN_CATEGORY_ID + " TEXT not null, "
                        + Columns.COLUMN_COUNT + " TEXT not null, "
                        + Columns.COLUMN_PRICE + " TEXT not null, "
                        + Columns.COLUMN_IMG_URL + " TEXT not null "
                        + ");";
                public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
            }
        }
    }

    public static class BundleKeys{
        public static final String ID_KEY = "id_key";
        public static final String EAT_KEY = "eat_key";
        public static final String CONTAINER_NAME_KEY = "container_name_key";
        public static final String TITLE_KEY = "title_key";
    }
}
