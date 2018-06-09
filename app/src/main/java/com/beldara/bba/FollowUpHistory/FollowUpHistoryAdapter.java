package com.beldara.bba.FollowUpHistory;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.beldara.bba.R;
import com.beldara.bba.core.model.FollowUpHistoryEntity;

import java.util.List;

/**
 * Created by IN-RB on 10-06-2018.
 */

public class FollowUpHistoryAdapter extends RecyclerView.Adapter<FollowUpHistoryAdapter.ApplicationItem> implements View.OnClickListener  {
    Context mContex;
    List<FollowUpHistoryEntity> mAppList;

    public FollowUpHistoryAdapter(Context context, List<FollowUpHistoryEntity> list) {
        this.mContex = context;
        mAppList = list;

    }

    @Override
    public ApplicationItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_followhistory_item, parent, false);
        return new ApplicationItem(itemView);
    }

    @Override
    public void onBindViewHolder(ApplicationItem holder, int position) {

        if (holder instanceof ApplicationItem) {
            ApplicationItem item = (ApplicationItem) holder;
            FollowUpHistoryEntity entity = mAppList.get(position);

            item.txtName.setText(entity.getName());
            item.txtMobileNo.setText(entity.getMobile());
            item.txtStatus.setText(entity.getStatus());
            item.txtEmail.setText(entity.getEmail());


            item.txtremark.setText(entity.getRemark());
            item.txtsellerid.setText(entity.getSellerid());
            item.txtfollowup_date.setText(entity.getFollowup_date().split(" ")[0]);


        }

    }


    @Override
    public void onClick(View view) {

//        switch (view.getId()) {
//            case R.id.txtOverflowMenu:
//                openPopUp(view, (FollowUpHistoryEntity) view.getTag(R.id.txtOverflowMenu));
//                break;
//
//        }
    }

    @Override
    public int getItemCount() {
        return mAppList.size();
    }

    public class ApplicationItem extends RecyclerView.ViewHolder {

        public TextView txtName, txtMobileNo, txtStatus, txtEmail,txtremark ,txtsellerid,txtfollowup_date;


        // LinearLayout linearLayout, linearLayout1;
        public ApplicationItem(View view) {
            super(view);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtMobileNo = (TextView) itemView.findViewById(R.id.txtMobileNo);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtEmail = (TextView) itemView.findViewById(R.id.txtEmail);

            txtremark = (TextView) itemView.findViewById(R.id.txtremark);
            txtsellerid = (TextView) itemView.findViewById(R.id.txtsellerid);
            txtfollowup_date = (TextView) itemView.findViewById(R.id.txtfollowup_date);



        }
    }


    public void refreshAdapter(List<FollowUpHistoryEntity> list) {
        mAppList = list;
    }

}
