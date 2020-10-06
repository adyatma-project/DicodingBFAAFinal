package id.adyatma.consumerapp;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import id.adyatma.consumerapp.adapter.FavAdapter;

import static id.adyatma.consumerapp.db.DatabaseContract.CONTENT_URI;


@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {
    Cursor mListFav;
    private FavAdapter adapter;
    private RecyclerView rv;
    private ImageView imgnull;
    private TextView tvnull;
    private ProgressBar prg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv_fav);
        imgnull = findViewById(R.id.imgnull);
        tvnull = findViewById(R.id.tvnull);
        prg = findViewById(R.id.progressBar);
        prg.setVisibility(View.VISIBLE);
        imgnull.setVisibility(View.VISIBLE);
        tvnull.setVisibility(View.VISIBLE);
        String title = (getString(R.string.Favorite));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
        adapter = new FavAdapter(this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        rv.setAdapter(adapter);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.line));
        rv.addItemDecoration(itemDecoration);
        new threadFav().execute();
    }


    @Override
    protected void onResume() {
        new threadFav().execute();
        super.onResume();
    }


    private class threadFav extends AsyncTask<Void, Void, Cursor> {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        protected Cursor doInBackground(Void... voids) {
            return MainActivity.this.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            mListFav = cursor;
            if (mListFav != null && mListFav.getCount() > 0) {
                imgnull.setVisibility(View.GONE);
                tvnull.setVisibility(View.GONE);
            } else {
                imgnull.setVisibility(View.VISIBLE);
                tvnull.setVisibility(View.VISIBLE);
            }
            prg.setVisibility(View.GONE);
            adapter.setUserCursor(mListFav);
            adapter.notifyDataSetChanged();
        }
    }
}