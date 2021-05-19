package br.com.gabrielferreira.listadecompras;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button clearBtn;
    private Button newItemBtn;

    private EditText quantityTxt;
    private EditText itemNameTxt;

    private ListView listView;

    ShoppingDb db;
    Cursor cursor;

    ArrayList<String> arrayList;
    List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTxt = (EditText) findViewById(R.id.quantity_Input);
        itemNameTxt = (EditText) findViewById(R.id.itemName_Input);

        clearBtn = (Button) findViewById(R.id.closeList_btn);
        newItemBtn = (Button) findViewById(R.id.newItem_btn);

        listView = findViewById(R.id.toBuyList);

        db = new ShoppingDb(this);

        //Criar novo item na lista
        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItem();
            }
        });

        //Limpar a lista
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteAll();
                refreshList();
            }
        });

        //Deletar somente item clicado
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Tem certeza que deseja deletar " + itemList.get(pos).getName() + "?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteItem(itemList.get(pos));
                        refreshList();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        refreshList();
    }

    /**
     * Adiciona um novo item a lista de compras.
     */
    private void newItem() {

        if (!TextUtils.isEmpty(itemNameTxt.getText().toString())) {
            String name = itemNameTxt.getText().toString();
            int quantity;
            try {
                quantity = Integer.parseInt(String.valueOf(quantityTxt.getText()));
            } catch (NumberFormatException e){
                Toast.makeText(this, "Campos foram deixados em branco...", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.addItem(new Item(quantity, name))) {
                refreshList();
                itemNameTxt.setText("");
                quantityTxt.setText("");
            }
        } else {
            Toast.makeText(this, "Campos foram deixados em branco", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Atualiza a lista de compras.
     */
    private void refreshList(){

        itemList = db.getAllItems();

        if (itemList != null){
           ArrayList arrayList = new ArrayList<String>();
           ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    R.layout.row, arrayList);

           listView.setAdapter(adapter);
           for(Item i : itemList){
                arrayList.add(i);
           }
        }
    }
}