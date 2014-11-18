package com.beautyteam.smartkettle.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class SmartContentProvider extends ContentProvider {
    private static final String LOG_TAG = "ContentProviderLogs";
    private static final String PROTOCOL = "content://";
    private static final String AUTHORITY = "com.beautyteam.smartkettle.Database.smartkettle";
    private static final String SEPARATOR = "/";

    private static final String NEWS_PATH = "news";
    private static final String DEVICE_PATH = "devices";

    public static final Uri NEWS_CONTENT_URI = Uri.parse(PROTOCOL + AUTHORITY + SEPARATOR + NEWS_PATH);
    public static final Uri DEVICE_CONTENT_URI = Uri.parse(PROTOCOL + AUTHORITY + SEPARATOR + DEVICE_PATH);

    private static final String NEWS_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + NEWS_PATH;
    private static final String NEWS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + NEWS_PATH;
    private static final String DEVICE_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + DEVICE_PATH;
    private static final String DEVICE_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + DEVICE_PATH;

    private static final int URI_NEWS = 0;
    private static final int URI_NEWS_ID = 1;

    private static final int URI_DEVICE = 2;
    private static final int URI_DEVICE_ID = 3;

    private static final String GRILLE = "/#";

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, NEWS_PATH, URI_NEWS);
        uriMatcher.addURI(AUTHORITY, NEWS_PATH + GRILLE, URI_NEWS_ID);
        uriMatcher.addURI(AUTHORITY, DEVICE_PATH, URI_DEVICE);
        uriMatcher.addURI(AUTHORITY, DEVICE_PATH + GRILLE, URI_DEVICE_ID);
    }

    SmartDbHelper dbHelper;
    SQLiteDatabase db;

    public boolean onCreate() {
        Log.d(LOG_TAG, "onCreate");
        dbHelper = new SmartDbHelper(getContext());
        return true;
    }
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Log.d(LOG_TAG, "query, " + uri.toString());
        String id;
        String table;
        switch (uriMatcher.match(uri)) {
            case URI_NEWS: // общий Uri
                Log.d(LOG_TAG, "URI_NEWS");
                table = NewsContract.NewsEntry.TABLE_NAME;
                break;
            case URI_NEWS_ID: // Uri с ID
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_NEWS_ID, " + id);
                table = NewsContract.NewsEntry.TABLE_NAME;
                // добавляем ID к условию выборки
                if (TextUtils.isEmpty(selection)) {
                    selection = NewsContract.NewsEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + NewsContract.NewsEntry._ID + " = " + id;
                }
                break;
            case URI_DEVICE:
                Log.d(LOG_TAG, "URI_DEVICE");
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = DevicesContract.DevicesEntry.COLUMN_NAME_TITLE + " ASC";
                }
                break;
            case URI_DEVICE_ID:
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_DEVICE_ID");
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = NewsContract.NewsEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + NewsContract.NewsEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query(
                table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
        // просим ContentResolver уведомлять этот курсор
        // об изменениях данных в NEWS_CONTENT_URI
        cursor.setNotificationUri(getContext().getContentResolver(),
                NEWS_CONTENT_URI);
        cursor.setNotificationUri(getContext().getContentResolver(),
                DEVICE_CONTENT_URI);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues values) {
        String table;
        Log.d(LOG_TAG, "insert, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                table = NewsContract.NewsEntry.TABLE_NAME;
                break;
            case URI_DEVICE:
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        long newRowID = db.insert(
                table,
                null,
                values);
        Uri resultUri = ContentUris.withAppendedId(uri, newRowID);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.d(LOG_TAG, "delete, " + uri.toString());
        String id;
        String table;
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                Log.d(LOG_TAG, "URI_NEWS");
                table = NewsContract.NewsEntry.TABLE_NAME;
                break;
            case URI_NEWS_ID:
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_NEWS_ID, " + id);
                table = NewsContract.NewsEntry.TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = NewsContract.NewsEntry._ID + " = " + id; // ??? selectionArgs
                } else {
                    selection = selection + " AND " + NewsContract.NewsEntry._ID + " = " + id;
                }
                break;
            case URI_DEVICE:
                Log.d(LOG_TAG, "URI_DEVICE");
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                break;
            case URI_DEVICE_ID:
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_DEVICE_ID, " + id);
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = DevicesContract.DevicesEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + NewsContract.NewsEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int count = db.delete(
                table,
                selection,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        String id;
        String table;
        Log.d(LOG_TAG, "update, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                Log.d(LOG_TAG, "URI_NEWS");
                table = NewsContract.NewsEntry.TABLE_NAME;
                break;
            case URI_NEWS_ID:
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_NEWS_ID, " + id);
                table = NewsContract.NewsEntry.TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = NewsContract.NewsEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + NewsContract.NewsEntry._ID + " = " + id;
                }
                break;
            case URI_DEVICE:
                Log.d(LOG_TAG, "URI_DEVICE");
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                break;
            case URI_DEVICE_ID:
                id = uri.getLastPathSegment();
                Log.d(LOG_TAG, "URI_DEVICE_ID, " + id);
                table = DevicesContract.DevicesEntry.TABLE_NAME;
                if (TextUtils.isEmpty(selection)) {
                    selection = DevicesContract.DevicesEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + DevicesContract.DevicesEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        db = dbHelper.getWritableDatabase();
        int count = db.update(
                table,
                values,
                selection,
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
    public String getType(Uri uri) {
        Log.d(LOG_TAG, "getType, " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case URI_NEWS:
                return NEWS_CONTENT_TYPE;
            case URI_NEWS_ID:
                return NEWS_CONTENT_ITEM_TYPE;
            case URI_DEVICE:
                return DEVICE_CONTENT_TYPE;
            case URI_DEVICE_ID:
                return DEVICE_CONTENT_ITEM_TYPE;
        }
        return null;
    }
}
