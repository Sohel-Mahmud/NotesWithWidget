package battlemage.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by battlemage on 4/10/2017.
 */

public class CustomAdapter extends BaseAdapter {

    String TITLE[];
    String sDate[];
    //int icon[];
    LayoutInflater inflatery;

    public CustomAdapter(Context context,String[]TITLE, String[] sDate) {
        this.TITLE = TITLE;
        //this.icon= icon;
        this.sDate = sDate;
        inflatery = (LayoutInflater.from(context));
    }



    @Override
    public int getCount() {
        return TITLE.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view=inflatery.inflate(R.layout.custom_row,parent,false);
        TextView CustomTitle = (TextView)view.findViewById(R.id.CustomTitle);
        TextView CustomDate = (TextView)view.findViewById(R.id.CustomDate);
        //ImageView imgicon = (ImageView)View.findViewById(R.id.imgicon);
        CustomTitle.setText(TITLE[position]);
        CustomDate.setText(getDate(Long.parseLong(String.valueOf(sDate[position])),"dd/MM/yy hh:mm a"));


        return view;
    }
    public  String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
