package com.ofoegbuvgmail.phonegeek.activity;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ofoegbuvgmail.phonegeek.model.Phone;
import com.ofoegbuvgmail.phonegeek.adapter.PhoneAdapter;
import com.ofoegbuvgmail.phonegeek.fragment.PhoneDetailDialogFragment;
import com.ofoegbuvgmail.phonegeek.R;

import java.util.ArrayList;
import java.util.List;


public class ResultActivity extends AppCompatActivity implements PhoneAdapter.ItemClickListener {

    private PhoneAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView mRecyclerView;
    private FirebaseFirestore db;
    private CollectionReference collectionReference;
    private Bundle receivedBundle;
    private ProgressBar pb;
    private TextView emptyStateTextView;
    private List<Phone> mPhoneList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Phones");

        receivedBundle = getIntent().getBundleExtra("bundle");

        pb = findViewById(R.id.pbLoading);
        emptyStateTextView = findViewById(R.id.empty_state_textView);
        pb.setVisibility(View.VISIBLE);
        mRecyclerView = findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new PhoneAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);
        fetchPhones();
    }

    public void fetchPhones() {
        if (receivedBundle != null) {

            String fromPrice = receivedBundle.getString("fromPrice");
            String toPrice = receivedBundle.getString("toPrice");
            String ramSize = receivedBundle.getString("ramSize");

            collectionReference.whereEqualTo("RAM", ramSize)
                    .whereLessThanOrEqualTo("price", toPrice)
                    .whereGreaterThanOrEqualTo("price", fromPrice)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        //Convert the whole snapshot to a POJO
                        List<Phone> phones = task.getResult().toObjects(Phone.class);
                        mAdapter.setPhoneList(phones);
                        mPhoneList = phones;
                        pb.setVisibility(View.GONE);
                        Log.d("TAG", "Documents retrieved");
                    } else {
                        emptyStateTextView.setVisibility(View.VISIBLE);
                        pb.setVisibility(View.GONE);
                        Log.w("TAG", "Error getting documents.", task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pb.setVisibility(View.GONE);
                    emptyStateTextView.setText(R.string.internet_error);
                    emptyStateTextView.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Log.d("TAG", "The Bundle was Null, check it out");
            Toast.makeText(getApplicationContext(), "An Error Occurred", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClickListener(int id) {
        FragmentManager fm;
        fm = getSupportFragmentManager();
        Phone phone = mPhoneList.get(id);
        String imageUrl = phone.getImageUrl();
        String price = phone.getPrice();
        String review = phone.getReview();
        List<String> specs = phone.getSpecifications();
        ArrayList<String> specList = new ArrayList<>();
        specList.addAll(specs);

        PhoneDetailDialogFragment dialog = PhoneDetailDialogFragment.newInstance(imageUrl, price, review, specList);
        dialog.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        dialog.show(fm, "detail_dialog");
    }
}