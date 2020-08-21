
package com.cynoteck.petofyvet.activities;

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
import com.cynoteck.petofyvet.response.getPetReportsResponse.getClinicVisitDetails.GetClinicVisitsDetailsResponse;
import com.cynoteck.petofyvet.utils.Config;

import retrofit2.Response;

public class ViewReportsDeatilsActivity extends AppCompatActivity implements ApiResponse, View.OnClickListener {

    String clinic_id;
    TextView vet_name_textView,visit_date_textView,nature_ofVist_textView,weight_textView,temperature_textView,vaccine_textView,symptoms_textView,diagnosis_textView,remarks_textView;
    ImageView back_arrow_IV;
    Button deleteReport_BT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reports_deatils);

        init();
        getclinicVisitsReportDetails();

    }

    private void getclinicVisitsReportDetails() {

        PetClinicVistsDetailsParams petClinicVistsDetailsParams = new PetClinicVistsDetailsParams();
        petClinicVistsDetailsParams.setId(clinic_id.substring(0,clinic_id.length()-2));
        PetClinicVisitDetailsRequest petClinicVisitDetailsRequest = new PetClinicVisitDetailsRequest();
        petClinicVisitDetailsRequest.setData(petClinicVistsDetailsParams);
        Log.d("petClinicVisitDetail",petClinicVisitDetailsRequest.toString());
        ApiService<GetClinicVisitsDetailsResponse> service = new ApiService<>();
        service.get(this, ApiClient.getApiInterface().getClinicVisitDetails(Config.token,petClinicVisitDetailsRequest), "GetPetClinicVisitDetails");

    }

    private void init() {
        Bundle extras = getIntent().getExtras();
        clinic_id=extras.getString("clinic_id");

        deleteReport_BT=findViewById(R.id.deleteReport_BT);
        vet_name_textView=findViewById(R.id.vet_name_textView);
        visit_date_textView=findViewById(R.id.visit_date_textView);
        nature_ofVist_textView=findViewById(R.id.nature_ofVist_textView);
        weight_textView=findViewById(R.id.weight_textView);
        temperature_textView=findViewById(R.id.temperature_textView);
        vaccine_textView=findViewById(R.id.vaccine_textView);
        symptoms_textView=findViewById(R.id.symptoms_textView);
        diagnosis_textView=findViewById(R.id.diagnosis_textView);
        remarks_textView=findViewById(R.id.remarks_textView);
        back_arrow_IV=findViewById(R.id.back_arrow_IV);
        back_arrow_IV.setOnClickListener(this);
        deleteReport_BT.setOnClickListener(this);

    }

    @Override
    public void onResponse(Response response, String key) {
        switch (key){
            case "GetPetClinicVisitDetails":
                try {
                    Log.d("ResponseClinicVisit",response.body().toString());
                    GetClinicVisitsDetailsResponse getClinicVisitsDetailsResponse = (GetClinicVisitsDetailsResponse) response.body();
                    int responseCode = Integer.parseInt(getClinicVisitsDetailsResponse.getResponse().getResponseCode());
                    if (responseCode== 109){
                        vet_name_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVeterinarian());
                        visit_date_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVisitDate());
                        nature_ofVist_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getNatureOfVisit().getNature());
                        weight_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getWeightLbs());
                        temperature_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTemperature());
                        vaccine_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getVaccine());
                        symptoms_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDescription());
                        diagnosis_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getDiagnosisProcedure());
                        remarks_textView.setText(getClinicVisitsDetailsResponse.getData().getPetClinicVisitDetails().getTreatmentRemarks());


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
        Log.e("error",t.getLocalizedMessage());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.back_arrow_IV:
                onBackPressed();
                break;

            case R.id.deleteReport_BT:


                break;
        }

    }
}