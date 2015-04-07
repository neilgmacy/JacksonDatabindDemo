package org.mcguiggan.jackson_databind_demo.ui;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.TextView;
import org.mcguiggan.jackson_databind_demo.R;
import org.mcguiggan.jackson_databind_demo.data.UserManager;
import org.mcguiggan.jackson_databind_demo.model.User;

import java.util.List;

public class MainActivity extends ActionBarActivity implements UserManager.UserManagerListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.text_view);

        UserManager.getInstance().requestUsers(this);
    }

    @Override
    public void onUserDataLoaded(final List<User> userList) {
        //To check that the list of users was received correctly, set a breakpoint here
        Log.d(TAG, "received parsed UserList");

        //update the UI to show that the data has been received
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTextView.setText("Received parsed UserList with " + userList.size() + " users.");
            }
        });
    }
}
