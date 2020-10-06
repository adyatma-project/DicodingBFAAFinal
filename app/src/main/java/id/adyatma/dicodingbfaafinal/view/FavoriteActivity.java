package id.adyatma.dicodingbfaafinal.view;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import id.adyatma.dicodingbfaafinal.R;
import id.adyatma.dicodingbfaafinal.adapter.FavAdapter;
import id.adyatma.dicodingbfaafinal.db.DatabaseContract;
import id.adyatma.dicodingbfaafinal.db.DatabaseHelper;
import id.adyatma.dicodingbfaafinal.model.Favorite;

public class FavoriteActivity extends AppCompatActivity {

    private DatabaseHelper detek;
    private ArrayList<Favorite> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        dataList = new ArrayList<>();
        ImageView imgnull = findViewById(R.id.imgnull);
        TextView tvnull = findViewById(R.id.tvnull);
        String title = (getString(R.string.Favorite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        detek = new DatabaseHelper(getBaseContext());
        RecyclerView recyclerView = findViewById(R.id.rv_fav);
        getData();

        if (dataList.size() == 0) {
            imgnull.setVisibility(View.VISIBLE);
            tvnull.setVisibility(View.VISIBLE);
        } else {
            imgnull.setVisibility(View.GONE);
            tvnull.setVisibility(View.GONE);
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        FavAdapter adapter = new FavAdapter(dataList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FavoriteActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @SuppressLint("Recycle")
    protected void getData() {
        SQLiteDatabase ReadData = detek.getReadableDatabase();
        Cursor cursor = ReadData.rawQuery("SELECT * FROM " + DatabaseContract.TABLE_NAME, null);
        cursor.moveToFirst();
        for (int count = 0; count < cursor.getCount(); count++) {
            cursor.moveToPosition(count);
            dataList.add(new Favorite(cursor.getString(1), cursor.getString(2)));
        }
    }


}