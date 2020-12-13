package hila.peri.hw2.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hila.peri.hw2.R;
import hila.peri.hw2.fragments.FragmentList;
import hila.peri.hw2.fragments.FragmentMap;
import hila.peri.hw2.interfaces.RecordCallBack;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.objects.TopTenRecords;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.services.RecordItemAdapter;
import hila.peri.hw2.utils.Constants;
import hila.peri.hw2.utils.MyScreenUtils;
import com.google.gson.Gson;

import static hila.peri.hw2.utils.Constants.TOP_TEN;


public class RecordsPage extends AppCompatActivity {
    private FragmentMap fragmentMap;
    private TopTenRecords topTenRecords;

    private RecordCallBack recordCallBack = new RecordCallBack() {
        @Override
        public void displayLocation(Record record) {
            fragmentMap.showPlayerLocation(record);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_page);

        getTopTen();

        findViews();

        RecordItemAdapter itemAdapter = new RecordItemAdapter(this,
                R.layout.record_item, topTenRecords.getRecords());

        FragmentList fragmentList = new FragmentList(itemAdapter, recordCallBack);
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_list, fragmentList).commit();

        fragmentMap = new FragmentMap();
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_map, fragmentMap).commit();
    }

    private void findViews() {
        ImageView record_IMG_background = findViewById(R.id.record_IMG_background);
        MyScreenUtils.updateBackground(Constants.BACKGROUND_NAME, this, record_IMG_background);
    }

    private void getTopTen() {
        topTenRecords = new TopTenRecords();
        Gson gson = new Gson();

        String jsonFromMemory = MySP.getInstance().getString(TOP_TEN, "");
        if (!jsonFromMemory.equals("")) {
            topTenRecords = gson.fromJson(jsonFromMemory, TopTenRecords.class);
        }
    }
}