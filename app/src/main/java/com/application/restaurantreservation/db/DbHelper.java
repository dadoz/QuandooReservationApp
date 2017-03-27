package com.application.restaurantreservation.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.application.restaurantreservation.model.Customer;
import com.application.restaurantreservation.modules.NetworkModule.*;
import com.application.restaurantreservation.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class DbHelper extends SQLiteOpenHelper {

    //USER TABLE
    public static final String CUSTOMER_TABLE_NAME = "customers";
    public static final String CUSTOMER_COLUMN_CUSTOMER_ID = "id";
    public static final String CUSTOMER_COLUMN_CUSTOMER_FIRST_NAME = "customer_first_name";
    public static final String CUSTOMER_COLUMN_CUSTOMER_LAST_NAME = "customer_last_name";
    public static final String CUSTOMER_COLUMN_CUSTOMER_CREATED_AT = "created_at";
    public static final String CUSTOMER_COLUMN_CUSTOMER_UPDATED_AT = "updated_at";

    //USER TABLE
    public static final String TABLE_TBL_NAME = "tables";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_RESERVATION_ARRAY = "reservation_array";
    public static final String TABLE_COLUMN_CREATED_AT = "created_at";
    public static final String TABLE_COLUMN_UPDATED_AT = "updated_at";

    @Inject
    public DbHelper(@ApplicationContext Context context,
                    @DatabaseInfo String dbName,
                    @DatabaseInfo Integer version) {
        super(context, dbName, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        customerTblCreateStatements(db);
        tableTblCreateStatements(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME);
        onCreate(db);
    }

    private void customerTblCreateStatements(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + CUSTOMER_TABLE_NAME + "("
                            + CUSTOMER_COLUMN_CUSTOMER_ID + " INTEGER PRIMARY KEY, "
                            + CUSTOMER_COLUMN_CUSTOMER_FIRST_NAME + " VARCHAR(20), "
                            + CUSTOMER_COLUMN_CUSTOMER_LAST_NAME + " VARCHAR(50), "
                            + CUSTOMER_COLUMN_CUSTOMER_CREATED_AT + " VARCHAR(10) DEFAULT " + getCurrentTimeStamp() + ", "
                            + CUSTOMER_COLUMN_CUSTOMER_UPDATED_AT + " VARCHAR(10) DEFAULT " + getCurrentTimeStamp() + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void tableTblCreateStatements(SQLiteDatabase db) {
        try {
            db.execSQL(
                    "CREATE TABLE IF NOT EXISTS "
                            + TABLE_TBL_NAME + "("
                            + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
                            + TABLE_COLUMN_RESERVATION_ARRAY + " TEXT, "
                            + TABLE_COLUMN_CREATED_AT + " VARCHAR(10) DEFAULT " + getCurrentTimeStamp() + ", "
                            + TABLE_COLUMN_UPDATED_AT + " VARCHAR(10) DEFAULT " + getCurrentTimeStamp() + ")"
            );

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Observable<List<Customer>> getCustomers() throws Resources.NotFoundException, NullPointerException {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + CUSTOMER_TABLE_NAME, new String[]{});
            CursorIterator iterable = new CursorIterator(cursor);

            ArrayList list = new ArrayList<>();
            while (iterable.iterator().hasNext()) {
                Customer customer = createCustomer(iterable.iterator().next());
                list.add(customer);
            }
            return Observable.just(list);
        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    /**
     * truncate table
     */
    private void truncateCustomerTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("TRUNCATE TABLE " + CUSTOMER_TABLE_NAME);
    }

    /**
     * build list of customer
     * @param cursor
     * @return
     */
    private Customer createCustomer(Cursor cursor) {
        Customer customer = new Customer();
        customer.setId(cursor.getInt(cursor.getColumnIndex(CUSTOMER_COLUMN_CUSTOMER_ID)));
        customer.setCustomerFirstName(cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_CUSTOMER_FIRST_NAME)));
        customer.setCustomerLastName(cursor.getString(cursor.getColumnIndex(CUSTOMER_COLUMN_CUSTOMER_LAST_NAME)));
        return customer;
    }

    protected Long insertCustomer(Customer customer) throws Exception {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(CUSTOMER_COLUMN_CUSTOMER_FIRST_NAME, customer.getCustomerFirstName());
            contentValues.put(CUSTOMER_COLUMN_CUSTOMER_LAST_NAME, customer.getCustomerLastName());
            contentValues.put(CUSTOMER_COLUMN_CUSTOMER_ID, customer.getId());
            return db.insert(CUSTOMER_TABLE_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private String getCurrentTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    public Observable<List<Boolean>> getTables() {
        Cursor cursor = null;
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            cursor = db.rawQuery(
                    "SELECT * FROM "
                            + TABLE_TBL_NAME, new String[]{});
            boolean moveToFirst = cursor.moveToFirst();
            return Observable.fromArray(cursor.getString(cursor.getColumnIndex(TABLE_COLUMN_RESERVATION_ARRAY)).split(", "))
                    .filter(strings -> strings != null)
                    .map(Boolean::valueOf)
                    .toList()
                    .toObservable();

        } catch (NullPointerException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (cursor != null)
                cursor.close();
        }
    }

    public Long insertTables(List<Boolean> reservationArray) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COLUMN_ID, 0);
            contentValues.put(TABLE_COLUMN_RESERVATION_ARRAY, Utils.stringify(reservationArray));
            return db.insert(TABLE_TBL_NAME, null, contentValues);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Integer updateTable(List<Boolean> tableReservationList) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(TABLE_COLUMN_ID, 0);
            contentValues.put(TABLE_COLUMN_RESERVATION_ARRAY, Utils.stringify(tableReservationList));
            return db.update(TABLE_TBL_NAME, contentValues, null, new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}