package com.authism.connect.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.authism.connect.R;

public class SettingsActivity extends AppCompatActivity {
    Spinner spinner;
    SwitchCompat onOff;
    String currentCardsCount;
    String[] arr = {"3", "4", "5"};
    boolean hasBottomBox;
    ImageButton btnBack, btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        spinner = findViewById(R.id.countSpinner);
        onOff = findViewById(R.id.onOffSwitch);
        btnBack = findViewById(R.id.btnBackSettings);
        btnSave = findViewById(R.id.btnSaveSettings);
        currentCardsCount = PreferenceManager.getDefaultSharedPreferences(this).getString("count", "3");
        hasBottomBox = Boolean.parseBoolean(
                PreferenceManager.getDefaultSharedPreferences(this).getString("withBox", "true"));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, arr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(currentCardsCount));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCardsCount = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        onOff.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                hasBottomBox = isChecked;
            }
        });
        onOff.setChecked(hasBottomBox);
    }

    public void onSettingsClick(View v) {
        if (v.getId() == btnBack.getId()) {
            finish();
        } else if (v.getId() == btnSave.getId()) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("count", currentCardsCount).apply();
            PreferenceManager.getDefaultSharedPreferences(this).edit().putString("withBox", hasBottomBox + "").apply();
            Toast.makeText(SettingsActivity.this, "Сохранено", Toast.LENGTH_LONG).show();
            finish();
        }
    }

}