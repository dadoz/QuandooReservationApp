package com.application.restaurantreservation;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.application.restaurantreservation.db.DataManager;
import com.application.restaurantreservation.db.DbHelper;
import com.application.restaurantreservation.modules.NetworkModule;
import com.application.restaurantreservation.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;


@RunWith(AndroidJUnit4.class)
public class RetrofitServiceTableTest {
    private MockWebServer server;

    private final static String TABLE_RESPONSE_SUCCESS_FILENAME = "table_response_200.json";
    private final static String TABLE_RESPONSE_ERROR_FILENAME = "table_response_400.json";

    @Rule
    public ActivityTestRule<TableGridActivity> mRule = new ActivityTestRule<>(TableGridActivity.class);
    private DataManager dataManager;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();

        //inject fake server
        NetworkModule.baseUrlEndpoint = server.url("/").url().toString();

        //start activity
        mRule.getActivity().startActivity(new Intent(mRule.getActivity(), TableGridActivity.class));

        //get db
        dataManager = new DataManager(new DbHelper(getTargetContext(), "restaurant-reservations.db", 1));
    }


    @Test
    public void getTablesSuccessTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                TABLE_RESPONSE_SUCCESS_FILENAME);
        dataManager.clearAllReservations();

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseString));

        onView(withId(R.id.tableEmptyViewId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tableProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tableRecyclerViewId)).check(matches(isDisplayed()));
    }


    @Test
    public void getTablesErrorTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                TABLE_RESPONSE_ERROR_FILENAME);
        dataManager.clearAllReservations();

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(responseString));

        onView(withId(R.id.tableEmptyViewId)).check(matches(isDisplayed()));
        onView(withId(R.id.tableProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.tableRecyclerViewId)).check(matches(not(isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
        dataManager.close();
    }

}
