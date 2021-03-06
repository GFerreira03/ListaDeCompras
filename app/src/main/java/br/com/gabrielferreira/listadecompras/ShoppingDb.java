package br.com.gabrielferreira.listadecompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ShoppingDb extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "ShoppingList";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Items";
    private static final String ID = "id";
    private static final String QUANTITY = "quantity";
    private static final String NAME = "name";
    private static ShoppingDb shoppingDb;

    public ShoppingDb (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Armazena todos os itens do MYSQLite em uma variável.
     * @return Uma variável com os itens armazenados.
     */
    public List<Item> getAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor items = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Item> itemList = new ArrayList<>();
        try {
            if (items.moveToFirst()){
                do {
                    Item item = new Item (items.getInt(items.getColumnIndex(QUANTITY)),
                                          items.getString(items.getColumnIndex(NAME)));
                    itemList.add(item);
                } while (items.moveToNext());
            }
        } catch (Exception e){
            Log.d("Search error", e.toString());
        } finally {
            items.close();
        }
        return itemList;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + ID + " INTEGER PRIMARY KEY, "
                + QUANTITY + " TEXT NOT NULL, "
                + NAME + " TEXT NOT NULL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * Adiciona um item a lista
     * @param item Item a ser adicionado
     * @return True caso tenha conseguido adicionar, false caso tenha falhado.
     */
    public boolean addItem (Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, item.getName());
        values.put(QUANTITY, item.getQuantity());
        values.put(ID, item.getHash());
        long result = db.insert(TABLE_NAME, null, values);

        if (result == -1)
            return false;

        return true;
    }

    /**
     * Deleta um item da lista e banco de dados.
     * @param item Item a ser deletado.
     */
    public void deleteItem(Item item){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "ID = ?", new String[]{String.valueOf(item.getHash())});
        getAllItems();
    }

    /**
     * Deleta todos os itens da tabela.
     */
    public void deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        getAllItems();
    }
}
