package br.com.gabrielferreira.listadecompras;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button endBtn;
    private Button newItemBtn;

    private EditText quantityTxt;
    private EditText itemNameTxt;

    private ListView listView;

    ShoppingDb db;
    Cursor cursor;

    CustomAdapter adapter;

    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        quantityTxt = (EditText) findViewById(R.id.quantity_Input);
        itemNameTxt = (EditText) findViewById(R.id.itemName_Input);

        endBtn = (Button) findViewById(R.id.closeList_btn);
        newItemBtn = (Button) findViewById(R.id.newItem_btn);

        listView = findViewById(R.id.toBuyList);

        db = new ShoppingDb(this);

        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newItem();
            }
        });

        fillList();
       /* List<Item> items = db.getAllItems();
        if (items != null){
            arrayList = new ArrayList<String>();
            adapter = new CustomAdapter(items, this);
        }*/
    }

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
                fillList();
                itemNameTxt.setText("");
                quantityTxt.setText("");
            }
        } else {
            Toast.makeText(this, "Campos foram deixados em branco", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillList(){

        List<Item> itemList = db.getAllItems();

        if (itemList != null){
           ArrayList arrayList = new ArrayList<String>();
           ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, arrayList);

           listView.setAdapter(adapter);
           for(Item i : itemList){
                arrayList.add(i.getQuantity() + " | " + i.getName());
           }
        }
    }

    //Custom Adapter
    public class CustomAdapter extends BaseAdapter{
        private List<Item> items;
        private Activity activity;

        public CustomAdapter(List<Item> items, Activity activity){
            this.items = items;
            this.activity = activity;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            @SuppressLint("ViewHolder")
            View v = activity.getLayoutInflater().inflate(R.layout.row,
                     viewGroup,
                    false);
            Item item = items.get(i);

            TextView quantity = (TextView) v.findViewById(R.id.quantity_text);
            TextView name = (TextView) v.findViewById(R.id.itemName_text);

            name.setText(item.getName());
            quantity.setText(item.getQuantity());

            return v;

        }
    }
}