package br.com.gabrielferreira.listadecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class ShoppingDb extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ShoppingList";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Items";
    private static final String ID = "id";
    private static final String QUANTITY = "quantity";
    private static final String NAME = "name";
    private static final String DONE = "done";
    private static ShoppingDb shoppingDb;

    private ShoppingDb (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ShoppingDb getInstance(Context context){
        if (shoppingDb == null){
            shoppingDb = new ShoppingDb(context.getApplicationContext());
        }
        return shoppingDb;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QUANTITY + "INTEGER, "
                + NAME + "TEXT NOT NULL, "
                + DONE + "TINYINT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addItem (Item item){
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(QUANTITY, item.quantity);
            values.put(NAME, item.name);
            db.insertOrThrow(TABLE_NAME, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("Adicionando ao BD", e.toString());
        } finally {
            db.endTransaction();
        }
    }
}
