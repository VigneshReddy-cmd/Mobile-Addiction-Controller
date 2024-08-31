package com.multiscreen.final_lock;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.sql.Time;

public class AppDetail implements Parcelable {
    private CharSequence label;
    private Time time;
    private Drawable icon;
    private Bitmap iconBitmap;

    // Constructor without time
    public AppDetail(CharSequence label, Drawable icon) {
        this.label = label;
        this.icon = icon;
        this.iconBitmap = drawableToBitmap(icon);
    }

    // Constructor with time
    public AppDetail(CharSequence label, Drawable icon, Time time) {
        this.label = label;
        this.icon = icon;
        this.time = time;
        this.iconBitmap = drawableToBitmap(icon);
    }

    protected AppDetail(Parcel in) {
        label = in.readString();
        long timeMillis = in.readLong();
        time = timeMillis == -1 ? null : new Time(timeMillis);
        iconBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        icon = new BitmapDrawable(iconBitmap); // Convert Bitmap back to Drawable
    }

    public static final Creator<AppDetail> CREATOR = new Creator<AppDetail>() {
        @Override
        public AppDetail createFromParcel(Parcel in) {
            return new AppDetail(in);
        }

        @Override
        public AppDetail[] newArray(int size) {
            return new AppDetail[size];
        }
    };

    // Getters and setters
    public CharSequence getLabel() {
        return label;
    }

    public Drawable getIcon() {
        return icon;
    }

    public Time getTime() {
        return time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(label.toString());
        dest.writeLong(time != null ? time.getTime() : -1);
        dest.writeParcelable(iconBitmap, flags);
    }

    // Utility method to convert Drawable to Bitmap
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}
