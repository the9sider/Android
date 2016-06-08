package com.ktds.cocomo.addressbook.db;

import com.ktds.cocomo.addressbook.vo.AddressBookVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 206-032 on 2016-06-08.
 */
public class AddressBookDB {

    private static Map<String, AddressBookVO> db = new HashMap<String, AddressBookVO>();

    public static void addAddressBook(String index, AddressBookVO addressBook) {
        db.put(index, addressBook);
    }

    public static AddressBookVO getAddressBook(String index) {
        return db.get(index);
    }

    public static List<String> getIndexes() {

        Iterator<String> keys = db.keySet().iterator();
        List<String> keyList = new ArrayList<String>();

        String key = "";
        while( keys.hasNext() ) {
            key = keys.next();
            keyList.add(key);
        }
        return keyList;
    }
}
