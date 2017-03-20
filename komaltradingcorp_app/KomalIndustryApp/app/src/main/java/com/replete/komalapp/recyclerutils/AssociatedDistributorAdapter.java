package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.AssociatedDistributor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 30-Mar-16.
 */
public class AssociatedDistributorAdapter extends RecyclerView.Adapter<AssociatedDistributorAdapter.CustomViewHolder> {
    private Context mContext;
    private List<AssociatedDistributor> associatedDistributorList, removedAssociatedDistributorList;
    private AssociatedDistributor associatedDistributorItem;
    private DatabaseHandler databaseHandler;
    private boolean isVisible;
    Gson gson = new Gson();
    private View.OnClickListener deleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            final int position = holder.getAdapterPosition();
            //associatedDistributor  obj fetched from list, which is selected to remove
            final AssociatedDistributor associatedDistributorToRemove = associatedDistributorList.get(position);
            android.app.AlertDialog.Builder removeItemDialog = new android.app.AlertDialog.Builder(mContext);
            removeItemDialog.setMessage("Do you want to remove this distributor?");
            removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removedAssociatedDistributorList = new ArrayList<>();
                    //set associatedDistributor obj with isDeleted=true in DB
                    AssociatedDistributor associatedDistributor = new AssociatedDistributor(associatedDistributorToRemove.getDistributorId(), associatedDistributorToRemove.getDistributorFirstName(),
                            associatedDistributorToRemove.getDistributorLastName(), associatedDistributorToRemove.getDistributorDisplayName(), true);
                    removedAssociatedDistributorList.add(associatedDistributor);

                   /* SharedPreferences removedAssociatedDistributorPref = mContext.getSharedPreferences("removedAssociatedDistributorPref", mContext.MODE_PRIVATE);
                    SharedPreferences.Editor removedAssociatedDistributorPrefEditor  = removedAssociatedDistributorPref.edit();
                    String gsonRemovedDistributorsList = gson.toJson(removedAssociatedDistributorList);
                    Log.d("gsonRemoveList", gsonRemovedDistributorsList);
                    removedAssociatedDistributorPrefEditor.putString("GsonRemovedDistributorsList", gsonRemovedDistributorsList );
                    removedAssociatedDistributorPrefEditor.commit();*/

                    databaseHandler.updateAssociatedDistributorValue(associatedDistributor);
                    associatedDistributorList.remove(position);
                    notifyItemRemoved(position);

                }
            });
            removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            removeItemDialog.show();
        }
    };

    public AssociatedDistributorAdapter(Context mContext, List<AssociatedDistributor> associatedDistributorList, boolean isVisible) {
        this.mContext = mContext;
        this.associatedDistributorList = associatedDistributorList;
        this.isVisible = isVisible;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_distributor, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        databaseHandler = new DatabaseHandler(mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        associatedDistributorItem = associatedDistributorList.get(position);
        customViewHolder.textViewDistributor.setText(Html.fromHtml(associatedDistributorItem.getDistributorDisplayName()));
/*

        if (isVisible) {
            customViewHolder.imageViewDelete.setVisibility(View.VISIBLE);
            customViewHolder.imageViewDelete.setOnClickListener(deleteClickListener);
            customViewHolder.imageViewDelete.setTag(customViewHolder);
        } else
            customViewHolder.imageViewDelete.setVisibility(View.GONE);
*/

    }

    @Override
    public int getItemCount() {
        return (null != associatedDistributorList ? associatedDistributorList.size() : 0);
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewDistributor;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textViewDistributor = (TextView) itemView.findViewById(R.id.textViewDistributor);
        }
    }
}
