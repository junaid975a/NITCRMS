package com.example.module_user_login.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.module_user_login.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;


public class AddItems extends Fragment {
    String[] categoryName = {"Electronics", "Furniture"};
    String[] furnitureItemName = {"Table", "Chair"};
    String[] electronicsItemName = {"Computer", "Projector"};

    String cat_value,item_value;
    Spinner spinner_category, spinner_item;

    DatabaseReference reference;
    Button btn_add;
    MaterialEditText quantity;

    public AddItems() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_items, container, false);
        //code to set spinner value
        spinner_category = (Spinner) view.findViewById(R.id.spinner_category);
        btn_add=view.findViewById(R.id.add_item_btn);
        quantity = view.findViewById(R.id.quantity);
        spinner_item = (Spinner) view.findViewById(R.id.spinner_item);
        // spinner_user_type.setOnItemSelectedListener(this);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getBaseContext(), android.R.layout.simple_spinner_item, categoryName);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_category.setAdapter(adapter1);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cat_value = adapterView.getItemAtPosition(i).toString();
                // Toast.makeText(getContext(), cat_value, Toast.LENGTH_SHORT).show();



                //from here 2nd spinner will start to fetch item data with conditions


                // spinner_user_type.setOnItemSelectedListener(this);
                if (cat_value.equals("Electronics"))
                {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getBaseContext(), android.R.layout.simple_spinner_item, electronicsItemName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_item.setAdapter(adapter2);
                }
                else if(cat_value.equals("Furniture"))
                {
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<>(Objects.requireNonNull(getActivity()).getBaseContext(), android.R.layout.simple_spinner_item, furnitureItemName);
                    adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_item.setAdapter(adapter2);
                }

                spinner_item.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        item_value = adapterView.getItemAtPosition(i).toString();
                        // Toast.makeText(getContext(), item_value, Toast.LENGTH_SHORT).show();


                        btn_add.setOnClickListener(view1 -> {

                            String id;
                            String qtty = quantity.getText().toString();
                            if(qtty.equals("")) {
                                Toast.makeText(getContext(), "Give Quantity to be Added", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                int qnt = Integer.parseInt(qtty);
                                for (int idx = 0; idx < qnt; idx++) {
                                    String uniqueID = UUID.randomUUID().toString();
                                    String lastFour = uniqueID.substring(uniqueID.length() - 4);
                                    id = cat_value.charAt(0) + item_value.substring(0, 1) + lastFour;

                                    reference = FirebaseDatabase.getInstance().getReference();

                                    String finalId = id;
                                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            HashMap<String, Object> userdataMap = new HashMap<>();
                                            userdataMap.put("item", item_value);
                                            userdataMap.put("category", cat_value);
                                            userdataMap.put("id", finalId);
                                            userdataMap.put("alloted", "No");


                                            reference.child("Resources").child(finalId).setValue(userdataMap);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                                Toast.makeText(getContext(), "Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }



            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //end of spinner
        //=======================================




        return view;
    }
}