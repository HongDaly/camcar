package com.its.camcar.helper;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.its.camcar.model.Booking;
import com.its.camcar.model.Schedule;
import com.its.camcar.model.User;

public class FirebaseHelper {

    private FirebaseFirestore db;
    private Context context;
    private FirebaseStorage storage;

    public FirebaseHelper(Context context){
        db = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        this.context = context;
    }

    public void saveUser(final Uri image, final User user){
        final String name = String.valueOf(System.currentTimeMillis()/1000);
        storage.getReference("card_id/"+name).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference("card_id/"+name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Image Url", "onSuccess: uri= "+ uri.toString());
//                        add user to firestore
                        user.setVerifyCardUrl(uri.toString());
                        addUserToFirestore(user);

                    }
                });
            }
        });
    }

    public void addUserToFirestore(User user){

        db.collection("users").document(user.getId()).set(user);
    }

//    add profile user
    public void addProfileUser(final Uri image){
        final String name = String.valueOf(System.currentTimeMillis()/1000);
        storage.getReference("profile/"+name).putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storage.getReference("profile/"+name).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("Image Url", "onSuccess: uri= "+ uri.toString());
//                        add user to firestore
                       String profileUrl = uri.toString();
                       String userId = FirebaseAuth.getInstance().getUid();
                        if (userId != null) {
                            db.collection("users").document(userId)
                                    .set(profileUrl, SetOptions.merge());
                        }

                    }
                });
            }
        });
    }

    //add schedule
    public void addScheduleToFirestore(Schedule schedule){
        String id =  db.collection("schedules").document().getId();
        schedule.setId(id);
        db.collection("schedules").document(schedule.getId()).set(schedule);
    }
//  update schedule

    public void updateScheduleToFirestore(Schedule schedule){
        db.collection("schedules").document(schedule.getId()).set(schedule);
    }

    //  update schedule
    public void deleteScheduleToFirestore(Schedule schedule){
        db.collection("schedules").document(schedule.getId()).delete();
    }

//    get user
    public Task<DocumentSnapshot> getUser(String userid){
       return db.collection("users").document(userid).get();
    }


//    get Schedule
    public  Task<QuerySnapshot> getSchedules(){
        return  db.collection("schedules").get();
    }
//    get Schedule By ID
    public  Task<DocumentSnapshot> getScheduleByID(String id){
        return db.collection("schedules").document(id).get();
    }
//
    public Task<QuerySnapshot> search(String locationFrom , String locationDestination){

        String[] lf = locationFrom.split(",");
        String lfDistrict = lf[0];
        String lfProvince = lf[1];


        String[] ld = locationDestination.split(",");
        String ldDistrict = ld[0];
        String ldProvince = ld[1];


        return db.collection("schedules")
                .whereEqualTo("arrivedDistrict",ldDistrict)
                .whereEqualTo("arrivedProvince",ldProvince)
                .whereEqualTo("startDistrict",lfDistrict)
                .whereEqualTo("startProvince",lfProvince).get();

    }

//
    public Task<Void> addBooking(Booking booking){
        String id =  db.collection("booking").document().getId();
        booking.setId(id);
        return db.collection("booking").document(booking.getId()).set(booking);
    }
//    get History

    public Task<QuerySnapshot> getHistory(String userID){
        return db.collection("booking").whereEqualTo("customerId",userID)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get();
    }

}
