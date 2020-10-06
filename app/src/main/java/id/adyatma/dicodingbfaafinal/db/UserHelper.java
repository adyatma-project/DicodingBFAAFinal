package id.adyatma.dicodingbfaafinal.db;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.TABLE_NAME;
import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.UserColumns.LOGIN;

public class UserHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static UserHelper INSTANCE;
    private static SQLiteDatabase database;


    private UserHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static UserHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserHelper(context);
                }
            }
        }
        return INSTANCE;
    }


    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }


    public Boolean readRowData(String login) {
        open();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + LOGIN + " " + " LIKE " + "'" + login + "'";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return true;
        } else if (cursor.getCount() == 0) {
            return false;
        }
        return false;
    }

}
