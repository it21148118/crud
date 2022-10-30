package com.codingwithzo.realtimedbaddreadupdatedelete;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

    public class FoodRecyclerAdapter extends RecyclerView.Adapter<FoodRecyclerAdapter.ViewHolder> {

        Context context;
        ArrayList<FoodItem> foodItemArrayList;
        DatabaseReference databaseReferencef;

        public FoodRecyclerAdapter(Context context, ArrayList<FoodItem> foodItemArrayList) {
            this.context = context;
            this.foodItemArrayList = foodItemArrayList;
            databaseReferencef = FirebaseDatabase.getInstance().getReference();
        }

        @NonNull
        @Override
        public FoodRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View view = layoutInflater.inflate(R.layout.food_item, parent, false);
            return new FoodRecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FoodRecyclerAdapter.ViewHolder holder, int position) {

            FoodItem foodItem = foodItemArrayList.get(position);

            holder.textNamef.setText("Name : " + foodItem.getFoodName());
            holder.textPricef.setText("Price : " + foodItem.getFoodPrice());
            holder.textDescriptionf.setText("Description : " +foodItem.getFoodDescription());

            holder.buttonUpdatef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodRecyclerAdapter.ViewDialogUpdatef viewDialogUpdate = new FoodRecyclerAdapter.ViewDialogUpdatef();
                    viewDialogUpdate.showDialog(context, foodItem.getFoodID(), foodItem.getFoodName(), foodItem.getFoodPrice(), foodItem.getFoodDescription());
                }
            });

            holder.buttonDeletef.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FoodRecyclerAdapter.ViewDialogConfirmDeletef viewDialogConfirmDelete = new FoodRecyclerAdapter.ViewDialogConfirmDeletef();
                    viewDialogConfirmDelete.showDialog(context, foodItem.getFoodID());
                }
            });

        }

        @Override
        public int getItemCount() {
            return foodItemArrayList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            TextView textNamef;
            TextView textPricef;
            TextView textDescriptionf;

            Button buttonDeletef;
            Button buttonUpdatef;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                textNamef = itemView.findViewById(R.id.textNamef);
                textPricef = itemView.findViewById(R.id.textPricef);
                textDescriptionf = itemView.findViewById(R.id.textDescriptionf);

                buttonDeletef = itemView.findViewById(R.id.buttonDeletef);
                buttonUpdatef = itemView.findViewById(R.id.buttonUpdatef);
            }
        }

        public class ViewDialogUpdatef {
            public void showDialog(Context context, String id, String name, String price, String description) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.activity_dialog_add_new_item);

                EditText textNamef = dialog.findViewById(R.id.textNamef);
                EditText textPricef = dialog.findViewById(R.id.textPricef);
                EditText textDescriptionf = dialog.findViewById(R.id.textDescriptionf);

                textNamef.setText(name);
                textPricef.setText(price);
                textDescriptionf.setText(description);


                Button buttonUpdatef = dialog.findViewById(R.id.buttonAddf);
                Button buttonCancelf = dialog.findViewById(R.id.buttonCancelf);

                buttonUpdatef.setText("UPDATE");

                buttonCancelf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                buttonUpdatef.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String newName = textNamef.getText().toString();
                        String newPrice = textPricef.getText().toString();
                        String newDescription = textDescriptionf.getText().toString();

                        if (name.isEmpty() || price.isEmpty() || description.isEmpty()) {
                            Toast.makeText(context, "Please Enter All data...", Toast.LENGTH_SHORT).show();
                        } else {

                            if (newName.equals(name) && newPrice.equals(price) && newDescription.equals(description)) {
                                Toast.makeText(context, "you don't change anything", Toast.LENGTH_SHORT).show();
                            } else {
                                databaseReferencef.child("USERS").child(id).setValue(new FoodItem(id, newName, newPrice, newDescription));
                                Toast.makeText(context, "Food Updated successfully!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }


                        }
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        }


        public class ViewDialogConfirmDeletef {
            public void showDialog(Context context, String id) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.view_dialog_confirm_delete);

                Button buttonDelete = dialog.findViewById(R.id.buttonDeletef);
                Button buttonCancel = dialog.findViewById(R.id.buttonCancelf);

                buttonCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        databaseReferencef.child("Food").child(id).removeValue();
                        Toast.makeText(context, "Food Deleted successfully!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        }
    }

