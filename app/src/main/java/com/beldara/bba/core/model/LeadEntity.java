package com.beldara.bba.core.model;

public  class LeadEntity {
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
    }