package com.sam_chordas.android.stockhawk.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.ui.MyStocksActivity;

/**
 * Implementation of App Widget functionality.
 */
public class MyStocks extends AppWidgetProvider {

    Context mContext;


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        mContext=context;
        for (int appWidgetId : appWidgetIds) {


            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_stocks);
            Intent widgetIntent=new Intent(context,MyStocksActivity.class);
            PendingIntent pi=PendingIntent.getActivity(context,0,widgetIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            views.setPendingIntentTemplate(R.id.listView,pi);


            Intent i=new Intent(context,WidgetService.class);
            i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,appWidgetId);
            i.setData(Uri.parse(i.toUri(Intent.URI_INTENT_SCHEME)));
            views.setRemoteAdapter(R.id.listView,i);


            views.setEmptyView(R.id.listView,R.id.emptyList);


            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created


    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


}

