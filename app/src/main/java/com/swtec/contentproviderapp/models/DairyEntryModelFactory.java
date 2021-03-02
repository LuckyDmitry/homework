package com.swtec.contentproviderapp.models;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;

public class DairyEntryModelFactory implements ViewModelProvider.Factory {

    private final WeakReference<MainActivity> reference;
    private final Uri uri;

    public DairyEntryModelFactory(WeakReference<MainActivity> reference, Uri uri) {
        this.reference = reference;
        this.uri = uri;
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DairyEntryModel(reference, uri);
    }
}
