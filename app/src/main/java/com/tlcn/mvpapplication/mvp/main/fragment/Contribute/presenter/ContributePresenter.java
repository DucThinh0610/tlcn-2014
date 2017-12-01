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
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;
import com.tlcn.mvpapplication.utils.DateUtils;

import java.io.File;

public class ContributePresenter extends BasePresenter implements IContributePresenter {
    public ContributionRequest contribution = new ContributionRequest();
    private File fileUpload;

    public void attachView(IContributeView view) {
        super.attachView(view);
    }

    public IContributeView getView() {
        return (IContributeView) getIView();
    }

    private StorageReference storageRef;
    private FirebaseStorage storage = FirebaseStorage.getInstance();

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
        if (fileUpload == null) {
            getView().onFail("Bạn cần đăng kèm hình ảnh!");
        } else {
            final Uri file = Uri.fromFile(fileUpload);
            final String filename = DateUtils.getCurrentDate() + file.getLastPathSegment();
            StorageReference imageRef = storageRef.child("images/" + filename);
            UploadTask uploadTask = imageRef.putFile(file);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d("Fail", e.getMessage());
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("Success", "OK");
                    fileUpload = null;
                    contribution.setFile(filename);
                    sendContribution();
                    getView().removeImageView();
                }
            });
        }
    }

    public void setFileUpload(File fileUpload) {
        this.fileUpload = fileUpload;
    }

    public File getImageUpload() {
        return fileUpload;
    }
}
