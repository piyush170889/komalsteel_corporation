package com.replete.komalapp.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.mikepenz.actionitembadge.library.ActionItemBadge;
import com.mikepenz.actionitembadge.library.utils.BadgeStyle;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.replete.komalapp.Config.AppController;
import com.replete.komalapp.Config.ConfigUrls;
import com.replete.komalapp.Config.Constants;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.recyclerutils.ReviewProductsNSubcategoriesAdapter;
import com.replete.komalapp.recyclerutils.State;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;
import com.replete.komalapp.rowitem.CartSubcategoryInfo;
import com.replete.komalapp.rowitem.ShippingAddress;
import com.replete.komalapp.utils.SimpleDividerItemDecoration;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MR JOSHI on 26-Mar-16.
 */
public class ReviewProductActivity extends AppCompatActivity implements View.OnClickListener {

    private int TIME_OUT_IN_MS = 15000;
    private int RETRY_COUNT = 0;
    private float BACKOFF_MULTIPLIER = 1F;

    private Button buttonProceed;
    private TextView textViewAddress, textViewCity, textViewPinCode, textViewContactNo, textViewDiscount;
    //    private TextView textViewSubTotal, textViewShippingCharges, textViewTotalAmount, textViewServiceCharges, textViewVat;
    private RecyclerView recyclerViewReviewCartProdcut;
    private EditText editTextCartNote;
    private SharedPreferences tempSharedPref;
    private SharedPreferences.Editor editor;
    private String TAG = "ReviewProductActivity";
    //    private double cartTotal;
    private String vat;
    private int count = 0;
    private boolean isOrderPlaced = false;
    private String updatedGSt;

    private BadgeStyle style = ActionItemBadge.BadgeStyles.GREY.getStyle();

    DatabaseHandler databaseHandler;
    /*private BroadcastReceiver myWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if wifi is ON or mobile connection is ON
            if (SingletonUtil.getSingletonConfigInstance().checkNetConnection(ReviewProductActivity.this)) {
                if (0 == count)
//                    getTaxValuesFromServer();
            }
        }
    };*/
    private double vatValue;
    private double finalAmount;
    private ImageView imageViewEditAddress;
    private JSONArray cartJsonArray;
    private EditText input_dialog_gst2;
    private ProgressBar progressBar;
    private EditText editTextPinCode;
    private EditText editTextAddress;
    //    private EditText editTextCity;
//    private EditText editTextState;
    private HashMap<String, String> userDetails;
    private ShippingAddress shippingAddressFromDb;
    private int badgeCount = 0;
    private SingletonUtil singletonUtil;
    private String userContactNo;
    private boolean isShippingInfoFromText = false;
    private Spinner spinnerCity;
    private Spinner spinnerState;
    private ArrayList<State> stateList;

    private String SELECT_STATE = "Select State";
    private String SELECT_CITY = "Select City";
    private TextView textView_mark;
    private TextView textView_destination;
    private TextView textView_transporter_name;
    private TextView textViewVatTinNo;
    private TextView textView_GST;
    private int shippingAddressId;
    private EditText input_dialog_mark;
    private EditText input_dialog_gst;
    private EditText input_dialog_destination;
    private EditText input_dialog_transName;
    private EditText input_dialog_vat_tin_no;
    private EditText input_mark;
    private EditText input_gst_no;
    private EditText input_destination;
    private EditText input_transName;
    private EditText input_vat_tin_no;
    private boolean isDefaultAddress = true;
    private boolean isEmptyShippingAddress = false;


    private ArrayList<CartProductNSubCategories> cartProductNSubCategoriesList;
    //    private ProgressBar dialogprogressBar;
    private TextView textViewState;
//    private TextView textViewEmail;
//    private TextView textViewTitleProductPriceInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_review_product);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Review product");

        initComponants();

        //set cart products
        setCartProducts();

        spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

//                if (currentState != spinnerState.getItemAtPosition(position).toString()) {
//                ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ProfileActivity.this,
//                        R.layout.category_simple_spinner_item_category, new ArrayList<String>());
//                spinnerCity.setAdapter(emptyAdapter);

                ArrayList<String> cityStringList = new ArrayList<>();

                if (position >= 1) {
                    State state = stateList.get(position - 1);
                    callTogetCity(state.getStateId());
                }
                if (spinnerState.getItemAtPosition(position).toString().equals(SELECT_STATE)) {
                    cityStringList.add(SELECT_CITY);

                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ReviewProductActivity.this,
                            R.layout.simple_spinner_dropdown_item, cityStringList);
                    spinnerCity.setAdapter(cityAdapter);
                }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//        if (0 == count) getTaxValuesFromServer();


//        textViewSubTotal.setText("Rs." + tempSharedPref.getString("subtotal", "0"));

//        textViewTitleProductPriceInfo.setText("Rs." + tempSharedPref.getString("subtotal", "0"));
//        cartTotal = Double.parseDouble(tempSharedPref.getString("subtotal", "0.0"));
//        textViewTitleProductPriceInfo.setText( Html.fromHtml("<font color='red'><b></b></font>"));

        //TODO: Don't delete comments
//        TextView textView_service_tax_per=((TextView) findViewById(R.id.textView_service_tax_per));
//        textView_service_tax_per.setText("Service Tax (5%) :");
//        TextView textView_discount_per=((TextView) findViewById(R.id.textView_discount_per));
//        textView_discount_per.setText("Discount (10%) :");


    }

    private void setCartProducts() {

//        List<CartProducts> cartProductsList = databaseHandler.getAllCartProducts();

        cartProductNSubCategoriesList = new ArrayList<>();
        CartProductNSubCategories cartProductNSubCategories = null;
        List<CartSubcategoryInfo> cartSubcategoryInfoList = databaseHandler.getAllCartSubcategoryInfo();
        for (int i = 0; i < cartSubcategoryInfoList.size(); i++) {

            CartSubcategoryInfo cartSubcategoryInfo = cartSubcategoryInfoList.get(i);
//            databaseHandler.getCartProductBySubcatId(cartSubcategoryInfo.getSubcategoryId());

            cartProductNSubCategories =
                    new CartProductNSubCategories(cartSubcategoryInfo.getSubcategoryId(),
                            cartSubcategoryInfo.getSubcategoryName(),
                            databaseHandler.getCartProductBySubcatId(cartSubcategoryInfo.getSubcategoryId()));

            cartProductNSubCategoriesList.add(cartProductNSubCategories);
        }

        Log.d("CartActivity", "size=" + cartProductNSubCategoriesList.size());

        cartJsonArray = new JSONArray();

        for (int i = 0; i < cartProductNSubCategoriesList.size(); i++) {

            CartProductNSubCategories cartProductNSubCategoriesObj = cartProductNSubCategoriesList.get(i);
            List<CartProducts> cartProductsList = cartProductNSubCategoriesObj.getCartProductsList();

            for (int j = 0; j < cartProductsList.size(); j++) {
                CartProducts cartProducts = cartProductsList.get(j);

//            cartProducts.getProductCostPerUnit();

                JSONObject requestJson = new JSONObject();
                try {
                    requestJson.put("itemName", cartProducts.getProductName());
//                requestJson.put("itemPrice", cartProducts.getProductTotal());
                    requestJson.put("itemQty", cartProducts.getProductQuantity());
                    requestJson.put("itemMasterDtlsId", cartProducts.getProductId());
                    requestJson.put("itemPrice", "0");
                    requestJson.put("isOfferAppld", "false");
                    requestJson.put("offerDtlId", "0");
                    cartJsonArray.put(requestJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        TextView textViewTitleProductInfo = (TextView) findViewById(R.id.textView_title_product_info);


        textViewTitleProductInfo.setText(Html.fromHtml("<font>PRODUCTS</font>") + "(" + databaseHandler.getCartProductCounts() + ")");
        textViewTitleProductInfo.setTypeface(null, Typeface.BOLD);
        //get cartItem's List, created while items were added to cart, and set list to recyclerview through adapter
        ReviewProductsNSubcategoriesAdapter adapter = new ReviewProductsNSubcategoriesAdapter(ReviewProductActivity.this, cartProductNSubCategoriesList);
        adapter.notifyDataSetChanged();
        recyclerViewReviewCartProdcut.setAdapter(adapter);
    }

    private void initComponants() {

        singletonUtil = SingletonUtil.getSingletonConfigInstance();

        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        buttonProceed = (Button) findViewById(R.id.button_proceed_to_payment);
        buttonProceed.setOnClickListener(ReviewProductActivity.this);
        textViewAddress = (TextView) findViewById(R.id.textView_address);

        editTextPinCode = (EditText) findViewById(R.id.input_pincode);
        editTextAddress = (EditText) findViewById(R.id.input_address);
        input_mark = (EditText) findViewById(R.id.input_mark);
        input_gst_no = (EditText) findViewById(R.id.input_gst_no);
        input_destination = (EditText) findViewById(R.id.input_destination);
        input_transName = (EditText) findViewById(R.id.input_transName);
        input_vat_tin_no = (EditText) findViewById(R.id.input_vat_tin_no);
//        textViewEmail = (TextView) findViewById(R.id.textView_email);


        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerState = (Spinner) findViewById(R.id.spinnerState);
        stateList = new ArrayList<>();


        databaseHandler = new DatabaseHandler(this);
        userDetails = databaseHandler.getUserDetails();
        userContactNo = userDetails.get("contact_no");

        textViewCity = (TextView) findViewById(R.id.textView_city);
        textViewPinCode = (TextView) findViewById(R.id.textView_pincode);
        textViewContactNo = (TextView) findViewById(R.id.textView_contact_no);
        textViewState = (TextView) findViewById(R.id.textView_state);

        textView_mark = (TextView) findViewById(R.id.textView_mark);
        textView_destination = (TextView) findViewById(R.id.textView_destination);
        textView_transporter_name = (TextView) findViewById(R.id.textView_transporter_name);
        textViewVatTinNo = (TextView) findViewById(R.id.textView_vat_tin_no);
        textView_GST = (TextView) findViewById(R.id.textView_gst_no);

//        textViewServiceCharges = (TextView) findViewById(R.id.textView_service_tax);
//        textViewVat = (TextView) findViewById(R.id.textView_vat);
//        textViewSubTotal = (TextView) findViewById(R.id.textView_subtotal);
//        textViewDiscount= (TextView) findViewById(R.id.textView_discount);
//        textViewShippingCharges = (TextView) findViewById(R.id.textView_shipping_charges);
//        textViewTotalAmount = (TextView) findViewById(R.id.textView_total_amount);
        imageViewEditAddress = (ImageView) findViewById(R.id.imageView_edit_address);
        imageViewEditAddress.setOnClickListener(ReviewProductActivity.this);
        recyclerViewReviewCartProdcut = (RecyclerView) findViewById(R.id.recycler_view_review_product);
        editTextCartNote = (EditText) findViewById(R.id.editTextCartNote);

        recyclerViewReviewCartProdcut.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewReviewCartProdcut.addItemDecoration(new SimpleDividerItemDecoration(this));
//to disable scrolling of recyclerview
        recyclerViewReviewCartProdcut.setNestedScrollingEnabled(false);

        tempSharedPref = getSharedPreferences(Constants.TEMP_SHARED_PREF, MODE_PRIVATE);
        editor = tempSharedPref.edit();
    }


    @Override
    public void onBackPressed() {
        if (isOrderPlaced)
            onPaymentSuccessFull();
        else {
            super.onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        }
        if (!isDefaultAddress)
            isDefaultAddress = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        if (badgeCount == 0) {
            //hide cart icon If count is 0
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        } else {
            //Update cart items number on incrementing count
            ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), FontAwesome.Icon.faw_shopping_cart, style, badgeCount);
        }
        //        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_2).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_samplebadge:
                //Redirect to CartActivity on click of cart image in menu
              /*  Intent intentToCart = new Intent(ReviewProductActivity.this, CartActivity.class);
                startActivity(intentToCart);*/
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update count in menu onResume
        badgeCount = databaseHandler.getCartProductCounts();
        invalidateOptionsMenu();

//        getTaxValuesFromServer();
        callToGetProfileShippingAddress();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView_edit_address:

                //Dialog to add new address info
                callDialogForNewShippingAddress();

               /* Intent newIntent = new Intent(this, ProfileActivity.class);
//                editor.putBoolean("shouldNotEdit", false);
//                editor.commit();
                startActivity(newIntent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);*/
//                finish();
                break;

            case R.id.button_proceed_to_payment:

              /*  Log.d(TAG, "cartNote=" + (editTextCartNote.getText().toString().isEmpty() ? "" : editTextCartNote.getText().toString()));
//        editor.putString("shippingCharges", shippingCharge.toString());
                editor.putString("serviceCharges", "0");
                editor.putString("serviceChargesValue", "0");
//        editor.putString("discount", "10");
//        editor.putString("discountValue", discount.toString());
                editor.putString("vat", vat);
                editor.putString("vatValue", Double.toString(vatValue));
                editor.putString("totalAmount", Double.toString(finalAmount));
                editor.putString("cartNote", editTextCartNote.getText().toString().isEmpty() ? "" : editTextCartNote.getText().toString());
                editor.commit();

                Intent intent = new Intent(this, PaymentActivity.class);
//                intent.putExtra("YouPay", textViewTotalAmount.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/

//                if (shippingAddressFromDb == null || (shippingAddressFromDb.getCity().equals("null") || shippingAddressFromDb.getAddress().equals("null") || shippingAddressFromDb.getPincode().equals("null") || shippingAddressFromDb.getState().equals("null"))) {
                if (isEmptyShippingAddress) {
                    //if shipping address is empty in profile
                    if (!validate())
                        Toast.makeText(this, "Please fill Shipping Address details", Toast.LENGTH_SHORT).show();

                    else if (spinnerState.getSelectedItem().toString().equals(SELECT_STATE)) {
                        Toast.makeText(this, "Please select state name", Toast.LENGTH_SHORT).show();

                    } else if (spinnerCity.getSelectedItem().toString().equals(SELECT_CITY)) {
                        Toast.makeText(this, "Please select city name", Toast.LENGTH_SHORT).show();
                    } else if (shippingAddressFromDb.getGSTNo().toString().equals("")) {
                        callTOGSTDialog();
                    } else {
//                        String paymentGateway;
//                        paymentMode = "COD";
//                        paymentGateway = "";
//                        *************


                        callForPlaceOrder();
                        break;
                    }
                } else {
//                    input_gst_no textView_GST
                    if (shippingAddressFromDb.getGSTNo().toString().equals("")) {
                        callTOGSTDialog();
                    } else {
                     /*if shipping address fetched from db*/
//                    String paymentGateway;
//                    paymentMode = "COD";
//                    paymentGateway = "";

                        callForPlaceOrder();
                    }
                    break;
                }
        }
    }

    private void callDialogForNewShippingAddress() {


        final AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(ReviewProductActivity.this);
        LayoutInflater inflater = ReviewProductActivity.this.getLayoutInflater();
        // inflate the custom dialog view
        final View mDialogView = inflater.inflate(R.layout.dialog_new_shipping_address, null);
        // set the View for the AlertDialog
        mAlertDialogBuilder.setView(mDialogView);

        TextView textViewTitle = (TextView) mDialogView.findViewById(R.id.textViewTitle);
        textViewTitle.setText("Enter Shipping Address");
        input_dialog_mark = (EditText) mDialogView.findViewById(R.id.input_mark);
        input_dialog_destination = (EditText) mDialogView.findViewById(R.id.input_destination);
        input_dialog_transName = (EditText) mDialogView.findViewById(R.id.input_transName);
        input_dialog_vat_tin_no = (EditText) mDialogView.findViewById(R.id.input_vat_tin_no);
        input_dialog_gst = (EditText) mDialogView.findViewById(R.id.input_gst_no);

        input_dialog_mark.setText(textView_mark.getText().toString());
        input_dialog_destination.setText(textView_destination.getText().toString());
        input_dialog_transName.setText(textView_transporter_name.getText().toString());
        input_dialog_gst.setText(textView_GST.getText().toString());

//        dialogprogressBar = ((ProgressBar) mDialogView.findViewById(R.id.progress_bar));

        Button dialogBtnOk = (Button) mDialogView.findViewById(R.id.btnOK);
        Button btnCancel = (Button) mDialogView.findViewById(R.id.btnCancel);
        final AlertDialog alertDialog = mAlertDialogBuilder.create();

        alertDialog.show();
        alertDialog.setCancelable(false);

        dialogBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateDialog())
                    Toast.makeText(ReviewProductActivity.this, "Please enter valid Shipping Address details", Toast.LENGTH_SHORT).show();
                else {
                    /*
                    dialogprogressBar.setVisibility(View.VISIBLE);
                    dialogBtnOk.setClickable(false);
                    buttonProceed.setClickable(false);
                    callForPlaceOrder();*/

                    isDefaultAddress = false;
                    textView_mark.setText(input_dialog_mark.getText().toString());
                    textView_GST.setText(input_dialog_gst.getText().toString());

//                    textViewEmail.setText(input_dialog_mark.getText().toString());
                    textView_destination.setText(input_dialog_destination.getText().toString());
                    textView_transporter_name.setText(input_dialog_transName.getText().toString());
                    if (!input_dialog_vat_tin_no.getText().toString().isEmpty())
                        textViewVatTinNo.setText(input_dialog_vat_tin_no.getText().toString());
                    alertDialog.dismiss();
                }

            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                alertDialog.dismiss();
            }

        });
    }

    private void callForPlaceOrder() {
        if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(ReviewProductActivity.this)) {

            final String pincode, city, state, address, mark, destination, transporterName, vatTinNo, GST;
            //            if (shippingAddressFromDb == null || (shippingAddressFromDb.getCity().equals("null") || shippingAddressFromDb.getAddress().equals("null") || shippingAddressFromDb.getPincode().equals("null") || shippingAddressFromDb.getState().equals("null"))) {
            if (isEmptyShippingAddress) {
                /*fetch from edittext in case of shipping adress empty*/
                pincode = editTextPinCode.getText().toString();
                address = editTextAddress.getText().toString();
                city = spinnerCity.getSelectedItem().toString() == null ? "" : spinnerCity.getSelectedItem().toString();
                state = spinnerState.getSelectedItem().toString() == null ? "" : spinnerCity.getSelectedItem().toString();
                mark = input_mark.getText().toString();
                GST = updatedGSt;
//                GST = input_gst_no.getText().toString();
                destination = input_destination.getText().toString();
                transporterName = input_transName.getText().toString();
                vatTinNo = input_vat_tin_no.getText().toString();

            } else {

                /*shipping adress is not empty*/
//                isShippingInfoFromText = false;
               /* pincode = shippingAddressFromDb.getPincode();
                address = shippingAddressFromDb.getAddress();
                city = shippingAddressFromDb.getCity();
                state = shippingAddressFromDb.getState();
                mark = shippingAddressFromDb.getMark();
                destination = shippingAddressFromDb.getDestination();
                transporterName = shippingAddressFromDb.getTransporterName();
                vatTinNo = shippingAddressFromDb.getVatTinNo();*/
                GST = updatedGSt;
                pincode = shippingAddressFromDb.getPincode();
                address = textViewAddress.getText().toString();
                city = textViewCity.getText().toString();
                state = textViewState.getText().toString();
//                GST = textView_GST.getText().toString();
                mark = textView_mark.getText().toString();
                destination = textView_destination.getText().toString();
                transporterName = textView_transporter_name.getText().toString();
                vatTinNo = textViewVatTinNo.getText().toString();

            }

            hideSoftKeyboard();


            final AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(ReviewProductActivity.this);


            removeItemDialog.setMessage("Are you sure you want to place order?");
            removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {


//                    if(textView_GST.getText().toString().equals("") || textView_GST.getText()== null)
//                    {
//                    }
//                    else {
                    buttonProceed.setClickable(false);
                    callToOrder(pincode,
                            address,
                            city,
                            state, mark, GST, destination, transporterName, vatTinNo, userContactNo,
                            editTextCartNote.getText().toString().isEmpty() ? "" : editTextCartNote.getText().toString());
//                    }

                }
            });
            removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    buttonProceed.setClickable(true);
                }
            });

            final AlertDialog alertDialog = removeItemDialog.create();

            Window view = ((AlertDialog) alertDialog).getWindow();
            view.setBackgroundDrawableResource(R.color.white);

            alertDialog.show();
            alertDialog.setCancelable(false);

        } else
            Toast.makeText(ReviewProductActivity.this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();
    }

    private void hideSoftKeyboard() {

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            View view = getCurrentFocus();

            if (view != null) {
                if (view.getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }
    }


    public void callTOGSTDialog() {
        final AlertDialog.Builder mAlertDialogBuilder = new AlertDialog.Builder(ReviewProductActivity.this);
        LayoutInflater inflater = ReviewProductActivity.this.getLayoutInflater();
        // inflate the custom dialog view
        final View mDialogView = inflater.inflate(R.layout.dialog_new_gst, null);
        // set the View for the AlertDialog
        mAlertDialogBuilder.setView(mDialogView);

        TextView textViewTitle = (TextView) mDialogView.findViewById(R.id.textViewTitle);
        textViewTitle.setText("Enter GST No");
        input_dialog_gst2 = (EditText) mDialogView.findViewById(R.id.input_gst_no2);

//        dialogprogressBar = ((ProgressBar) mDialogView.findViewById(R.id.progress_bar));

        Button dialogBtnOk = (Button) mDialogView.findViewById(R.id.btnOK);
        Button btnCancel = (Button) mDialogView.findViewById(R.id.btnCancel);
        final AlertDialog alertDialog = mAlertDialogBuilder.create();

        alertDialog.show();
        alertDialog.setCancelable(false);

        dialogBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!validateDialogGST())
                    Toast.makeText(ReviewProductActivity.this, "Please enter GST No", Toast.LENGTH_SHORT).show();
                else {
                    updateGST(input_dialog_gst2.getText().toString());
                    isDefaultAddress = false;
//                    textView_GST.setText(input_dialog_gst2.getText().toString());

                    alertDialog.dismiss();
                }

            }

        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Close dialog
                alertDialog.dismiss();
            }

        });

    }
  /*  private void getTaxValuesFromServer() {
        final ProgressBar progressBar = ((ProgressBar) findViewById(R.id.progress_bar));
        progressBar.setVisibility(View.VISIBLE);
        count++;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_TAXES,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            imageViewEditAddress.setOnClickListener(ReviewProductActivity.this);
                            if (responseObj.getString("status").equals("200")) {
                                JSONObject requestObj = response.getJSONObject("request");
                                //add button clicklistener in only success response
                                buttonProceed.setOnClickListener(ReviewProductActivity.this);
                                vat = requestObj.getString("configVal");
                                //get taxes n set in textview

                                setValusNCalculate();

                            } else
                                Toast.makeText(ReviewProductActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            imageViewEditAddress.setOnClickListener(ReviewProductActivity.this);
                            e.printStackTrace();
                            Toast.makeText(ReviewProductActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                imageViewEditAddress.setOnClickListener(ReviewProductActivity.this);
                Toast.makeText(ReviewProductActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        String tag_string_req = "json_request";
//        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }*/

    /*private void setValusNCalculate() {
        //TODO: Don't delete commnts
        TextView textView_vat_per = ((TextView) findViewById(R.id.textView_vat_per));
        textView_vat_per.setText("VAT (" + vat + "%) :");

//        Double shippingCharge = 100.00;
//        Double serviceTax = cartTotal * 0.05;
//        Double discount = cartTotal * 0.1;

        vatValue = (cartTotal * Double.parseDouble(vat)) / 100;
        finalAmount = Double.parseDouble(tempSharedPref.getString("subtotal", "")) + vatValue;

//        textViewShippingCharges.setText("+Rs." + shippingCharge);
//        textViewServiceCharges.setText("+Rs." + serviceTax);
//        textViewDiscount.setText("-Rs." + discount);
        textViewVat.setText("+Rs." + vatValue);
        textViewTotalAmount.setText("Rs." + Double.toString(finalAmount));

        callToGetProfileShippingAddress();

    }

*/

    private void callToOrder(String pincode, String address, String city, String state, String mark, String GST, String destination, String transporterName,
                             String vatTinNo, String contactNo,
                             String cartNote) {

        progressBar.setVisibility(View.VISIBLE);
        final DatabaseHandler databaseHandler = new DatabaseHandler(this);
        final HashMap<String, String> userDetails = databaseHandler.getUserDetails();
        JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();
        JSONArray finalArray = new JSONArray();

        try {
            requestJson.put("cartNotes", cartNote);
            requestJson.put("cartPrice", "");
            requestJson.put("subTotal", "");

            requestJson.put("isOfferApld", "false");
            requestJson.put("offerApldId", "0");
            requestJson.put("alternateCntc", contactNo);
            requestJson.put("addressType", "Shipping");
            requestJson.put("city", city);
            requestJson.put("country", "India");
            //TODO: get current latlong
            requestJson.put("latitude", "0.00");
            requestJson.put("longitude", "0.00");

            requestJson.put("postalCode", pincode);
            requestJson.put("stAddress1", address);
            requestJson.put("stAddress2", "");
            requestJson.put("stAddress3", "");
            requestJson.put("state", state);
            requestJson.put("paymentAmount", "");

            requestJson.put("paymentGateway", "");
            requestJson.put("paymentMode", "COD");
            requestJson.put("amountBal", "");
            requestJson.put("amountPaid", "");
            requestJson.put("discount", "");
            requestJson.put("discountValue", "");
            requestJson.put("grandTotal", "");

            requestJson.put("miscCharges", "0");
            requestJson.put("serviceTax", "");
            requestJson.put("serviceTaxValue", "");
            requestJson.put("shippingCharges", "");
            requestJson.put("vat", "");
            requestJson.put("vatValue", "");

            if (isDefaultAddress) {

                if (isEmptyShippingAddress) {

                    requestJson.put("isDefaultAddress", false);
                    requestJson.put("mark", mark);
                    requestJson.put("gstNo", GST);
                    requestJson.put("destination", destination);
                    requestJson.put("tranNm", transporterName);
                    requestJson.put("tinNo", vatTinNo);
                } else {
                    requestJson.put("isDefaultAddress", isDefaultAddress);
                    requestJson.put("shippingDtlsId", shippingAddressId);
                }
            } else {
                requestJson.put("isDefaultAddress", isDefaultAddress);
                requestJson.put("mark", mark);
                requestJson.put("gstNo", GST);
                requestJson.put("destination", destination);
                requestJson.put("tranNm", transporterName);
                requestJson.put("tinNo", vatTinNo);
            }

            requestJson.put("cartItemsList", cartJsonArray);
            finalArray.put(requestJson);

            finalObject.put("request", finalArray);
            Log.d(TAG, "finalObject place order" + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.POST, ConfigUrls.URL_PLACE_ORDER + userDetails.get("userTrackId"), finalObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "place order response" + response.toString());
                        Log.d(TAG, "URL place order=> " +ConfigUrls.URL_PLACE_ORDER + userDetails.get("userTrackId"));
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            if (responseObj.getString("status").equals("200")) {

                                String msg = "Order placed successfully";
                                if (!isDefaultAddress)
                                    isDefaultAddress = true;//reset after successfull order
                                onOrderPlacedSuccessfull(msg);

                                /*if (paymentMode.equals("COD")) {

//                                    Toast.makeText(PaymentActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();

                                } else if (paymentMode.equals("Online")) {


                                    JSONObject paymentUrlDetailsObj = response.getJSONObject("paymentUrlDetails");

                                    databaseHandler.clearCartTable();
                                    databaseHandler.clearShippingAddressTable();
                                    tempSharedPrefEditor.clear().commit();

                                    Intent intent = new Intent(PaymentActivity.this, WebViewPayNowActivity.class);
                                    intent.putExtra("url", paymentUrlDetailsObj.getString("value"));
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                                    finish();

                                }*/

                                //       singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            } else if (responseObj.getString("status").equals("601")) {
//in case user is not verified
                                isOrderPlaced = true;
//                                    buttonPayNow.setClickable(false);
                                buttonProceed.setClickable(false);

                                final AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(ReviewProductActivity.this);
                                removeItemDialog.setMessage(responseObj.getString("message"));
                               /* removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });*/
                                removeItemDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        onPaymentSuccessFull();
                                    }
                                });


                                final AlertDialog alertDialog = removeItemDialog.create();
                                Window alertDialogView = ((AlertDialog) alertDialog).getWindow();
                                alertDialogView.setBackgroundDrawableResource(R.color.white);
                                alertDialog.show();

                                alertDialog.setCancelable(false);
                            } else {
                                buttonProceed.setClickable(true);
                                SingletonUtil.getSingletonConfigInstance().showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            buttonProceed.setClickable(true);
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                error.printStackTrace();

                buttonProceed.setClickable(true);
                singletonUtil.showSnackBar("Unable to connect server!! Try again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void onOrderPlacedSuccessfull(String msg) {
        isOrderPlaced = true;
//                                    buttonPayNow.setClickable(false);
//        buttonProceed.setClickable(false);

        Snackbar snackbar = Snackbar
                .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), msg, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onPaymentSuccessFull();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void onPaymentSuccessFull() {
        databaseHandler.clearCartTable();
        databaseHandler.clearCartSubcategoryTable();
        databaseHandler.clearShippingAddressTable();
        editor.clear().commit();

        Intent intent = new Intent(ReviewProductActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }


    private boolean validate() {
        boolean valid = true;
        String pincode = editTextPinCode.getText().toString();
        String address = editTextAddress.getText().toString();
        String mark = input_mark.getText().toString();
        String dest = input_destination.getText().toString();
        String transName = input_transName.getText().toString();
        String GST = input_gst_no.getText().toString();

        if (pincode.isEmpty() || pincode.length() < 6) {
            editTextPinCode.setError(Html.fromHtml("<font color='red'>Enter at least 6 numbers</font>"));
            requestFocus(editTextPinCode);
            valid = false;
        } else
            editTextPinCode.setError(null);

        if (address.isEmpty()) {
            editTextAddress.setError(Html.fromHtml("<font color='red'>Enter valid address name</font>"));
            requestFocus(editTextAddress);
            valid = false;
        } else
            editTextAddress.setError(null);

        if (mark.isEmpty()) {
            input_mark.setError(Html.fromHtml("<font color='red'>Enter valid Mark</font>"));
            requestFocus(input_mark);
            valid = false;
        } else
            input_mark.setError(null);


        if (dest.isEmpty()) {
            input_destination.setError(Html.fromHtml("<font color='red'>Enter valid destination</font>"));
            requestFocus(input_destination);
            valid = false;
        } else
            input_destination.setError(null);

        if (transName.isEmpty()) {
            input_transName.setError(Html.fromHtml("<font color='red'>Enter valid transporter Name</font>"));
            requestFocus(input_transName);
            valid = false;
        } else
            input_transName.setError(null);

//        if (GST.isEmpty()) {
//            input_gst_no.setError(Html.fromHtml("<font color='red'>Enter GST No</font>"));
//            requestFocus(input_gst_no);
//            valid = false;
//        } else
//            input_gst_no.setError(null);


        return valid;
    }


    private boolean validateDialog() {
        boolean valid = true;
        String dilog_mark = input_dialog_mark.getText().toString();
        String dialog_destination = input_dialog_destination.getText().toString();
        String dialog_transName = input_dialog_transName.getText().toString();
        String dialog_gst_no = input_dialog_gst.getText().toString();


        if (dilog_mark.isEmpty()) {
            input_dialog_mark.setError(Html.fromHtml("<font color='red'>Enter Mark</font>"));
            requestFocus(input_dialog_mark);
            valid = false;
        } else
            input_dialog_mark.setError(null);

        if (dialog_destination.isEmpty()) {
            input_dialog_destination.setError(Html.fromHtml("<font color='red'>Enter Destination</font>"));
            requestFocus(input_dialog_destination);
            valid = false;
        } else
            input_dialog_destination.setError(null);

        if (dialog_transName.isEmpty()) {
            input_dialog_transName.setError(Html.fromHtml("<font color='red'>Enter Transporter Name</font>"));
            requestFocus(input_dialog_transName);
            valid = false;
        } else
            input_dialog_transName.setError(null);


//        if (dialog_gst_no.isEmpty()) {
//            input_dialog_gst.setError(Html.fromHtml("<font color='red'>Enter GST no</font>"));
//            requestFocus(input_dialog_gst);
//            valid = false;
//        } else
//            input_dialog_gst.setError(null);

        return valid;
    }


    private boolean validateDialogGST() {
        boolean valid = true;
        String dialog_gst_no = input_dialog_gst2.getText().toString();


        if (dialog_gst_no.isEmpty()) {
            input_dialog_gst2.setError(Html.fromHtml("<font color='red'>Enter GST no</font>"));
            requestFocus(input_dialog_gst2);
            valid = false;
        } else
            input_dialog_gst2.setError(null);

        return valid;
    }


    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    /**
     * call to get current shipping address details from server
     */

    private void callToGetProfileShippingAddress() {

        progressBar.setVisibility(View.VISIBLE);

        /*JSONObject requestJson = new JSONObject();
        JSONObject finalObject = new JSONObject();
        try {
            requestJson.put("firstName", editTextFirstName.getText().toString());
            finalObject.put("userDetails", requestJson);
            Log.d(TAG, "finalObject" + finalObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }*/

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_PROFILE + userDetails.get("userTrackId"),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                JSONObject userDetailsObj = response.getJSONObject("userDetails");

                                shippingAddressId = Integer.parseInt(userDetailsObj.getString("otherAddressId"));

                                String postalCode = userDetailsObj.getString("postalCode").equals(null) ? "" : userDetailsObj.getString("postalCode");
                                String address = userDetailsObj.getString("stAddress1").equals(null) ? "" : userDetailsObj.getString("stAddress1");
                                String city = userDetailsObj.getString("city").equals(null) ? "" : userDetailsObj.getString("city");
                                String state = userDetailsObj.getString("state").equals(null) ? "" : userDetailsObj.getString("state");
                                String mark = userDetailsObj.getString("mark").equals(null) ? "" : userDetailsObj.getString("mark");
                                String destination = userDetailsObj.getString("destination").equals(null) ? "" : userDetailsObj.getString("destination");
                                String transporterName = userDetailsObj.getString("tranNm").equals(null) ? "" : userDetailsObj.getString("tranNm");
                                String tinNo = userDetailsObj.getString("tinNo").equals(null) ? "" : userDetailsObj.getString("tinNo");
                                String GST = userDetailsObj.getString("gstNo").equals(null) ? "" : userDetailsObj.getString("gstNo");
                                ShippingAddress shippingAddress = new ShippingAddress(shippingAddressId,
                                        postalCode,
                                        address,
                                        city,
                                        state,
                                        mark,
                                        destination,
                                        transporterName,
                                        tinNo, GST);

                                databaseHandler.addShippingAddress(shippingAddress);

                                //set shipping address layout according to it's null or not null
                                RelativeLayout relativeLayoutForShippingAddress = (RelativeLayout) findViewById(R.id.relativeLayoutForShippingAddress);
                                RelativeLayout relativeLayoutForNotShippingAddress = (RelativeLayout) findViewById(R.id.relativeLayoutForNotShippingAddress);

                                shippingAddressFromDb = databaseHandler.getShippingAddress();

                                if (shippingAddressFromDb == null) {
                                    System.out.println("IN NULL DATA");
//            if (shippingAddressFromDb.getCity().equals("null") || shippingAddressFromDb.getAddress().equals("null") || shippingAddressFromDb.getPincode().equals("null") || shippingAddressFromDb.getState().equals("null")) {
                                    relativeLayoutForNotShippingAddress.setVisibility(View.VISIBLE);
                                    relativeLayoutForShippingAddress.setVisibility(View.GONE);
                                    //shown editable fiels for address
                                    isEmptyShippingAddress = true;

                                    showDialogMessage("Update your shipping address and GST detail from profile");

                                    ArrayList<String> cityStringList = new ArrayList<>();

                                    cityStringList.add(SELECT_CITY);

                                    ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(ReviewProductActivity.this,
                                            R.layout.simple_spinner_dropdown_item, cityStringList);
                                    spinnerCity.setAdapter(cityAdapter);
                                    callTogetState();
//            }
                                } else {
                                    System.out.println("IN  DATA");
                                    if (shippingAddressFromDb.getCity().equals("null") || shippingAddressFromDb.getCity().isEmpty()
                                            || shippingAddressFromDb.getAddress().equals("null") || shippingAddressFromDb.getAddress().isEmpty()
                                            || shippingAddressFromDb.getPincode().equals("null") || shippingAddressFromDb.getPincode().isEmpty()
                                            || shippingAddressFromDb.getState().equals("null") || shippingAddressFromDb.getState().isEmpty()
                                            || shippingAddressFromDb.getMark().equals("null") || shippingAddressFromDb.getMark().isEmpty()
                                            || shippingAddressFromDb.getDestination().equals("null") || shippingAddressFromDb.getDestination().isEmpty()
                                            || shippingAddressFromDb.getTransporterName().equals("null") || shippingAddressFromDb.getTransporterName().isEmpty())
//                                            || shippingAddressFromDb.getGSTNo().equals("null") || shippingAddressFromDb.getGSTNo().isEmpty())
                                    {
                                        relativeLayoutForNotShippingAddress.setVisibility(View.VISIBLE);
                                        relativeLayoutForShippingAddress.setVisibility(View.GONE);

                                        isEmptyShippingAddress = true;
                                        showDialogMessage("Update your shipping address detail from profile");
                                        callTogetState();
                                        callTogetState();

                                    } else {

                                        relativeLayoutForNotShippingAddress.setVisibility(View.GONE);
                                        relativeLayoutForShippingAddress.setVisibility(View.VISIBLE);
                                        isEmptyShippingAddress = false;

                                        textViewAddress.setText(shippingAddressFromDb.getAddress());
                                        //BATMAN
                                        System.out.println("BATMAN==> " + shippingAddressFromDb.getGSTNo());
                                        textView_GST.setText(shippingAddressFromDb.getGSTNo());
                                        textViewCity.setText(shippingAddressFromDb.getCity());
                                        textViewPinCode.setText(" - " + shippingAddressFromDb.getPincode());
                                        textViewContactNo.setText(userContactNo);
                                        textViewState.setText(shippingAddressFromDb.getState());
                                        textView_mark.setText(shippingAddressFromDb.getMark());
//                                        textViewEmail.setText(shippingAddressFromDb.());
                                        textView_destination.setText(shippingAddressFromDb.getDestination());
                                        textView_transporter_name.setText(shippingAddressFromDb.getTransporterName());
                                        textViewVatTinNo.setText(shippingAddressFromDb.getVatTinNo());

                                    }
                                }

                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            onError();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } finally {
                            //on updation of address from profile reset the flag
                            isDefaultAddress = true;
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                onError();
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void showDialogMessage(String title) {

        final AlertDialog.Builder dialog = new AlertDialog.Builder(ReviewProductActivity.this);

        dialog.setMessage(title);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);

            }
        });
        dialog.setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();

        final AlertDialog alertDialog = dialog.create();
        Window alertDialogView = ((AlertDialog) alertDialog).getWindow();
        alertDialogView.setBackgroundDrawableResource(R.color.white);
        alertDialog.show();


    }

    private void onError() {
        Snackbar snackbar = Snackbar
                .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), "Unable to connect server!! Press OK to try again..", Snackbar.LENGTH_INDEFINITE)

                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callToGetProfileShippingAddress();
                    }
                });
        // Changing message text color
        snackbar.setActionTextColor(Color.WHITE);
        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    private void callTogetState() {
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_STATE,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            int currentStateID = 0;
                            if (responseObj.getString("status").equals("200")) {
                                JSONArray userDetailsArrayObj = response.getJSONArray("response");
                                ArrayList<String> stateStringList = new ArrayList<>();

                                stateStringList.add(SELECT_STATE);
                                State state = null;

                                for (int i = 0; i < userDetailsArrayObj.length(); i++) {
//                                    currentState = userDetailsArrayObj.getJSONObject(0).getString("locationName");
                                    currentStateID = userDetailsArrayObj.getJSONObject(0).getInt("locationId");
                                    JSONObject jsonObject = userDetailsArrayObj.getJSONObject(i);
                                    state = new State(jsonObject.getInt("locationId"), jsonObject.getString("locationName"));
                                    stateList.add(state);
                                    stateStringList.add(jsonObject.getString("locationName"));
                                }

                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ReviewProductActivity.this,
                                        R.layout.simple_spinner_dropdown_item, stateStringList);
                                spinnerState.setAdapter(stateAdapter);

//                                callTogetCity(currentStateID);

                              /*  spinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                        if (currentState != spinnerState.getItemAtPosition(position).toString()) {
                                            ArrayAdapter<String> emptyAdapter = new ArrayAdapter<String>(ProfileActivity.this,
                                                    R.layout.category_simple_spinner_item_category, new ArrayList<String>());
                                            spinnerCity.setAdapter(emptyAdapter);
                                            State state = stateList.get(position);
                                            callTogetCity(state.getStateId());
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });*/


                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                error.printStackTrace();
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    private void callTogetCity(int stateId) {
        progressBar.setVisibility(View.VISIBLE);
        String urlStr = ConfigUrls.URL_GET_CITY + "?stateId=" + stateId;
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URI uri = null;
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        progressBar.setVisibility(View.GONE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            if (responseObj.getString("status").equals("200")) {
                                JSONArray userDetailsArrayObj = response.getJSONArray("response");
                                ArrayList<String> cityList = new ArrayList<>();
                                cityList.add(SELECT_CITY);
                                for (int i = 0; i < userDetailsArrayObj.length(); i++) {
//                                    currentState = userDetailsArrayObj.getJSONObject(0).getString("locationName");
                                    JSONObject jsonObject = userDetailsArrayObj.getJSONObject(i);
                                    cityList.add(jsonObject.getString("locationName"));
                                }

                                ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(ReviewProductActivity.this,
                                        R.layout.simple_spinner_dropdown_item, cityList);
                                spinnerCity.setAdapter(stateAdapter);

                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                error.printStackTrace();
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    private void updateGST(String GSTNo) {

        progressBar.setVisibility(View.VISIBLE);
        String urlStr = ConfigUrls.URL_GST + "/" + userDetails.get("userTrackId") + "/" + GSTNo;
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URI uri = null;
        try {
            uri = new URI(url.getProtocol(), url.getUserInfo(), url.getHost(), url.getPort(), url.getPath(), url.getQuery(), url.getRef());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        try {
            url = uri.toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "GST URL" + url.toString());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, url.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "GST " + response.toString());

                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");

                            if (responseObj.getString("status").equals("200")) {
                                textView_GST.setText(input_dialog_gst2.getText().toString());
                                updatedGSt = response.get("gstNo").toString();
                                System.out.println("updatedGSt  " + updatedGSt);
//                        newDialog();
                                callForPlaceOrder();
                                progressBar.setVisibility(View.GONE);

                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);

                error.printStackTrace();
                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

}
