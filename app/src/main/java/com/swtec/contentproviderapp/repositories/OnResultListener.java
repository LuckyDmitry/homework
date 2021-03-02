package com.swtec.contentproviderapp.repositories;

import com.swtec.contentproviderapp.data.DairyEntry;

import java.util.List;

public interface OnResultListener {

    void onSuccess(List<DairyEntry> data);

    void onFailure(Throwable throwable);
}
