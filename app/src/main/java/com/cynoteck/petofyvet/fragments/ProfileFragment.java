package com.cynoteck.petofyvet.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.cynoteck.petofyvet.R;
import com.cynoteck.petofyvet.activities.LoginActivity;
import com.cynoteck.petofyvet.activities.OperatingHoursActivity;
import com.cynoteck.petofyvet.activities.ViewFullProfileVetActivity;
import com.cynoteck.petofyvet.utils.Config;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView tv,heder,vet_name_TV,vet_email_TV,vet_study_TV,vet_id_TV;
    ImageView vet_profile_pic;
    SwitchCompat online_switch;
    View view;
    RelativeLayout veterian_full_profile_layout,operating_hrs_layout,setings_layout,logout_layout;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        initialize();
        getVetInfo();
        switchOnline();

        return view;
    }

    private void getVetInfo() {
        Glide.with(this)
                .load(Config.user_Veterian_url)
                .into(vet_profile_pic);
        vet_name_TV.setText(Config.user_Veterian_name);
        vet_email_TV.setText(Config.user_Veterian_emial);
        vet_study_TV.setText(Config.user_Veterian_study);
        vet_id_TV.setText(Config.user_Veterian_id);

        if (Config.user_Veterian_online.equals("true")){
            online_switch.setChecked(true);
        }else {
            online_switch.setChecked(false);
        }
    }

    private void switchOnline() {
        online_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Config.user_Veterian_online = "true";
                }else {
                    Config.user_Veterian_online = "false";

                }
            }
        });
    }
    public  void initialize()
    {
        veterian_full_profile_layout=view.findViewById(R.id.veterian_full_profile_layout);
        operating_hrs_layout=view.findViewById(R.id.operating_hrs_layout);
        setings_layout=view.findViewById(R.id.setings_layout);
        logout_layout=view.findViewById(R.id.logout_layout);

        vet_email_TV = view.findViewById(R.id.vet_email_TV);
        vet_name_TV = view.findViewById(R.id.vet_name_TV);
        vet_study_TV = view.findViewById(R.id.vet_study_TV);
        vet_id_TV = view.findViewById(R.id.vet_id_TV);
        vet_profile_pic = view.findViewById(R.id.vet_profile_pic);
        online_switch = view.findViewById(R.id.online_switch);

        veterian_full_profile_layout.setOnClickListener(this);
        operating_hrs_layout.setOnClickListener(this);
        setings_layout.setOnClickListener(this);
        logout_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.veterian_full_profile_layout:
                Intent intent = new Intent(getContext(), ViewFullProfileVetActivity.class);
                startActivity(intent);
                break;

            case R.id.operating_hrs_layout:
                startActivity(new Intent(getActivity(), OperatingHoursActivity.class));
                break;

            case R.id.setings_layout:

                break;

            case R.id.logout_layout:
                SharedPreferences preferences =getActivity().getSharedPreferences("userdetails",0);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
                break;

        }
    }
}
