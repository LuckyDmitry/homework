package com.swtec.contentproviderapp.repositories;

import android.content.ContentValues;
import android.net.Uri;

import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContentResolverUpdate implements ContentResolverQueries {

    private final WeakReference<MainActivity> mReference;
    private final Uri mUri;
    private OnResultListener mListener;
    private ContentValues mContentValues;
    private final Executor mExecutor;

    public ContentResolverUpdate(WeakReference<MainActivity> mReference, Uri mUri) {
        this.mReference = mReference;
        this.mUri = mUri;
        this.mExecutor = Executors.newSingleThreadExecutor();
    }

    @Override
    public void setOnResultListener(OnResultListener listener) {
        mListener = listener;
    }

    @Override
    public void setValue(Object values) {
        mContentValues = (ContentValues) values;
    }

    @Override
    public void makeRequest() {
        if (mContentValues != null) {
            mExecutor.execute(() -> {
                try {
                    mReference.get().getApplicationContext().getContentResolver().update(
                            mUri, mContentValues, null, null);
                    onSuccess();
                } catch (Error error) {
                    onFailure(error);
                }
            });
        }
    }

    private void onSuccess() {
        if (mListener != null) {
            mListener.onSuccess(null);
        }
    }

    private void onFailure(Throwable throwable) {
        if (mListener != null) {
            mListener.onFailure(throwable);
        }
    }
}
