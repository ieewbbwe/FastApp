package com.webber.mcorelibspace.demo.core.mvvm;


class EmailLoginRequestModelInternal {
    private String email;
    private String password;

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    private EmailLoginRequestModelInternal(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }


    public static class Builder {
        private String email;
        private String password;

        public Builder email(String email) {
            this.email = (email == null ? "" : email);
            return this;
        }

        public Builder password(String password) {
            this.password = (password == null ? "" : password);
            return this;
        }

        public EmailLoginRequestModelInternal build() {
            return new EmailLoginRequestModelInternal(this);
        }
    }
}
