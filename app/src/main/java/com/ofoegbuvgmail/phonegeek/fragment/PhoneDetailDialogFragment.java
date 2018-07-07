package com.ofoegbuvgmail.phonegeek.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ofoegbuvgmail.phonegeek.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PhoneDetailDialogFragment extends DialogFragment implements View.OnClickListener {
    private ImageView phoneImageView;
    private TextView specificationTextView;
    private TextView phonePrice;
    private TextView phoneReview;
    private Button buttonDone;


    public PhoneDetailDialogFragment() {
        // Required empty public constructor
    }

    public static PhoneDetailDialogFragment newInstance(String ImageUrl, String price, String review, ArrayList<String> phones) {
        PhoneDetailDialogFragment frag = new PhoneDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("price", price);
        args.putString("review", review);
        args.putStringArrayList("phones", phones);
        args.putString("imageUrl", ImageUrl);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_phone_detail_dialog, container, false);
        phoneImageView = rootView.findViewById(R.id.expandedImageView);
        specificationTextView = rootView.findViewById(R.id.specificationsTextView);
        phonePrice = rootView.findViewById(R.id.priceDetail);
        phoneReview = rootView.findViewById(R.id.review);
        buttonDone = rootView.findViewById(R.id.btn_done);
        buttonDone.setOnClickListener(this);

        return rootView;
    }


    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {

            String imageUrl = getArguments().getString("imageUrl", "https://www.jumia.com.ng/tecno-tecno-k7-dual-sim-16gb-rom1gb-ram-5.5-inch-hd-13mp5mp-champagne-gold-12649422.html");
            ArrayList<String> phoneSpecs = getArguments().getStringArrayList("phones");
            String price = getArguments().getString("price", "Price not available");
            String review = getArguments().getString("review", "The Phone is a great device and very cheap with the following features.");

            Log.d("TAG", "Image url: " + imageUrl);

            String nairaPrice = "₦" + price;
            phonePrice.setText(nairaPrice);

            phoneReview.setText(review);

            for (int i = 0; i < phoneSpecs.size(); i++) {
                String text = phoneSpecs.get(i);
                Log.d("TAG", "text:" + text);
                specificationTextView.append(". " + text + "\n\n");
            }

            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference mStorageRef = storage.getReferenceFromUrl(imageUrl);
            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {

                    Glide.with(getActivity())
                            .load(uri)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(phoneImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Unable to load Image", Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    @Override
    public void onClick(View v) {
        getDialog().dismiss();
    }
}
