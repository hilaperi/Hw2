package hila.peri.hw2.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import hila.peri.hw2.R;
import hila.peri.hw2.interfaces.RecordCallBack;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.services.RecordItemAdapter;

public class FragmentList extends Fragment {
    private final RecordItemAdapter itemAdapter;
    private ListView record_LST_TopTen;
    private RecordCallBack recordCallBack;

    public FragmentList(RecordItemAdapter itemAdapter, RecordCallBack recordCallBack) {
        this.itemAdapter = itemAdapter;
        this.recordCallBack = recordCallBack;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        findViews(view);
        initViews();

        return view;
    }

    private void findViews(View view) {
        record_LST_TopTen = (ListView) view.findViewById(R.id.record_LST_TopTen);
    }

    private void initViews() {
        record_LST_TopTen.setAdapter(itemAdapter);
        record_LST_TopTen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                Record record = itemAdapter.getItem(position);
                if (recordCallBack != null) {
                    recordCallBack.displayLocation(record);
                }
            }
        });
    }
}