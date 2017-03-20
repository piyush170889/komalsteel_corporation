package com.replete.komalapp.activity;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.replete.komalapp.StaggeredGridView.DataSet;
import com.replete.komalapp.StaggeredGridView.Item;
import com.replete.komalapp.StaggeredGridView.STGVAdapter;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.utils.DemoPagerAdapter;
import com.replete.komalapp.utils.GridViewWithHeaderAndFooter;
import com.replete.komalapp.utils.SessionManager;
import com.replete.komalapp.R;
import com.replete.komalapp.rowitem.Category;
import com.replete.komalapp.recyclerutils.CategoryAdapter;
import com.replete.komalapp.rowitem.SubCategory;
import com.replete.komalapp.utils.SingletonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * Created by MR JOSHI on 15-Mar-16.
 */
public class MainProductListActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AbsListView.OnScrollListener {

    private int TIME_OUT_IN_MS = 10000;
    public int RETRY_COUNT = 1;
    private float BACKOFF_MULTIPLIER = 1F;
    int headerCount = 0;

    //    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private View content;
    private String TAG = "MainActivity";
    private CategoryAdapter mAdapter1;

    List<SubCategory> subCategoryList = null;
    SubCategory subCategory;
    Category category;
    List<Category> categories;
    private ViewPager viewpager;
    private String productview1, productview2, productview3;
    private boolean alreadyExecuted;

    DatabaseHandler databaseHandler = new DatabaseHandler(this);
    private ProgressBar progressBar;
    private int count = 0;


    //    StaggeredGridView stgv;
    STGVAdapter stgvAdapter;
    private DataSet mData = new DataSet();
    private ArrayList<Item> mItems = new ArrayList<Item>();
    private SingletonUtil singletonUtil;
    private View footerView;

    private BroadcastReceiver myWifiReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //if wifi is ON or mobile connection is ON
            if (SingletonUtil.getSingletonConfigInstance().checkNetConnection(MainProductListActivity.this)) {
                if (0 == count) {
                    progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                    //TODO: call to get categories
//                    getProductListFromServer();
                    //count is incremented ,so that webservice shouldnn't be called continuously
                }
            }
        }
    };

    private static final int RightToLeft = 1;
    private static final int LeftToRight = 2;
    private static final int DURATION = 30000;
    private RectF mDisplayRect = new RectF();
    private final Matrix mMatrix = new Matrix();
    private int mDirection = RightToLeft;
    private ValueAnimator mCurrentAnimator;
    private float mScaleFactor;
    protected ImageView mBackground;
    private int pageNo = 1;
    private GridViewWithHeaderAndFooter gridView;
    private ViewGroup header;
    private int lastTopValue = 0;
    private boolean isLoadMore = false;
    private TextView textViewAllProducts;
    private int firstVisibleItem;
    private int visibleItemCount;
    private int totalItemCount;
    private TextView textViewPullUpMsg;
    private ProgressBar progressbarFooter;
    private int mLastFirstVisibleItem = 0;
    private boolean mIsScrollingUp;
    private String previousPageNo;
    private int badgeCount = 0;

    private BadgeStyle style = ActionItemBadge.BadgeStyles.GREY.getStyle();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        content = findViewById(R.id.content);
        //register broadcast receiver
        this.registerReceiver(this.myWifiReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        //gridview initial state
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.gridview);

        LayoutInflater inflater2 = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater2.inflate(R.layout.layout_loading_footer, null);
        textViewPullUpMsg = (TextView) footerView.findViewById(R.id.textViewPullUpMsg);
        progressbarFooter = (ProgressBar) footerView.findViewById(R.id.progress_bar_footer);
        gridView.addFooterView(footerView);

//        initViewPager();

//        initRecyclerView();

        initToolbar();
        setupDrawerLayout();
        categories = new ArrayList<>();
//        initImageAnimation();
//        initImagePagination();
        if (SingletonUtil.getSingletonConfigInstance().isConnectingToInternet(this)) {

            /*if (0 == count)
                getProductListFromServer();*/
        } else
            Toast.makeText(this, getString(R.string.check_net_connection), Toast.LENGTH_SHORT).show();
        String currentPageNo = Integer.toString(pageNo);
        callToCategories(currentPageNo);


    }

   /* private void initImagePagination() {
        stgv = (StaggeredGridView) findViewById(R.id.stgv);
        singletonUtil = SingletonUtil.getSingletonConfigInstance();
        int margin = getResources().getDimensionPixelSize(R.dimen.stgv_margin);

        stgv.setItemMargin(margin);
        stgv.setPadding(margin, 0, margin, 0);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = inflater.inflate(R.layout.layout_loading_footer, null);
        stgv.setFooterView(footerView);
//        addCategoriesToList();
        String currentPageNo = Integer.toString(pageNo);
        callToCategories(currentPageNo);
        //set category List first time and set in adapter

      *//*  mAdapter = new STGVAdapter(this, getApplication(), addCategoriesToList());
        stgv.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();*//*

        stgv.setOnLoadmoreListener(new StaggeredGridView.OnLoadmoreListener() {
            @Override
            public void onLoadmore() {
//                new LoadMoreTask().execute();
//                addCategoriesToList();
                String currentPageNo = Integer.toString(pageNo);
                callToCategories(currentPageNo);
            }
        });

    }
*/


    /**
     * image moving animation left to right and bottom to top
     */


  /*  private void initImageAnimation() {
        final ImageView mImageView = (ImageView) findViewById(R.id.image);
        mImageView.setImageDrawable(getResources().getDrawable(R.drawable.header_02));
//        TranslateAnimation translateAnimation =new TranslateAnimation()
        final TranslateAnimation _translateAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -50f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f);
//        Animation _translateAnimation= new TranslateAnimation(mImageView.getLeft(), mImageView.getLeft()+150, mImageView.getTop(), mImageView.getTop());
        _translateAnimation.setDuration(5000);
        _translateAnimation.setFillAfter(true);
        _translateAnimation.setFillEnabled(true);
        _translateAnimation.setRepeatCount(1);
        _translateAnimation.setRepeatMode(Animation.REVERSE); // REVERSE
        _translateAnimation.setInterpolator(new LinearInterpolator());
        _translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                final TranslateAnimation tAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, -50f);
                tAnimation.setDuration(5000);
                tAnimation.setInterpolator(new LinearInterpolator());
                tAnimation.setFillAfter(true);
                tAnimation.setAnimationListener(new Animation.AnimationListener() {

                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mImageView.startAnimation(_translateAnimation);
                    }
                });

                mImageView.startAnimation(tAnimation);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mImageView.startAnimation(_translateAnimation);

    }
*/


    /**
     * Add image slideshow to viewpager
     */
/*

    private void initViewPager() {


//        View pagerView = View.inflate(this, R.layout.custom_header, null);

        // we take the background image and button reference from the header
        viewpager = (ViewPager) header.findViewById(R.id.viewpager);

        CircleIndicator indicator = (CircleIndicator) header.findViewById(R.id.indicator);


        viewpager.setAdapter(new DemoPagerAdapter());
        indicator.setViewPager(viewpager);
        viewpager.setCurrentItem(0);
//        viewpager.setPageTransformer(true, new DepthPageTransformer());
        //image slideshow
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int count = 0;

            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (count <= 3) {
                            viewpager.setCurrentItem(count, true);
//                            viewpager.setCurrentItemInternal(count, true, true, 10);
                            count++;
                        } else {
                            count = 0;
                            viewpager.setCurrentItem(count, true);
//                            viewpager.setCurrentItemInternal(count, true, true, 10);
                        }
                    }
                });
            }
        }, 2000, 2000);
    }
*/

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
        if (badgeCount == 0) {
            //hide cart icon If count is 0
            ActionItemBadge.hide(menu.findItem(R.id.item_samplebadge));
        } else {
            //Update cart items numlber on incrementing count
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
                Intent intentToCart = new Intent(MainProductListActivity.this, CartActivity.class);
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

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                Intent intent = new Intent(MainProductListActivity.this, ProfileActivity.class);
                startActivity(intent);
//                Toast.makeText(MainProductListActivity.this, "header view", Toast.LENGTH_SHORT).show();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_track_order:
                        Intent intent = new Intent(MainProductListActivity.this, OrderLoadMoreActivity.class);
                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.drawer_logout:
                        SessionManager session = new SessionManager(getApplicationContext());
                        session.setLogin(false);
                        session.clear();
                        databaseHandler.clearCartTable();
                        databaseHandler.clearCartSubcategoryTable();
                        databaseHandler.clearShippingAddressTable();
                        databaseHandler.clearAssociateDistributorTable();
                        Intent loginIntent = new Intent(MainProductListActivity.this, LoginActivity.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(loginIntent);
//                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        finish();
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
                        intent = new Intent(MainProductListActivity.this, ContactUsActivity.class);
                        startActivity(intent);
                        break;

                    /*case R.id.drawer_settings:
                        Intent settingIntent = new Intent(MainProductListActivity.this, SettingActivity.class);
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

   /* private void getProductListFromServer() {
        progressBar.setVisibility(View.VISIBLE);
        String tag_string_req = "json_req";
        count++;
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                Request.Method.GET, ConfigUrls.URL_GET_PRODUCT_LIST + "56",
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            JSONObject responseObj = response.getJSONObject("responseMessage");
                            progressBar.setVisibility(View.GONE);
                            if (responseObj.getString("status").equals("200")) {
                                parseJson(response);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mAdapter = new CategoryAdapter(MainProductListActivity.this, categories);
                                        mAdapter.setExpandCollapseListener(new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                                            @Override
                                            public void onListItemExpanded(int position) {
                                                Category expandedCategory = categories.get(position);
                                            }

                                            @Override
                                            public void onListItemCollapsed(int position) {
                                                Category collapsedCategory = categories.get(position);
                                            }
                                        });
                                        recyclerView.setAdapter(mAdapter);
                                    }
                                }, 100);
                            } else
                                Toast.makeText(MainProductListActivity.this, responseObj.getString("message"), Toast.LENGTH_SHORT).show();

//                                singletonUtil.showSnackBar(responseObj.getString("message"), (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(MainProductListActivity.this, "Try Again!!", Toast.LENGTH_SHORT).show();
//                            singletonUtil.showSnackBar("Try Again!!", (RelativeLayout) findViewById(R.id.relativeLayoutParent));
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainProductListActivity.this, "Unable to connect server!! Try again..", Toast.LENGTH_SHORT).show();
            }
        }) {
        };
//        hideDialog();
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT_IN_MS, RETRY_COUNT, BACKOFF_MULTIPLIER));
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_string_req);
    }

    *//**
     * parsing json obj for item and subitems list
     *
     * @param productJsonObj : JsonObj fetched from webservice(currently from getProductList())
     * @return
     *//*
    public List<Category> parseJson(JSONObject productJsonObj) {

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
    }*/


    /**
     * call to server for loading images
     *
     * @param currentPageNo
     */
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
                               /* if (footerView.getVisibility() == View.VISIBLE)
                                    footerView.setVisibility(View.GONE);*/
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

                                /*new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        String currentPageNo = Integer.toString(pageNo);
                                        callToCategories(currentPageNo);
                                    }
                                }, 1000);
*/
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

    private void onError() {
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

    private void addCategoriesToList(JSONObject response) {

        try {
            JSONArray jsonArray = response.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject newJsonObj = jsonArray.getJSONObject(i);
                Item item = new Item(newJsonObj.getString("url"), newJsonObj.getString("categoryId"), newJsonObj.getString("categoryName"));

           /* item.width= mData.width[i];
            item.height= mData.height[i];*/
                mItems.add(item);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


       /* for (int i = 0; i < 20; i++) {
            Item item = new Item();
            item.url = mData.url[i];
           *//* item.width= mData.width[i];
            item.height= mData.height[i];*//*
            mItems.add(item);
        }*/


        // inflate custom header and attach it to the list
        LayoutInflater inflater = getLayoutInflater();
        header = (ViewGroup) inflater.inflate(R.layout.custom_header, gridView, false);
//        textViewAllProducts = (TextView) header.findViewById(R.id.textView_all_products);

        if (headerCount == 0) {
//            initViewPager();
            gridView.addHeaderView(header, null, false);
            headerCount++;
        }

        gridView.setAdapter(new STGVAdapter(this, mItems));

        pageNo++;

        gridView.setOnScrollListener(MainProductListActivity.this);

       /* stgvAdapter = new STGVAdapter(this, mItems);
        stgv.setAdapter(stgvAdapter);
        stgv.setExpanded(true);
        stgvAdapter.notifyDataSetChanged();*/
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        final int threshold = 1;
        final int count = gridView.getCount();

        if (scrollState != SCROLL_STATE_IDLE) {
//            if (gridView.getLastVisiblePosition() >= count - threshold && isLoadMore){

//            boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount ;

//            if (loadMore && isLoadMore) {
            Log.d(TAG, "***************************");
            Log.d(TAG, "gridView.getLastVisiblePosition" + gridView.getLastVisiblePosition() + "count " + count);
            Log.d(TAG, "***************************");

            /*if (view.getId() == gridView.getId()) {
                Log.d(TAG, "***************************");
                Log.d(TAG, "Id are equal");
                Log.d(TAG, "***************************");

                final int currentFirstVisibleItem = gridView.getFirstVisiblePosition();
                if (currentFirstVisibleItem >= mLastFirstVisibleItem) {
                    mIsScrollingUp = false;
                    Log.i("a", "scrolling down...");*/

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean loadMore = firstVisibleItem + visibleItemCount == totalItemCount;

                    Log.d(TAG, "loadMore" + loadMore);

                    if ((gridView.getLastVisiblePosition() == (count - threshold)) && isLoadMore && loadMore) {
//                if (progressbarFooter.getVisibility() == View.GONE)
//                    progressbarFooter.setVisibility(View.VISIBLE);
                        textViewPullUpMsg.setVisibility(View.VISIBLE);
//                gridView.addFooterView(footerView);
                        progressbarFooter.setVisibility(View.VISIBLE);

                        String currentPageNo = Integer.toString(pageNo);
                        if (!previousPageNo.equals(currentPageNo))
                            callToCategories(currentPageNo);
                        else
                            Log.d(TAG, "Call was for same page no rejected");
                    }
                /*} else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    mIsScrollingUp = true;
                    Log.i("a", "scrolling up...");
                }
                mLastFirstVisibleItem = currentFirstVisibleItem;*/
                }
            }, 1000);

        }

    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {
        Rect rect = new Rect();
        viewpager.getLocalVisibleRect(rect);
        textViewAllProducts.getLocalVisibleRect(rect);

        this.firstVisibleItem = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;

//        Log.d(TAG, "firstVisibleItem " + firstVisibleItem + "visibleItemCount=" + visibleItemCount + "totalItemCount=" + totalItemCount);

        if (lastTopValue != rect.top) {
            lastTopValue = rect.top;
            viewpager.setY((float) (rect.top / 2.0));
            textViewAllProducts.setY((float) (rect.top / 2.0));
        }


       /* boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount - 1;

        if (loadMore && isLoadMore) {
            if (footerView.getVisibility() == View.GONE)
                footerView.setVisibility(View.VISIBLE);
//                gridView.addFooterView(footerView);

            String currentPageNo = Integer.toString(pageNo);
            callToCategories(currentPageNo);

        }*/

    }
}
