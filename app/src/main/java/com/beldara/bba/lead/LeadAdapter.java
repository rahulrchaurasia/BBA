package com.beldara.bba.lead;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beldara.bba.R;
import com.beldara.bba.core.model.LeadEntity;

import java.util.List;

/**
 * Created by IN-RB on 09-06-2018.
 */

public class LeadAdapter  extends RecyclerView.Adapter<LeadAdapter.LeadItem> {

    Activity mContext;
    List<LeadEntity> LeadLst;

    public LeadAdapter(Activity mContext, List<LeadEntity> leadLst) {
        this.mContext = mContext;
        LeadLst = leadLst;
    }

    public class LeadItem extends RecyclerView.ViewHolder
    {
        public TextView txtName, txtMobile, txtCategory,txtEmailID ,txtContPerson;
        public LinearLayout lyCalling,lyEdit;

        public LeadItem(View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtMobile = (TextView) itemView.findViewById(R.id.txtMobile);
            txtEmailID = (TextView) itemView.findViewById(R.id.txtEmailID);
            txtContPerson = (TextView) itemView.findViewById(R.id.txtContPerson);
            lyCalling = (LinearLayout) itemView.findViewById(R.id.lyCalling);
            lyEdit = (LinearLayout) itemView.findViewById(R.id.lyEdit);

        }
    }


    @Override
    public LeadAdapter.LeadItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_lead_item, parent, false);

        return new LeadAdapter.LeadItem(itemView);

    }

    @Override
    public void onBindViewHolder(LeadAdapter.LeadItem holder, int position) {

        final LeadEntity leadEntity = LeadLst.get(position);
        holder.txtName.setText("" + leadEntity.getCompany());
        holder.txtMobile.setText("" + leadEntity.getMobile());
        holder.txtEmailID.setText("" + leadEntity.getEmail());
        holder.txtContPerson.setText("" + leadEntity.getSellerName());

  //      holder.txtRemark.setText("" + leadListEntity.getRemark());
//        holder.lyCalling.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((AddLeadListActivity) context).dialCall(leadListEntity.getMbno(), leadListEntity, position);
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return LeadLst.size();
    }
}
