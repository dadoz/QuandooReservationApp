package com.application.subitoit.githubstargazers;

import android.app.Application;
import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.application.subitoit.githubstargazers.application.StargazersApplication;
import com.application.subitoit.githubstargazers.managers.RetrofitManager;
import com.application.subitoit.githubstargazers.utils.Utils;

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
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
public class StargazersRetrofitTest {
    private MockWebServer server;

    private final static String STARGAZERS_RESPONSE_SUCCESS_FILENAME = "stargazers_response_200.json";
    private final static String STARGAZERS_RESPONSE_ERROR_FILENAME = "stargazers_response_400.json";

    @Rule
    public ActivityTestRule<MainActivity> mRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
        RetrofitManager.baseUrlEndpoint = server.url("/").url().toString();

        ((StargazersApplication) mRule.getActivity().getApplication()).setOwner("dadoz");
        ((StargazersApplication) mRule.getActivity().getApplication()).setRepo("SelectCardViewPrototype");

        mRule.getActivity().startActivity(new Intent(mRule.getActivity(), StargazersListActivity.class));
    }

    @Test
    public void getStargazerSuccessTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                STARGAZERS_RESPONSE_SUCCESS_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(responseString));

        onView(withId(R.id.emptyViewId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.stargazerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.stargazerRecyclerViewId)).check(matches(isDisplayed()));
    }


    @Test
    public void getStargazerErrorTest() throws Exception {
        String responseString = Utils.readFileFromAssets(mRule.getActivity().getAssets(),
                STARGAZERS_RESPONSE_ERROR_FILENAME);

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(responseString));

        onView(withId(R.id.emptyViewId)).check(matches(isDisplayed()));
        onView(withId(R.id.stargazerProgressbarId)).check(matches(not(isDisplayed())));
        onView(withId(R.id.stargazerRecyclerViewId)).check(matches(not(isDisplayed())));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

}
