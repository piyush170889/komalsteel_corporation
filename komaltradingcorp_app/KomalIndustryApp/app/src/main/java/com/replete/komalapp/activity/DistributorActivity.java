package com.replete.komalapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.replete.komalapp.BaseInterface;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.AllDistributors;
import com.replete.komalapp.recyclerutils.AllDistributorsAdapter;
import com.replete.komalapp.rowitem.AssociatedDistributor;
import com.replete.komalapp.recyclerutils.AssociatedDistributorAdapter;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MR JOSHI on 30-Mar-16.
 */
public class DistributorActivity extends AppCompatActivity implements BaseInterface, View.OnClickListener {


    //    private AutoCompleteTextView textViewAssociatedDistributor;
//    String[] distributors = {"abcd", "abcdef", "abckfkgjgjg", "pqrs", "xyz", "jklm", "qwerty"};
    private AssociatedDistributorAdapter associatedDistributorAdapter;
    String distributorName;
    private AllDistributorsAdapter allDistributorsAdapter;
    private RecyclerView associatedRecyclerView;
    private List<AssociatedDistributor> associatedDistributorList;
    private List<AllDistributors> allDistributorList;
    private String TAG = "DistributorActivity";
    private JSONArray assDistributorJsonArray;
    private ProgressBar dialogProgressBar;
    private DatabaseHandler databaseHandler;
    private SharedPreferences associatedDistributorPref;
    private SharedPreferences.Editor associatedDistributorPrefEditor;
    private AssociatedDistributorAdapter selectedAssociatedDistributorAdapter;
    private HashMap<String, String> userDetails;
    private RecyclerView allDistributorsRecyclerView;
    private TextView dialog_textView_empty_msg;
    private TextView input_no_associated_distributor_msg;
    private TextView input_associated_distributor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_list);
//        textViewAssociatedDistributor = ((AutoCompleteTextView) findViewById(R.id.input_associated_distributor));

        associatedRecyclerView = (RecyclerView) findViewById(R.id.associated_recycler_view);
        associatedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        input_no_associated_distributor_msg = (TextView) findViewById(R.id.input_no_associated_distributor_msg);
        input_associated_distributor = (TextView) findViewById(R.id.input_associated_distributor);
//        selectedAssociatedRecyclerView = (RecyclerView) findViewById(R.id.selected_associated_recycler_view);
//        selectedAssociatedRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(this);
        userDetails = databaseHandler.getUserDetails();

       /* ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, distributors);
        textViewAssociatedDistributor.setAdapter(adapter);
        textViewAssociatedDistributor.setThreshold(1);

        textViewAssociatedDistributor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                distributorName = arg0.getItemAtPosition(position).toString();
            }
        });*/

        associatedDistributorList = new ArrayList<>();
//        selectedAssociatedDistributorList = new ArrayList<>();

//        ImageView imageViewAddDistributorToList = ((ImageView) findViewById(R.id.imageViewAddDistributor));
//        imageViewAddDistributorToList.setOnClickListener(this);
//        Button btnSave = ((Button) findViewById(R.id.button_save));
//        btnSave.setOnClickListener(this);
//        Button btnSkip = ((Button) findViewById(R.id.button_skip));
//        btnSkip.setOnClickListener(this);

//        SharedPreferences associatedDistributorPref = getSharedPreferences("associatedDistributorPref", MODE_PRIVATE);
//        String jsonAssociatedDistributorsList = associatedDistributorPref.getString("GsonAssociatedDistributorsList", null);
//        Gson gson = new Gson();
//        ((TextView) findViewById(R.id.textView_title_distributor_info)).setVisibility(View.GONE);

        //show associated distributor list if available
        setAssociatedDistributorListToRecyclerview();
    }

    private void setAssociatedDistributorListToRecyclerview() {
        try {
            associatedDistributorList = databaseHandler.getAssociatedDistributor();
            if (associatedDistributorList.size() > 0) {

                input_no_associated_distributor_msg.setVisibility(View.GONE);
                input_associated_distributor.setVisibility(View.VISIBLE);
                associatedRecyclerView.setVisibility(View.VISIBLE);

                associatedDistributorAdapter = new AssociatedDistributorAdapter(DistributorActivity.this, associatedDistributorList, true);
                associatedDistributorAdapter.notifyDataSetChanged();
                associatedRecyclerView.setAdapter(associatedDistributorAdapter);
            } else if (associatedDistributorList.size() == 0) {
                input_no_associated_distributor_msg.setVisibility(View.VISIBLE);
                input_associated_distributor.setVisibility(View.GONE);
                associatedRecyclerView.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, e.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            /*case R.id.imageViewAddDistributor:
                if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this))
                    getAllDistributorsFromServernShowDialog();
                else
                    Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();

                break;*/

          /*  case R.id.button_save:

                List<AssociatedDistributor> associatedDistributorToDeleteList = databaseHandler.getAssociatedDistributorToDelete();
                Log.d(TAG, "associatedDistributorToDeleteList  size" + associatedDistributorToDeleteList.size());

                if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this)) {

                    if (selectedAssociatedDistributorList.size() > 0) {
                        callToSaveAssociatedDistributors();
                    } else if (associatedDistributorToDeleteList != null && selectedAssociatedDistributorList.size() == 0) {
                        callToDeleteAssociatedDistributor(associatedDistributorToDeleteList);
                    } else if (associatedDistributorList.size() == 0 && selectedAssociatedDistributorList.size() == 0) {
                        android.app.AlertDialog.Builder dialog2 = new android.app.AlertDialog.Builder(this);
                        dialog2.setMessage("You will not be associated to any distributor");
                        dialog2.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        dialog2.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dialog2.show();
                    }
                } else
                    Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();


                break;
            case R.id.button_skip:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        Intent intent = new Intent(DistributorActivity.this, MainProductListActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
                    }
                }, 1000);
                break;*/
        }
    }

  /*  private void getAllDistributorsFromServernShowDialog() {
        final Dialog dialog = new Dialog(DistributorActivity.this);
        //setting custom layout to dialog
        dialog.setContentView(R.layout.dialog_all_distributor);

        allDistributorsRecyclerView = (RecyclerView) dialog.findViewById(R.id.selectable_distributor_recycler_view);
        allDistributorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialogProgressBar = ((ProgressBar) dialog.findViewById(R.id.progress_bar));
        dialog_textView_empty_msg = (TextView) dialog.findViewById(R.id.textView_empty_msg);
        allDistributorList = new ArrayList<>();

        //call webservice to get all distributor list
        callToAllDistributor();

        Button buttonOk = (Button) dialog.findViewById(R.id.button_ok);
        Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //get associated distributor list from sharedpreferance which created using selected checkboxes
                    associatedDistributorPref = getSharedPreferences("associatedDistributorPref", MODE_PRIVATE);
                    String jsonAssociatedDistributorsList = associatedDistributorPref.getString("GsonAssociatedDistributorsList", null);
                    Gson gson = new Gson();
                    AssociatedDistributor[] associatedDistributorItem = gson.fromJson(jsonAssociatedDistributorsList, AssociatedDistributor[].class);
                    //store associated distributed's object in JSONArray
                    assDistributorJsonArray = new JSONArray();
                    for (int i = 0; i < associatedDistributorItem.length; i++) {
                        JSONObject associatedSDistributorIdJson = new JSONObject();
                        associatedSDistributorIdJson.put("trackId", associatedDistributorItem[i].getDistributorId());
                        associatedSDistributorIdJson.put("firstName", associatedDistributorItem[i].getDistributorFirstName());
                        associatedSDistributorIdJson.put("lastName", associatedDistributorItem[i].getDistributorLastName());
                        associatedSDistributorIdJson.put("displayName", associatedDistributorItem[i].getDistributorDisplayName());
                        assDistributorJsonArray.put(associatedSDistributorIdJson);
                    }

                    ((TextView) findViewById(R.id.textView_title_distributor_info)).setVisibility(View.VISIBLE);
                    selectedAssociatedDistributorList = Arrays.asList(associatedDistributorItem);
                    Log.d(TAG, "size of selectedAssociatedDistributorList=" + selectedAssociatedDistributorList.size());

                    selectedAssociatedDistributorAdapter = new AssociatedDistributorAdapter(DistributorActivity.this, selectedAssociatedDistributorList, false);
//                    AssociatedDistributorAdapter.CustomViewHolder.imageViewDelete.setVisibility(View.GONE);
                    selectedAssociatedDistributorAdapter.notifyDataSetChanged();
                    selectedAssociatedRecyclerView.setAdapter(selectedAssociatedDistributorAdapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }*/

  /*  private void callToAllDistributor() {
        dialogProgressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_ALL_DISTRIBUTOR + userDetails.get("userTrackId") + "/",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            dialogProgressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                allDistributorList = parseAllDistributor(response);
                                //set all distributor list fetched from server to recyclerview
                                Log.d(TAG, "allDistributorList size=" + allDistributorList.size());

                                if (allDistributorList.size() == 0) {
                                    dialog_textView_empty_msg.setVisibility(View.VISIBLE);
                                    dialog_textView_empty_msg.setText("Distributors List is Empty");
                                } else {
                                    dialog_textView_empty_msg.setVisibility(View.GONE);
                                    allDistributorsAdapter = new AllDistributorsAdapter(DistributorActivity.this, allDistributorList);
                                    allDistributorsAdapter.notifyDataSetChanged();
                                    allDistributorsRecyclerView.setAdapter(allDistributorsAdapter);
                                }
                            } else {
                                //            singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                                Toast.makeText(DistributorActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DistributorActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
                            //          singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialogProgressBar.setVisibility(View.GONE);
                Toast.makeText(DistributorActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
                //singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }
*/
  /*  private List<AllDistributors> parseAllDistributor(JSONObject response) {
        List<AllDistributors> allDistributorsList = new ArrayList<>();
        JSONArray distributorsListArrayObj = null;
        try {
            distributorsListArrayObj = response.getJSONArray("distributorList");
            Log.d(TAG, "distributorsListArrayObj" + distributorsListArrayObj.toString());
            for (int i = 0; i < distributorsListArrayObj.length(); i++) {
                JSONObject elem = distributorsListArrayObj.getJSONObject(i);
                Log.d(TAG, "distributorsListArrayObj.getJSONObject(i)" + distributorsListArrayObj.getJSONObject(i));
                if (elem != null) {
                    AllDistributors allDistributors = new AllDistributors(elem.getString("trackId"), elem.getString("firstName"), elem.getString("lastName"), elem.getString("displayName"));
                    allDistributorsList.add(allDistributors);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return allDistributorsList;
    }


    private void callToDeleteAssociatedDistributor(final List<AssociatedDistributor> associatedDistributorToDeleteList) {


        JSONArray removedAssDistributorJsonArray = new JSONArray();
        for (int i = 0; i < associatedDistributorToDeleteList.size(); i++) {
            JSONObject associatedSDistributorIdJson = new JSONObject();
            try {
                associatedSDistributorIdJson.put("value", associatedDistributorToDeleteList.get(i).getDistributorId());
                removedAssDistributorJsonArray.put(associatedSDistributorIdJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject mainJsonObj = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            mainJsonObj.put("associatedDistributorsList", removedAssDistributorJsonArray);
            finalObject.put("request", mainJsonObj);
            Log.d(TAG, "finalObject " + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_DELETE_DEALER_ASSOCIATED_DISTRIBUTOR + userDetails.get("userTrackId"), finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        AssociatedDistributor associatedDistributor = null;
                        try {
                            JSONObject Obj = response.getJSONObject("responseMessage");
//                            dialogProgressBar.setVisibility(View.GONE);
                            if (Obj.getString("status").equals("200")) {
                                databaseHandler.deleteAssociatedDistributor();
                                associatedDistributorToDeleteList.clear();
                                setAssociatedDistributorListToRecyclerview();
                                Toast.makeText(DistributorActivity.this, "Removed Distributors successfully", Toast.LENGTH_SHORT).show();
                                onSuccessfulResponse();
                            } else {
                                databaseHandler.updateAssociatedDistributor(false);
                                setAssociatedDistributorListToRecyclerview();
                                Toast.makeText(DistributorActivity.this, Obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            databaseHandler.updateAssociatedDistributor(false);
                            setAssociatedDistributorListToRecyclerview();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            Toast.makeText(DistributorActivity.this, "Try again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                dialogProgressBar.setVisibility(View.GONE);
                error.printStackTrace();
                databaseHandler.updateAssociatedDistributor(false);
                setAssociatedDistributorListToRecyclerview();
                Toast.makeText(DistributorActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {

        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);

    }*/

    private void onSuccessfulResponse() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(DistributorActivity.this, MainProductListActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.slide_up, R.anim.slide_up);
            }
        }, 3000);
    }

  /*  private void callToSaveAssociatedDistributors() {
        dialogProgressBar.setVisibility(View.VISIBLE);

        final DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final HashMap<String, String> userDetails = databaseHandler.getUserDetails();


        JSONObject finalObject = new JSONObject();
        try {
            finalObject.put("distributorList", assDistributorJsonArray);

            Log.d(TAG, "finalObject " + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG, ConfigUrls.URL_DEALER_ASSOCIATED_DISTRIBUTOR + userDetails.get("userTrackId"));
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_DEALER_ASSOCIATED_DISTRIBUTOR + userDetails.get("userTrackId"), finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        AssociatedDistributor associatedDistributor = null;
                        try {
                            JSONObject Obj = response.getJSONObject("responseMessage");
                            dialogProgressBar.setVisibility(View.GONE);
                            if (Obj.getString("status").equals("200")) {

                                JSONArray responseArrayObj = response.getJSONArray("distributorList");
                                for (int i = 0; i < responseArrayObj.length(); i++) {
                                    //store response of added associated distributors in DB
                                    JSONObject assDistributorObj = responseArrayObj.getJSONObject(i);
                                    associatedDistributor = new AssociatedDistributor(assDistributorObj.getString("userDistributionListId"), assDistributorObj.getString("firstName"),
                                            assDistributorObj.getString("lastName"), assDistributorObj.getString("displayName"), false);
                                    databaseHandler.addAssociatedDistributor(associatedDistributor);
                                    //clear shared preferance (used to store ass. distributors checked from list)

                                    associatedDistributorPrefEditor = associatedDistributorPref.edit();
                                    associatedDistributorPrefEditor.clear().commit();
                                    Log.d(TAG, "selectedAssociatedDistributorList size after pref clear=" + selectedAssociatedDistributorList.size());
                                    selectedAssociatedDistributorAdapter.notifyDataSetChanged();
                                    selectedAssociatedRecyclerView.setVisibility(View.GONE);


                                    //show associated distributor list if available
                                    setAssociatedDistributorListToRecyclerview();
                                    ((TextView) findViewById(R.id.textView_title_distributor_info)).setVisibility(View.GONE);
                                }
                                Toast.makeText(DistributorActivity.this, "Associated Distributor list updated", Toast.LENGTH_SHORT).show();
                                onSuccessfulResponse();
                            } else
                                Toast.makeText(DistributorActivity.this, Obj.getString("message"), Toast.LENGTH_SHORT).show();
//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            Toast.makeText(DistributorActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                dialogProgressBar.setVisibility(View.GONE);
                Toast.makeText(DistributorActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }*/

}
