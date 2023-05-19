package com.cy.smartsecure;




import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;



public class mAdapter extends FirebaseRecyclerAdapter<mModel,mAdapter.myViewHolder> {

    Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public mAdapter(@androidx.annotation.NonNull FirebaseRecyclerOptions<mModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull mModel model) {

        holder.mSavingType.setText(model.getHow());
        holder.mWaterQuality.setText(model.getWaterQuality());
        holder.mTdsSensor.setText(model.getTds());
        holder.mTurSensor.setText(model.getTurbidity());
        holder.mLocation.setText(model.getCity());


        String android_id = Settings.Secure.getString(holder.mSavingType.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);

        if (android_id.equals(model.getAndroid_id())){
            holder.mMainlyr.setVisibility(View.VISIBLE);
        } else {
            holder.mMainlyr.setVisibility(View.GONE);
        }



        holder.mSavingType.setOnClickListener(v -> {

            if (holder.mViewlyr.getVisibility() == View.VISIBLE) {

                holder.mViewlyr.setVisibility(View.GONE);
            } else {
                holder.mViewlyr.setVisibility(View.VISIBLE);
            }


        });


    }

    @androidx.annotation.NonNull
    @Override
    public myViewHolder onCreateViewHolder(@androidx.annotation.NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list,parent,false);
        return new myViewHolder(view);
    }



    class myViewHolder extends RecyclerView.ViewHolder{

         TextView mSavingType;
         TextView mWaterQuality;
         TextView mTdsSensor;
         TextView mTurSensor;
         TextView mLocation;

         LinearLayout mMainlyr;
         LinearLayout mViewlyr;





        public myViewHolder(View itemview){
            super(itemview);

            mSavingType = itemview.findViewById(R.id.saving_type);
            mWaterQuality = itemview.findViewById(R.id.waterQuality);
            mTdsSensor = itemview.findViewById(R.id.tdsSensor);
            mTurSensor = itemview.findViewById(R.id.turSensor);
            mLocation = itemview.findViewById(R.id.location);
            mMainlyr = itemview.findViewById(R.id.mainlyr);
            mViewlyr = itemview.findViewById(R.id.viewlyr);

        }


    }

}
