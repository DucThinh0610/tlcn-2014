package com.tlcn.mvpapplication.service;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.tlcn.mvpapplication.api.ApiManager;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;

/**
 * Created by apple on 10/13/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
        ApiManager apiManager = new ApiManager();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            apiManager.pushNotificationToken(FirebaseAuth.getInstance().getCurrentUser().getUid(), token, new ApiCallback<BaseResponse>() {
                @Override
                public void success(BaseResponse res) {
                    Log.e("Fcm", res.getMessage());
                }

                @Override
                public void failure(RestError error) {
                    Toast.makeText(MyFirebaseInstanceIDService.this, error.message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
