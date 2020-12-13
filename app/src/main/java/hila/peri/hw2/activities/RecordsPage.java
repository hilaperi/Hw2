package hila.peri.hw2.activities;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import hila.peri.hw2.R;
import hila.peri.hw2.fragments.Fragment_List;
import hila.peri.hw2.fragments.Fragment_Map;
import hila.peri.hw2.objects.Record;
import hila.peri.hw2.objects.TopTenRecords;
import hila.peri.hw2.services.MySP;
import hila.peri.hw2.services.RecordItemAdapter;
import hila.peri.hw2.services.MyScreenUtils;
import hila.peri.hw2.services.Sound;

import com.google.gson.Gson;

import static hila.peri.hw2.services.MyScreenUtils.Constants.TOP_TEN;


public class RecordsPage extends AppCompatActivity {
    private Fragment_Map fragmentMap;
    private TopTenRecords topTenRecords;
    private Sound topTenSound;


    private RecordCallBack recordCallBack = new RecordCallBack() {
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

        Fragment_List fragmentList = new Fragment_List(itemAdapter, recordCallBack);
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_list, fragmentList).commit();

        fragmentMap = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_map, fragmentMap).commit();
        topTenSound.playSound();

    }

    private void findViews() {
        ImageView record_IMG_background = findViewById(R.id.record_IMG_background);
        MyScreenUtils.updateBackground(MyScreenUtils.Constants.BACKGROUND_NAME, this, record_IMG_background);

        topTenSound = new Sound();
        topTenSound.setSound(this, R.raw.top_ten);
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