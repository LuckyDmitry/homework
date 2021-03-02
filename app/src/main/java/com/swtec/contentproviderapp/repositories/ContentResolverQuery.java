package com.swtec.contentproviderapp.repositories;

import android.database.Cursor;
import android.net.Uri;

import com.swtec.contentproviderapp.data.DairyEntry;
import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContentResolverQuery implements ContentResolverQueries {

    private OnResultListener mListener;
    private final WeakReference<MainActivity> mReference;
    private final Uri mUri;
    private final List<DairyEntry> mDataFromQuery = new ArrayList<>();
    private final Executor executors = Executors.newSingleThreadExecutor();

    public ContentResolverQuery(WeakReference<MainActivity> weakReference, Uri uri) {
        mReference = weakReference;
        mUri = uri;
    }

    @Override
    public void setOnResultListener(OnResultListener listener) {
        mListener = listener;
    }

    @Override
    public void makeRequest() {

        executors.execute(() -> {
            Cursor cursor = mReference.get().getApplicationContext().getContentResolver().query(
                    mUri, null, null, null, null);
            setData(cursor);
        });
    }

    private void onSuccess() {
        if (mListener != null) {
            mListener.onSuccess(mDataFromQuery);
        }
    }

    private void onFailure(Throwable throwable) {
        if (mListener != null) {
            mListener.onFailure(throwable);
        }
    }

    private void setData(Cursor cursor) {
        mDataFromQuery.clear();
        if (cursor != null) {
            String entryText;
            String entryDate;
            int id;
            cursor.isBeforeFirst();
            while (cursor.moveToNext()) {
                try {
                    entryText = cursor.getString(cursor.getColumnIndex("entry_text"));
                    entryDate = cursor.getString(cursor.getColumnIndex("entry_date"));
                    id = Integer.parseInt(cursor.getString(cursor.getColumnIndex("id")));
                    mDataFromQuery.add(new DairyEntry(entryText, entryDate, id));
                } catch (Error error) {
                    onFailure(error);
                }
            }
            onSuccess();
            cursor.close();
        }
    }
}
