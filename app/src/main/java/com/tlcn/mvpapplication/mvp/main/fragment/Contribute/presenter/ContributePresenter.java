package com.tlcn.mvpapplication.mvp.main.fragment.Contribute.presenter;

import android.graphics.Bitmap;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.tlcn.mvpapplication.R;
import com.tlcn.mvpapplication.api.network.ApiCallback;
import com.tlcn.mvpapplication.api.network.BaseResponse;
import com.tlcn.mvpapplication.api.network.RestError;
import com.tlcn.mvpapplication.api.request.contribution.ContributionRequest;
import com.tlcn.mvpapplication.api.response.file.UploadFileResponse;
import com.tlcn.mvpapplication.app.App;
import com.tlcn.mvpapplication.base.BasePresenter;
import com.tlcn.mvpapplication.mvp.main.fragment.Contribute.view.IContributeView;
import com.tlcn.mvpapplication.utils.FileUtils;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;

import okhttp3.MultipartBody;

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
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            getView().hideLoading();
            getView().onFail(App.getContext().getString(R.string.please_login));
            return;
        }
        contribution.setToken(App.getUserInfo().getInfo().getToken());
        getManager().addContribution(contribution, new ApiCallback<BaseResponse>() {
            @Override
            public void success(BaseResponse res) {
                if (!isViewAttached())
                    return;
                getView().hideLoading();
                contribution = new ContributionRequest();
                getView().onSuccess();
            }

            @Override
            public void failure(RestError error) {
                if (!isViewAttached())
                    return;
                getView().onFail(error.message);
                getView().hideLoading();
            }
        });
    }

    @Override
    public void uploadImage() {
        getView().showLoading();
        if (App.getUserInfo().getInfo() == null || App.getUserInfo().getInfo().getToken().isEmpty()) {
            getView().hideLoading();
            getView().onFail(App.getContext().getString(R.string.please_login));
            return;
        }
        if (fileUpload == null) {
            getView().hideLoading();
            getView().onFail(App.getContext().getString(R.string.you_need_upload_with_image));
        } else {
            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
            Tiny.getInstance().source(fileUpload.getPath()).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                    //return the compressed file path and bitmap object
                    if (isSuccess) {
                        File file = new File(outfile);
//                        Uri uriFile = Uri.fromFile(file);
//                        String filename = DateUtils.getCurrentDate() + uriFile.getLastPathSegment();
                        MultipartBody.Part part = FileUtils.createMultipartBodyPart(App.getContext(), file);
                        getManager().uploadFile(App.getUserInfo().getInfo().getToken(), part, new ApiCallback<UploadFileResponse>() {
                            @Override
                            public void success(UploadFileResponse res) {
                                if (!isViewAttached())
                                    return;
                                fileUpload = null;
                                contribution.setFile(res.getImageFile().getPath());
                                sendContribution();
                                getView().removeImageView();
                            }

                            @Override
                            public void failure(RestError error) {

                            }
                        });
//                        Uri uriFile = Uri.fromFile(file);
//                        String filename = DateUtils.getCurrentDate() + uriFile.getLastPathSegment();

//                        StorageReference imageRef = storageRef.child("images/" + filename);
//                        UploadTask uploadTask = imageRef.putFile(uriFile);
//                        uploadTask.addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.d("Fail", e.getMessage());
//                            }
//                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Log.d("Success", "OK");
//                                fileUpload = null;
//                                contribution.setFile(filename);
//                                sendContribution();
//                                getView().removeImageView();
//                            }
//                        });
                    } else {
                        getView().hideLoading();
                        getView().onFail(App.getContext().getString(R.string.haven_error_please_check));
                    }
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
