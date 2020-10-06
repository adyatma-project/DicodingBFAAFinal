package id.adyatma.dicodingbfaafinal.db;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import id.adyatma.dicodingbfaafinal.model.User;

import static android.provider.BaseColumns._ID;
import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.TABLE_NAME;
import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.UserColumns.AVATAR_URL;
import static id.adyatma.dicodingbfaafinal.db.DatabaseContract.UserColumns.LOGIN;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NAME, _ID, LOGIN, DatabaseContract.UserColumns.AVATAR_URL);

    public static String DATABASE_NAME = "Favorite";
    Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LOGIN, user.getLogin());
        cv.put(AVATAR_URL, user.getAvatar_url());
        return db.insert(TABLE_NAME, null, cv);
    }


    public void deleteUser(String login) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, LOGIN + " = " + "'" + login + "'", null);
        if (result == -1) {
            Toast.makeText(context, "Error 404", Toast.LENGTH_SHORT).show();
        }
    }
}
