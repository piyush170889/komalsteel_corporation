package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.AllDistributors;
import com.replete.komalapp.rowitem.AssociatedDistributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 31-Mar-16.
 */
public class AllDistributorsAdapter extends RecyclerView.Adapter<AllDistributorsAdapter.CustomViewHolder> {
    private String TAG = "AllDistributorsAdapter";
    private Context mContext;
    private List<AllDistributors> allDistributorsList;
    private AllDistributors allDistributorsItem;
    private List<AssociatedDistributor> associatedDistributorList = new ArrayList<>();
    AssociatedDistributor associatedDistributorItem;
    SharedPreferences associatedDistributorPref ;
    SharedPreferences.Editor associatedDistributorPrefEditor ;
    Gson gson = new Gson();

    private CompoundButton.OnCheckedChangeListener checkboxChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CustomViewHolder holder = (CustomViewHolder) buttonView.getTag();
            final int position = holder.getAdapterPosition();
            final AllDistributors allDistributors = allDistributorsList.get(position);
            if (isChecked) {
                associatedDistributorItem = new AssociatedDistributor(allDistributors.getId(),allDistributors.getFirstName() ,allDistributors.getLastName(),allDistributors.getDisplayName(),false);
                associatedDistributorList.add(associatedDistributorItem);
                //store selected distributors in shared preferance
                String gsonAssociatedDistributorsList = gson.toJson(associatedDistributorList);
                Log.d("AllDistributorAdapter", gsonAssociatedDistributorsList);
                associatedDistributorPrefEditor.putString("GsonAssociatedDistributorsList", gsonAssociatedDistributorsList);
                associatedDistributorPrefEditor.commit();
            } else {
                if (associatedDistributorList.contains(associatedDistributorItem)) {
                    associatedDistributorList.remove(associatedDistributorItem);
                    //store selected distributors in shared preferance
                    String gsonAssociatedDistributorsList = gson.toJson(associatedDistributorList);
                    Log.d("AllDistributorAdapter", gsonAssociatedDistributorsList);
                    associatedDistributorPrefEditor.putString("GsonAssociatedDistributorsList", gsonAssociatedDistributorsList);
                    associatedDistributorPrefEditor.commit();
                }
            }
        }
    };

    public AllDistributorsAdapter(Context mContext, List<AllDistributors> allDistributorsList) {
        this.mContext = mContext;
        this.allDistributorsList = allDistributorsList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_all_distributor, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        allDistributorsItem = allDistributorsList.get(position);
        Log.d(TAG,"allDistributorList size in adapter="+allDistributorsList.size());
        associatedDistributorPref = mContext.getSharedPreferences("associatedDistributorPref", mContext.MODE_PRIVATE);
        associatedDistributorPrefEditor = associatedDistributorPref.edit();
        customViewHolder.textViewDistributorDisplayName.setText(Html.fromHtml(allDistributorsItem.getDisplayName()));
        customViewHolder.textViewDistributorFirstName.setText(Html.fromHtml(allDistributorsItem.getFirstName()));
        customViewHolder.textViewDistributorLastName.setText(Html.fromHtml(allDistributorsItem.getLastName()));
        customViewHolder.checkBoxDistributorAdded.setOnCheckedChangeListener(checkboxChangeListener);
        customViewHolder.checkBoxDistributorAdded.setTag(customViewHolder);
    }

    @Override
    public int getItemCount() {
        return (null != allDistributorsList ? allDistributorsList.size() : 0);
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDistributorDisplayName;
        private TextView textViewDistributorFirstName;
        private TextView textViewDistributorLastName;
        private CheckBox checkBoxDistributorAdded;


        public CustomViewHolder(View itemView) {
            super(itemView);
            this.textViewDistributorDisplayName = (TextView) itemView.findViewById(R.id.textViewDistributorDisplayName);
            this.textViewDistributorFirstName = (TextView) itemView.findViewById(R.id.textViewDistributorFirstName);
            this.textViewDistributorLastName = (TextView) itemView.findViewById(R.id.textViewDistributorLastName);
            this.checkBoxDistributorAdded = (CheckBox) itemView.findViewById(R.id.checkBoxDistributorAdded);
        }
    }
}