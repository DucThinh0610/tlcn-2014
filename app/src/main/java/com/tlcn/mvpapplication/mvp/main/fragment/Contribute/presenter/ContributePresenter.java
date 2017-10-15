package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;
import com.tlcn.mvpapplication.utils.DateUtils;
import com.tlcn.mvpapplication.utils.Utilities;

import java.io.File;

import okhttp3.MultipartBody;

public class ContributePresenter extends BasePresenter implements IContributePresenter {
    public ContributionRequest contribution = new ContributionRequest();

    private MultipartBody.Part mtlPart;
    private File fileUpload;

    public void setMtlPart(MultipartBody.Part mtlPart) {
        this.mtlPart = mtlPart;
    }

    public void attachView(IContributeView view) {
        super.attachView(view);
    }

    public IContributeView getView() {
        return (IContributeView) getIView();
    }

    private StorageReference storageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    UploadTask uploadTask;

    @Override
    public void onCreate() {
        super.onCreate();
        storageRef = storage.getReference();
    }

    @Override
    public void sendContribution() {
        getManager().addContribution(contribution, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                getView().hideLoading();
                contribution = new ContributionRequest();
                getView().onSuccess();
            }

            @Override
            public void failure(RestError error) {
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }

    @Override
    public void uploadImage() {
        getView().showLoading();
        Uri file = Uri.fromFile(fileUpload);
        Log.d("Request", "images/" + DateUtils.getCurrentDate() + file.getLastPathSegment());
        StorageReference imageRef = storageRef.child("images/" + Utilities.createFileName() + file.getLastPathSegment());
        uploadTask = imageRef.putFile(file);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Fail", e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d("Success", "OK");
            }
        });

        if (mtlPart == null) {
            sendContribution();
        } else {
            getManager().uploadFile(this.mtlPart, new ApiCallback<UploadFileResponse>() {
                @Override
                public void success(UploadFileResponse res) {
                    mtlPart = null;
                    contribution.setFile(res.getImageFile().getUrl());
                    sendContribution();
                    getView().removeImageView();
                }

                @Override
                public void failure(RestError error) {
                    sendContribution();
                }
            });
        }
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }
}
