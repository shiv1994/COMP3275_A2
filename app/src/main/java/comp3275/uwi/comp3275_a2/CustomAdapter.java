package comp3275.uwi.comp3275_a2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import comp3275.uwi.comp3275_a2.models.LocationObject;

/**
 * Created by Shiva on 4/1/2016.
 */
public class CustomAdapter extends ArrayAdapter<LocationObject> {
    private ArrayList<LocationObject> items = new ArrayList<>();
    private int resource;
    public CustomAdapter(Context context, int resource ,ArrayList<LocationObject> items){
        super(context, 0, items);
        this.resource=resource;
        this.items = items;
    }

    public View getView(int position, View v, ViewGroup p){
        // use the layout we created.
        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        View rowView = inflater.inflate(resource, p, false);

        //get the elements.
        TextView time = (TextView)rowView.findViewById(R.id.timeTxtView);
        TextView lat = (TextView) rowView.findViewById(R.id.latTxtView);
        TextView longt = (TextView) rowView.findViewById(R.id.longTxtView);

        //get item from the correct position.
        LocationObject locObj = items.get(position);

        time.setText(locObj.getTime());
        lat.setText(locObj.getLatitude());
        longt.setText(locObj.getLongtitude());

        return rowView;
    }
}
