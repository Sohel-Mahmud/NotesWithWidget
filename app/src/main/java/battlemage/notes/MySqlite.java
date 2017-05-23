package battlemage.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by battlemage on 4/10/2017.
 */

public class MySqlite extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "mydb.db";
    private static final String TABLE_NAME = "mytable";


    private static final String COLUMN1 = "ID";
    private static final String COLUMN2 = "TITLE";
    private static final String COLUMN3 = "NOTE";
    private static final String COLUMN4 = "DATE";


    public MySqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY NOT NULL, TITLE TEXT, NOTE TEXT, DATE LONG)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS " + TABLE_NAME); //if previous db found, delete it exists
        onCreate(db);
    }

    public boolean addToTable(String ID,String TITLE, String NOTE,Long DATE) {

        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN1, ID);
        contentValues.put(COLUMN2, TITLE);
        contentValues.put(COLUMN3, NOTE);
        contentValues.put(COLUMN4, java.lang.System.currentTimeMillis());

        long chk = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        //when data is not inserted , insert() return -1
        if (chk == -1) {
            return false;
        } else
            return true;
    }

    public Cursor display() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        /*Cursor result;
        result = sqLiteDatabase.rawQuery("SELECT *FROM " + TABLE_NAME+" ORDER BY DATE DESC", null);*/
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN1, COLUMN2, COLUMN3, COLUMN4},null,null, null,null,COLUMN4+ " DESC");

        /*java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
        String datedata= dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(COLUMN4))).getTime());*/
        return cursor;
    }
    public boolean updateData(String ID, String TITLE, String NOTE) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN2, TITLE);
        contentValues.put(COLUMN3, NOTE);

        int chk = sqLiteDatabase.update(TABLE_NAME, contentValues, "ID = ?", new String[]{(ID)});

        if (chk > 0) return true;
        else return false;
    }

    public boolean deleteData(String ID) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        int chk = sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{(ID)});

        if (chk > 0) return true;
        else return false;

    }

}
