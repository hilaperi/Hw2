package hila.peri.hw2.views;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import hila.peri.hw2.R;
import hila.peri.hw2.logic.Record;
import java.util.ArrayList;

public class AdapterRecord extends ArrayAdapter<Record> {
    private final Activity myContext;
    private final ArrayList<Record> records;

    public AdapterRecord(Context context, int textViewResourceId,
                         ArrayList<Record> records) {
        super(context, textViewResourceId, records);
        myContext = (Activity) context;
        this.records = records;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = myContext.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.top_ten_list, null);

        TextView postTitleView = (TextView) rowView
                .findViewById(R.id.topTen_TXT_name);

        postTitleView.setText(records.get(position).getName());

        TextView postScoreView = (TextView) rowView
                .findViewById(R.id.topTen_TXT_scorePlay);

        postScoreView.setText(records.get(position).getScore() + "");

        TextView postDateView = (TextView) rowView
                .findViewById(R.id.topTen_TXT_date);

        postDateView.setText(records.get(position).getDate() + "");
        return rowView;
    }
}