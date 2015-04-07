package org.mcguiggan.jackson_databind_demo.data;

import android.util.Log;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.mcguiggan.jackson_databind_demo.BuildConfig;
import org.mcguiggan.jackson_databind_demo.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Used for making a request to the server to load the JSON data.
 * This only requests data from the server, but could easily be extended to manage a local
 * in-memory and/or disk-based cache as well.
 */
public class UserManager {

    private static final String TAG = UserManager.class.getSimpleName();

    private static UserManager sInstance; //Singleton instance

    private UserManager() {
        //This default constructor is hidden, use getInstance() to instantiate the singleton UserManager.
    }

    /**
     * This method is used to access the Singleton instance of the UserManager class,
     * and also creates it if necessary.
     *
     * @return the Singleton instance of the UserManager class.
     */
    public static UserManager getInstance() {
        if (sInstance == null) {
            sInstance = new UserManager();
        }

        return sInstance;
    }

    /**
     * Make a request to the server to get the social data.
     *
     * @param listener To receive the loaded data object.
     */
    public void requestUsers(final UserManagerListener listener) {
        //Create your client.
        OkHttpClient client = new OkHttpClient();

        //Construct your request.
        Request request = new Request.Builder()
                .url(BuildConfig.SOCIAL_DATA_ENDPOINT) //Configured in the app's build.gradle file.
                .build();

        //Use the client to make the request, with a Callback to handle the response.
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                //do proper error handling here
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //do proper error handling here
                    Log.e(TAG, "Unexpected code " + response);
                }

                if (listener != null) {
                    Log.d(TAG, "Loaded from server");
                    final byte[] responseBytes = response.body().bytes();
                    ObjectMapper objectMapper = new ObjectMapper();
                    User[] userList = objectMapper.readValue(responseBytes, User[].class);
                    listener.onUserDataLoaded(Arrays.asList(userList));
                }
            }
        });
    }

    /**
     * Interface to be implemented by any class that requests data from the UserManager.
     */
    public interface UserManagerListener {

        /**
         * Callback for when the social data has been fetched.
         *
         * @param userList The list of users that has been loaded.
         */
        void onUserDataLoaded(List<User> userList);
    }
}
