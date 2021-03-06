package com.tlcn.mvpapplication.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

/**
 * Created by ducthinh on 12/09/2017.
 */

public class GoogleApiHelper implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = GoogleApiHelper.class.getSimpleName();
    private Context context;
    private GoogleApiClient mGoogleApiClient;
    private ConnectionListener connectionListener;
    private Bundle connectionBundle;

    public GoogleApiHelper(Context context) {
        this.context = context;
        buildGoogleApiClient();
        connect();
    }

    private void connect() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    public void disconnect() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    public boolean isConnected() {
        return mGoogleApiClient != null && mGoogleApiClient.isConnected();
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .build();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        connectionBundle = bundle;
        if (connectionListener != null) {
            connectionListener.onConnected(bundle);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "onConnectionSuspended: googleApiClient.connect()");
        mGoogleApiClient.connect();
        if (connectionListener != null) {
            connectionListener.onConnectionSuspended(i);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed: connectionResult = " + connectionResult);
        if (connectionListener != null) {
            connectionListener.onConnectionFailed(connectionResult);
        }
    }

    public interface ConnectionListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);

        void onConnectionSuspended(int i);

        void onConnected(Bundle bundle);
    }
    public GoogleApiClient getGoogleApiClient() {
        return this.mGoogleApiClient;
    }

}
