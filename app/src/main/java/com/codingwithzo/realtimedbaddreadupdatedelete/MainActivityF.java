package com.codingwithzo.realtimedbaddreadupdatedelete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class MainActivityF extends AppCompatActivity {

    DatabaseReference databaseReferencef;

    RecyclerView recyclerViewf;
    ArrayList<FoodItem> foodItemArrayList;
    FoodRecyclerAdapter adapterf;

    Button buttonAddf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainf);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true); // work offline
        Objects.requireNonNull(getSupportActionBar()).hide();

        databaseReferencef = FirebaseDatabase.getInstance().getReference();

        recyclerViewf = findViewById(R.id.recyclerViewf);
        recyclerViewf.setHasFixedSize(true);
        recyclerViewf.setLayoutManager(new LinearLayoutManager(this));

        foodItemArrayList = new ArrayList<>();

        buttonAddf = findViewById(R.id.buttonAddf);
        buttonAddf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewDialogAddf viewDialogAddf = new ViewDialogAddf();
                viewDialogAddf.showDialog(MainActivityF.this);
            }
        });

        readData();
    }

    private void readData() {

        databaseReferencef.child("Food").orderByChild("foodName").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                foodItemArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    FoodItem foodItem = dataSnapshot.getValue(FoodItem.class);
                    foodItemArrayList.add(foodItem);
                }
                adapterf = new FoodRecyclerAdapter(MainActivityF.this, foodItemArrayList);
                recyclerViewf.setAdapter(adapterf);
                adapterf.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewDialogAddf {
        public void showDialog(Context context) {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.activity_dialog_add_new_item);

            EditText textNamef = dialog.findViewById(R.id.textNamef);
            EditText textPricef = dialog.findViewById(R.id.textPricef);
            EditText textDescriptionf= dialog.findViewById(R.id.textDescriptionf);


            Button buttonAddf = dialog.findViewById(R.id.buttonAddf);
            Button buttonCancelf = dialog.findViewById(R.id.buttonCancelf);

            buttonAddf.setText("ADD");
            buttonCancelf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });

            buttonAddf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = "food" + new Date().getTime();
                    String name = textNamef.getText().toString();
                    String price = textPricef.getText().toString();
                    String description = textDescriptionf.getText().toString();

                    if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                        Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                    } else {
                        databaseReferencef.child("Food").child(id).setValue(new FoodItem(id, name, price, description));
                        Toast.makeText(context, "DONE!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                }
            });


            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.show();

        }
    }
}