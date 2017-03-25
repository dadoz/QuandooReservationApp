package com.application.restaurantreservation.utils;

import android.content.res.AssetManager;
import android.util.SparseArray;

import java.io.InputStream;

public class Utils {

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
}
