package com.beldara.bba.core.model;

import android.os.Parcel;
import android.os.Parcelable;

public  class LeadEntity implements Parcelable {
        /**
         * sellerid : 720909
         * sellerName : 
         * email : nitind@indiabigshop.com
         * mobile : 9657416324
         * company : IBS
         * assigned : 0
         * Category : Wooden Doors
         */

        private String sellerid;
        private String sellerName;
        private String email;
        private String mobile;
        private String company;
        private String assigned;
        private String Category;

    protected LeadEntity(Parcel in) {
        sellerid = in.readString();
        sellerName = in.readString();
        email = in.readString();
        mobile = in.readString();
        company = in.readString();
        assigned = in.readString();
        Category = in.readString();
    }

    public static final Creator<LeadEntity> CREATOR = new Creator<LeadEntity>() {
        @Override
        public LeadEntity createFromParcel(Parcel in) {
            return new LeadEntity(in);
        }

        @Override
        public LeadEntity[] newArray(int size) {
            return new LeadEntity[size];
        }
    };

    public String getSellerid() {
            return sellerid;
        }

        public void setSellerid(String sellerid) {
            this.sellerid = sellerid;
        }

        public String getSellerName() {
            return sellerName;
        }

        public void setSellerName(String sellerName) {
            this.sellerName = sellerName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getAssigned() {
            return assigned;
        }

        public void setAssigned(String assigned) {
            this.assigned = assigned;
        }

        public String getCategory() {
            return Category;
        }

        public void setCategory(String Category) {
            this.Category = Category;
        }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(sellerid);
        dest.writeString(sellerName);
        dest.writeString(email);
        dest.writeString(mobile);
        dest.writeString(company);
        dest.writeString(assigned);
        dest.writeString(Category);
    }
}