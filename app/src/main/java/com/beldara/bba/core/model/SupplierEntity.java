package com.beldara.bba.core.model;

public  class SupplierEntity {
        /**
         * sellerid : 720909
         * company : IBS
         * mobile : 9657416324
         * email : 
         * name : Nitin 
         */

        private String sellerid;
        private String company;
        private String mobile;
        private String email;
        private String name;

        public String getSellerid() {
            return sellerid;
        }

        public void setSellerid(String sellerid) {
            this.sellerid = sellerid;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }