package com.beldara.bba.FollowUpHistory;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.beldara.bba.BaseActivity;
import com.beldara.bba.R;
import com.beldara.bba.core.APIResponse;
import com.beldara.bba.core.IResponseSubcriber;
import com.beldara.bba.core.controller.register.RegisterController;
import com.beldara.bba.core.model.FollowUpHistoryEntity;
import com.beldara.bba.core.response.FollowUpHistoryResponse;
import com.beldara.bba.lead.LeadActivity;

import java.util.ArrayList;
import java.util.List;

public class FollowUpHistoryActivity extends BaseActivity implements  IResponseSubcriber {


    RecyclerView rvList;
    FollowUpHistoryAdapter mAdapter;

    List<FollowUpHistoryEntity> mList;
    boolean isHit = false;
String sellerid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mList = new ArrayList<FollowUpHistoryEntity>();

        if (getIntent().getStringExtra(LeadActivity.FROM_ID) != null) {
            sellerid = getIntent().getStringExtra(LeadActivity.FROM_ID);
            //  homeLoanRequestEntity = fmHomeLoanRequest.getHomeLoanRequest();


        }
        init();

        fetchPendingCases(0);

        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastCompletelyVisibleItemPosition = 0;

                lastCompletelyVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastVisibleItemPosition();


                if (lastCompletelyVisibleItemPosition == mList.size() - 1) {
                    if (!isHit) {
                        isHit = true;
                        fetchPendingCases(mList.size());

                    }
                }
            }
        });
    }

    private void fetchPendingCases(int count) {

    //    showDialog();
        new RegisterController(this).getFollowupHistory(sellerid, this);


    }

    private void init() {
        rvList = (RecyclerView) findViewById(R.id.rvList);
        rvList.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(layoutManager);
        mAdapter = new FollowUpHistoryAdapter(this, mList);
        rvList.setAdapter(mAdapter);
    }

    @Override
    public void OnSuccess(APIResponse response, String message) {
     //   cancelDialog();
        List<FollowUpHistoryEntity> list = new ArrayList<>();
        if (response instanceof FollowUpHistoryResponse) {
            list = ((FollowUpHistoryResponse) response).getResult();
            if (list.size() > 0) {
                isHit = false;
                // Toast.makeText(this, "fetching more...", Toast.LENGTH_SHORT).show();

                for (FollowUpHistoryEntity entity : list) {
                    if (!mList.contains(entity)) {
                        mList.add(entity);
                    }
                }
            }
            //mAdapter = new PendingCasesAdapter(this, mPendingList);
            //rvPendingCasesList.setAdapter(mAdapter);
        } else if (response instanceof FollowUpHistoryResponse) {
           // mPendingList.remove(removePendingCasesEntity);
            //list=mPendingList;
            // mAdapter.refreshAdapter(mPendingList);
            // mAdapter.notifyDataSetChanged();
        }

        //mPendingList =list;
        mAdapter.refreshAdapter(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void OnFailure(Throwable t) {
        cancelDialog();
        Toast.makeText(this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
    }


}
