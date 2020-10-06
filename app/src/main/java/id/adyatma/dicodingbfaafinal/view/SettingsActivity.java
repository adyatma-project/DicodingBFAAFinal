package id.adyatma.dicodingbfaafinal.view;
/*This App Created By ADYATMA LAKATJINDA To Dicoding Indonesia*/
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import id.adyatma.dicodingbfaafinal.R;
import id.adyatma.dicodingbfaafinal.ReminderReceiver;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    public static final String SP_REMINDER = "sp_reminder";
    public static final String KEY = "key";
    private ReminderReceiver mReminderReceiver;
    private Switch mSwitchDaily;
    int set = 10;
    int a =0;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        mSwitchDaily = findViewById(R.id.switch_daily);
        Button btnBahasa = findViewById(R.id.btnbahasa);
        mReminderReceiver = new ReminderReceiver();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.pengaturan);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mSharedPreferences = getSharedPreferences(SP_REMINDER,
                MODE_PRIVATE);

        stateSwitch();
        mSwitchDaily.setOnCheckedChangeListener(this);
        btnBahasa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bahasa();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    protected void bahasa() {
        Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
        startActivity(mIntent);
    }


    private void stateSwitch() {
        SharedPreferences load = getSharedPreferences(SP_REMINDER, MODE_PRIVATE);
        a = load.getInt(KEY, 0);
        if (a == 10) {
            mSwitchDaily.setChecked(true);
        } else {
            mSwitchDaily.setChecked(false);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.isChecked()) {
            setReminder();
        } else {
            cancelReminder();
        }
    }

    private void setReminder() {
        mReminderReceiver.setReminder(SettingsActivity.this);
        SharedPreferences sha1 = getSharedPreferences(SP_REMINDER, MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sha1.edit();
        editor2.putInt(KEY, set);
        editor2.apply();

    }

    private void cancelReminder() {
        mReminderReceiver.cancelReminder(SettingsActivity.this);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.remove(KEY);
        mEditor.apply();
    }
}