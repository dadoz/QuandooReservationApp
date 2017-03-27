package com.application.restaurantreservation.utils;

import android.content.res.AssetManager;
import android.util.Log;
import android.util.SparseArray;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

public class Utils {

    private static final String TAG = "Utils";

    /**
     * read file from assets, depending on filename provided
     * @param assets
     * @param filename
     * @return
     */
    public static String readFileFromAssets(AssetManager assets, String filename) {
        try {
            InputStream is = assets.open(filename);
            int size = is.available();

            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            return new String(buffer);
        } catch (Exception e) {
            return null;
        }
    }

    public static String stringify(List<Boolean> list) {
        return Arrays.toString(list.toArray());
    }

}
