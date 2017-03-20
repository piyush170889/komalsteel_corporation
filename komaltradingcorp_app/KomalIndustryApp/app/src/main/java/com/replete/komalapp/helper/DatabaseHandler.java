package com.replete.komalapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.replete.komalapp.rowitem.AssociatedDistributor;
import com.replete.komalapp.rowitem.CartProductNSubCategories;
import com.replete.komalapp.rowitem.CartProducts;
import com.replete.komalapp.rowitem.CartSubcategoryInfo;
import com.replete.komalapp.rowitem.ShippingAddress;
import com.replete.komalapp.rowitem.SubCategory;
import com.replete.komalapp.utils.SingletonUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by MR JOSHI on 17-Mar-16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private SingletonUtil singletonUtilObj = SingletonUtil.getSingletonConfigInstance();

    // Database Name
    private static final String DATABASE_NAME = "KomalAppDatabse";

    // SubItems table name
//    private final String TABLE_SubItemS = "SubItems";
    private final String TABLE_CART_ITEMS = "CartItems";
    private final String TABLE_SHIPPING_ADDRESS = "ShippingAddressTable";
    private final String TABLE_USER_INFO = "UserInfoTable";
    //    private final String TABLE_ALL_DISTRIBUTOR = "AllDistributorTable";
    private final String TABLE_ASSOCIATED_DISTRIBUTOR = "AssociatedDistributorTable";
    private final String TABLE_CART_SUBCATEGORY_INFO = "CartSubcategoryInfo";


   /* // SubItems Table Columns names
    private final String KEY_ID = "id";
    private final String KEY_NAME = "name";*/

    // CartItems Table Columns names
    private final String CART_ITEM_ID = "cartItemId";
    private final String CART_ITEM_NAME = "cartItemName";
    private final String CART_ITEM_QUANTITY = "cartItemQuantity";
    private final String CART_ITEM_TYPE = "cartItemType";
    private final String CART_ITEM_SUBCAT_ID = "cartItemSubcatId";
    private final String CART_ITEM_QUANTITY_RANGE_START = "cartItemQuantityRangeStart";
    private final String CART_ITEM_QUANTITY_RANGE_END = "cartItemQuantityRangeEnd";
    private final String CART_ITEM_QUANTITY_INC_VALUE = "cartItemQuantityIncValue";

    // Shipping Address Table Columns names
    private final String ADDRESS_ID = "addressId";
    private final String PINCODE = "pincode";
    private final String ADDRESS = "address";
    private final String CITY = "city";
    private final String STATE = "state";
    private final String MARK = "mark";
    private final String DESTINATION = "destination";
    private final String TRANSPORTER_NAME = "transporterName";
    private final String VAT_TIN_NO = "vatTinNo";

    // User Details Table Columns names
    private final String USER_ID = "userTrackId";
    private final String FIRSTNAME = "firstName";
    private final String LASTNAME = "lastName";
    private final String TINNO = "tinNo";
    private final String PANNO = "panNo";
    private final String CONTACTNO = "contactNo";
    private final String DISPLAYNAME = "displayName";
    private final String USERTYPE = "userType";
    private final String STATUS = "status";
    private final String EMAIL = "emailId";
    private final String COMPANYINFOID = "CompanyInfoId";

    /*// All Distributor Table Columns names
    private final String DISTRIBUTOR_ID = "distributorId";
    private final String DISTRIBUTOR_FIRSTNAME = "distributorFirstName";
    private final String DISTRIBUTOR_LASTNAME = "distributorLastName";
    private final String DISTRIBUTOR_DISPLAYNAME = "distributorDisplayName";*/

    // Associated Distributor Table Columns names
    private final String ASS_DISTRIBUTOR_ID = "assDistributorId";
    private final String ASS_DISTRIBUTOR_FIRSTNAME = "assDistributorFirstName";
    private final String ASS_DISTRIBUTOR_LASTNAME = "assDistributorLastName";
    private final String ASS_DISTRIBUTOR_DISPLAYNAME = "assDistributorDisplayName";
    private final String ASS_DISTRIBUTOR_IS_DELETED = "assDistributorIsDeleted";
    private String TAG = "DatabaseHandler";

    private final String CART_SUBCAT_ID = "cartSubcatId";
    private final String CART_SUBCAT_NAME = "cartSubcatName";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
      /*  String CREATE_SubItemS_TABLE = "CREATE TABLE " + TABLE_SubItemS + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_SubItemS_TABLE);*/

        String CREATE_CART_ITEMS_TABLE = "CREATE TABLE " + TABLE_CART_ITEMS + "("
                + CART_ITEM_ID + " STRING," +
                CART_ITEM_NAME + " TEXT," +
                CART_ITEM_TYPE + " TEXT," +
                CART_ITEM_QUANTITY + " TEXT," + CART_ITEM_SUBCAT_ID + " INTEGER," +
                CART_ITEM_QUANTITY_RANGE_START + " INTEGER," +
                CART_ITEM_QUANTITY_RANGE_END + " INTEGER," +
                CART_ITEM_QUANTITY_INC_VALUE + " INTEGER," +
                " PRIMARY KEY(" + CART_ITEM_ID + "," + CART_ITEM_TYPE + ")) ";
        Log.d("SQLiteHandler", CREATE_CART_ITEMS_TABLE);
        db.execSQL(CREATE_CART_ITEMS_TABLE);

        String CREATE_SHIPPING_ADDRESS_TABLE = "CREATE TABLE " + TABLE_SHIPPING_ADDRESS + "("
                + ADDRESS_ID + " INTEGER PRIMARY KEY ," +
                PINCODE + " TEXT," +
                ADDRESS + " TEXT," +
                CITY + " TEXT," +
                STATE + " TEXT," +
                MARK + " TEXT, " +
                DESTINATION + " TEXT, " +
                TRANSPORTER_NAME + " TEXT, " +
                VAT_TIN_NO + " TEXT" +
                ") ";
        Log.d("SQLiteHandler", CREATE_SHIPPING_ADDRESS_TABLE);
        db.execSQL(CREATE_SHIPPING_ADDRESS_TABLE);

        String CREATE_USER_DETAILS_TABLE = "CREATE TABLE " + TABLE_USER_INFO + "("
                + USER_ID + " TEXT PRIMARY KEY ," +
                FIRSTNAME + " TEXT," +
                LASTNAME + " TEXT," +
                EMAIL + " TEXT," +
                CONTACTNO + " TEXT," +
                TINNO + " TEXT," +
                DISPLAYNAME + " TEXT," +
                PANNO + " TEXT,"
                + STATUS + " TEXT," +
                USERTYPE + " TEXT," +
                COMPANYINFOID + " TEXT ) ";
        Log.d("SQLiteHandler", CREATE_USER_DETAILS_TABLE);
        db.execSQL(CREATE_USER_DETAILS_TABLE);

     /*   String CREATE_ALL_DISTRIBUTOR_TABLE = "CREATE TABLE " + TABLE_ALL_DISTRIBUTOR + "("
                + DISTRIBUTOR_ID + " TEXT PRIMARY KEY ," +
                DISTRIBUTOR_FIRSTNAME + " TEXT," +
                DISTRIBUTOR_LASTNAME + " TEXT," +
                DISTRIBUTOR_DISPLAYNAME + " TEXT ) ";
        Log.d("SQLiteHandler", CREATE_ALL_DISTRIBUTOR_TABLE);
        db.execSQL(CREATE_ALL_DISTRIBUTOR_TABLE);*/

        String CREATE_ASS_DISTRIBUTOR_TABLE = "CREATE TABLE " + TABLE_ASSOCIATED_DISTRIBUTOR + "("
                + ASS_DISTRIBUTOR_ID + " TEXT PRIMARY KEY ," +
                ASS_DISTRIBUTOR_FIRSTNAME + " TEXT," +
                ASS_DISTRIBUTOR_LASTNAME + " TEXT," +
                ASS_DISTRIBUTOR_DISPLAYNAME + " TEXT ," +
                ASS_DISTRIBUTOR_IS_DELETED + " BOOLEAN  ) ";
        Log.d("SQLiteHandler", CREATE_ASS_DISTRIBUTOR_TABLE);
        db.execSQL(CREATE_ASS_DISTRIBUTOR_TABLE);

        String CREATE_TABLE_CART_SUBCATEGORY_INFO = "CREATE TABLE " + TABLE_CART_SUBCATEGORY_INFO + "("
                + CART_SUBCAT_ID + " TEXT PRIMARY KEY ," +
                CART_SUBCAT_NAME + " TEXT ) ";
        Log.d("SQLiteHandler", CREATE_TABLE_CART_SUBCATEGORY_INFO);
        db.execSQL(CREATE_TABLE_CART_SUBCATEGORY_INFO);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SubItemS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHIPPING_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_INFO);
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_DISTRIBUTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSOCIATED_DISTRIBUTOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART_SUBCATEGORY_INFO);
        // Create tables again
        onCreate(db);
    }


    // Adding new CartProduct
    public void addCartProduct(CartProducts cartProducts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_ITEM_ID, cartProducts.getProductId());
        values.put(CART_ITEM_NAME, cartProducts.getProductName());
        values.put(CART_ITEM_TYPE, cartProducts.getProductType());
        values.put(CART_ITEM_QUANTITY, cartProducts.getProductQuantity());
        values.put(CART_ITEM_SUBCAT_ID, cartProducts.getSubCategoryId());
        values.put(CART_ITEM_QUANTITY_RANGE_START, cartProducts.getProductQuantityRangeStart());
        values.put(CART_ITEM_QUANTITY_RANGE_END, cartProducts.getProductQuantityRangeEnd());
        values.put(CART_ITEM_QUANTITY_INC_VALUE, cartProducts.getProductQuantityIncValue());

//        values.put(CART_ITEM_PER_PRICE, cartProducts.getProductCostPerUnit());
//        values.put(CART_ITEM_IMAGE,singletonUtilObj.getBytes(cartProducts.getProductImageBitmap()));
//        values.put(CART_ITEM_TOTAL, cartProducts.getProductTotal());
        // Inserting Row
        db.insert(TABLE_CART_ITEMS, null, values);
        db.close(); // Closing database connection
    }


    // Getting single CartItem with unique id and type
    public CartProducts getCartProduct(String id, String quantityType) {
        CartProducts cartProducts = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART_ITEMS,
                new String[]{CART_ITEM_ID,
                        CART_ITEM_NAME, CART_ITEM_TYPE, CART_ITEM_QUANTITY, CART_ITEM_SUBCAT_ID, CART_ITEM_QUANTITY_RANGE_START,
                        CART_ITEM_QUANTITY_RANGE_END, CART_ITEM_QUANTITY_INC_VALUE},
                CART_ITEM_ID + "=? AND " + CART_ITEM_TYPE + "=?",
                new String[]{id, quantityType}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    cartProducts = new CartProducts(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)
                            , cursor.getInt(5)
                            , cursor.getInt(6)
                            , cursor.getInt(7));
                } while (cursor.moveToNext());
            }
        }
        return cartProducts;
    }

    // Getting All Cart Products
    public List<CartProducts> getAllCartProducts() {

        List<CartProducts> cartProductsList = new ArrayList<CartProducts>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartProducts cartProducts = new CartProducts();
                cartProducts.setProductId(cursor.getString(0));
                cartProducts.setProductName(cursor.getString(1));
                cartProducts.setProductType(cursor.getString(2));
                cartProducts.setProductQuantity(cursor.getString(3));
                cartProducts.setSubCategoryId(cursor.getInt(4));
                cartProducts.setProductQuantityRangeStart(cursor.getInt(5));
                cartProducts.setProductQuantityRangeEnd(cursor.getInt(6));
                cartProducts.setProductQuantityIncValue(cursor.getInt(7));
                // Adding contact to list
                cartProductsList.add(cartProducts);
            } while (cursor.moveToNext());
        }
        // return contact list
        return cartProductsList;
    }

    //    Getting cartProducts Count
    public int getCartProductCounts() {
        String countQuery = "SELECT  * FROM " + TABLE_CART_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
//        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating single cartProduct
    public int updateCartProduct(CartProducts cartProducts) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_ITEM_ID, cartProducts.getProductId());
        values.put(CART_ITEM_NAME, cartProducts.getProductName());
        values.put(CART_ITEM_TYPE, cartProducts.getProductType());
        values.put(CART_ITEM_QUANTITY, cartProducts.getProductQuantity());
        values.put(CART_ITEM_SUBCAT_ID, cartProducts.getSubCategoryId());
        values.put(CART_ITEM_QUANTITY_RANGE_START, cartProducts.getProductQuantityRangeStart());
        values.put(CART_ITEM_QUANTITY_RANGE_END, cartProducts.getProductQuantityRangeEnd());
        values.put(CART_ITEM_QUANTITY_INC_VALUE, cartProducts.getProductQuantityIncValue());

        // updating row
        return db.update(TABLE_CART_ITEMS, values, CART_ITEM_ID + "=? AND " + CART_ITEM_TYPE + "=?",
                new String[]{cartProducts.getProductId(), cartProducts.getProductType()});
    }

    // Deleting single cart Product
    public void deleteCartProducts(CartProducts cartProducts) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ITEMS, CART_ITEM_ID + "=? AND " + CART_ITEM_TYPE + "=?",
                new String[]{cartProducts.getProductId(), cartProducts.getProductType()});
        db.close();
    }

    /**
     * Remove all cart products
     */
    public void clearCartTable() {
        // db.delete(String tableName, String whereClause, String[] whereArgs);
        // If whereClause is null, it will delete all rows.
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_CART_ITEMS, null, null);
    }


    // Getting single CartItem with unique id and type
    public List<CartProducts> getCartProductBySubcatId(int subCatId) {

        List<CartProducts> cartProductsListBySubcatId = new ArrayList<CartProducts>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART_ITEMS,
                new String[]{CART_ITEM_ID,
                        CART_ITEM_NAME, CART_ITEM_TYPE, CART_ITEM_QUANTITY, CART_ITEM_SUBCAT_ID, CART_ITEM_QUANTITY_RANGE_START
                        , CART_ITEM_QUANTITY_RANGE_END, CART_ITEM_QUANTITY_INC_VALUE},
                CART_ITEM_SUBCAT_ID + "=?",
                new String[]{String.valueOf(subCatId)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    CartProducts cartProducts = new CartProducts(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4)
                            , cursor.getInt(5), cursor.getInt(6), cursor.getInt(7));
                    cartProductsListBySubcatId.add(cartProducts);
                } while (cursor.moveToNext());
            }
        }
        return cartProductsListBySubcatId;
    }


    // Adding new ShippingAddress
    public void addShippingAddress(ShippingAddress shippingAddress) {
        clearShippingAddressTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ADDRESS_ID, shippingAddress.getShippingAddressId());
        values.put(PINCODE, shippingAddress.getPincode());
        values.put(ADDRESS, shippingAddress.getAddress());
        values.put(CITY, shippingAddress.getCity());
        values.put(STATE, shippingAddress.getState());
        values.put(MARK, shippingAddress.getMark());
        values.put(DESTINATION, shippingAddress.getDestination());
        values.put(TRANSPORTER_NAME, shippingAddress.getTransporterName());
        values.put(VAT_TIN_NO, shippingAddress.getVatTinNo());
        // Inserting Row
        db.insert(TABLE_SHIPPING_ADDRESS, null, values);
        Log.d(TAG, TABLE_SHIPPING_ADDRESS + values);
        db.close(); // Closing database connection
    }

    // Getting single ShippingAddress with unique id
    public ShippingAddress getShippingAddress() {
        ShippingAddress shippingAddress = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SHIPPING_ADDRESS,
                new String[]{ADDRESS_ID,
                        PINCODE, ADDRESS, CITY, STATE,
                        MARK, DESTINATION, TRANSPORTER_NAME, VAT_TIN_NO},
                null, null, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    shippingAddress = new ShippingAddress(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                            cursor.getString(5), cursor.getString(6)
                            , cursor.getString(7), cursor.getString(8));
                } while (cursor.moveToNext());
            }
        }
        return shippingAddress;
    }


    public void clearShippingAddressTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_SHIPPING_ADDRESS, null, null);
        db.close();
    }

    public void addUserDetails(String userTrackId, String firstName, String lastName,
                               String loginId, String contact_num, String panNo,
                               String tinNo, String userType, String cmpnyInfoId,
                               String status, String displayName) {
        deleteUserDetails();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_ID, userTrackId);
        values.put(FIRSTNAME, firstName);
        values.put(LASTNAME, lastName);
        values.put(EMAIL, loginId);
        values.put(CONTACTNO, contact_num);
        values.put(TINNO, tinNo);
        values.put(DISPLAYNAME, displayName);
        values.put(PANNO, panNo);
        values.put(STATUS, status);
        values.put(USERTYPE, userType);
        values.put(COMPANYINFOID, cmpnyInfoId);
        db.insert(TABLE_USER_INFO, null, values);
        db.close(); // Closing database connection
    }

    private void deleteUserDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER_INFO, null, null);
        db.close();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER_INFO;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("userTrackId", cursor.getString(0));
            user.put("firstName", cursor.getString(1));
            user.put("lastName", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("contact_no", cursor.getString(4));
            user.put("tinNo", cursor.getString(5));
            user.put("displayname", cursor.getString(6));
            user.put("panNo", cursor.getString(7));
            user.put("status", cursor.getString(8));
            user.put("userType", cursor.getString(9));
            user.put("companyInfoId", cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        Log.d("SQLiteHandler", "Fetching user from Sqlite: " + user.toString());
        return user;
    }

    // Adding new all Distributors obj
    public void addAssociatedDistributor(AssociatedDistributor associatedDistributor) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASS_DISTRIBUTOR_ID, associatedDistributor.getDistributorId());
        values.put(ASS_DISTRIBUTOR_FIRSTNAME, associatedDistributor.getDistributorFirstName());
        values.put(ASS_DISTRIBUTOR_LASTNAME, associatedDistributor.getDistributorLastName());
        values.put(ASS_DISTRIBUTOR_DISPLAYNAME, associatedDistributor.getDistributorDisplayName());
        values.put(ASS_DISTRIBUTOR_IS_DELETED, associatedDistributor.isDeleted());
        // Inserting Row
        db.insert(TABLE_ASSOCIATED_DISTRIBUTOR, null, values);
        db.close(); // Closing database connection
    }


    public void updateUserDetails(String userTrackId, String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIRSTNAME, firstName);
        values.put(LASTNAME, lastName);
//        values.put(CONTACTNO, ContactNo);
        db.update(TABLE_USER_INFO, values, USER_ID + "=?", new String[]{userTrackId});
    }

    // Getting single CartItem with unique id and type
    public List<AssociatedDistributor> getAssociatedDistributorToDelete() {
        List<AssociatedDistributor> associatedDistributorList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ASSOCIATED_DISTRIBUTOR,
                new String[]{ASS_DISTRIBUTOR_ID,
                        ASS_DISTRIBUTOR_FIRSTNAME, ASS_DISTRIBUTOR_LASTNAME, ASS_DISTRIBUTOR_DISPLAYNAME, ASS_DISTRIBUTOR_IS_DELETED},
                ASS_DISTRIBUTOR_IS_DELETED + "=? ",
                new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    AssociatedDistributor associatedDistributor = new AssociatedDistributor(cursor.getString(0),
                            cursor.getString(1), cursor.getString(2), cursor.getString(3), Boolean.parseBoolean(cursor.getString(4)));
                    associatedDistributorList.add(associatedDistributor);
                } while (cursor.moveToNext());
            }
        }
        return associatedDistributorList;
    }

    // Getting All Contacts
    public List<AssociatedDistributor> getAssociatedDistributor() {
        List<AssociatedDistributor> associatedDistributorList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSOCIATED_DISTRIBUTOR;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AssociatedDistributor associatedDistributor = new AssociatedDistributor(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), Boolean.parseBoolean(cursor.getString(4)));
                // Adding contact to list
                associatedDistributorList.add(associatedDistributor);
            } while (cursor.moveToNext());
        }
        // return contact list
        return associatedDistributorList;
    }

    /**
     * delete all records having isdeleted=true (after success response)
     */
    public void deleteAssociatedDistributor() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSOCIATED_DISTRIBUTOR, ASS_DISTRIBUTOR_IS_DELETED + "=? ",
                new String[]{String.valueOf(1)});


        db.close();
    }

    /**
     * set isdeleted=true to isdeleted=false (after failure reponse)
     *
     * @param isDeleted
     */
    public void updateAssociatedDistributor(boolean isDeleted) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASS_DISTRIBUTOR_IS_DELETED, false);
        db.update(TABLE_ASSOCIATED_DISTRIBUTOR, values, ASS_DISTRIBUTOR_IS_DELETED + "=?", new String[]{String.valueOf(1)});
    }


    /**
     * set isdeleted=true to given associated Distributor(for deleting after success response)
     *
     * @param associatedDistributor
     */
    public void updateAssociatedDistributorValue(AssociatedDistributor associatedDistributor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ASS_DISTRIBUTOR_IS_DELETED, true);
        db.update(TABLE_ASSOCIATED_DISTRIBUTOR, values, ASS_DISTRIBUTOR_ID + "=?", new String[]{associatedDistributor.getDistributorId()});
    }

    public void clearAssociateDistributorTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_ASSOCIATED_DISTRIBUTOR, null, null);
        db.close();
    }


    public void addCartSubcategoryInfo(CartSubcategoryInfo cartSubcategoryInfo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CART_SUBCAT_ID, cartSubcategoryInfo.getSubcategoryId());
        values.put(CART_SUBCAT_NAME, cartSubcategoryInfo.getSubcategoryName());
        db.insert(TABLE_CART_SUBCATEGORY_INFO, null, values);
        db.close(); // Closing database connection
    }

    public CartSubcategoryInfo getCartSubcategoryInfo(int id) {
        CartSubcategoryInfo cartSubcategoryInfo = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART_SUBCATEGORY_INFO,
                new String[]{CART_SUBCAT_ID,
                        CART_SUBCAT_NAME},
                CART_SUBCAT_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    cartSubcategoryInfo = new CartSubcategoryInfo(cursor.getInt(0),
                            cursor.getString(1));
                } while (cursor.moveToNext());
            }
        }
        return cartSubcategoryInfo;
    }

   /* public List<Integer> getCartSubcategoryInfoId() {
//        CartSubcategoryInfo cartSubcategoryInfo = null;
        List<Integer> subcatIdList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CART_SUBCATEGORY_INFO,
                new String[]{CART_SUBCAT_ID},
                null,
                null, null, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    subcatIdList.add(cursor.getInt(0));
                } while (cursor.moveToNext());
            }
        }
        return subcatIdList;
    }*/

    public List<CartSubcategoryInfo> getAllCartSubcategoryInfo() {

        List<CartSubcategoryInfo> cartSubcategoryInfoList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CART_SUBCATEGORY_INFO;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CartSubcategoryInfo cartSubcategoryInfo = new CartSubcategoryInfo(cursor.getInt(0), cursor.getString(1));

                // Adding contact to list
                cartSubcategoryInfoList.add(cartSubcategoryInfo);
            } while (cursor.moveToNext());
        }
        // return contact list
        return cartSubcategoryInfoList;
    }


    public void deleteCartProductsByCartId(String cartProductId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ITEMS, CART_ITEM_ID + "=?",
                new String[]{String.valueOf(cartProductId)});
        db.close();
    }


    public void deleteCartProductsBySubcatId(int subcategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ITEMS, CART_ITEM_SUBCAT_ID + "=?",
                new String[]{String.valueOf(subcategoryId)});
        db.close();
    }

    public void deleteSubcategoryBySubcatId(int subcategoryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_SUBCATEGORY_INFO, CART_SUBCAT_ID + "=?",
                new String[]{String.valueOf(subcategoryId)});
        db.close();
    }


    public int checkCountOfSubcategory(int subCategoryId) {
        String countQuery = "SELECT * FROM " + TABLE_CART_ITEMS + " WHERE " + CART_ITEM_SUBCAT_ID + " = " + subCategoryId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getCount();
    }

    public int getSumOfQuantity() {
        String countQuery = "SELECT SUM ( " + CART_ITEM_QUANTITY + " ) FROM " + TABLE_CART_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor.getInt(0);

    }

    public void clearCartSubcategoryTable() {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_CART_SUBCATEGORY_INFO, null, null);
    }

    public void clearUserInfoTable() {
        SQLiteDatabase db = this.getWritableDatabase(); // helper is object extends SQLiteOpenHelper
        db.delete(TABLE_USER_INFO, null, null);
    }
}
