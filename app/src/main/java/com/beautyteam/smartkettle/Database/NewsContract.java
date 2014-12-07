package com.beautyteam.smartkettle.Database;

import android.provider.BaseColumns;

public final class NewsContract {
    public NewsContract() {}

    public static  abstract class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_NEWS_ID = "news_id";
        public static final String COLUMN_NAME_DEVICE = "device";
        public static final String COLUMN_NAME_EVENT_DATE_BEGIN = "event_date_begin";
        public static final String COLUMN_NAME_SHORT_NEWS = "short_news";
        public static final String COLUMN_NAME_LONG_NEWS = "long_news";
        public static final String COLUMN_NAME_EVENT_DATE = "event_date";

        public static final String COLUMN_NAME_TRANSACTION_STATE = "transaction_state";
    }
}
