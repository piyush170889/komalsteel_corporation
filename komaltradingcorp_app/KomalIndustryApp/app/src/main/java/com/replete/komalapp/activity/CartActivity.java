package com.replete.komalapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.replete.komalapp.Config.Constants;
import com.replete.komalapp.R;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.recyclerutils.CartInfoListAdapter;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;
import com.replete.komalapp.rowitem.CartSubcategoryInfo;
import com.replete.komalapp.utils.SessionManager;
import com.replete.komalapp.utils.SingletonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MR JOSHI on 17-Mar-16.
 */
public class CartActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    //    private List<CartProducts> cartProductsList;
    private CartInfoListAdapter adapter;
    private DatabaseHandler databaseHandler;
    private int totalQty = 0;
    private Button buttonProceedToPayment;
    private TextView textViewTotalQty;
    private Button buttonBack;
    private List<CartProductNSubCategories> cartProductNSubCategoriesList;
    private MenuItem clearCartItem;
    private TextView textViewCartEmptyMessage;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //init toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        //initialize recyclerview

        session = new SessionManager(CartActivity.this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        textViewTotalQty = ((TextView) findViewById(R.id.textView_message_total_qty));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setNestedScrollingEnabled(false);
        //initilize buttons
        buttonProceedToPayment = (Button) findViewById(R.id.button_proceed_to_payment);
        buttonProceedToPayment.setOnClickListener(this);

        buttonBack = (Button) findViewById(R.id.button_back);
        buttonBack.setOnClickListener(this);

        textViewCartEmptyMessage = (TextView) findViewById(R.id.textViewCartEmptyMessage);

        //get cartProduct's List, created while items were added to cart,
        databaseHandler = new DatabaseHandler(this);

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

//        cartProductsList = databaseHandler.getAllCartProducts();

        // Set cartProduct's List to recyclerview through adapter

        if (cartProductNSubCategoriesList.size() == 0) {
            textViewTotalQty.setText("0");
            textViewCartEmptyMessage.setVisibility(View.VISIBLE);

        } else {

            textViewCartEmptyMessage.setVisibility(View.GONE);
            adapter = new CartInfoListAdapter(CartActivity.this, cartProductNSubCategoriesList);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        //show number of cart products in cart and hide if it is 0
        clearCartItem = menu.findItem(R.id.action_clear_cart);
        if (cartProductNSubCategoriesList.size() > 0)
            menu.findItem(R.id.action_clear_cart).setVisible(true);
        else
            menu.findItem(R.id.action_clear_cart).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {

            //call onBackPressed() on pressing home button(so that it works same as hardware back button)
            case android.R.id.home:
                onBackPressed();
                break;
            //clear cart

            case R.id.action_clear_cart:
                //show dialog before clearing cart

                AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(this);
                removeItemDialog.setMessage("Do you want to remove all cart items?");
                removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartProductNSubCategoriesList.clear();
                        databaseHandler.clearCartTable();
                        databaseHandler.clearCartSubcategoryTable();
                        adapter.notifyDataSetChanged();
                        item.setVisible(false);
                        textViewTotalQty.setText("0");
                        ((TextView) findViewById(R.id.textViewCartEmptyMessage)).setVisibility(View.VISIBLE);
//                        ((TextView) findViewById(R.id.textView_message)).setVisibility(View.GONE);
                    }
                });

                removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                final AlertDialog alertDialog = removeItemDialog.create();
                Window view = ((AlertDialog) alertDialog).getWindow();
                view.setBackgroundDrawableResource(R.color.white);
                alertDialog.show();

                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        //set transition on backpressed
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_proceed_to_payment:
                if (Integer.parseInt(textViewTotalQty.getText().toString()) == 0) {
                    Toast.makeText(this, "Cart is Empty!! Can't Proceed", Toast.LENGTH_SHORT).show();
                } else {

                    if (session.isLoggedIn()) {
                        SharedPreferences tempSharedPref = getSharedPreferences(Constants.TEMP_SHARED_PREF, MODE_PRIVATE);
                        SharedPreferences.Editor editor = tempSharedPref.edit();
                        //set by default true, so that from review screen if it is reqired to edit then set to false
                        //so pickAddressActivity is created, otherwise it don't
                        editor.putBoolean("shouldNotEdit", true);
//                    editor.putString("subtotal", textViewTotalQty.getText().toString());
                        editor.commit();
                        Intent intentToProceed = new Intent(this, ReviewProductActivity.class);
                        startActivity(intentToProceed);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {

                        SingletonUtil.getSingletonConfigInstance().showDialogToLogin(CartActivity.this);

                    }
                }
                break;

            case R.id.button_back:
                onBackPressed();
                break;
        }
    }


//    *
//     * Calculate total for showing final amount on cart
//     *
//     * @param totalPerProduct : per product total


    public void calculateTotal(int perProductQty) {
        totalQty = totalQty + perProductQty;
        textViewTotalQty.setText(Integer.toString(totalQty));
    }


//    *
//     * subtract amount from total, on reducing number of items
//     *
//     * @param productSubTotal : per product total

    public void subtractFromTotal(int perProductQty) {
        totalQty -= perProductQty;
        textViewTotalQty.setText(Integer.toString(totalQty));
    }

    public void updateTotal() {
        /*totalQty -= productQty;
        totalQty = totalQty + updatedProductQuantity;*/
        totalQty = databaseHandler.getSumOfQuantity();
        Log.d("CART", "updateTotal: totalQty=" + totalQty);
        textViewTotalQty.setText(Integer.toString(totalQty));

    }

}
