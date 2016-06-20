package com.ktds.cocomo.memo.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.ktds.cocomo.memo.vo.MemoVO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 206-032 on 2016-06-20.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public void testDB() {
        SQLiteDatabase db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // String 보다 StringBuffer가 Query 만들기 편하다.
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE MEMO_TABLE ( ");
        sb.append(" _ID INTEGER PRIMARY KEY AUTOINCREMENT, ");
        sb.append(" TITLE TEXT, ");
        sb.append(" DESCRIPTION TEXT, ");
        sb.append(" DATE TEXT, ");
        sb.append(" TIME TEXT ) ");

        // SQLite Database로 쿼리 실행
        db.execSQL(sb.toString());

        Toast.makeText(context, "Table 생성완료", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "버전이 올라갔습니다.", Toast.LENGTH_SHORT).show();
    }

    public void addMemo(MemoVO memo) {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowDate = dateFormat.format(date);

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MEMO_TABLE ( ");
        sb.append(" TITLE, DESCRIPTION, DATE, TIME ) ");
        sb.append(" VALUES ( ?, ?, ?, ? ) ");
        db.execSQL(sb.toString(),
                new Object[]{
                        memo.getTitle(),
                        memo.getDescription(),
                        nowDate, now + ""
                });;
        Toast.makeText(context, "Insert 완료", Toast.LENGTH_SHORT).show();
    }

    public void modifyMemo(MemoVO newMemo) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE MEMO_TABLE ");
        sb.append(" SET TITLE = ?, ");
        sb.append(" DESCRIPTION = ?, ");
        sb.append(" WHERE _ID = ? ");
        db.execSQL(sb.toString(),
                new Object[]{
                        newMemo.getTitle(),
                        newMemo.getDescription(),
                });
        Toast.makeText(context, "Modify 완료", Toast.LENGTH_SHORT).show();
    }

    public List<MemoVO> getAllMemo() {

        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT _ID, TITLE, DESCRIPTION, DATE, TIME FROM MEMO_TABLE ");

        // 읽기 전용 DB 객체를 만든다.
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(sb.toString(), null);

        List memoList = new ArrayList();
        MemoVO memo = null;

        // moveToNext 다음에 데이터가 있으면 true 없으면 false
        while( cursor.moveToNext() ) {
            memo = new MemoVO();
            memo.set_id(cursor.getInt(0));
            memo.setTitle(cursor.getString(1));
            memo.setDescription(cursor.getString(2));
            memo.setDate(cursor.getString(3));
            memo.setTime(Long.parseLong(cursor.getString(4)));

            memoList.add(memo);
        }

        return memoList;
    }

    public void deleteMemoById(int _id) {

        SQLiteDatabase db = getWritableDatabase();
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE ");
        sb.append(" FROM MEMO_TABLE ");
        sb.append(" WHERE _ID = ? ");
        db.execSQL(sb.toString(),
                new Object[]{ _id });

        Toast.makeText(context, "Delete 완료", Toast.LENGTH_SHORT).show();
    }
}
