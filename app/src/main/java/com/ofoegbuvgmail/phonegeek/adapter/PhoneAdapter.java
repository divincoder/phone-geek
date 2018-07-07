package com.ofoegbuvgmail.phonegeek.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.ofoegbuvgmail.phonegeek.R;
import com.ofoegbuvgmail.phonegeek.model.Phone;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.PhoneDataHolder> {

    private List<Phone> phoneList;
    final private ItemClickListener mItemClickListener;
    private Context mContext;
    private StorageReference mStorageRef;

    public PhoneAdapter(ItemClickListener mItemClickListener, Context mContext) {
        this.mItemClickListener = mItemClickListener;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PhoneDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.phone_item, parent, false);

        return new PhoneDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PhoneDataHolder holder, int position) {
        Phone currentPhone = phoneList.get(position);
        holder.phoneModel.setText(currentPhone.getModel());
        holder.phonePrice.setText(currentPhone.getPrice());
        String ramSize = currentPhone.getRAM() + " GB" + " RAM";
        holder.phoneRAM.setText(ramSize);

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReferenceFromUrl(currentPhone.getImageUrl());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                Glide.with(mContext)
                        .load(uri).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(holder.phoneImage);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if (phoneList == null) {
            return 0;
        }
        return phoneList.size();
    }



    public interface ItemClickListener {
        void onItemClickListener(int id);
    }

    public List<Phone> getPhoneList() {
        return phoneList;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
        notifyDataSetChanged();
    }

    class PhoneDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        //Variables to fill
        private TextView phoneModel;
        private TextView phoneRAM;
        private TextView phonePrice;
        private ImageView phoneImage;

        public PhoneDataHolder(View itemView) {
            super(itemView);
            phoneModel = itemView.findViewById(R.id.phone_model);
            phoneRAM = itemView.findViewById(R.id.phone_ram);
            phonePrice = itemView.findViewById(R.id.phone_price);
            phoneImage = itemView.findViewById(R.id.phone_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
             mItemClickListener.onItemClickListener(getAdapterPosition());
        }
    }
}
