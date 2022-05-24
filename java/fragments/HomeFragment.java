package com.example.assignment_workshop_app.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.assignment_workshop_app.R;
import com.example.assignment_workshop_app.activities.ShowAllActivity;
import com.example.assignment_workshop_app.adapters.CategoryAdapter;
import com.example.assignment_workshop_app.adapters.NewWorkshopAdapter;
import com.example.assignment_workshop_app.models.CategoryModel;
import com.example.assignment_workshop_app.models.NewWorkshopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView catShowAll, newWorkshopShowAll;

    LinearLayout linearLayout;
    ProgressDialog progressDialog;

    RecyclerView catRecyclerview, newWorkshopRecyclerview;
    //Category recyclerview
    CategoryAdapter categoryAdapter;
    List<CategoryModel> categoryModelList;

    //New Workshop Recyclerview
    NewWorkshopAdapter newWorkshopAdapter;
    List<NewWorkshopModel> newWorkshopModelList;

    //Firebase Store
    FirebaseFirestore db;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getActivity());
        catRecyclerview = root.findViewById(R.id.rec_category);
        newWorkshopRecyclerview = root.findViewById(R.id.new_product_rec);

        catShowAll = root.findViewById(R.id.category_see_all);
        newWorkshopShowAll = root.findViewById(R.id.newProducts_see_all);

        catShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });
        
        // Show All Button
        newWorkshopShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllActivity.class);
                startActivity(intent);
            }
        });

        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);

        //image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.banner1,"[String]", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner2,"[String]", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.banner3,"[String]", ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels);
        
        //Pop up message for sign in and waiting
        progressDialog.setTitle("Welcome To My App");
        progressDialog.setMessage("please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        //Category
        catRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryModelList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryModelList);
        catRecyclerview.setAdapter(categoryAdapter);

        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                CategoryModel categoryModel = document.toObject(CategoryModel.class);
                                categoryModelList.add(categoryModel);
                                categoryAdapter.notifyDataSetChanged();
                                linearLayout.setVisibility(View.VISIBLE);
                                progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //New Workshops
        newWorkshopRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        newWorkshopModelList = new ArrayList<>();
        newWorkshopAdapter = new NewWorkshopAdapter(getContext(),newWorkshopModelList);
        newWorkshopRecyclerview.setAdapter(newWorkshopAdapter);

        db.collection("NewWorkshop")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewWorkshopModel newWorkshopModel = document.toObject(NewWorkshopModel.class);
                                newWorkshopModelList.add(newWorkshopModel);
                                newWorkshopAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        return root;
    }
}
