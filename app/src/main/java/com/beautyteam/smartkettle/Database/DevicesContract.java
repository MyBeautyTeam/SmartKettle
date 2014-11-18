package com.beautyteam.smartkettle.Database;

import android.provider.BaseColumns;

public  final class DevicesContract {
    public DevicesContract() {}

    public static  abstract class DevicesEntry implements BaseColumns {
        public static final String TABLE_NAME = "devices";
        public static final String COLUMN_NAME_DEVICES_ID = "devices_id";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUMMARY = "summary";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_TRANSACTION_STATE = "transaction_state";
    }
}
