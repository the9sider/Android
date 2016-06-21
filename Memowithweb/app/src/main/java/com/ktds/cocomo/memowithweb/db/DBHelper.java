package com.ktds.cocomo.memowithweb.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ktds.cocomo.memowithweb.vo.MemoVo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 206-032 on 2016-06-21.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    private Context context;

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Query 작성
        StringBuffer sb = new StringBuffer();
        sb.append(" CREATE TABLE MEMO2_TABLE ( ");
        sb.append(" MEMO_ID TEXT PRIMARY KEY, ");
        sb.append(" TITLE TEXT, ");
        sb.append(" DESCRIPTION TEXT, ");
        sb.append(" CRT_DT TEXT ) ");

        // SQLite DB에 입력
        db.execSQL(sb.toString());
        Toast.makeText(context, "테이블 생성", Toast.LENGTH_SHORT).show();
        Log.d("MEMO", "테이블이 생성되었습니다.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("MEMO", "버전이 올라갔습니다.");
    }

    public void addMemo(MemoVo memo) {

        // Query 작성
        StringBuffer sb = new StringBuffer();
        sb.append(" INSERT INTO MEMO2_TABLE ( ");
        sb.append(" MEMO_ID, TITLE, DESCRIPTION, CRT_DT ) ");
        sb.append(" VALUES ( ?, ?, ?, ? ) ");

        // SQLite DB에 입력
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sb.toString(),
                new Object[]{
                        memo.getMemoId(),
                        memo.getTitle(),
                        memo.getDescription(),
                        memo.getCreatedDate()
                });
        Toast.makeText(context, "메모 추가", Toast.LENGTH_SHORT).show();
        Log.d("MEMO", "메모 추가 완료");
    }

    public void modifyMemo(MemoVo memo) {

        // Query 작성
        StringBuffer sb = new StringBuffer();
        sb.append(" UPDATE MEMO2_TABLE ");
        sb.append(" SET TITLE = ?, DESCRIPTION = ? ");
        sb.append(" WHERE MEMO_ID = ? ");

        // SQLite DB에 입력
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sb.toString(),
                new Object[]{
                        memo.getTitle(),
                        memo.getDescription(),
                        memo.getMemoId()
                });
        Toast.makeText(context, "메모 수정", Toast.LENGTH_SHORT).show();
        Log.d("MEMO", "메모 수정 완료");
    }

    public void deleteMemo(String memoId) {

        // Query 작성
        StringBuffer sb = new StringBuffer();
        sb.append(" DELETE ");
        sb.append(" FROM MEMO2_TABLE ");
        sb.append(" WHERE MEMO_ID = ? ");

        // SQLite DB에 입력
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sb.toString(), new Object[]{ memoId });
        Toast.makeText(context, "메모 삭제", Toast.LENGTH_SHORT).show();
        Log.d("MEMO", "메모 삭제 완료");
    }

    public List<MemoVo> getAllMemo() {

        // Query 작성
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT MEMO_ID, TITLE, DESCRIPTION, CRT_DT");
        sb.append(" FROM MEMO2_TABLE ");

        // SQLite DB에 입력
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sb.toString(), null);

        List<MemoVo> memoList = new ArrayList<MemoVo>();
        MemoVo memo = null;

        while( cursor.moveToNext() ) {
            memo = new MemoVo();
            memo.setMemoId(cursor.getString(0));
            memo.setTitle(cursor.getString(1));
            memo.setDescription(cursor.getString(2));
            memo.setCreatedDate(cursor.getString(3));

            memoList.add(memo);
        }
        return memoList;
    }
}
