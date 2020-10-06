package id.adyatma.consumerapp.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "id.adyatma.dicodingbfaafinal";
    public static final String TABLE_NAME = "user";
    private static final String SCHEME = "content";
    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();

    private DatabaseContract() {
    }

    public static int getUserFavoriteInt(Cursor cursor, String coloumnUser) {
        return cursor.getInt(cursor.getColumnIndex(coloumnUser));
    }

    public static String getUserFavoriteString(Cursor cursor, String coloumnUser) {
        return cursor.getString(cursor.getColumnIndex(coloumnUser));
    }

    public static class UserColumns implements BaseColumns {
        public static final String LOGIN = "login";
        public static final String AVATAR_URL = "avatar_url";
    }
}

