package com.beldara.bba.core.requestbuilder;



import com.beldara.bba.core.RetroRequestBuilder;
import com.beldara.bba.core.model.RegisterEnity;
import com.beldara.bba.core.response.LeadResponse;
import com.beldara.bba.core.response.LoginResponse;
import com.beldara.bba.core.response.RegisterResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

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
        Call<LeadResponse> getAcceptLead(@Body HashMap<String, String> body);


        @POST("/kapi.php?m=R")
        Call<RegisterResponse> SaveRegister(@Body RegisterEnity bodyParameter);
    }
}
