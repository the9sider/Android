package com.ktds.cocomo.mydatabase.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ktds.cocomo.mydatabase.vo.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-032 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper{

    public static final int DB_VERSION = 2;

    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    /**
     * Database가 존재하지 않을 때, 딱 한번 실행된다.
     * DB를 만드는 역할을 한다.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE TEST_TABLE ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" NAME TEXT, ");
        sb.append(" AGE INTEGER, ");
        sb.append(" PHONE TEXT ) ");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());

        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    /**
     * Application의 버전이 올라가서
     * Table 구조가 변경되었을 때 실행된다.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if( oldVersion == 1 && newVersion == 2 ) {
            StringBuffer sb = new StringBuffer();
            sb.append(" ALTER TABLE TEST_TABLE ADD ADDRESS TEXT ");

            // 쿼리 실행
            db.execSQL(sb.toString());
        }

        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void testDB() {
       SQLiteDatabase db = getReadableDatabase();
    }

    public void addPerson(Person person) {

        // 1. 쓸 수 있는 DB 객체를 가져온다.
        SQLiteDatabase db = getWritableDatabase();

        // 2. Person Data를 Insert한다.
        // _id는 자동으로 증가하기 때문에 넣지 않습니다.
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO TEST_TABLE ( ");
        sb.append(" NAME, AGE, PHONE, ADDRESS ) ");
        sb.append(" VALUES ( ?, ?, ?, ? ) ");
        db.execSQL(sb.toString(),
                new Object[]{
                        person.getName(),
                        person.getAge(),
                        person.getPhone(),
                        person.getAddress()});;
        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show();
    }

    public List<Person> getAllPersonData() {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, NAME, AGE, PHONE, ADDRESS FROM TEST_TABLE ");

        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<Person> people = new ArrayList<Person>();
        Person person = null;

        // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while( cursor.moveToNext() ) {
            person = new Person();
            person.set_id(cursor.getInt(0));
            person.setName(cursor.getString(1));
            person.setAge(cursor.getInt(2));
            person.setPhone(cursor.getString(3));
            person.setAddress(cursor.getString(4));

            people.add(person);
        }

        return people;
    }

    public Person getPersonById(int _id) {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT NAME, AGE, PHONE, ADDRESS FROM TEST_TABLE WHERE _ID = ? ");

        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), new String[]{_id + ""});

        Person person = new Person();
        if(cursor.moveToNext()) {
            person.setName(cursor.getString(0));
            person.setAge(cursor.getInt(1));
            person.setPhone(cursor.getString(2));
            person.setAddress(cursor.getString(3));
        }
        return person;
    }
}
