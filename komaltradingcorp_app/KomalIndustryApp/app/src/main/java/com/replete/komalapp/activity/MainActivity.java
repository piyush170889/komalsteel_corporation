package com.replete.komalapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
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
import com.replete.komalapp.R;
import com.replete.komalapp.UpdateActivity;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.recyclerutils.CustomGridAdapter;
import com.replete.komalapp.rowitem.Category;
import com.replete.komalapp.rowitem.SubCategory;
import com.replete.komalapp.utils.GridViewWithHeaderAndFooter;
import com.replete.komalapp.utils.SessionManager;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MR JOSHI on 09-Jul-16.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {

    private int TIME_OUT_IN_MS = 10000;
    public int RETRY_COUNT = 1;
    private float BACKOFF_MULTIPLIER = 1F;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View content;
    private String TAG = "MainActivity";
    List<Category> categories;
    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    private ProgressBar progressBar;
    private int count = 0;
    List<Category> categoryList = new ArrayList<>();

    private BroadcastReceiver myWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if wifi is ON or mobile connection is ON
            if (SingletonUtil.getSingletonConfigInstance().checkNetConnection(MainActivity.this)) {
                if (0 == count) {
                    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                    //TODO: call to get categories
//                    getProductListFromServer();
                    //count is incremented ,so that webservice shouldnn't be called continuously
                }
            }
        }
    };

    private GridViewWithHeaderAndFooter gridView;
    private int badgeCount = 0;

    private BadgeStyle style = ActionItemBadge.BadgeStyles.GREY.getStyle();
    private TextView textView_product_name;
    private Spinner spinnerCategories;
    private ViewGroup header;
    private int headerCount = 0;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        content = findViewById(R.id.content);
        //register broadcast receiver
        this.registerReceiver(this.myWifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        session = new SessionManager(getApplicationContext());
     /* InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        try {
            View view = getCurrentFocus();

            if (view != null) {
                if (view.getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }

        } catch (NullPointerException ne) {
            ne.printStackTrace();
        }*/


        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridview);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            gridView.setNestedScrollingEnabled(false);
        }
*/


        initToolbar();
        categories = new ArrayList<>();
        if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this)) {
            /*if (0 == count)
                getProductListFromServer();*/
            getProductListFromServer();
        } else
            Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();


    }

    private void callSubcategoryOfSelectedCategory(String itemId) {
        progressBar.setVisibility(View.VISIBLE);
        String tag_string_req = "json_req";
        count++;
        Log.d(" URL MAIN SUB ACTIVITY", ConfigUrls.URL_GET_SUBCATEGORY_OF_CATEGORY + itemId);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_SUBCATEGORY_OF_CATEGORY + itemId,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(final JSONObject response) {

                        Log.d("URL_GET_SUBCATEGORY_OF_CATEGORY MAIN ACTIVITY", response.toString());
                        Category category = null;

                        SubCategory subCategory = null;
                        final List<SubCategory> subCategoryList = new ArrayList<>();
//                        final ArrayList<String> categoryToSpinnerList = new ArrayList<>();

                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {

                                //assign categoryName to textview
                                final String categoryName = response.getJSONObject("activeCategoryDetails").getString("name");


                                /*//prepopulate spinner
                                JSONArray categoryJsonArray = response.getJSONArray("categoryDetails");
                                for (int i = 0; i < categoryJsonArray.length(); i++) {
                                    JSONObject categoryJsonObject = categoryJsonArray.getJSONObject(i);
                                    category = new Category(categoryJsonObject.getString("id"), categoryJsonObject.getString("name"));
                                    categoryList.add(category);

                                    categoryToSpinnerList.add(categoryJsonObject.getString("name"));

                                }*/

                                //populate images of subcategory
                                JSONArray subCategoryJsonArray = response.getJSONArray("subCategoryDetails");
                                for (int i = 0; i < subCategoryJsonArray.length(); i++) {
                                    JSONObject subcategoryJsonObject = subCategoryJsonArray.getJSONObject(i);

                                    subCategory = new SubCategory(subcategoryJsonObject.getString("id"), subcategoryJsonObject.getString("name"), subcategoryJsonObject.getString("catDesc"),
                                            subcategoryJsonObject.getString("parantId"),
                                            subcategoryJsonObject.getString("url"), response.getJSONObject("activeCategoryDetails").getString("id"));
                                    subCategoryList.add(subCategory);
                                }

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                                                android.R.layout.simple_spinner_item, categoryToSpinnerList);
                                        spinnerCategories.setAdapter(adapter);*/
                                        textView_product_name.setText(categoryName);
                                        CustomGridAdapter customGridAdapter = new CustomGridAdapter(MainActivity.this, subCategoryList);
//                                        mAdapter = new CategoryAdapter(MainActivity.this, categories);
                                        gridView.setAdapter(customGridAdapter);
                                    }
                                }, 100);
                            } else
                                Toast.makeText(MainActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();

//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
//        hideDialog();
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }


    @Override
    protected void onDestroy() {
        //unregister broadcast receiver on Destroy of activity
        if (myWifiReceiver != null)
            this.unregisterReceiver(myWifiReceiver);
        super.onDestroy();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
       /* if (badgeCount == 0) {
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
                Intent intentToCart = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intentToCart);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;

            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Update count in menu onResume
        badgeCount = databaseHandler.getCartProductCounts();
        invalidateOptionsMenu();
        setupDrawerLayout();
    }


    /*@Override
    public void onItemClick(View view, ViewModel viewModel) {
//        DetailActivity.navigate(this, view.findViewById(R.id.image), viewModel);
        Intent intentToProductDetail = new Intent(this, ProductDetailActivity.class);
        startActivity(intentToProductDetail);
    }*/

  /*  private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
    }*/

    private void initToolbar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.menu);
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    /**
     * set drawer layout and drawer items clicklisteners
     */
    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);

        //set username and email in drawer
//        TextView textViewUsername = (TextView) header.findViewById(R.id.textView_header_username);
        TextView textViewHeaderName = (TextView) header.findViewById(R.id.textView_header_email);
        HashMap<String, String> userDetails = databaseHandler.getUserDetails();
//        textViewUsername.setText(userDetails.get("firstName") + " " + userDetails.get("lastName"));
        textViewHeaderName.setText(userDetails.get("firstName"));


        Menu drawerMenu = navigationView.getMenu();
        MenuItem logoutMenuItem = drawerMenu.findItem(R.id.drawer_logout);
        MenuItem loginMenuItem = drawerMenu.findItem(R.id.drawer_login);
        if (session.isLoggedIn()) {

            /*if user is logged in, show logout menuitem*/
            logoutMenuItem.setVisible(true);
            loginMenuItem.setVisible(false);

        } else {
            /*if user is logged in, show login menuitem*/
            logoutMenuItem.setVisible(false);
            loginMenuItem.setVisible(true);
        }

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (session.isLoggedIn()) {
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(intent);
                } else {
                    SingletonUtil.getSingletonConfigInstance().showDialogToLogin(MainActivity.this);

                }
//                Toast.makeText(MainActivity.this, "header view", Toast.LENGTH_SHORT).show();
            }
        });


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_track_order:

                        if (session.isLoggedIn()) {
                            Intent intent = new Intent(MainActivity.this, OrderLoadMoreActivity.class);
                            startActivity(intent);
                        } else {
                            SingletonUtil.getSingletonConfigInstance().showDialogToLogin(MainActivity.this);
                        }
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;

                    case R.id.drawer_logout:
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.setLogin(false);
                        session.clear();
                        databaseHandler.clearUserInfoTable();
                        databaseHandler.clearCartTable();
                        databaseHandler.clearCartSubcategoryTable();
                        databaseHandler.clearShippingAddressTable();
                        databaseHandler.clearAssociateDistributorTable();
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                        finish();
                        break;

                    case R.id.drawer_login:
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                        break;


                    case R.id.drawer_feedback:
                        final Intent intentToMail = new Intent(Intent.ACTION_SENDTO);
                        intentToMail.setData(Uri.parse("mailto:tekchandbheda@gmail.com"));
                        // intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                        intentToMail.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                        if (intentToMail.resolveActivity(getPackageManager()) != null) {
                            startActivity(intentToMail);
                        }
                        break;

                    case R.id.drawer_call_support:
                        Intent intentToContact = new Intent(MainActivity.this, ContactUsActivity.class);
                        startActivity(intentToContact);
                        break;

                    /*case R.id.drawer_settings:
                        Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(settingIntent);
                        break;*/
                }
//                Snackbar.make(content, menuItem.getTitle() + " pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);

                //change color of selected cuurent menuitem
                /*ColorStateList myColorStateList = new ColorStateList(
                        new int[][]{
                                new int[]{android.R.attr.state_checked},
                                new int[]{android.R.attr.state_focused},
                                new int[]{}
                        },
                        new int[] {
                                Color.parseColor(getString(R.string.color_primary)),
                                Color.parseColor(getString(R.string.color_primary)),
                                Color.BLACK
                        }
                );

                navigationView.setItemTextColor(myColorStateList);
                navigationView.setItemIconTintList(myColorStateList);*/
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }


    private void getProductListFromServer() {
        progressBar.setVisibility(View.VISIBLE);
        String tag_string_req = "json_req";
        count++;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_PRODUCT_LIST + "56",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG + "  URL_GET_PRODUCT_LIST", ConfigUrls.URL_GET_PRODUCT_LIST + "56");
                        Log.d(TAG + "  URL_GET_PRODUCT_LIST", response.toString());
                        Category category = null;

                        SubCategory subCategory = null;
                        final List<SubCategory> subCategoryList = new ArrayList<>();
                        final ArrayList<String> categoryToSpinnerList = new ArrayList<>();

                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {

                                String appVersion = responseObj.getString("apiVersion");

                                PackageInfo pInfo = null;
                                try {
                                    pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                                } catch (PackageManager.NameNotFoundException e) {
                                    e.printStackTrace();
                                }
                                int versionCode = pInfo.versionCode;
                                System.out.println("versionCode  " + versionCode);


                                if (versionCode == Integer.parseInt(appVersion)) {

                                    //assign categoryName to textview

                                    final String categoryName = response.getJSONObject("activeCategoryDetails").getString("name");

                                    //prepopulate spinner
                                    JSONArray categoryJsonArray = response.getJSONArray("categoryDetails");
                                    for (int i = 0; i < categoryJsonArray.length(); i++) {
                                        JSONObject categoryJsonObject = categoryJsonArray.getJSONObject(i);
                                        category = new Category(categoryJsonObject.getString("id"), categoryJsonObject.getString("name"));
                                        categoryList.add(category);
                                        categoryToSpinnerList.add(categoryJsonObject.getString("name"));
                                    }

                                    //populate images of subcategory
                                    JSONArray subCategoryJsonArray = response.getJSONArray("subCategoryDetails");
                                    for (int i = 0; i < subCategoryJsonArray.length(); i++) {
                                        JSONObject subcategoryJsonObject = subCategoryJsonArray.getJSONObject(i);
                                        subCategory = new SubCategory(subcategoryJsonObject.getString("id"), subcategoryJsonObject.getString("name"), subcategoryJsonObject.getString("catDesc"),
                                                subcategoryJsonObject.getString("parantId"),
                                                subcategoryJsonObject.getString("url"), response.getJSONObject("activeCategoryDetails").getString("id"));
                                        subCategoryList.add(subCategory);
                                    }

                                    updateUI(categoryName, categoryToSpinnerList, subCategoryList);
                                } else {
                                    Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            } else
                                Toast.makeText(MainActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();

//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainActivity.this, "Try after some time!!", Toast.LENGTH_SHORT).show();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Unable to connect server!! Try after some time..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
//        hideDialog();
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    private void updateUI(final String categoryName, final ArrayList<String> categoryToSpinnerList, final List<SubCategory> subCategoryList) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // inflate custom header and attach it to the list
                LayoutInflater inflater = getLayoutInflater();
                header = (ViewGroup) inflater.inflate(R.layout.custom_header, gridView, false);

                textView_product_name = (TextView) header.findViewById(R.id.textView_product_name);
                spinnerCategories = (Spinner) header.findViewById(R.id.spinnerCategories);

                if (headerCount == 0) {
//                    initViewPager();
                    gridView.addHeaderView(header, null, false);
                    headerCount++;
                }


                textView_product_name.setText(categoryName);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                        R.layout.category_simple_spinner_item_category, categoryToSpinnerList);
                spinnerCategories.setAdapter(adapter);
//                spinnerCategories.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_ATOP);

                CustomGridAdapter customGridAdapter = new CustomGridAdapter(MainActivity.this, subCategoryList);
//                                        mAdapter = new CategoryAdapter(MainActivity.this, categories);
                gridView.setAdapter(customGridAdapter);
                gridView.setOnScrollListener(MainActivity.this);

                if (spinnerCategories != null) {
                    spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Category selectedCategory = categoryList.get(position);
                            callSubcategoryOfSelectedCategory(selectedCategory.getItemId());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                }

            }
        }, 1000);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /* *
      * parsing json obj for item and subitems list
      *
      * @param productJsonObj : JsonObj fetched from webservice(currently from getProductList())
      * @return
      */
   /* public List<Category> parseJson(JSONObject productJsonObj) {

        SubCategory subCategory;
        Category category;

        try {
            JSONArray jsonArray = productJsonObj.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject categoryNSubcategoryObj = jsonArray.getJSONObject(i);
                Log.d(TAG, categoryNSubcategoryObj.toString());
                List<SubCategory> subCategoryList = new ArrayList<>();
                JSONArray subcategoryArray = categoryNSubcategoryObj.getJSONArray("subCategory");
                for (int j = 0; j < subcategoryArray.length(); j++) {
                    subCategory = new SubCategory();
                    JSONObject jsonObject = subcategoryArray.getJSONObject(j);
                    Log.d(TAG, jsonObject.toString());
//                subCategory.setSubitemId(jsonObject.getString("subitemId"));
                    subCategory.setSubCategoryName(jsonObject.getString("value"));
                    subCategory.setCategoryOfSubCategoryName(categoryNSubcategoryObj.getString("category"));
                    subCategoryList.add(subCategory);
                }
                category = new Category(categoryNSubcategoryObj.getString("category"), subCategoryList);
                categories.add(category);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return categories;
    }
*/

    /**
     * call to server for loading images
     *
     * @param currentPageNo
     *//*
    private void callToCategories(final String currentPageNo) {
        if (currentPageNo.equals("1"))
            progressBar.setVisibility(View.VISIBLE);
        previousPageNo = currentPageNo;
        progressbarFooter.setVisibility(View.VISIBLE);
        Log.d(TAG, "url=" + ConfigUrls.URL_GET_CATEGORY + currentPageNo);
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_CATEGORY + currentPageNo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, ConfigUrls.URL_GET_CATEGORY + currentPageNo);
                        Log.d(TAG, response.toString());
                        textViewPullUpMsg.setVisibility(View.VISIBLE);
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            JSONObject paginationObj = response.getJSONObject("pagination");
                            int numOfOrders = paginationObj.getInt("numOfCategory");
                            int recordPerPage = paginationObj.getInt("recordPerPage");
                            int current_page = paginationObj.getInt("pageNumber");
                            pageNo = current_page;
                            if (currentPageNo.equals("1"))
                                progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                               *//* if (footerView.getVisibility() == View.VISIBLE)
                                    footerView.setVisibility(View.GONE);*//*
                                progressbarFooter.setVisibility(View.GONE);
                                addCategoriesToList(response);
                            } else
                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));

                            if ((numOfOrders - (recordPerPage * (current_page))) <= 0) {
//                                stgv.removeView(footerView);
//                                gridView.removeView(footerView);
                                if (progressbarFooter.getVisibility() == View.VISIBLE)
                                    progressbarFooter.setVisibility(View.GONE);
                                textViewPullUpMsg.setVisibility(View.GONE);
                                gridView.setSelection(gridView.getAdapter().getCount() - 1);
                                isLoadMore = false;
                            } else {
                                isLoadMore = true;
                                gridView.setSelection(gridView.getAdapter().getCount() - 1);

                                *//*new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String currentPageNo = Integer.toString(pageNo);
                                        callToCategories(currentPageNo);
                                    }
                                }, 1000);
*//*
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                            progressbarFooter.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                if (currentPageNo.equals("1"))
                    progressBar.setVisibility(View.GONE);
                error.printStackTrace();
                onError();
                progressbarFooter.setVisibility(View.GONE);
//                singletonUtil.showSnackBar("Unable to connect server!! Try again..", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
            }
        }) {
        };
        String tag_string_req = "json_request";
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }
*/
  /*  private void onError() {
        Snackbar snackbar = Snackbar
                .make((RelativeLayout) findViewById(R.id.relativeLayoutParent), "Unable to connect server!! Press OK to try again..", Snackbar.LENGTH_INDEFINITE)

                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String currentPageNo = Integer.toString(pageNo);
                        callToCategories(currentPageNo);
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
*/
  /*  private void addCategoriesToList(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newJsonObj = jsonArray.getJSONObject(i);
                Item item = new Item(newJsonObj.getString("url"), newJsonObj.getString("categoryId"), newJsonObj.getString("categoryName"));

           *//* item.width= mData.width[i];
            item.height= mData.height[i];*//*
                mItems.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


       *//* for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.url = mData.url[i];
           *//**//* item.width= mData.width[i];
            item.height= mData.height[i];*//**//*
            mItems.add(item);
        }*//*


        // inflate custom header and attach it to the list
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.custom_header, gridView, false);
        textViewAllProducts = (TextView) header.findViewById(R.id.textView_all_products);

        if (headerCount == 0) {
            initViewPager();
            gridView.addHeaderView(header, null, false);
            headerCount++;
        }

        gridView.setAdapter(new STGVAdapter(this, mItems));

        pageNo++;

        gridView.setOnScrollListener(MainActivity.this);

       *//* stgvAdapter = new STGVAdapter(this, mItems);
        stgv.setAdapter(stgvAdapter);
        stgv.setExpanded(true);
        stgvAdapter.notifyDataSetChanged();*//*
    }*/


}
