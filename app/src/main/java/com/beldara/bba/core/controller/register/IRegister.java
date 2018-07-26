package com.beldara.bba.core.controller.register;

import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.model.RegisterEnity;
import com.beldara.bba.core.requestmodel.FollowupRequestEntity;

import java.util.HashMap;

import okhttp3.MultipartBody;

/**
 * Created by IN-RB on 09-06-2018.
 */

public interface IRegister {


    void getLogin(String user, String pass, String lat, String lon,String typ, IResponseSubcriber iResponseSubcriber);

    void getOpenLead( IResponseSubcriber iResponseSubcriber);

    void getMyLead(String userid, IResponseSubcriber iResponseSubcriber);

    void getAcceptLead(String userid,String sellerid, IResponseSubcriber iResponseSubcriber);

    void  SaveRegister(RegisterEnity entity, IResponseSubcriber iResponseSubcriber);

    void getFollowupHistory(String userid, IResponseSubcriber iResponseSubcriber);

    void getStatusMaster( IResponseSubcriber iResponseSubcriber);

    void  saveFollowUp(FollowupRequestEntity entity, IResponseSubcriber iResponseSubcriber);

    void uploadDocuments(MultipartBody.Part document, HashMap<String, Integer> body, final IResponseSubcriber iResponseSubcriber);


}
