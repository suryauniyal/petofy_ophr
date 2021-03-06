package com.cynoteck.petofyOPHR.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cynoteck.petofyOPHR.R;
import com.cynoteck.petofyOPHR.adapters.SearchAdapter;
import com.cynoteck.petofyOPHR.api.ApiClient;
import com.cynoteck.petofyOPHR.api.ApiResponse;
import com.cynoteck.petofyOPHR.api.ApiService;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetDataParams;
import com.cynoteck.petofyOPHR.params.petReportsRequest.PetDataRequest;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.getPetListResponse.GetPetListResponse;
import com.cynoteck.petofyOPHR.response.getPetReportsResponse.getPetListResponse.PetList;
import com.cynoteck.petofyOPHR.utils.Config;
import com.cynoteck.petofyOPHR.utils.Methods;
import com.cynoteck.petofyOPHR.utils.SearchInterface;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements TextWatcher, ApiResponse, SearchInterface {
    EditText searchpet;
    MaterialCardView back_arrow_CV;
    ArrayList<PetList> profileList = new ArrayList<>();
    RecyclerView register_pet_RV;
    com.cynoteck.petofyOPHR.adapters.SearchAdapter SearchAdapter;
    Methods methods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        methods = new Methods(this);
        searchpet =  findViewById(R.id.search_pet);
        back_arrow_CV =  findViewById(R.id.back_arrow_CV);
        register_pet_RV = findViewById(R.id.register_pet_RV);
        back_arrow_CV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        searchpet.requestFocus();
        searchpet.addTextChangedListener(new TextWatcher() {
            long lastChange = 0;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() > 1) {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            if (System.currentTimeMillis() - lastChange >= 300) {
                                //send request
                                if (methods.isInternetOn()) {
                                    register_pet_RV.setVisibility(View.GONE);
                                    if (!searchpet.getText().toString().equals("")) {
                                        petSearchDependsOnPrefix(searchpet.getText().toString());
                                    }
                                } else {
                                    methods.DialogInternet();
                                }
                            }
                        }
                    }, 300);
                    lastChange = System.currentTimeMillis();

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = s.toString();
//                petSearchDependsOnPrefix(value);
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void petSearchDependsOnPrefix(String prefix) {
        PetDataParams getPetDataParams = new PetDataParams();
        getPetDataParams.setPageNumber(0);//0
        getPetDataParams.setPageSize(10);//0
        getPetDataParams.setSearch_Data(prefix);
        PetDataRequest getPetDataRequest = new PetDataRequest();
        getPetDataRequest.setData(getPetDataParams);

        ApiService<GetPetListResponse> service = new ApiService<>();
        service.get(SearchActivity.this, ApiClient.getApiInterface().getPetList(Config.token, getPetDataRequest), "GetPetListBySearch");
        Log.e("DATALOG", "check1=> " + getPetDataRequest);
    }

    @Override
    public void onResponse(Response arg0, String key) {
        switch (key) {
            case "GetPetListBySearch":
                try {
                    GetPetListResponse getPetListResponse = (GetPetListResponse) arg0.body();
                    int responseCode = Integer.parseInt(getPetListResponse.getResponse().getResponseCode());
                    profileList.clear();
                    if (responseCode == 109) {
                        if (getPetListResponse.getData().getPetList().isEmpty()) {
                            Toast.makeText(this, "Pet Not Exist !", Toast.LENGTH_SHORT).show();
                        }
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
                        register_pet_RV.setLayoutManager(linearLayoutManager);
                        if (getPetListResponse.getData().getPetList().size() > 0) {

                            for (int i = 0; i < getPetListResponse.getData().getPetList().size(); i++) {
                                PetList petList = new PetList();
                                petList.setPetUniqueId(getPetListResponse.getData().getPetList().get(i).getPetUniqueId());
                                petList.setDateOfBirth(getPetListResponse.getData().getPetList().get(i).getDateOfBirth());
                                petList.setPetName(getPetListResponse.getData().getPetList().get(i).getPetName());
                                petList.setPetSex(getPetListResponse.getData().getPetList().get(i).getPetSex());
                                petList.setPetParentName(getPetListResponse.getData().getPetList().get(i).getPetParentName());
                                petList.setPetProfileImageUrl(getPetListResponse.getData().getPetList().get(i).getPetProfileImageUrl());
                                petList.setEncryptedId(getPetListResponse.getData().getPetList().get(i).getEncryptedId());
                                petList.setId(getPetListResponse.getData().getPetList().get(i).getId());
                                petList.setPetAge(getPetListResponse.getData().getPetList().get(i).getPetAge());
                                petList.setLastVisitEncryptedId(getPetListResponse.getData().getPetList().get(i).getLastVisitEncryptedId());

                                profileList.add(petList);
                            }
                            register_pet_RV.setVisibility(View.VISIBLE);
                            SearchAdapter = new SearchAdapter(SearchActivity.this, profileList, this);
                            register_pet_RV.setAdapter(SearchAdapter);
                            SearchAdapter.notifyDataSetChanged();

                        } else {

                            Log.e("No_DATA", "NO_DATA");
//                    Toast.makeText(SearchActivity.this, "Data Not found", Toast.LENGTH_SHORT).show();

                        }


                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onError(Throwable t, String key) {

    }

    @Override
    public void onViewDetailsClick(int position) {

        Intent petDetailsIntent = new Intent(SearchActivity.this, PetDetailsActivity.class);
        Bundle data = new Bundle();
        data.putString("pet_id", profileList.get(position).getId());
        data.putString("pet_name", profileList.get(position).getPetName());
        data.putString("pet_parent", profileList.get(position).getPetParentName());
        data.putString("pet_sex", profileList.get(position).getPetSex());
        data.putString("pet_age", profileList.get(position).getPetAge());
        data.putString("pet_unique_id", profileList.get(position).getPetUniqueId());
        data.putString("pet_DOB", profileList.get(position).getDateOfBirth());
        data.putString("pet_encrypted_id", profileList.get(position).getEncryptedId());
        data.putString("pet_cat_id", profileList.get(position).getPetCategoryId());
        data.putString("lastVisitEncryptedId", profileList.get(position).getLastVisitEncryptedId());
        petDetailsIntent.putExtras(data);
        startActivity(petDetailsIntent);

    }
}