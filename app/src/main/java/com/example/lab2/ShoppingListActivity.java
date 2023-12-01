package com.example.lab2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShoppingListActivity extends AppCompatActivity {

    private EditText addItemEditText;
    private EditText idChangeEditText;
    private EditText changeEditText;
    private EditText idDeleteEditText;

    private Button addButton;
    private Button changeButton;
    private Button deleteButton;
    private Button exitButton;

    private RecyclerView recyclerView;
    private Adapter adapter;
    private List<String> shoppingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.shopping_list_layout);

        addItemEditText = findViewById(R.id.addItemEditText);
        idChangeEditText = findViewById(R.id.idChangeEditText);
        changeEditText = findViewById(R.id.changeEditText);
        idDeleteEditText = findViewById(R.id.idDeleteEditText);

        addButton = findViewById(R.id.addButton);
        changeButton = findViewById(R.id.changeButton);
        deleteButton = findViewById(R.id.deleteButton);
        exitButton = findViewById(R.id.exitButton);

        recyclerView = findViewById(R.id.recyclerView);

        shoppingList = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(shoppingList);
        recyclerView.setAdapter(adapter);

        loadShoppingList();

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addItemToShoppingList();
                saveShoppingList();
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { changeItemInShoppingList(); saveShoppingList();}
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { deleteItemFromShoppingList(); saveShoppingList(); }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); saveShoppingList();}
        });

    }

    private void addItemToShoppingList() {
        String newItem = addItemEditText.getText().toString();
        if (!newItem.isEmpty()) {
            shoppingList.add(newItem);
            adapter.notifyDataSetChanged();
            addItemEditText.setText("");

            Log.d("CreateActivity", "Item added: " + newItem);
        }
    }

    private void changeItemInShoppingList() {
        Log.d("ShoppingListActivity", "changeItemFromShoppingList called");
        try {
            int itemId = Integer.parseInt(idChangeEditText.getText().toString());
            String newItemName = changeEditText.getText().toString();

            if (itemId >= 0 && itemId < shoppingList.size() && !newItemName.isEmpty()) {
                shoppingList.set(itemId, newItemName);
                adapter.notifyDataSetChanged();
                idChangeEditText.setText("");
                changeEditText.setText("");
                Log.d("ShoppingListActivity", "Item changed");
            } else {
                Log.d("ShoppingListActivity", "Invalid item ID or new item name is empty");
            }
        } catch (NumberFormatException e) {
            Log.d("ShoppingListActivity", "Invalid item ID format");
        }
    }

    private void deleteItemFromShoppingList() {
        Log.d("ShoppingListActivity", "deleteItemFromShoppingList called");
        try {
            int itemId = Integer.parseInt(idDeleteEditText.getText().toString());
            if (!shoppingList.isEmpty() && itemId >= 0 && itemId < shoppingList.size()) {
                shoppingList.remove(itemId);
                adapter.notifyDataSetChanged();
                idDeleteEditText.setText("");
                Log.d("ShoppingListActivity", "Item deleted");
            } else {
                Log.d("ShoppingListActivity", "Invalid item ID or list is empty");
            }
        } catch (NumberFormatException e) {
            Log.d("ShoppingListActivity", "Invalid item ID format");
        }
    }

    private void saveShoppingList() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> set = new HashSet<>(shoppingList);
        editor.putStringSet("shoppingList", set);
        editor.apply();
    }

    private void loadShoppingList() {
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        Set<String> set = prefs.getStringSet("shoppingList", new HashSet<>());
        shoppingList.clear();  // Очистите текущий список перед добавлением элементов.
        shoppingList.addAll(set);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("CreateActivity", "onDestroy: Activity destroyed");
    }
}

