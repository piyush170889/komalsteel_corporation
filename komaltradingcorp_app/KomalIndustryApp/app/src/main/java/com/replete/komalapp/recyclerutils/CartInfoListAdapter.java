package com.replete.komalapp.recyclerutils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.replete.komalapp.R;
import com.replete.komalapp.activity.CartActivity;
import com.replete.komalapp.helper.DatabaseHandler;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;
import com.replete.komalapp.rowitem.Products;
import com.replete.komalapp.utils.SimpleDividerItemDecoration;
//import com.replete.komalapp.utils.CartProductPrefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by patil on 2/17/2016.
 */
public class CartInfoListAdapter extends RecyclerView.Adapter<CartInfoListAdapter.CustomViewHolder> {

    public RelativeLayout relativeLayoutProductTitle;
    private CartInfoListAdapter obj;
    //    CartProducts cartInfoRowItem = new CartProducts();
    private Context mContext;

    CartProductNSubCategories cartProductNSubCategoriesItem = new CartProductNSubCategories();

    private List<CartProductNSubCategories> cartProductNSubCategoriesList;
    DatabaseHandler databaseHandler;


    //ClickListener of RemoveItem Button
    View.OnClickListener clickListener = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
                final CustomViewHolder holder = (CustomViewHolder) view.getTag();
                final int position = holder.getAdapterPosition();

                final CartProductNSubCategories cartProductNSubCategories = cartProductNSubCategoriesList.get(position);

//            CartProducts cartProducts = cartProductNSubCategories.getCartProductsList();

            AlertDialog.Builder removeItemDialog = new AlertDialog.Builder(mContext);
            removeItemDialog.setMessage("Do you want to remove this Subcategory?");
            removeItemDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
//                    databaseHandler.deleteCartProducts(cartProducts);
                    databaseHandler.deleteCartProductsBySubcatId(cartProductNSubCategories.getSubcategoryId());
                    databaseHandler.deleteSubcategoryBySubcatId(cartProductNSubCategories.getSubcategoryId());

                    cartProductNSubCategoriesList.remove(position);
                    notifyItemRemoved(position);

                    /*CartSubCategoryProductListAdapter cartSubCategoryProductListAdapter = new CartSubCategoryProductListAdapter();
                    cartSubCategoryProductListAdapter.notifyDataSetChanged();
*/
                    int totalRemovableQuantity = 0;
                    List<CartProducts> cartProductsList = cartProductNSubCategories.getCartProductsList();
                    for (int j = 0; j < cartProductsList.size(); j++) {

                        CartProducts cartProducts = cartProductsList.get(j);
                        totalRemovableQuantity = totalRemovableQuantity + Integer.parseInt(cartProducts.getProductQuantity());
                    }

                    // TODO: 9/7/2016 remove total of subcat quantity
                    if (mContext instanceof CartActivity) {
                        ((CartActivity) mContext).subtractFromTotal(totalRemovableQuantity);
                    }
                }
            });
            removeItemDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            final AlertDialog alertDialog = removeItemDialog.create();

            Window alertDialogView = ((AlertDialog) alertDialog).getWindow();
            alertDialogView.setBackgroundDrawableResource(R.color.white);
            alertDialog.show();

        }
    };
    private int tableIdCount = 0;
    private CartSubCategoryProductListAdapter adapter;

    public CartInfoListAdapter(Context mContext, List<CartProductNSubCategories> cartProductNSubCategoriesList) {
        this.obj = this;
        this.mContext = mContext;
        this.cartProductNSubCategoriesList = cartProductNSubCategoriesList;
    }

    public CartInfoListAdapter() {
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_ro, null);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_cart, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        databaseHandler = new DatabaseHandler(mContext);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {
        cartProductNSubCategoriesItem = cartProductNSubCategoriesList.get(position);
        customViewHolder.textViewProductName.setText(Html.fromHtml(cartProductNSubCategoriesItem.getSubcategoryName()));

        List<CartProducts> cartProductsArrayList = cartProductNSubCategoriesItem.getCartProductsList();

        for (int i = 0; i < cartProductsArrayList.size(); i++) {

            CartProducts cartProducts = cartProductsArrayList.get(i);

            adapter = new CartSubCategoryProductListAdapter(mContext, obj, position, cartProductNSubCategoriesList, cartProductsArrayList);
            adapter.notifyDataSetChanged();
            customViewHolder.recyclerViewCartSubcatProducts.setAdapter(adapter);

//            addProductTypesToTable(cartProducts.getProductType(), cartProducts.getProductQuantity(), customViewHolder);

            if (mContext instanceof CartActivity) {
                ((CartActivity) mContext).calculateTotal(Integer.parseInt(cartProducts.getProductQuantity()));
            }
        }

        customViewHolder.imageViewRemoveFromCart.setOnClickListener(clickListener);
        customViewHolder.imageViewRemoveFromCart.setTag(customViewHolder);

//        customViewHolder.textViewQuantity.setText(Html.fromHtml(cartInfoRowItem.getProductQuantity()));
//        customViewHolder.textViewSize.setText(Html.fromHtml(cartInfoRowItem.getProductType()));
//        customViewHolder.textViewTotalPrice.setText(Html.fromHtml(cartInfoRowItem.getProductTotal()));
//        customViewHolder.imageViewProduct.setImageBitmap(cartInfoRowItem.getProductImageBitmap());

//Handle click event on both title and image click

       /* customViewHolder.imageViewAddItem.setOnClickListener(addClickListener);
        customViewHolder.imageViewAddItem.setTag(customViewHolder);

        customViewHolder.imageViewRemoveItem.setOnClickListener(removeClickListener);
        customViewHolder.imageViewRemoveItem.setTag(customViewHolder);
*/

//        customViewHolder.setIsRecyclable(true);
    }

    @Override
    public int getItemCount() {
        return (null != cartProductNSubCategoriesList ? cartProductNSubCategoriesList.size() : 0);
    }

    /**
     * CustomHolder
     */
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProduct;
        private TextView textViewProductName;
        private TextView textViewQuantity;
        private TextView textViewSize;
        //        private TextView textViewCostPerUnit;
        private ImageView imageViewRemoveFromCart;
        private ImageView imageViewAddItem;
        private ImageView imageViewRemoveItem;
        //        public TableLayout tableCartProductInfo;
        public RecyclerView recyclerViewCartSubcatProducts;


        public CustomViewHolder(View itemView) {
            super(itemView);

            this.textViewProductName = (TextView) itemView.findViewById(R.id.textView_cart_item_name);
//            this.textViewQuantity = (TextView) itemView.findViewById(R.id.textView_cart_item_quantity);
//            this.textViewSize = (TextView) itemView.findViewById(R.id.textView_size);
            this.imageViewRemoveFromCart = (ImageView) itemView.findViewById(R.id.imageView_remove_from_cart);
//            tableCartProductInfo = (TableLayout) itemView.findViewById(R.id.tableCartProductInfo);
            recyclerViewCartSubcatProducts = (RecyclerView) itemView.findViewById(R.id.recycler_view_cart_subcat_products);
            recyclerViewCartSubcatProducts.setLayoutManager(new LinearLayoutManager(mContext));
//            recyclerViewCartSubcatProducts.addItemDecoration(new SimpleDividerItemDecoration(mContext));
            recyclerViewCartSubcatProducts.setNestedScrollingEnabled(false);
            relativeLayoutProductTitle = (RelativeLayout) itemView.findViewById(R.id.relativeLayoutProductTitle);
//              this.imageViewAddItem = (ImageView) itemView.findViewById(R.id.imageview_add_item);
//            this.imageViewProduct = (ImageView) itemView.findViewById(R.id.imageView_product);
//            this.imageViewRemoveItem = (ImageView) itemView.findViewById(R.id.imageview_remove_item);

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
//        tr.addView(spinner);
//        tr.addView(tvproductType);

        customViewHolder.tableCartProductInfo.addView(tableRow, new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        tableIdCount++;
    }*/

}
