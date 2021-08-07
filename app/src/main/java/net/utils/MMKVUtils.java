package net.utils;

import com.tencent.mmkv.MMKV;

import net.HistoryEntity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MMKVUtils {

    public static void saveKeys(String key, String keyValues) {
        Set<String> keys = MMKV.defaultMMKV().decodeStringSet(key);
        if (keys == null) {
            keys = new HashSet<>();
        }
        keys.add(keyValues);
        MMKV.defaultMMKV().encode(key, keys);
    }

    public static ArrayList<HistoryEntity> getAllDatas(String key) {
        ArrayList<HistoryEntity> result = new ArrayList<>();
        Set<String> keySet = MMKV.defaultMMKV().decodeStringSet(key);
        if (keySet != null) {
            for (String item : keySet) {
                HistoryEntity entity = MMKV.defaultMMKV().decodeParcelable(item, HistoryEntity.class);
                result.add(entity);
            }
        }
        return result;
    }
}
