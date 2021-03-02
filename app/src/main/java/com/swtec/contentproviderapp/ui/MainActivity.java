package com.swtec.contentproviderapp.ui;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.swtec.contentproviderapp.R;
import com.swtec.contentproviderapp.adapters.EntryAdapter;
import com.swtec.contentproviderapp.data.DairyEntry;
import com.swtec.contentproviderapp.models.DairyEntryModel;
import com.swtec.contentproviderapp.models.DairyEntryModelFactory;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    static final String AUTHORITY = "com.swtecnn.contentproviderlesson.DbContentProvider";
    static final String DIARY_ENTRY_TABLE = "DiaryEntry";
    static final Uri uri = Uri.parse("content://" + AUTHORITY + "/" + DIARY_ENTRY_TABLE);
    static final String INTENT_KEY = "com.swtec.contentprovider";
    private final Integer EDIT_ACTIVITY = 1;

    private DairyEntryModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.activity_main__rv);
        EntryAdapter entryAdapter = new EntryAdapter(this, new WeakReference<>(this));
        recyclerView.setAdapter(entryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this, new DairyEntryModelFactory(
                new WeakReference<>(this), uri)).get(DairyEntryModel.class);
        viewModel.getDairyData().observe(this, entryAdapter::setItems);
        viewModel.queryData();

    }

    public void editButton(DairyEntry value) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(MainActivity.INTENT_KEY, value);
        startActivityForResult(intent,
                EDIT_ACTIVITY,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void deleteButton(DairyEntry value) {
        Uri uri = Uri.parse("content://" +
                AUTHORITY + "/" + DIARY_ENTRY_TABLE + "/" + value.getId());
        viewModel.deleteData(uri);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == EDIT_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    DairyEntry dairyEntry = data.getExtras().getParcelable(MainActivity.INTENT_KEY);
                    ContentValues contentValues = new ContentValues();
                    if (dairyEntry != null) {
                        contentValues.put("entry_text", dairyEntry.getEntryText());
                        contentValues.put("entry_date", dairyEntry.getEntryDate());
                        contentValues.put("id", dairyEntry.getId());
                        viewModel.updateData(contentValues);

                    }
                }
            }
        }
    }
}
