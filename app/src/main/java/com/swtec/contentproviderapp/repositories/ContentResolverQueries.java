package com.swtec.contentproviderapp.repositories;

public interface ContentResolverQueries<T> {

    void setOnResultListener(OnResultListener listener);

    default void setValue(T values) {
    }

    void makeRequest();
}
