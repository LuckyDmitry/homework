package com.swtec.contentproviderapp.repositories;

import android.net.Uri;

import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ContentResolverDelete implements ContentResolverQueries {


    private Uri mDeleteUri;
    private final WeakReference<MainActivity> mReference;
    private final Executor mExecutors;
    private OnResultListener mListener;


    public ContentResolverDelete(WeakReference<MainActivity> reference) {
        mReference = reference;
        mExecutors = Executors.newSingleThreadExecutor();
    }

    @Override
    public void setOnResultListener(OnResultListener listener) {
        mListener = listener;
    }

    @Override
    public void setValue(Object values) {
        mDeleteUri = (Uri) values;
    }

    @Override
    public void makeRequest() {
        mExecutors.execute(() -> {
            try {
                mReference.get().getContentResolver().delete(mDeleteUri, null, null);
                onSuccess();
            } catch (Error error) {
                onFailure(error);
            }
        });
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
