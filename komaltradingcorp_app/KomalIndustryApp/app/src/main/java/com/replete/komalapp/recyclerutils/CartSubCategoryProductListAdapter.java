package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.CartActivity;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Replete Android on 9/7/2016.
 */
public class CartSubCategoryProductListAdapter extends RecyclerView.Adapter<CartSubCategoryProductListAdapter.CustomViewHolder> {

    private final List<CartProductNSubCategories> cartProductNSubCategoriesList;
    private final CartInfoListAdapter cartInfoListAdapterObj;
    private int positionOfParent;
    CartProducts cartInfoRowItem = new CartProducts();
    private Context mContext;


//    CartProductNSubCategories cartProductNSubCategoriesItem = new CartProductNSubCategories();

    private List<CartProducts> cartProductsList;
    DatabaseHandler databaseHandler;


    //ClickListener of RemoveItem Button
    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final CustomViewHolder holder = (CustomViewHolder) view.getTag();
            final int position = holder.getAdapterPosition();

            final CartProducts cartProducts = cartProductsList.get(position);

            AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(mContext);
            removeItemDialog.setMessage("Do you want to remove this item?");
            removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int countOfSubcategoryProducts = databaseHandler.checkCountOfSubcategory(cartProducts.getSubCategoryId());

                    if (countOfSubcategoryProducts > 1) {
                        //delete only cart products
                        databaseHandler.deleteCartProductsByCartId(cartProducts.getProductId());
                        cartProductsList.remove(position);
                        notifyItemRemoved(position);

                    } else if (countOfSubcategoryProducts == 1) {

                        //delete cart products and subcat
                        databaseHandler.deleteCartProductsByCartId(cartProducts.getProductId());
                        databaseHandler.deleteSubcategoryBySubcatId(cartProducts.getSubCategoryId());
                        cartInfoListAdapterObj.relativeLayoutProductTitle.setVisibility(View.GONE);
                        cartProductNSubCategoriesList.remove(positionOfParent);
                        cartInfoListAdapterObj.notifyItemRemoved(positionOfParent);

//                        cartProductNSubCategoriesList.remove(position);
//                        notifyItemRemoved(position);

//                        CartInfoListAdapter cartInfoListAdapter = new CartInfoListAdapter();
//                        cartInfoListAdapter.notifyDataSetChanged();

                    }

//                    databaseHandler.deleteCartProducts(cartProducts);

                  /*  CartInfoListAdapter cartInfoListAdapter = new CartInfoListAdapter();
                    cartInfoListAdapter.notifyItemRemoved(positionOfParent);*/


                    if (mContext instanceof CartActivity) {
                        ((CartActivity) mContext).subtractFromTotal(Integer.parseInt(cartProducts.getProductQuantity()));
                    }
                }
            });
            removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            AlertDialog alertDialog = removeItemDialog.create();

            Window alertDialogView = ((AlertDialog) alertDialog).getWindow();
            alertDialogView.setBackgroundDrawableResource(R.color.white);
            alertDialog.show();
        }
    };

    private int tableIdCount = 0;


    /*private AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if (view != null) {
                final CustomViewHolder holder = (CustomViewHolder) view.getTag();

                if (holder != null) {

                    final int positionOfSelectedSpinner = holder.getAdapterPosition();
                    final CartProducts cartProducts = cartProductsList.get(positionOfSelectedSpinner);

                    String selectedQuantity = holder.spinnerProductQty.getItemAtPosition(position).toString();

                    CartProducts cartProductsToUpdate = new CartProducts(cartProducts.getProductId(), cartProducts.getProductName(),
                            cartProducts.getProductType(),
                            selectedQuantity, cartProducts.getSubCategoryId(), cartProducts.getProductQuantityRangeStart(),
                            cartProducts.getProductQuantityRangeEnd(), cartProducts.getProductQuantityIncValue());

                    databaseHandler.updateCartProduct(cartProductsToUpdate);
                }
            }


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };*/


    public CartSubCategoryProductListAdapter(Context mContext,
                                             CartInfoListAdapter obj, int position,
                                             List<CartProductNSubCategories> cartProductNSubCategoriesList,
                                             List<CartProducts> cartProductsArrayList) {
        this.mContext = mContext;
        this.cartInfoListAdapterObj = obj;
        this.positionOfParent = position;
        this.cartProductNSubCategoriesList = cartProductNSubCategoriesList;
        this.cartProductsList = cartProductsArrayList;

    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_ro, null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_cart_subcat_product_new, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        databaseHandler = new DatabaseHandler(mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, final int position) {
        cartInfoRowItem = cartProductsList.get(position);

//        addProductTypesToTable(cartInfoRowItem.getProductType(), cartInfoRowItem.getProductQuantity(), customViewHolder);

       /* if (mContext instanceof CartActivity) {
            ((CartActivity) mContext).calculateTotal(Integer.parseInt(cartInfoRowItem.getProductQuantity()));
        }
*/

//        customViewHolder.textViewProductQuantity.setText(Html.fromHtml(cartInfoRowItem.getProductQuantity()) + " Cartoons");
        customViewHolder.textViewProductType.setText(Html.fromHtml(cartInfoRowItem.getProductType()));
//        customViewHolder.textViewTotalPrice.setText(Html.fromHtml(cartInfoRowItem.getProductTotal()));
//        customViewHolder.imageViewProduct.setImageBitmap(cartInfoRowItem.getProductImageBitmap());


        ArrayList<Integer> quantityArrayList = new ArrayList<>();
        for (int i = cartInfoRowItem.getProductQuantityRangeStart(); i <= cartInfoRowItem.getProductQuantityRangeEnd(); i += cartInfoRowItem.getProductQuantityIncValue()) {
            if (i > 0)
                quantityArrayList.add(i);

//            products = new Products(productID, i + "");
//            spinnerProductList.add(products);
        }

        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(mContext, R.layout.simple_spinnet_item, quantityArrayList);
        customViewHolder.spinnerProductQty.setAdapter(spinnerArrayAdapter);

        customViewHolder.spinnerProductQty.setSelection(spinnerArrayAdapter.getPosition(Integer.parseInt(cartInfoRowItem.getProductQuantity())));

        customViewHolder.spinnerProductQty.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int spinnerPosition, long id) {

                        CartProducts cartProduct = cartProductsList.get(position);
                        String selectedQuantity = customViewHolder.spinnerProductQty.getItemAtPosition(spinnerPosition).toString();
                        CartProducts cartProductsToUpdate = new CartProducts(cartProduct.getProductId(), cartProduct.getProductName(),
                                cartProduct.getProductType(),
                                selectedQuantity, cartProduct.getSubCategoryId(), cartProduct.getProductQuantityRangeStart(),
                                cartProduct.getProductQuantityRangeEnd(), cartProduct.getProductQuantityIncValue());

                        databaseHandler.updateCartProduct(cartProductsToUpdate);

                        if (mContext instanceof CartActivity) {
                            ((CartActivity) mContext).updateTotal();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                }

        );


//Handle click event on both title and image click
       /* customViewHolder.imageViewRemoveFromCart.setOnClickListener(clickListener);
        customViewHolder.imageViewRemoveFromCart.setTag(customViewHolder);
*/
       /* customViewHolder.imageViewAddItem.setOnClickListener(addClickListener);
        customViewHolder.imageViewAddItem.setTag(customViewHolder);

        customViewHolder.imageViewRemoveItem.setOnClickListener(removeClickListener);
        customViewHolder.imageViewRemoveItem.setTag(customViewHolder);
*/

      /*  customViewHolder.spinnerProductQty.setOnItemSelectedListener(itemSelectedListener);
        customViewHolder.spinnerProductQty.setTag(customViewHolder);*/

        customViewHolder.imageViewRemoveProduct.setOnClickListener(clickListener);
        customViewHolder.imageViewRemoveProduct.setTag(customViewHolder);
//        customViewHolder.setIsRecyclable(true);
    }

    @Override
    public int getItemCount() {
        return (null != cartProductsList ? cartProductsList.size() : 0);
    }

    /**
     * CustomHolder
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //        public TableLayout tableCartProductInfo;
        private TextView textViewProductType;
        //        private TextView textViewProductQuantity;
        private ImageView imageViewRemoveProduct;
        private Spinner spinnerProductQty;


        public CustomViewHolder(View itemView) {
            super(itemView);
//            tableCartProductInfo = (TableLayout) itemView.findViewById(R.id.tableCartProductInfo);
            textViewProductType = (TextView) itemView.findViewById(R.id.textViewProductType);
//            textViewProductQuantity = (TextView) itemView.findViewById(R.id.textViewProductQuantity);
            imageViewRemoveProduct = (ImageView) itemView.findViewById(R.id.imageViewRemoveProduct);
            spinnerProductQty = (Spinner) itemView.findViewById(R.id.spinnerProductQty);
        }
    }


    /*private void addProductTypesToTable(String type, String quantity, CustomViewHolder customViewHolder) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mLinearLayout = inflater.inflate(R.layout.tablerow_cart_product, null);


        TextView textViewTableRowType = (TextView) mLinearLayout.findViewById(R.id.textViewTableRowType);
        TextView textViewTableRowQuantity = (TextView) mLinearLayout.findViewById(R.id.textViewTableRowQuantity);

        TableRow tableRow = (TableRow) mLinearLayout.findViewById(R.id.tableRow);

        textViewTableRowType.setText(type);
        textViewTableRowQuantity.setText(quantity + " Cartoons");

//        tr.addView(tvId);
//        tr.addView(tvproductName);
//        tr.addView(tvproductType);
//        tr.addView(spinner);

        customViewHolder.tableCartProductInfo.addView(tableRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        tableIdCount++;
    }*/

}
