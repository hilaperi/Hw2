package hila.peri.hw2.logic;

import java.util.ArrayList;
import java.util.Collections;
@SuppressWarnings("unchecked")

public class TopTenRecords {
    //    public TopTenRecords(ArrayList<Record> records) {
//        this.records = records;
//    }
    private ArrayList<Record> records;

    public TopTenRecords() {
        records = new ArrayList<>();
    }


    public ArrayList<Record> getRecords() {
        return records;
    }

//    public TopTenRecords setRecords(ArrayList<Record> records) {
//        this.records = records;
//        return this;
//    }

    public boolean addRecord(Record record) {
        boolean isExist = false;
        if (records.size() < 10) {
            records.add(record);
            isExist = true;
        } else if (records.get(records.size() - 1).getScore() >= record.getScore()) {
            if (records.size() == 10) {
                records.remove(9);
            }
            records.add(record);
            isExist = true;
        }

        if (isExist) {
            Collections.sort(records);
        }
        return isExist;
    }
}
