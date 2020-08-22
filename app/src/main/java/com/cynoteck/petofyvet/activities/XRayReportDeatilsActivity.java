package com.cynoteck.petofyvet.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.api.ApiClient;
import com.cynoteck.petofyvet.api.ApiResponse;
import com.cynoteck.petofyvet.api.ApiService;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVisitDetailsRequest;
import com.cynoteck.petofyvet.params.petReportsRequest.PetClinicVistsDetailsParams;
import com.cynoteck.petofyvet.response.getPetReportsResponse.AddUpdateDeleteClinicVisitResponse;
import com.cynoteck.petofyvet.response.getXRayReports.getXRayReportDetailsResponse.GetXRayReportDeatilsResponse;
import com.cynoteck.petofyvet.utils.Config;

import retrofit2.Response;

public class XRayReportDeatilsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    TextView nature_of_visit_textView,test_date_textView,Result_textView,recommended_follow_up_textView,recommended_follow_up_date_textView;
    Button deleteReport_BT;
    ImageView back_arrow_IV;
    TextView pet_name_TV,pet_sex_TV,pet_id_TV,pet_owner_name_TV,pet_owner_phone_no_TV;
    String pet_unique_id, pet_name,pet_sex, pet_owner_name,pet_owner_contact,pet_id ,report_type_id,type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_ray_report_deatils);

        getIntentData();
        init();
        setdataInFields();
        getXRayReportDeatilsDeatils();
    }



    private void setdataInFields() {
        pet_name_TV.setText(pet_name);
        pet_sex_TV.setText(pet_sex);
        pet_id_TV.setText(pet_unique_id);
        pet_owner_name_TV.setText(pet_owner_name);
        pet_owner_phone_no_TV.setText(pet_owner_contact);
    }

    private void init() {

        nature_of_visit_textView = findViewById(R.id.nature_of_visit_textView);
        test_date_textView = findViewById(R.id.test_date_textView);
        Result_textView = findViewById(R.id.Result_textView);
        recommended_follow_up_textView = findViewById(R.id.recommended_follow_up_textView);
        recommended_follow_up_date_textView = findViewById(R.id.lab_phone_textView);

        pet_name_TV = findViewById(R.id.pet_name_TV);
        pet_sex_TV = findViewById(R.id.pet_sex_TV);
        pet_id_TV = findViewById(R.id.pet_id_TV);
        pet_owner_name_TV = findViewById(R.id.pet_owner_name_TV);
        pet_owner_phone_no_TV = findViewById(R.id.pet_owner_phone_no_TV);
        deleteReport_BT = findViewById(R.id.deleteReport_BT);
        back_arrow_IV = findViewById(R.id.back_arrow_IV);

        back_arrow_IV.setOnClickListener(this);
        deleteReport_BT.setOnClickListener(this);
    }

    private void getIntentData() {
        Intent extras = getIntent();
        pet_id = extras.getExtras().getString("pet_id");
        pet_owner_contact = extras.getExtras().getString("pet_owner_contact");
        pet_owner_name = extras.getExtras().getString("pet_owner_name");
        pet_sex = extras.getExtras().getString("pet_sex");
        pet_name = extras.getExtras().getString("pet_name");
        pet_unique_id = extras.getExtras().getString("pet_unique_id");
        report_type_id=extras.getExtras().getString("id");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.deleteReport_BT:
                deleteXRay();

                break;
        }

    }
    private void getXRayReportDeatilsDeatils() {

        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(report_type_id.substring(0,report_type_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("GetXRaykDetails",petClinicVisitDetailsRequest.toString());

        ApiService<GetXRayReportDeatilsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getTestXRayDetails(Config.token,petClinicVisitDetailsRequest), "GetXRaykDetails");


    }


    private void deleteXRay() {
        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(report_type_id.substring(0,report_type_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("DeleteTestXRay",petClinicVisitDetailsRequest.toString());
        ApiService<AddUpdateDeleteClinicVisitResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().deleteTestXRay(Config.token,petClinicVisitDetailsRequest), "DeleteTestXRay");

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "GetXRaykDetails":
                try {
                    Log.d("GetXRaykDetails",response.body().toString());
                    GetXRayReportDeatilsResponse getXRayReportDeatilsResponse  = (GetXRayReportDeatilsResponse) response.body();
                    int responseCode = Integer.parseInt(getXRayReportDeatilsResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        nature_of_visit_textView.setText(getXRayReportDeatilsResponse.getData().getTypeOfTest().getTestType());
                        test_date_textView.setText(getXRayReportDeatilsResponse.getData().getDateTested());
                        Result_textView.setText(getXRayReportDeatilsResponse.getData().getResults());
                        recommended_follow_up_textView.setText("");
                        recommended_follow_up_date_textView.setText(getXRayReportDeatilsResponse.getData().getFollowUpDate());
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;

            case "DeleteTestXRay":
                try {
                    Log.d("DeleteClinicVisit",response.body().toString());
                    AddUpdateDeleteClinicVisitResponse addUpdateDeleteClinicVisitResponse = (AddUpdateDeleteClinicVisitResponse) response.body();
                    int responseCode = Integer.parseInt(addUpdateDeleteClinicVisitResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        Config.type = "XRay";
                        onBackPressed();
                        Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();

                    }
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    public void onError(Throwable t, String key) {
        Log.e("",t.getLocalizedMessage());

    }
}