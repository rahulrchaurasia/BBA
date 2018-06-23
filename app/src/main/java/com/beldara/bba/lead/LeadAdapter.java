package com.beldara.bba.lead;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beldara.bba.R;
import com.beldara.bba.core.model.FollowUpHistoryEntity;
import com.beldara.bba.core.model.LeadEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class LeadAdapter  extends RecyclerView.Adapter<LeadAdapter.LeadItem> {

    Activity mContext;
    List<LeadEntity> LeadLst;
    String LeadType = "";

    public LeadAdapter(Activity mContext, List<LeadEntity> leadLst , String leadType ) {
        this.mContext = mContext;
        LeadLst = leadLst;
        LeadType = leadType;
    }

    public class LeadItem extends RecyclerView.ViewHolder
    {
        public TextView txtName, txtMobile, txtCategory,txtEmailID ,txtContPerson;
        public LinearLayout lyCalling,lyEdit,lyHistory;
        Button btnAssignee;

        public LeadItem(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtEmailID = (TextView) itemView.findViewById(R.id.txtEmailID);
            txtContPerson = (TextView) itemView.findViewById(R.id.txtContPerson);
            txtCategory = (TextView) itemView.findViewById(R.id.txtCategory);
            lyCalling = (LinearLayout) itemView.findViewById(R.id.lyCalling);
            lyEdit = (LinearLayout) itemView.findViewById(R.id.lyEdit);
            lyHistory = (LinearLayout) itemView.findViewById(R.id.lyHistory);
            btnAssignee = (Button) itemView.findViewById(R.id.btnAssignee);

        }
    }


    @Override
    public LeadAdapter.LeadItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lead_item, parent, false);

        return new LeadAdapter.LeadItem(itemView);

    }

    @Override
    public void onBindViewHolder(LeadAdapter.LeadItem holder, final int position) {

        final LeadEntity leadEntity = LeadLst.get(position);

        if(LeadType.equals("M"))
        {
            holder.lyCalling.setVisibility(View.VISIBLE);
            holder.lyEdit.setVisibility(View.VISIBLE);
            holder.lyHistory.setVisibility(View.VISIBLE);

            holder.btnAssignee.setVisibility(View.GONE);
        }else{
            holder.lyCalling.setVisibility(View.GONE);
            holder.lyEdit.setVisibility(View.GONE);
            holder.lyHistory.setVisibility(View.GONE);
            holder.btnAssignee.setVisibility(View.VISIBLE);
        }
        holder.txtName.setText("" + leadEntity.getCompany());
        holder.txtMobile.setText("" + leadEntity.getMobile());
        holder.txtEmailID.setText("" + leadEntity.getEmail());
        holder.txtContPerson.setText("" + leadEntity.getSellerName());

  //      holder.txtRemark.setText("" + leadListEntity.getRemark());
        holder.lyCalling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LeadActivity) mContext).dialCall(leadEntity.getMobile(), leadEntity);
            }
        });

        holder.lyHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LeadActivity) mContext).redirectQuotefollowup(leadEntity.getSellerid());
            }
        });

        holder.btnAssignee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LeadActivity) mContext).getAcceptLead(leadEntity.getSellerid());
            }
        });

    }

    @Override
    public int getItemCount() {
        return LeadLst.size();
    }
}
