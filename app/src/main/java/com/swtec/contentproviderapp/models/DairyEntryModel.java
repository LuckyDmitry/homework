package com.swtec.contentproviderapp.models;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.swtec.contentproviderapp.data.DairyEntry;
import com.swtec.contentproviderapp.repositories.DairyEntryRepository;
import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class DairyEntryModel extends ViewModel {

    DairyEntryRepository dairyEntryRepository;

    public DairyEntryModel(WeakReference<MainActivity> reference, Uri uri) {
        dairyEntryRepository = new DairyEntryRepository(reference, uri);
    }

    public LiveData<List<DairyEntry>> getDairyData() {
        return dairyEntryRepository.getDairyData();
    }

    public void queryData() {
        dairyEntryRepository.queryData();
    }

    public void updateData(ContentValues contentValues) {
        dairyEntryRepository.updateData(contentValues);
    }

    public void deleteData(Uri uri) {
        dairyEntryRepository.deleteData(uri);
    }
}
