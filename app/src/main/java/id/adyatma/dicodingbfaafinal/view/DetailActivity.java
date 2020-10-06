package id.adyatma.dicodingbfaafinal.view;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import de.hdodenhof.circleimageview.CircleImageView;
import id.adyatma.dicodingbfaafinal.R;
import id.adyatma.dicodingbfaafinal.adapter.PagerAdapter;
import id.adyatma.dicodingbfaafinal.db.DatabaseHelper;
import id.adyatma.dicodingbfaafinal.db.UserHelper;
import id.adyatma.dicodingbfaafinal.model.User;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";
    public static String login;
    public static String avatar;
    Boolean action = true;
    Boolean insert = true;
    Boolean delete = true;
    UserHelper userHelper;
    private TextView nama, repo, follower, following;
    private CircleImageView imgUser1;
    private ProgressBar progressBar;
    private FloatingActionButton fabadd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = (getString(R.string.detail_title));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String x = getIntent().getStringExtra(EXTRA_USER);
        login = x;

        nama = findViewById(R.id.tv_nama);
        repo = findViewById(R.id.repo);
        follower = findViewById(R.id.follower);
        following = findViewById(R.id.following);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        imgUser1 = findViewById(R.id.img_user);
        fabadd = findViewById(R.id.floatingadd);
        userHelper = UserHelper.getInstance(DetailActivity.this);
        setdetail();
        type();

        fabadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFavorit();
            }
        });


        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.github.com/users/" + x;
        client.addHeader("Authorization", "token 4132aeea442eeb21b45a8debf416f36ea81fb5f2");
        client.addHeader("User-Agent", "request");
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.GONE);
                String result = new String(responseBody);
                Log.d("onSuccess", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String login = jsonObject.getString("login");
                    String avatarUrl = jsonObject.getString("avatar_url");
                    String following1 = jsonObject.getString("following");
                    String follower2 = jsonObject.getString("followers");
                    String repo2 = jsonObject.getString("public_repos");

                    //Set Data
                    User user = new User();
                    user.setLogin(login);
                    user.setAvatar_url(avatarUrl);
                    user.setFollowing(following1);
                    user.setFollower(follower2);
                    user.setRepo(repo2);

                    //Get Data
                    String getLogin = user.getLogin();
                    String getAvatar = user.getAvatar_url();
                    String getFollowing = user.getFollowing();
                    String getFollowers = user.getFollower();
                    String getRepository = user.getRepo();

                    nama.setText(getLogin);
                    following.setText(getFollowing);
                    follower.setText(getFollowers);
                    repo.setText(getRepository);
                    avatar = getAvatar;
                    Glide.with(DetailActivity.this)
                            .load(getAvatar)
                            .into(imgUser1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String errorMessage;
                progressBar.setVisibility(View.GONE);
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;

                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;

                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;

                    default:
                        errorMessage = statusCode + " : " + error.getMessage();
                        break;
                }
                Log.d("OnFailure Error", errorMessage);
                error.printStackTrace();
            }
        });
        fragment();


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onResume() {
        super.onResume();
        setdetail();
        type();
    }

    private void setFavorit() {
        DatabaseHelper myDB = new DatabaseHelper(DetailActivity.this);
        if (insert && action) {
            User user2 = new User();
            user2.setLogin(login);
            user2.setAvatar_url(avatar);
            long result = myDB.addUser(user2);
            if (result > 0) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder
                        .setTitle("Telah Ditambahkan Ke Daftar Favorite")
                        .setIcon(R.mipmap.ic_launcher)
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                fabadd.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_24));
                                type();
                            }
                        });
                // membuat alert dialog dari builder
                AlertDialog alertDialog = alertDialogBuilder.create();
                // menampilkan alert dialog
                alertDialog.show();
            } else {
                Toast.makeText(DetailActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        } else if (delete && !action) {
            DatabaseHelper db = new DatabaseHelper(DetailActivity.this);
            db.deleteUser(login);
            fabadd.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_border_24));
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder
                    .setTitle("Telah Dihapus Dari Daftar Favorite")
                    .setIcon(R.mipmap.ic_launcher)
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            fabadd.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_baseline_favorite_24));
                            type();
                        }
                    });
            // membuat alert dialog dari builder
            AlertDialog alertDialog = alertDialogBuilder.create();
            // menampilkan alert dialog
            alertDialog.show();
        }
    }

    private void type() {
        if (userHelper.readRowData(login)) {
            action = false;
            delete = true;
            fabadd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24));
        } else if (!userHelper.readRowData(login)) {
            action = true;
            insert = true;
            fabadd.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24));
        }
    }

    private void setdetail() {
        String user3;
        user3 = getIntent().getStringExtra(EXTRA_USER);
        login = user3;
    }


    public void fragment() {
        final TabLayout tabLayout = findViewById(R.id.tabs);
        final ViewPager viewPager = findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}