package com.swtec.contentproviderapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.swtec.contentproviderapp.R;
import com.swtec.contentproviderapp.data.DairyEntry;
import com.swtec.contentproviderapp.ui.MainActivity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryViewHolder> {

    private final List<DairyEntry> mDairyEntries = new ArrayList<>();
    private final LayoutInflater mLayoutInflater;
    private final WeakReference<MainActivity> mReference;

    public EntryAdapter(Context context, WeakReference<MainActivity> reference) {
        mLayoutInflater = LayoutInflater.from(context);
        mReference = reference;
    }

    @NonNull
    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_entry, parent, false);
        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryViewHolder holder, int position) {
        DairyEntry dairyEntry = mDairyEntries.get(position);
        if (dairyEntry != null) {
            holder.mTextView.setText(dairyEntry.getEntryText());
            holder.mEditButton.setOnClickListener(v -> mReference.get().editButton(dairyEntry));
            holder.mDeleteButton.setOnClickListener(v -> mReference.get().deleteButton(dairyEntry));
        }
    }

    @Override
    public int getItemCount() {
        return mDairyEntries.size();
    }

    public void setItems(List<DairyEntry> newEntries) {
        mDairyEntries.clear();
        mDairyEntries.addAll(newEntries);
        notifyDataSetChanged();
    }

    static class EntryViewHolder extends RecyclerView.ViewHolder {

        private final Button mEditButton;
        private final Button mDeleteButton;
        private final TextView mTextView;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);

            mEditButton = itemView.findViewById(R.id.item_entry__btn_edit);
            mDeleteButton = itemView.findViewById(R.id.item_entry__btn_delete);
            mTextView = itemView.findViewById(R.id.item_entry__tv_mainText);
        }
    }
}
