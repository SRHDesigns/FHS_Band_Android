package srhdesigns.band;

/**
 * Created by grandslam700 on 1/24/15.
 */

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final ArrayList<String> itemsArrayList;

    public MyAdapter(Context context, ArrayList<String> itemsArrayList) {

        super(context, R.layout.row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Paint paint = new Paint();

        float width = paint.measureText(itemsArrayList.get(position));
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.row, parent, false);
        //rowView.setMinimumHeight((int)width+30);
        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.title);

        labelView.setTextColor(Color.BLACK);
        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position));

        // 5. retrn rowView
        return rowView;
    }
}
