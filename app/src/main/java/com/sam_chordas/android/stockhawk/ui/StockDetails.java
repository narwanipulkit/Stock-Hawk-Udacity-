package com.sam_chordas.android.stockhawk.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sam_chordas.android.stockhawk.R;
import com.sam_chordas.android.stockhawk.data.QuoteColumns;
import com.sam_chordas.android.stockhawk.data.QuoteProvider;

import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

public class StockDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);
        Bundle b = getIntent().getExtras();
        String symbol = b.getString("symbol");
        TextView title = (TextView) findViewById(R.id.graph_title);
        title.setText(symbol);

        ValueLineChart LineChart = (ValueLineChart) findViewById(R.id.chart);

        ValueLineSeries series = new ValueLineSeries();
        series.setColor(0xFF56B7F1);


        Cursor c = getContentResolver().query(QuoteProvider.Quotes.CONTENT_URI, null, "symbol = ?", new String[]{symbol}, null);
        if (c.getCount() == 1) {
            Toast.makeText(this, R.string.empty_graph, Toast.LENGTH_SHORT).show();
        }
        for (int i = 0; i < c.getCount(); i++) {
            c.moveToPosition(i);
            Log.e("For:" + c.getString(5), String.valueOf(c.getFloat(4)));
            series.addPoint(new ValueLinePoint(c.getString(5), c.getFloat(4)));
        }

        LineChart.addSeries(series);
        LineChart.startAnimation();


    }
}
