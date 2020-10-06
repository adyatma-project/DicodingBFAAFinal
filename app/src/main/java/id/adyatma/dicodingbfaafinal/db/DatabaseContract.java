package id.adyatma.dicodingbfaafinal.db;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "id.adyatma.dicodingbfaafinal";
    public static final String TABLE_NAME = "user";
    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build();
    private DatabaseContract() {
    }

    public static class UserColumns implements BaseColumns {
        public static final String LOGIN = "login";
        public static final String AVATAR_URL = "avatar_url";
    }
}