package com.sam_chordas.android.stockhawk.widget;

import android.app.Activity;
import android.app.LauncherActivity;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import java.util.ArrayList;

/**
 * Created by pulkitnarwani on 29/08/16.
 */
public class ListProvider implements RemoteViewsService.RemoteViewsFactory {


    private Context context = null;
    private int appWidgetId;
    Cursor c;

    public ListProvider(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);

        c = context.getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI,
                new String[]{QuoteColumns.SYMBOL, QuoteColumns.BIDPRICE, QuoteColumns.CHANGE}, "is_current = ? ", new String[]{"1"}, null);


    }


    @Override
    public void onCreate() {


    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {

        return c.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        RemoteViews remoteView = new RemoteViews(
                context.getPackageName(), R.layout.list_item_quote);
        Intent details = new Intent();

        if (c.getCount() != 0) {
            c.moveToPosition(position);

            remoteView.setTextViewText(R.id.stock_symbol, c.getString(0));
            remoteView.setTextViewText(R.id.bid_price, c.getString(1));
            remoteView.setTextViewText(R.id.change, c.getString(2));
            details.putExtra("symbol", c.getString(0));

        }


        details.putExtra("com.sam._chordas.android.stockhawk.EXTRA_ITEM", position);
        details.putExtra("position", position);

        remoteView.setOnClickFillInIntent(R.id.list_row, details);


        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
