package com.beldara.bba.core.requestbuilder;



import com.beldara.bba.core.RetroRequestBuilder;
import com.beldara.bba.core.model.RegisterEnity;
import com.beldara.bba.core.requestmodel.FollowupRequestEntity;
import com.beldara.bba.core.response.CommonResponse;
import com.beldara.bba.core.response.DocumentResponse;
import com.beldara.bba.core.response.FollowUpHistoryResponse;
import com.beldara.bba.core.response.FollowUpSaveResponse;
import com.beldara.bba.core.response.LeadResponse;
import com.beldara.bba.core.response.LoginResponse;
import com.beldara.bba.core.response.RegisterResponse;
import com.beldara.bba.core.response.StatusResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class RegisterRequestBuilder extends RetroRequestBuilder {

    public RegisterRequestBuilder.RegisterQuotesNetworkService getService() {

        return super.build().create(RegisterRequestBuilder.RegisterQuotesNetworkService.class);
    }

    public interface RegisterQuotesNetworkService {


        @POST("/kapi.php?m=L")
        Call<LoginResponse> getLogin(@Body HashMap<String, String> body);

        @POST("/kapi.php?m=O")
        Call<LeadResponse> getOpenLead();

        @POST("/kapi.php?m=M")
        Call<LeadResponse> getMyLead(@Body HashMap<String, String> body);


        @POST("/kapi.php?m=AL")
        Call<CommonResponse> getAcceptLead(@Body HashMap<String, String> body);


        @POST("/kapi.php?m=R")
        Call<RegisterResponse> SaveRegister(@Body RegisterEnity bodyParameter);

        //FollowUp
        @POST("/kapi.php?m=FH")
        Call<FollowUpHistoryResponse> getFollowupHistory(@Body HashMap<String, String> body);

        @POST("/kapi.php?m=FM")
        Call<StatusResponse> getStatusMaster();

        @POST("/kapi.php?m=FI")
        Call<FollowUpSaveResponse> saveFollowup(@Body FollowupRequestEntity followupRequestEntity);

        //////////// Upload File //////////////
        @Multipart
        @POST("/kapi.php?m=FL")
        Call<DocumentResponse> uploadDocument(@Part() MultipartBody.Part doc, @PartMap() Map<String, Integer> partMap);
    }
}
