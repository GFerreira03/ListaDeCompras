package br.com.gabrielferreira.listadecompras;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button endBtn;
    private Button newItemBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        endBtn = (Button) findViewById(R.id.closeList_btn);
        newItemBtn = (Button) findViewById(R.id.newItem_btn);

        newItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openItemDialog();
            }
        });

    }

    private void openItemDialog() {
        newItem_dialog item_dialog = new newItem_dialog();
        item_dialog.show(getSupportFragmentManager(), "item dialog");
    }
}