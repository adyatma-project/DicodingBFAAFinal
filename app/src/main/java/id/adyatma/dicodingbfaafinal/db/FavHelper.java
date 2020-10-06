package id.adyatma.dicodingbfaafinal.db;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.TABLE_NAME;

public class FavHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static SQLiteDatabase database;
    private static FavHelper INSTANCE;
    private DatabaseHelper dataBaseHelper;

    public FavHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }


    public Cursor queryAll() {
        return database.query(DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public Cursor queryById(String id) {
        return database.query(TABLE_NAME, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public int update(String id, ContentValues values) {
        return database.update(TABLE_NAME, values, _ID + " = ?", new String[]{id});
    }


    public long insert(ContentValues values) {
        return database.insert(TABLE_NAME, null, values);
    }

    public int deleteById(String id) {
        return database.delete(TABLE_NAME, _ID + " = ?", new String[]{id});
    }


}

