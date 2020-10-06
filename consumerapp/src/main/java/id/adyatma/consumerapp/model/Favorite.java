package id.adyatma.consumerapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import id.adyatma.consumerapp.db.DatabaseContract;

import static id.adyatma.consumerapp.db.DatabaseContract.getUserFavoriteInt;
import static id.adyatma.consumerapp.db.DatabaseContract.getUserFavoriteString;

public class Favorite implements Parcelable {
    public static final Creator<Favorite> CREATOR = new Creator<Favorite>() {
        @Override
        public Favorite createFromParcel(Parcel in) {
            return new Favorite(in);
        }

        @Override
        public Favorite[] newArray(int size) {
            return new Favorite[size];
        }
    };
    public int user_id;
    public String login;
    public String avatar_url;

    public Favorite(String login, String avatar_url) {
        this.login = login;
        this.avatar_url = avatar_url;
    }


    public Favorite() {

    }

    public Favorite(int user_id, String login, String avatar_url) {
        this.user_id = user_id;
        this.login = login;
        this.avatar_url = avatar_url;

    }

    protected Favorite(Parcel in) {
        user_id = in.readInt();
        login = in.readString();
        avatar_url = in.readString();
    }

    public Favorite(Cursor cursor) {
        this.user_id = getUserFavoriteInt(cursor, DatabaseContract.UserColumns._ID);
        this.login = getUserFavoriteString(cursor, DatabaseContract.UserColumns.LOGIN);
        this.avatar_url = getUserFavoriteString(cursor, DatabaseContract.UserColumns.AVATAR_URL);

    }
    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(user_id);
        dest.writeString(login);
        dest.writeString(avatar_url);
    }
}
