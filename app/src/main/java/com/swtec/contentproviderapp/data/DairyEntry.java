package com.swtec.contentproviderapp.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class DairyEntry implements Parcelable {
    private String entryText;
    private final String entryDate;
    private final Integer id;


    public DairyEntry(@NonNull String entryText, String entryDate, Integer id) {
        this.entryText = entryText;
        this.entryDate = entryDate;
        this.id = id;
    }

    public void setEntryText(String entryText) {
        this.entryText = entryText;
    }

    public String getEntryText() {
        return entryText;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public Integer getId() {
        return id;
    }

    protected DairyEntry(Parcel in) {
        entryText = in.readString();
        entryDate = in.readString();
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
    }

    public static final Creator<DairyEntry> CREATOR = new Creator<DairyEntry>() {
        @Override
        public DairyEntry createFromParcel(Parcel in) {
            return new DairyEntry(in);
        }

        @Override
        public DairyEntry[] newArray(int size) {
            return new DairyEntry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(entryText);
        dest.writeString(entryDate);
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
    }
}
