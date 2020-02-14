package com.its.camcar.ui.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.its.camcar.EditProfileActivity;
import com.its.camcar.R;
import com.its.camcar.helper.CamcarHelper;
import com.its.camcar.helper.FireAuthHelper;
import com.its.camcar.helper.FirebaseHelper;
import com.its.camcar.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView ivProfile;
    private TextView tvHeadName;
    private TextView tvHeadEmail;
    private TextView tvEditProfile;
    private TextView tvFullname;
    private TextView tvEmail;
    private TextView tvPhone;
    private LinearLayout llDriverInfo;

    private FirebaseHelper firebaseHelper;
    private FireAuthHelper fireAuthHelper;


    public ProfileFragment() {
        // Required empty public constructor
    }

    private AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v =  inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfile = v.findViewById(R.id.dfp_iv_profile);
        tvHeadName = v.findViewById(R.id.dfp_tv_head_name);
        tvHeadEmail = v.findViewById(R.id.dfp_tv_head_email);
        tvEditProfile = v.findViewById(R.id.dfp_tv_head_edit_profile);

        tvFullname = v.findViewById(R.id.dfp_tv_full_name);
        tvEmail = v.findViewById(R.id.dfp_tv_email);
        tvPhone = v.findViewById(R.id.dfp_tv_phone_number);
        llDriverInfo = v.findViewById(R.id.dfp_ll_driver_info);

        fireAuthHelper = new FireAuthHelper(getContext());
        firebaseHelper = new FirebaseHelper(getContext());


        dialog = CamcarHelper.progressBar(getContext()).create();

        initUI();


        tvEditProfile.setOnClickListener(this);
        llDriverInfo.setOnClickListener(this);


        return  v;
    }

    private void initUI(){
//        Get user Information
        dialog.show();
        final String userId = fireAuthHelper.getCurrentUser().getUid();
        firebaseHelper.getUser(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot != null){
                    User user = documentSnapshot.toObject(User.class);
                    if(user != null){
                        tvHeadName.setText(user.getFullName());
                        tvFullname.setText(user.getFullName());
                        tvHeadEmail.setText(user.getEmail());
                        tvEmail.setText(user.getEmail());
                        tvPhone.setText(user.getPhone());
                        if( user.getProfileUrl() != null){
                            Glide.with(getContext()).load(user.getProfileUrl()).into(ivProfile);
                            ivProfile.setTag(user.getProfileUrl());
                        }
                    }
                }
                if(dialog.isShowing()) dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(dialog.isShowing()) dialog.dismiss();
                Toast.makeText(getContext(),"Something went wrong!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == tvEditProfile.getId()){
//            Start edit profile activity
            Intent intent = new Intent(getContext(),EditProfileActivity.class);

            intent.putExtra("fullname",tvFullname.getText().toString());
            intent.putExtra("phone",tvPhone.getText().toString());
            if(ivProfile.getTag() != null){
                intent.putExtra("image_url",ivProfile.getTag().toString());
            }else {
                intent.putExtra("image_url","none");
            }

            getContext().startActivity(intent);
        }else if(id == llDriverInfo.getId()){
//            Start to Driver Information Activity
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        initUI();
    }
}
