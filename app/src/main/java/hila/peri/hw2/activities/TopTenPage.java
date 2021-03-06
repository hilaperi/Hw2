package hila.peri.hw2.activities;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import hila.peri.hw2.R;
import hila.peri.hw2.fragments.Fragment_List;
import hila.peri.hw2.fragments.Fragment_Map;
import hila.peri.hw2.logic.Record;
import hila.peri.hw2.logic.TopTenRecords;
import hila.peri.hw2.views.AdapterRecord;
import hila.peri.hw2.views.MyScreenUtils;
import hila.peri.hw2.views.Sound;
import com.google.gson.Gson;

import static hila.peri.hw2.views.MyScreenUtils.Const.TOP_TEN;


public class TopTenPage extends AppCompatActivity {
    private Fragment_Map fragmentMap;
    private TopTenRecords top_ten_RotateLoading;
    private Sound topTenSound;


    private CallBackTable recordCallBack = new CallBackTable() {
        public void showLocation(Record record) {
            fragmentMap.showPlayerLocation(record);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records_page);
        getTopTen();
        findViews();

        AdapterRecord itemAdapter = new AdapterRecord(this,
                R.layout.top_ten_list, top_ten_RotateLoading.getRecords());

        Fragment_List fragmentList = new Fragment_List(itemAdapter, recordCallBack);
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_list, fragmentList).commit();

        fragmentMap = new Fragment_Map();
        getSupportFragmentManager().beginTransaction().add(R.id.record_LAY_map, fragmentMap).commit();
        topTenSound.playSound();

    }

    private void findViews() {
        ImageView record_IMG_background = findViewById(R.id.record_IMG_background);
        MyScreenUtils.updateBackground(MyScreenUtils.Const.BACKGROUND_NAME, this, record_IMG_background);

        topTenSound = new Sound();
        topTenSound.setSound(this, R.raw.top_ten);
    }

    private void getTopTen() {
        top_ten_RotateLoading = new TopTenRecords();
        Gson gson = new Gson();

        String jsonFromMemory = SharedPreferencesSingleton.getInstance().getString(TOP_TEN, "");
        if (!jsonFromMemory.equals("")) {
            top_ten_RotateLoading = gson.fromJson(jsonFromMemory, TopTenRecords.class);
        }
    }


}