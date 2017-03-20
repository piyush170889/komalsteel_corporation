package com.replete.komalapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
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
import com.replete.komalapp.rowitem.CartProducts;
import com.replete.komalapp.rowitem.CartSubcategoryInfo;
import com.replete.komalapp.rowitem.ProductTypes;
import com.replete.komalapp.rowitem.Products;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 09-Jul-16.
 */
public class ProductDetailNewActivity extends AppCompatActivity implements View.OnClickListener {

    private int TIME_OUT_IN_MS = 20000;
    public int RETRY_COUNT = 0;
    private float BACKOFF_MULTIPLIER = 1F;
    //    private static final String AVATAR_URL = "http://javatechig.com/wp-content/uploads/2015/10/Bootstrap-Tutorials.png";
    private static final String TAG = "ProductDetailActivity";
    //    private ArrayList<String> priceList;
//    private TextView textViewCost, textTotalItem;
//    TextView textTotalCostTocart;
    //    private ImageView imageViewAddItem, imageViewRemoveItem;
//    private int itemCount;
//    private ViewPager viewpager;
    private BadgeStyle style = ActionItemBadge.BadgeStyles.GREY.getStyle();
    private Button buttonAddToCart;
    private int badgeCount = 0;
    private TextView textViewItemName;
    private DatabaseHandler databaseHandler;
    private List<CartProducts> cartProductList;
    private TableLayout tablePackagingInfo;
    private int tableIdCount = 1;
    //    private TextView textViewDescription;
    //    private RadioGroup radioGrpProductType;
    private RadioButton rbProductType;
    //    private TextView textViewAvailableItemsInCart;
    private ProgressBar progressBar;
    private List<ProductTypes> productTypesList;
    private int count = 0;

    private Double totalPayableAmount = 0.0;
    private SingletonUtil singletonUtilObj = SingletonUtil.getSingletonConfigInstance();
    private BroadcastReceiver myWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if wifi is ON or mobile connection is ON
            if (singletonUtilObj.checkNetConnection(ProductDetailNewActivity.this)) {
                if (0 == count) {
                    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                    callToProductDetails(getIntent().getExtras().getString("SubCategoryId"), getIntent().getExtras().getString("CategoryId"), getIntent().getExtras().getString("SubCategoryName"));
                }
            }
        }
    };

    //    private Bitmap productBitmap;
    private SharedPreferences cartProductSharedPref;
    private ArrayList<Integer> productIdList = new ArrayList<>();
    private int itemSubCategoryId;
    private String subCategoryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        //register broadcast receiver
        this.registerReceiver(this.myWifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        initToolbar();
        initComponents();

        subCategoryName = getIntent().getExtras().getString("SubCategoryName");
//        initTable();
        //Viewpager
//        viewpager = (ViewPager) findViewById(R.id.viewpager);
       /* CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        viewpager.setAdapter(new DemoPagerAdapter());
        indicator.setViewPager(viewpager);*/


//        textViewCost.setText("0.0");
//        textTotalCostTocart.setText("0.0");

        if (singletonUtilObj.isConnectingToInternet(this)) {
            if (0 == count) {
                Log.d(TAG, "onCreate: SubCategoryId=" + getIntent().getExtras().getString("SubCategoryId") + " CategoryId=" + getIntent().getExtras().getString("CategoryId"));
                callToProductDetails(getIntent().getExtras().getString("SubCategoryId"), getIntent().getExtras().getString("CategoryId"), subCategoryName);
            }
        } else
            Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();

//        imageViewAddItem.setOnClickListener(this);
//        imageViewRemoveItem.setOnClickListener(this);
        buttonAddToCart.setOnClickListener(this);
//        textViewShowDescriptionLink.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        //unregister broadcast receiver on Destroy of activity
        if (myWifiReceiver != null)
            this.unregisterReceiver(myWifiReceiver);
        super.onDestroy();
    }

    private void updateUI(String description, List<ProductTypes> productTypesList, String subCategoryName) {
//        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
//        viewpager.setAdapter(new DemoPagerAdapter());
        //save one image of product to display it in cart
//        productBitmap = singletonUtilObj.getBitmapFromString(imageString.get(0));

        //add list of image string to adapter  for slideshow of images
//        viewpager.setAdapter(new DemoPagerProductViewAdapter(imageString));
//        indicator.setViewPager(viewpager);

        //set product name and description
//        TextView textView_description_title = (TextView) findViewById(R.id.textView_description_title);
//        textView_description_title.setVisibility(View.VISIBLE);
        TextView textView_packaging_info_title = (TextView) findViewById(R.id.textView_packaging_info_title);
        textView_packaging_info_title.setVisibility(View.VISIBLE);
        tablePackagingInfo.setVisibility(View.VISIBLE);


        textViewItemName.setText(subCategoryName);
//        textViewDescription.setText(description);
        ProductTypes productTypes = null;

        //add all products with type in radiobuttons and in table
        for (int i = 0; i < productTypesList.size(); i++) {
            productTypes = productTypesList.get(i);

            //adds each product type and price in table
            addProductTypesToTable(productTypes.getProductID(), productTypes.getProductName(), productTypes.getProductSize(), productTypes.getProductMasterCartonQtyRange(), productTypes.getProductMasterCartonQtyIncVal());
        }


    }

   /* private void addProductTypesToTable(int productID, String productName, String productSize, String productMasterCartonQtyRange, String productMasterCartonQtyIncVal) {
        TableRow tr = new TableRow(ProductDetailNewActivity.this);
        tr.setMinimumHeight(200);
        //        tr.setId(1);

        String sizeOfName[] = productName.split(" ");
       *//* if (sizeOfName.length > 5) {

            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    50, 1.0f));
        } else {

            tr.setLayoutParams(new TableLayout.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        }*//*

       *//* RelativeLayout.LayoutParams Lparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams.setMargins(2, 2, 2, 2);

        RelativeLayout rl = new RelativeLayout(this);
        rl.setBackgroundResource(R.color.aluminum);
        rl.setLayoutParams(Lparams);*//*

        //Create two columns to add as table data
        // Create a TextView to add date


        TextView tvId = new TextView(ProductDetailNewActivity.this);
        tvId.setId(0 + 0);

//        if (sizeOfName.length > 7)
//            tvId.setHeight(200);
//        else
//            tvId.setHeight(100);
        tvId.setText(tableIdCount + "");
        tvId.setTypeface(null, Typeface.BOLD);
        tvId.setPadding(2, 5, 2, 0);
        tvId.setGravity(Gravity.CENTER);
        tvId.setBackgroundResource(R.drawable.cell_shape);
//        rl.addView(tvId);
//        tr.addView(tvId);


        *//*View view1 = new View(this);
        view1.setBackgroundColor(Color.BLACK);
        tr.addView(view1, new TableRow.LayoutParams(-1, TableRow.LayoutParams.MATCH_PARENT));*//*


       *//* RelativeLayout.LayoutParams Lparams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams2.setMargins(2, 2, 2, 2);

        RelativeLayout rl2 = new RelativeLayout(this);
        rl2.setBackgroundResource(R.color.aluminum);
        rl2.setLayoutParams(Lparams2);
*//*
        TextView tvproductName = new TextView(ProductDetailNewActivity.this);
        tvproductName.setId(1 + 0);
//        if (sizeOfName.length > 7)
//            tvproductName.setHeight(200);
//        else
//            tvproductName.setHeight(100);
       *//* String str1 = null, str2 = null;
        if (sizeOfName.length > 5) {
            for (int i = 0; i < 5; i++) {
                str1 = sizeOfName[i] + " ";
            }

            for (int i = 5; i < sizeOfName.length; i++) {
                str2 = sizeOfName[i] + " ";
            }
        }*//*
        tvproductName.setMaxLines(3);
        tvproductName.setMaxWidth(250);
        tvproductName.setText(productName);
        tvproductName.setPadding(2, 5, 2, 0);
        tvproductName.setGravity(Gravity.CENTER);
        tvproductName.setBackgroundResource(R.drawable.cell_shape);
//        rl2.addView(tvproductName);
//        tr.addView(tvproductName);

        *//*View view2 = new View(this);
        view2.setBackgroundColor(Color.BLACK);
        tr.addView(view2, new TableRow.LayoutParams(-1, TableRow.LayoutParams.MATCH_PARENT));*//*


       *//* RelativeLayout.LayoutParams Lparams3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams3.setMargins(2, 2, 2, 2);

        RelativeLayout rl3 = new RelativeLayout(this);
        rl3.setBackgroundResource(R.color.aluminum);
        rl3.setLayoutParams(Lparams3);*//*

        TextView tvproductType = new TextView(this);
        tvproductType.setId(2 + 0);

//        if (sizeOfName.length > 7)
//            tvproductType.setHeight(200);
//        else
//            tvproductType.setHeight(100);

        tvproductType.setText(productSize);
        tvproductType.setPadding(2, 5, 2, 0);
        tvproductType.setGravity(Gravity.CENTER);
        tvproductType.setBackgroundResource(R.drawable.cell_shape);
//        rl3.addView(tvCartonSize);

//        ViewGroup.LayoutParams paramsSize = tvproductType.getLayoutParams();
//        paramsSize.height = 70;
//        tvproductType.setLayoutParams(paramsSize);
//        tr.addView(tvproductType);

      *//*  View view3 = new View(this);
        view3.setBackgroundColor(Color.BLACK);
        tr.addView(view3, new TableRow.LayoutParams(-1, TableRow.LayoutParams.MATCH_PARENT));*//*


       *//* RelativeLayout.LayoutParams Lparams4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams4.setMargins(2, 2, 2, 2);

        RelativeLayout rl4 = new RelativeLayout(this);
        rl4.setBackgroundResource(R.color.aluminum);
        rl4.setLayoutParams(Lparams4);
*//*

        String range[] = productMasterCartonQtyRange.split("-");
        int incValue = Integer.parseInt(productMasterCartonQtyIncVal);
        Products products;
//        List<Products> spinnerProductList = new ArrayList<>();
        ArrayList<Integer> quantityArrayList = new ArrayList<>();
        for (int i = Integer.parseInt(range[0]); i <= Integer.parseInt(range[1]); i += incValue) {
            quantityArrayList.add(i);
//            products = new Products(productID, i + "");
//            spinnerProductList.add(products);
        }


//        Spinner
//        LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View spinnerLayout = inflater.inflate(R.layout.spinner_layout_for_table, null);
//        Spinner spinner = (Spinner) spinnerLayout.findViewById(R.id.spinner_for_table);

//        Spinner spinner = new Spinner(ProductDetailNewActivity.this, Spinner.MODE_DIALOG);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout = inflater.inflate(R.layout.spinner, null);
        Spinner spinner = (Spinner) mLinearLayout.findViewById(R.id.spinnerCustom);
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(ProductDetailNewActivity.this, R.layout.category_simple_spinner_item_category, quantityArrayList);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setId(productID + 1000);
        spinner.setGravity(Gravity.CENTER);

//        if (sizeOfName.length > 7)
//            spinner.setMinimumHeight(200);
//        else
//            spinner.setMinimumHeight(100);
//        spinner.setBackgroundResource(R.drawable.cell_shape);
//        spinner.setMinimumHeight(70);
//        ViewGroup.LayoutParams paramsQty = spinner.getLayoutParams();
//        paramsQty.height = 70;
//        spinner.setLayoutParams(paramsQty);

//        tr.addView(spinner);
*//*


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String value = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*//*


//        ViewGroup.LayoutParams paramsId = tvId.getLayoutParams();
//        paramsId.height = tvproductName.getHeight();
//        tvId.setLayoutParams(paramsId);


//        tvId.setHeight(height);
//        tvproductType.setHeight(height);
//        spinner.setMinimumHeight(height);

        tr.addView(tvId);
        tr.addView(tvproductName);
        tr.addView(tvproductType);
        tr.addView(spinner);

        tablePackagingInfo.addView(tr, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tableIdCount++;
    }
*/

    private void addProductTypesToTable(int productID, String productName, String productSize, String productMasterCartonQtyRange, String productMasterCartonQtyIncVal) {
       /* TableRow tr = new TableRow(ProductDetailNewActivity.this);
        tr.setMinimumHeight(200);

        String sizeOfName[] = productName.split(" ");

        TextView tvId = new TextView(ProductDetailNewActivity.this);
        tvId.setId(0 + 0);
        tvId.setText(tableIdCount + "");
        tvId.setTypeface(null, Typeface.BOLD);
        tvId.setPadding(2, 5, 2, 0);
        tvId.setGravity(Gravity.CENTER);
        tvId.setBackgroundResource(R.drawable.cell_shape);

        TextView tvproductName = new TextView(ProductDetailNewActivity.this);
        tvproductName.setId(1 + 0);
        tvproductName.setMaxLines(3);
        tvproductName.setMaxWidth(250);
        tvproductName.setText(productName);
        tvproductName.setPadding(2, 5, 2, 0);
        tvproductName.setGravity(Gravity.CENTER);
        tvproductName.setBackgroundResource(R.drawable.cell_shape);

        TextView tvproductType = new TextView(this);
        tvproductType.setId(2 + 0);
        tvproductType.setText(productSize);
        tvproductType.setPadding(2, 5, 2, 0);
        tvproductType.setGravity(Gravity.CENTER);
        tvproductType.setBackgroundResource(R.drawable.cell_shape);
        */

        String range[] = productMasterCartonQtyRange.split("-");
        int incValue = Integer.parseInt(productMasterCartonQtyIncVal);
        Products products;
//        List<Products> spinnerProductList = new ArrayList<>();
        ArrayList<Integer> quantityArrayList = new ArrayList<>();
        for (int i = Integer.parseInt(range[0]); i <= Integer.parseInt(range[1]); i += incValue) {
            quantityArrayList.add(i);
//            products = new Products(productID, i + "");
//            spinnerProductList.add(products);
        }

//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View mLinearLayout = inflater.inflate(R.layout.spinner, null);
//        Spinner spinner = (Spinner) mLinearLayout.findViewById(R.id.spinnerCustom);


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout = inflater.inflate(R.layout.tablerow, null);

        TextView tvId = (TextView) mLinearLayout.findViewById(R.id.textViewTableRowId);
        TextView tvproductName = (TextView) mLinearLayout.findViewById(R.id.textViewTableRowProduct);
        TextView tvproductType = (TextView) mLinearLayout.findViewById(R.id.textViewTableRowSize);


        Spinner spinner = (Spinner) mLinearLayout.findViewById(R.id.spinnerTableRowQty);
        TableRow tableRow = (TableRow) mLinearLayout.findViewById(R.id.tableRow);

        tvId.setText(tableIdCount + "");
        tvproductName.setText(productName);
        tvproductType.setText(productSize);


        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(ProductDetailNewActivity.this, R.layout.simple_spinnet_item, quantityArrayList);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setPopupBackgroundResource(R.color.white);

        }*/

        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setId(productID + 1000);

//        tr.addView(tvId);
//        tr.addView(tvproductName);
//        tr.addView(tvproductType);
//        tr.addView(spinner);

        tablePackagingInfo.addView(tableRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));
        tableIdCount++;
    }


    /*//set table header row in table
    private void initTable() {

        TableRow tr_head = new TableRow(this);
//        View viewLayout= LayoutInflater.from(this).inflate(R.layout.view, null);

        tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f));

        *//*RelativeLayout.LayoutParams Lparams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams.setMargins(2, 2, 2, 2);

        RelativeLayout rl = new RelativeLayout(this);
        rl.setBackgroundResource(R.color.aluminum);
        rl.setLayoutParams(Lparams);*//*


        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout = inflater.inflate(R.layout.textview, null);
        TextView labelInvoiceNo = (TextView) mLinearLayout.findViewById(R.id.textViewCustom);

//        TextView labelInvoiceNo = new TextView(this);
        labelInvoiceNo.setText(" Sr. \n No.");
//        labelInvoiceNo.setTypeface(null, Typeface.BOLD);
//        labelInvoiceNo.setPadding(2, 0, 2, 0);
//        labelInvoiceNo.setGravity(Gravity.CENTER);
//        labelInvoiceNo.setHeight(200);
//        labelInvoiceNo.setBackgroundResource(R.drawable.cell_shape);
//        rl.addView(labelInvoiceNo);
        tr_head.addView(labelInvoiceNo);// add the column to the table row here

       *//* View view1 = new View(this);
        view1.setBackgroundColor(Color.BLACK);
        tr_head.addView(view1, new TableRow.LayoutParams(-1,TableRow.LayoutParams.MATCH_PARENT ));*//*


       *//* RelativeLayout.LayoutParams Lparams2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams2.setMargins(2, 2, 2, 2);

        RelativeLayout rl2 = new RelativeLayout(this);
        rl2.setBackgroundResource(R.color.aluminum);
        rl2.setLayoutParams(Lparams2);
*//*

        LayoutInflater inflater2 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout2 = inflater2.inflate(R.layout.textview, null);
        TextView labelInfo = (TextView) mLinearLayout2.findViewById(R.id.textViewCustom);
//        TextView labelInfo = new TextView(this);
        labelInfo.setText(" Product \n Name "); // set the text for the header
//        labelInfo.setTypeface(null, Typeface.BOLD);
//        labelInfo.setPadding(2, 0, 2, 0);
//        labelInfo.setGravity(Gravity.CENTER);
        // set the padding (if required)
//        labelInfo.setHeight(200);
//        labelInfo.setBackgroundResource(R.drawable.table_headers_bkg);
//        labelInfo.setBackgroundResource(R.drawable.cell_shape);
//        rl2.addView(labelInfo);
        tr_head.addView(labelInfo); //add the column to the table row here


        *//*View view2 = new View(this);
        view2.setBackgroundColor(Color.BLACK);
        tr_head.addView(view2, new TableRow.LayoutParams(-1, TableRow.LayoutParams.MATCH_PARENT));*//*


       *//* RelativeLayout.LayoutParams Lparams3 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams3.setMargins(2, 2, 2, 2);

        RelativeLayout rl3 = new RelativeLayout(this);
        rl3.setBackgroundResource(R.color.aluminum);
        rl3.setLayoutParams(Lparams3);*//*


        LayoutInflater inflater3 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout3 = inflater3.inflate(R.layout.textview, null);
        TextView labelCartoon = (TextView) mLinearLayout3.findViewById(R.id.textViewCustom);

//        TextView labelCartoon = new TextView(this);
        labelCartoon.setText(" Size ");
//        labelCartoon.setTypeface(null, Typeface.BOLD);
//        labelCartoon.setGravity(Gravity.CENTER);
//        labelCartoon.setPadding(2, 5, 2, 0);
//        labelCartoon.setHeight(200);
//        labelCartoon.setBackgroundResource(R.drawable.cell_shape);
//        rl3.addView(labelCartoon);
        tr_head.addView(labelCartoon);// add the column to the table row here


       *//* View view3 = new View(this);
        view3.setBackgroundColor(Color.BLACK);
        tr_head.addView(view3, new TableRow.LayoutParams(-1, TableRow.LayoutParams.MATCH_PARENT));*//*


       *//* RelativeLayout.LayoutParams Lparams4 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        Lparams4.setMargins(2, 2, 2, 2);

        RelativeLayout rl4 = new RelativeLayout(this);
        rl4.setBackgroundResource(R.color.aluminum);
        rl4.setLayoutParams(Lparams4);
*//*

        LayoutInflater inflater4 = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout4 = inflater4.inflate(R.layout.textview, null);
        TextView labelPerPrice = (TextView) mLinearLayout4.findViewById(R.id.textViewCustom);

//        TextView labelPerPrice = new TextView(this);
        labelPerPrice.setText("Qty");
//        labelPerPrice.setTypeface(null, Typeface.BOLD);
//        labelPerPrice.setGravity(Gravity.CENTER);
//        labelPerPrice.setPadding(2, 5, 2, 0);
//        labelPerPrice.setHeight(200);
//        labelPerPrice.setBackgroundResource(R.drawable.cell_shape);
//        rl4.addView(labelPerPrice);// add the column to the table row here
        tr_head.addView(labelPerPrice);

        tablePackagingInfo.addView(tr_head, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));

    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        /*if (badgeCount == 0) {
            //hide cart icon If count is 0
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        } else {*/
        //Update cart items number on incrementing count
        ActionItemBadge.update(this, menu.findItem(R.id.item_samplebadge), FontAwesome.Icon.faw_shopping_cart, style, badgeCount);
//        }
        //        new ActionItemBadgeAdder().act(this).menu(menu).title(R.string.sample_2).itemDetails(0, SAMPLE2_ID, 1).showAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS).add(bigStyle, 1);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_samplebadge:
                //Redirect to CartActivity on click of cart image in menu
                Intent intentToCart = new Intent(ProductDetailNewActivity.this, CartActivity.class);
                startActivity(intentToCart);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

//        itemCount = 0;
//        textTotalItem.setText(Integer.toString(itemCount));
//        textTotalCostTocart.setText("0.0");

        /*try {
            if (productTypesList.size() > 0) {
                Log.d(TAG, "getCheckedRadioButtonId() OnResume" + radioGrpProductType.getCheckedRadioButtonId());
                Log.d(TAG, "Price List Size on resume : " + productTypesList.size());
                rbProductType = (RadioButton) findViewById(radioGrpProductType.getCheckedRadioButtonId());
                if (radioGrpProductType != null) {
                    int selectedProductIndex = radioGrpProductType.getCheckedRadioButtonId();

*//*
                    int indexCount = 0;
                    int actualProductId = selectedProductIndex / 1000;
                    for (Integer productId : productIds) {
                        if (productId == actualProductId) {
                            break;
                        }
                        ++indexCount;
                    }*//*
                    ProductTypes productTypes = productTypesList.get(selectedProductIndex - 1000);
                    textViewCost.setText(productTypes.getPerProductCost());
                    showItemsNumberInCart(Integer.toString(productTypes.getProductID()), rbProductType.getText().toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cartProductSharedPref.edit().clear().commit();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void initComponents() {
        databaseHandler = new DatabaseHandler(this);
        cartProductList = new ArrayList<>();
        productTypesList = new ArrayList<>();
        textViewItemName = (TextView) findViewById(R.id.text_image_title);
//        textViewCost = (TextView) findViewById(R.id.text_cost);
//        textTotalItem = (TextView) findViewById(R.id.text_item_count);
//        textTotalCostTocart = (TextView) findViewById(R.id.text_toal_cost_to_cart);
//        imageViewAddItem = (ImageView) findViewById(R.id.imageview_add_item);
//        imageViewRemoveItem = (ImageView) findViewById(R.id.imageview_remove_item);
        buttonAddToCart = (Button) findViewById(R.id.button_add_to_cart);
        tablePackagingInfo = (TableLayout) findViewById(R.id.tablePackagingInfo);
//        textViewShowDescriptionLink = (TextView) findViewById(R.id.textView_link_description);
//        textViewDescription = (TextView) findViewById(R.id.textView_description);
//        radioGrpProductType = (RadioGroup) findViewById(R.id.radioGroupProductType);
//        textViewAvailableItemsInCart = ((TextView) findViewById(R.id.textView_available_items_in_cart));
        progressBar = ((ProgressBar) findViewById(R.id.progress_bar));

//        textViewShowDescriptionLink.setText("View Description");
//        textViewDescription.setVisibility(View.GONE);

        cartProductSharedPref = getSharedPreferences(Constants.CART_PRODUCT_PREF_KEY, Context.MODE_PRIVATE);
    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_keyboard_backspace_white_24dp);
        getSupportActionBar().setTitle("Product Details");
    }

    /**
     * implement changes in price according to selected product type
     */
   /* private void checkChangeListenerForRadiobuttons() {
//set price according to radiogroup selected
        radioGrpProductType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int position) {
                //get selected radiobutton in radiogroup
                int selectedProduct = radioGrpProductType.getCheckedRadioButtonId();
                Log.d(TAG, "On checkedchange" + " position=" + position + "selectedProduct=" + selectedProduct);
                RadioButton rbProductType = (RadioButton) findViewById(selectedProduct);
                //set selected item count 0
                itemCount = 0;
                textTotalItem.setText(Integer.toString(itemCount));
                //set total cost adde dto cart 0
                textTotalCostTocart.setText("0.0");


                //set cost of selected radio button's product type and set other calculations according to it
                 *//*int indexCount = 0;
                int actualProductId = position / 1000;
                for (Integer productId : productIds) {
                    if (productId == actualProductId) {
                        break;
                    }
                    ++indexCount;
                }*//*
                ProductTypes productTypes = productTypesList.get(position - 1000);
                textViewCost.setText(productTypes.getPerProductCost());
                float perProductCost = Float.parseFloat(textViewCost.getText().toString());
                float totalCost = perProductCost * itemCount;
                textTotalCostTocart.setText(Float.toString(totalCost));
             *//*   //get selected radiobutton in radiogroup
                RadioButton radioBtn = (RadioButton) radioGroup.getChildAt(position);
                Log.d(TAG, "radiobutton ID=" + radioBtn.getId());*//*
                String productId = Integer.toString(productTypes.getProductID());
                showItemsNumberInCart(productId, rbProductType.getText().toString());
            }
        });
    }
*/
  /*  private void showItemsNumberInCart(String productId, String productType) {
        try {
            CartProducts cartProductsFetchedFromDB = databaseHandler.getCartProduct(productId, productType);
            if (cartProductsFetchedFromDB == null) {
                //show total item no. in cart, for selected product is 0
                textViewAvailableItemsInCart.setText("Total items of " + productType + " in cart : 0");
            } else {
                //show no. of item in cart, for selected product
                textViewAvailableItemsInCart.setText("Total items of " + productType + " in cart : " + cartProductsFetchedFromDB.getProductQuantity());
            }
        } catch (Exception e) {
            e.printStackTrace();
//                    textViewAvailableItemsInCart.setText("Total items in cart of " + rbProductType.getText().toString() + " are : 0");
        }

    }*/
    @Override
    public void onClick(View v) {

        boolean isProductAdded;
//        CartProductPrefs prefs;
        switch (v.getId()) {
            /*case R.id.imageview_add_item:
                itemCount++;
                textTotalItem.setText(Integer.toString(itemCount));
                textTotalCostTocart.setText("0.0");
                float perProductCost = Float.parseFloat(textViewCost.getText().toString());
                float totalCost = perProductCost * itemCount;
                textTotalCostTocart.setText(Float.toString(totalCost));
                break;

            case R.id.imageview_remove_item:
                if (itemCount > 0) {
                    itemCount--;
                    textTotalItem.setText(Integer.toString(itemCount));
                    textTotalCostTocart.setText("0.0");
                    float perProductCostafterDeduction = Float.parseFloat(textViewCost.getText().toString());
                    float totalCostafterDeduction = perProductCostafterDeduction * itemCount;
                    textTotalCostTocart.setText(Float.toString(totalCostafterDeduction));
                }
                break;*/

           /* case R.id.button_add_to_cart:

                Log.d(TAG, radioGrpProductType.getCheckedRadioButtonId() + "ID is:");
                if (radioGrpProductType.getCheckedRadioButtonId() >= 0) {
                    rbProductType = (RadioButton) findViewById(radioGrpProductType.getCheckedRadioButtonId());

//                Log.d(TAG, "selected ID is:" + rbProductType.getId());

                    ProductTypes productTypes = productTypesList.get(radioGrpProductType.getCheckedRadioButtonId() - 1000);
                    String productIdToCart = Integer.toString(productTypes.getProductID());

                    try {
                        CartProducts cartProducts = new CartProducts(productIdToCart, textViewItemName.getText().toString(),
                                rbProductType.getText().toString(), textTotalItem.getText().toString(),
                                textViewCost.getText().toString(),
                                textTotalCostTocart.getText().toString(), productBitmap);
                        // get a SharedPreferences instance
//                prefs = new CartProductPrefs(this.getApplicationContext());
                        if (Integer.parseInt(textTotalItem.getText().toString()) > 0) {
                            //if quantity of package is >0
                            CartProducts cartProductsFetchedFromDB = databaseHandler.getCartProduct(cartProducts.getProductId(), cartProducts.getProductType());
                            if (cartProductsFetchedFromDB == null) {
                                //if same object is not available in cart then add
                                //add Object to database
                                databaseHandler.addCartProduct(cartProducts);
                                cartProductList.add(cartProducts);
                                Toast.makeText(this, "Product Added in Cart", Toast.LENGTH_SHORT).show();
                                badgeCount++;
                                invalidateOptionsMenu();

                                textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + textTotalItem.getText().toString());
                                itemCount = 0;
                                textTotalItem.setText(Integer.toString(itemCount));
                                textTotalCostTocart.setText("0.0");

                            } else if (!cartProductsFetchedFromDB.getProductQuantity().equals(cartProducts.getProductQuantity())) {
                            //if same object is already is in cart but quantity is different then update it
                            databaseHandler.updateCartProduct(cartProducts);
                            Toast.makeText(this, "Product updated in Cart", Toast.LENGTH_SHORT).show();
                            textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + textTotalItem.getText().toString());
                            itemCount = 0;
                            textTotalItem.setText(Integer.toString(itemCount));
                            textTotalCostTocart.setText("0.0");
                        } else {
                                int itemNoToUpdate = Integer.parseInt(cartProductsFetchedFromDB.getProductQuantity()) + Integer.parseInt(textTotalItem.getText().toString());
                                double totalProductCostToUpdate = Double.parseDouble(cartProductsFetchedFromDB.getProductTotal()) + Double.parseDouble(textTotalCostTocart.getText().toString());

                                CartProducts cartProductsToUpdate = new CartProducts(productIdToCart, textViewItemName.getText().toString(),
                                        rbProductType.getText().toString(), Integer.toString(itemNoToUpdate),
                                        textViewCost.getText().toString(),
                                        Double.toString(totalProductCostToUpdate), productBitmap);

                                databaseHandler.updateCartProduct(cartProductsToUpdate);
                                Toast.makeText(this, "Product updated in Cart", Toast.LENGTH_SHORT).show();
                                textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + Integer.toString(itemNoToUpdate));
                                itemCount = 0;
                                textTotalItem.setText(Integer.toString(itemCount));
                                textTotalCostTocart.setText("0.0");
//                            //if same Object is in cart then show message
//                            Toast.makeText(this, "Product is already added in Cart", Toast.LENGTH_SHORT).show();
                            }
                        } else
                            Toast.makeText(this, "Please select quantity of product first", Toast.LENGTH_SHORT).show();
//                   } else
//                       Toast.makeText(this, "Select one package type ", Toast.LENGTH_SHORT).show();//if package type(size) is not choosen
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Please Select one package type ", Toast.LENGTH_SHORT).show();
                    }
                }
                break;*/

            case R.id.button_add_to_cart:
                // TODO: 09-Jul-16 remained to fetch bitmap for cart products

                isProductAdded = false;

                for (int i = 0; i < productTypesList.size(); i++) {

//                    int productId=productTypesList.get(i);
                    ProductTypes productTypes = productTypesList.get(i);

                    Spinner spinner1 = (Spinner) findViewById(productTypes.getProductID() + 1000);
                    String quantity = spinner1.getSelectedItem().toString();

                    if (Integer.parseInt(quantity) > 0) {
                        isProductAdded = true;
                    }
                }

                if (!isProductAdded)
                    Toast.makeText(this, "Please select quantity of product first", Toast.LENGTH_SHORT).show();


                for (int i = 0; i < productTypesList.size(); i++) {

//                    int productId=productTypesList.get(i);

                    ProductTypes productTypes = productTypesList.get(i);

                    Spinner spinner1 = (Spinner) findViewById(productTypes.getProductID() + 1000);
                    String quantity = spinner1.getSelectedItem().toString();

                    try {

                        if (Integer.parseInt(quantity) > 0) {

                            CartSubcategoryInfo cartSubcategoryInfo = new CartSubcategoryInfo(itemSubCategoryId, subCategoryName);

                            CartSubcategoryInfo CartSubcategoryInfoFromDb = databaseHandler.getCartSubcategoryInfo(itemSubCategoryId);
                            if (CartSubcategoryInfoFromDb == null) {
                                databaseHandler.addCartSubcategoryInfo(cartSubcategoryInfo);
                            }

                            String range[] = productTypes.getProductMasterCartonQtyRange().split("-");
                            CartProducts cartProducts = new CartProducts(Integer.toString(productTypes.getProductID()), productTypes.getProductName(),
                                    productTypes.getProductSize(),
                                    quantity, itemSubCategoryId, Integer.parseInt(range[0]),
                                    Integer.parseInt(range[1]), Integer.parseInt(productTypes.getProductMasterCartonQtyIncVal()));


                            //if quantity of package is >0
                            CartProducts cartProductsFetchedFromDB = databaseHandler.getCartProduct(cartProducts.getProductId(), cartProducts.getProductType());
                            if (cartProductsFetchedFromDB == null) {
                                //if same object is not available in cart then add
                                //add Object to database
                                databaseHandler.addCartProduct(cartProducts);
                                cartProductList.add(cartProducts);

                                Toast.makeText(this, "Product Added in Cart", Toast.LENGTH_SHORT).show();
                                badgeCount++;
//                                    productTypesAdapter.notifyDataSetChanged();
//                                invalidateOptionsMenu();

//                                textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + textTotalItem.getText().toString());
//                                itemCount = 0;
//                                textTotalItem.setText(Integer.toString(itemCount));
//                                textTotalCostTocart.setText("0.0");

                            }
                            // else if (!cartProductsFetchedFromDB.getProductQuantity().equals(cartProducts.getProductQuantity())) {
                            else {//if same object is already is in cart but quantity is different then update it
                                databaseHandler.updateCartProduct(cartProducts);
                                Toast.makeText(this, "Product updated in Cart", Toast.LENGTH_SHORT).show();
//                                    textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + textTotalItem.getText().toString());
//                                    itemCount = 0;
//                                    textTotalItem.setText(Integer.toString(itemCount));
//                                    textTotalCostTocart.setText("0.0");
                            } /*else {
                                int itemNoToUpdate = Integer.parseInt(cartProductsFetchedFromDB.getProductQuantity()) + Integer.parseInt(jsonObj.getString("cartProductsQuantity"));
                                double totalProductCostToUpdate = Double.parseDouble(cartProductsFetchedFromDB.getProductTotal()) + Double.parseDouble(jsonObj.getString("cartProductsTotal"));

                                CartProducts cartProductsToUpdate = new CartProducts(jsonObj.getString("cartProductsId"), jsonObj.getString("cartProductsName"),
                                        jsonObj.getString("cartProductsType"), Integer.toString(itemNoToUpdate),
                                        jsonObj.getString("cartPerProductCost"),
                                        Double.toString(totalProductCostToUpdate), productBitmap);
                                databaseHandler.updateCartProduct(cartProductsToUpdate);
                                Toast.makeText(this, "Product updated in Cart", Toast.LENGTH_SHORT).show();
                                productTypesAdapter.notifyDataSetChanged();
//                                textViewAvailableItemsInCart.setText("Total items of " + rbProductType.getText().toString() + " in cart : " + Integer.toString(itemNoToUpdate));
//                                itemCount = 0;
//                                textTotalItem.setText(Integer.toString(itemCount));
//                                textTotalCostTocart.setText("0.0");
//                            //if same Object is in cart then show message
//                            Toast.makeText(this, "Product is already added in Cart", Toast.LENGTH_SHORT).show();
                            }*/
                        } /*else {
                            Toast.makeText(this, "Please select quantity of product first", Toast.LENGTH_SHORT).show();
                        }*/
                    } catch (Exception e) {
                        e.printStackTrace();
                        break;
                    }
                }
                invalidateOptionsMenu();
                break;

/*
            case R.id.textView_link_description:
                //show or hide description
                if (textViewShowDescriptionLink.getText().toString().equals("View Description")) {
                    textViewDescription.setVisibility(View.VISIBLE);
                    textViewShowDescriptionLink.setText("Hide Description");
                } else {
                    textViewDescription.setVisibility(View.GONE);
                    textViewShowDescriptionLink.setText("View Description");
                }
                break;*/
        }

    }

    /**
     * method to get product details from server
     *
     * @param subCategoryId           : subcategoryIdof product
     * @param categoryIdOfSubCategory : categoryId of selected subcategory product
     * @param subCategoryName
     */

    private void callToProductDetails(final String subCategoryId,
                                      final String categoryIdOfSubCategory, final String subCategoryName) {

        progressBar.setVisibility(View.VISIBLE);

        String urlStr = ConfigUrls.URL_GET_PRODUCT_DETAILS + "?companyinfoid=56&categoryid=" + categoryIdOfSubCategory + "&subcategoryid=" + subCategoryId;
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

//        url = Uri.encode();

        //count is incremented ,so that webservice shouldnn't be called continuously
        count++;

        final URL finalUrl = url;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET,
                url.toString(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d(TAG, response.toString());
                        Log.d(TAG, "onResponse: " + finalUrl.toString());

                        progressBar.setVisibility(View.GONE);
                        try {
                            ProductTypes productTypes;
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            if (responseObj.getString("status").equals("200")) {
                                String productTitle = null, productDesc = null;
//                                List<String> imageStringList = new ArrayList<>();
                                if (responseObj != null) {
                                    JSONArray productDetailsResponseArrayObj = response.getJSONArray("resposne");
                                    if (productDetailsResponseArrayObj.length() != 0) {

                                        for (int i = 0; i < productDetailsResponseArrayObj.length(); i++) {
                                            JSONObject productDetailsResponseObj = productDetailsResponseArrayObj.getJSONObject(i);

                                            productTitle = productDetailsResponseObj.getString("itemNm");
                                            productDesc = productDetailsResponseObj.getString("itemDesc");

                                            Log.d(TAG, productDetailsResponseObj.toString());


//                                            productIdList.add(productDetailsResponseObj.getInt("itemMasterDtlsId"));

                                            productTypes = new ProductTypes(productDetailsResponseObj.getInt("itemMasterDtlsId"),
                                                    productDetailsResponseObj.getString("itemNm"),
                                                    productDetailsResponseObj.getString("uom"),
                                                    productDetailsResponseObj.getString("masterCartonQtyRange"),
                                                    productDetailsResponseObj.getString("masterCartonQtyIncVal"));

                                            itemSubCategoryId = Integer.parseInt(productDetailsResponseObj.getString("itemSubCategory"));


                                            //store list of image to show in slideshow
//                                            imageStringList.add(productDetailsResponseObj.getString("itemImage"));
                                            productTypesList.add(productTypes);
//                                    Log.d(TAG, "itemMasterDtlsId=" + productDetailsResponseObj.getInt("itemMasterDtlsId"));
                                        }
//                                Log.d(TAG, "priceList.size()=" + priceList.size());

                                        updateUI(productDesc, productTypesList, subCategoryName);
                                    } else
                                        Toast.makeText(ProductDetailNewActivity.this, "Unable to load details..try after some time", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProductDetailNewActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ProductDetailNewActivity.this, "Try again..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                Toast.makeText(ProductDetailNewActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
        String tag_string_req = "json_request";
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

/*
    *//**
     * Calculate total for showing final amount on cart
     *
     * @param totalPerProduct : per product total
     *//*

    public void calculateTotal(double totalPerProduct) {
        totalPayableAmount = totalPayableAmount + totalPerProduct;
        textTotalCostTocart.setText(Double.toString(totalPayableAmount));
    }


    *//**
     * subtract amount from total, on reducing number of items
     *
     totalPayableAmount -= productSubTotal;
     * @param productSubTotal : per product total
     *//*
    public void subtractFromTotal(Double productSubTotal) {
        textTotalCostTocart.setText(Double.toString(totalPayableAmount));
    }


    public void updateCartNotification() {
        badgeCount++;
        invalidateOptionsMenu();
    }*/


}

