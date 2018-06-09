package com.beldara.bba.core.controller.register;

import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.model.RegisterEnity;

/**
 * Created by IN-RB on 09-06-2018.
 */

public interface IRegister {


    void getLogin(String user, String pass, String lat, String lon,String typ, IResponseSubcriber iResponseSubcriber);

    void getOpenLead( IResponseSubcriber iResponseSubcriber);

    void getMyLead(String userid, IResponseSubcriber iResponseSubcriber);

    void getAcceptLead(String userid, IResponseSubcriber iResponseSubcriber);

    void  SaveRegister(RegisterEnity entity, IResponseSubcriber iResponseSubcriber);

}
