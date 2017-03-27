package com.application.restaurantreservation.db;

import android.database.Cursor;

import java.util.Iterator;

public class CursorIterator implements Iterable<Cursor> {
    private Cursor cursor;

    public CursorIterator(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public Iterator<Cursor> iterator() {
        return new Iterator<Cursor>() {
            @Override
            public boolean hasNext() {
                return !cursor.isClosed() && cursor.moveToNext();
            }

            @Override
            public Cursor next() {
                return cursor;
            }
        };
    }
}