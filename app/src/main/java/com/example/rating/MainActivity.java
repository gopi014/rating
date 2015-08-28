package com.example.rating;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rating.helper.DatabaseHelper;
import com.example.rating.model.Users;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

public class MainActivity extends Activity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private static final String TAG = "MainActivity";
    /* Request code used to invoke sign in user interactions. */
    private static final int RC_SIGN_IN = 0;
   private ProgressDialog progressBar;

    /* Client used to interact with Google APIs. */
    private GoogleApiClient mGoogleApiClient;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;

    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    private TextView mStatus;
    // Database Helper
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);
        db = new DatabaseHelper(getApplicationContext());

        // Set up view instances
        mStatus = (TextView) findViewById(R.id.status);

        // Build GoogleApiClient with access to basic profile
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
    }
    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            // Show signed-in user's name

            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
           // mStatus.setText(getString(R.string.signed_in_fmt, name))
           // Toast.makeText(this,email,Toast.LENGTH_LONG).show();
            if(db.getUserCount(email)>0){
                int userid=db.getuserid(email);

                Intent intent = new Intent(this, Products.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",String.valueOf(userid));
                intent.putExtras(bundle);
                startActivity(intent);
                progressBar.dismiss();
            }
            else{
                Users user=new Users(email);
                long user1_id=db.createUsers(user);
                int userid=db.getuserid(email);
                Intent intent = new Intent(this, Products.class);
                Bundle bundle = new Bundle();
                bundle.putString("userid",String.valueOf(userid));
                intent.putExtras(bundle);
                startActivity(intent);
                progressBar.dismiss();
            }

        } else {
            // Show signed-out message
           // mStatus.setText(R.string.signed_out);

            // Set button visibility
            findViewById(R.id.button).setEnabled(true);
            findViewById(R.id.button).setVisibility(View.VISIBLE);
           // findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult:" + requestCode + ":" + resultCode + ":" + data);

        if (requestCode == RC_SIGN_IN) {
            // If the error resolution was not successful we should not resolve further errors.
            if (resultCode != RESULT_OK) {
                mShouldResolve = false;
            }

            mIsResolving = false;
            mGoogleApiClient.connect();
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        // onConnected indicates that an account was selected on the device, that the selected
        // account has granted any requested permissions to our app and that we were able to
        // establish a service connection to Google Play services.
        Log.d(TAG, "onConnected:" + bundle);

        // Show the signed-in UI
        updateUI(true);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                // User clicked the sign-in button, so begin the sign-in process and automatically
                // attempt to resolve any errors that occur.
               // mStatus.setText(R.string.signing_in);
                // [START sign_in_clicked]
                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Logging in");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setProgress(0);
                progressBar.setMax(100);
                progressBar.show();
                mShouldResolve = true;
                mGoogleApiClient.connect();
                // [END sign_in_clicked]
                break;

        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG, "Could not resolve ConnectionResult.", e);
                    mIsResolving = false;
                    mGoogleApiClient.connect();
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                showErrorDialog(connectionResult);
            }
        } else {
            // Show the signed-out UI
            updateUI(false);
        }

    }
    private void showErrorDialog(ConnectionResult connectionResult) {
        int errorCode = connectionResult.getErrorCode();

        if (GooglePlayServicesUtil.isUserRecoverableError(errorCode)) {
            // Show the default Google Play services error dialog which may still start an intent
            // on our behalf if the user can resolve the issue.
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, RC_SIGN_IN,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            mShouldResolve = false;
                            updateUI(false);
                        }
                    }).show();
        } else {
            // No default Google Play Services error, display a message to the user.
            String errorString = getString(R.string.play_services_error_fmt, errorCode);
            Toast.makeText(this, errorString, Toast.LENGTH_SHORT).show();

            mShouldResolve = false;
            updateUI(false);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

}
