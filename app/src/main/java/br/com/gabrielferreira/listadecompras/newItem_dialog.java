package br.com.gabrielferreira.listadecompras;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class newItem_dialog extends AppCompatDialogFragment {
    private EditText quantityTxt;
    private EditText nameTxt;

    public int quantity;
    public String name;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_item_dialog,null);

        quantityTxt = view.findViewById(R.id.quantityInput);
        nameTxt = view.findViewById(R.id.itemNameInput);

        builder.setView(view)
                .setTitle("Novo item")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Adicionar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Item item = new Item(quantity, name);
                        ShoppingDb db = ShoppingDb.getInstance(getContext());
                        db.addItem(item);
                        Log.v("Add", "Item adicionado");
                    }
                });

        return builder.create();
    }
}
