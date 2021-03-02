package com.swtec.contentproviderapp.repositories;

import android.content.ContentValues;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.swtec.contentproviderapp.data.DairyEntry;
import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.List;

public class DairyEntryRepository {

    MutableLiveData<List<DairyEntry>> mData = new MutableLiveData<>();
    ContentResolverQueries contentResolverQuery;
    ContentResolverQueries contentResolverUpdate;
    ContentResolverQueries contentResolverDelete;

    public DairyEntryRepository(WeakReference<MainActivity> reference, Uri uri) {
        contentResolverQuery = new ContentResolverQuery(reference, uri);
        contentResolverQuery.setOnResultListener(new OnResultListener() {
            @Override
            public void onSuccess(List<DairyEntry> data) {
                reference.get().runOnUiThread(() -> mData.setValue(data));
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("DairyEntryRepository", "contentResolverQuery", throwable);
            }
        });

        contentResolverUpdate = new ContentResolverUpdate(reference, uri);
        contentResolverUpdate.setOnResultListener(new OnResultListener() {
            @Override
            public void onSuccess(List<DairyEntry> data) {
                reference.get().runOnUiThread(() -> Toast.makeText(reference.get(), "Data has been updated", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("DairyEntryRepository", "contentResolverUpdate", throwable);
                reference.get().runOnUiThread(() -> Toast.makeText(reference.get(), "Data hasn't been updated", Toast.LENGTH_SHORT).show());
            }
        });

        contentResolverDelete = new ContentResolverDelete(reference);
        contentResolverDelete.setOnResultListener(new OnResultListener() {
            @Override
            public void onSuccess(List<DairyEntry> data) {
                reference.get().runOnUiThread(() -> Toast.makeText(reference.get(), "Data has been deleted", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("DairyEntryRepository", "contentResolverDelete", throwable);
                reference.get().runOnUiThread(() -> Toast.makeText(reference.get(), "Data hasn't been deleted", Toast.LENGTH_SHORT).show());

            }
        });
    }

    public void updateData(ContentValues contentValues) {
        contentResolverUpdate.setValue(contentValues);
        contentResolverUpdate.makeRequest();
        queryData();
    }

    public void deleteData(Uri uri) {
        contentResolverDelete.setValue(uri);
        contentResolverDelete.makeRequest();
        queryData();
    }

    public LiveData<List<DairyEntry>> getDairyData() {
        return mData;
    }

    public void queryData() {
        contentResolverQuery.makeRequest();
    }
}
