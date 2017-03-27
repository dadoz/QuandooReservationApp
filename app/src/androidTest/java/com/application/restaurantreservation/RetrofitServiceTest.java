package com.application.restaurantreservation;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.application.restaurantreservation.MainActivity;
import com.application.restaurantreservation.modules.NetworkModule;
import com.application.restaurantreservation.utils.Utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.not;


@RunWith(AndroidJUnit4.class)
public class RetrofitServiceTest {
    private MockWebServer server;

    private final static String CUSTOMER_RESPONSE_SUCCESS_FILENAME = "customer_response_200.json";
    private final static String CUSTOMER_RESPONSE_ERROR_FILENAME = "customer_response_400.json";
    private final static String TABLE_RESPONSE_SUCCESS_FILENAME = "table_response_200.json";
    private final static String TABLE_RESPONSE_ERROR_FILENAME = "table_response_400.json";

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        NetworkModule.baseUrlEndpoint = server.url("/").url().toString();

        mRule.getActivity().startActivity(new Intent(mRule.getActivity(), MainActivity.class));
    }

    @Test
    public void getCustomerSuccessTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                CUSTOMER_RESPONSE_SUCCESS_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseString));

        onView(withId(R.id.emptyCustomerViewId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerRecyclerViewId)).check(matches(isDisplayed()));
    }


    @Test
    public void getCustomerErrorTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                CUSTOMER_RESPONSE_ERROR_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(responseString));

        onView(withId(R.id.emptyCustomerViewId)).check(matches(isDisplayed()));
        onView(withId(R.id.customerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerRecyclerViewId)).check(matches(not(isDisplayed())));
    }
    @Test
    public void getTablesSuccessTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                TABLE_RESPONSE_SUCCESS_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseString));

        onView(withId(R.id.emptyCustomerViewId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerRecyclerViewId)).check(matches(isDisplayed()));
    }


    @Test
    public void getTablesErrorTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                TABLE_RESPONSE_ERROR_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(responseString));

        onView(withId(R.id.emptyCustomerViewId)).check(matches(isDisplayed()));
        onView(withId(R.id.customerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.customerRecyclerViewId)).check(matches(not(isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

}
