package com.anubis.oauthkit;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by geo on 1/23/17.
 */

public class OAuthConfig implements Parcelable {
    private OAuthConfig(String baseUrl,
                       String callbackUrl,
                       String consumerKey,
                       String consumerSecret,
                       String requestTokenEndpoint,
                       String accessTokenEndpoint,
                       String authorizationEndpoint) {
        this.baseUrl = baseUrl;
        this.callbackUrl = callbackUrl;
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.requestTokenEndpoint = requestTokenEndpoint;
        this.accessTokenEndpoint = accessTokenEndpoint;
        this.authorizationEndpoint = authorizationEndpoint;
    }

    private static OAuthConfig instance;

    public static OAuthConfig getInstance(String baseUrl,
                                          String callbackUrl,
                                          String consumerKey,
                                          String consumerSecret,
                                          String requestTokenEndpoint,
                                          String accessTokenEndpoint,
                                          String authorizationEndpoint) {
        if (instance == null) {
            instance = new OAuthConfig(baseUrl,
                    callbackUrl,
                    consumerKey,
                    consumerSecret,
                    requestTokenEndpoint,
                    accessTokenEndpoint,
                    authorizationEndpoint);
        }
        return instance;
    }

    public static OAuthConfig getInstance() {
         return instance;
    }

    private String baseUrl;
    private String callbackUrl;
    private String consumerKey;
    private String consumerSecret;
    private String requestTokenEndpoint;
    private String accessTokenEndpoint;
    private String authorizationEndpoint;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getConsumerKey() {
        return consumerKey;
    }

    public void setConsumerKey(String consumerKey) {
        this.consumerKey = consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret = consumerSecret;
    }

    public String getRequestTokenEndpoint() {
        return requestTokenEndpoint;
    }

    public void setRequestTokenEndpoint(String requestTokenEndpoint) {
        this.requestTokenEndpoint = requestTokenEndpoint;
    }

    public String getAccessTokenEndpoint() {
        return accessTokenEndpoint;
    }

    public void setAccessTokenEndpoint(String accessTokenEndpoint) {
        this.accessTokenEndpoint = accessTokenEndpoint;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    public void setAuthorizationEndpoint(String authorizationEndpoint) {
        this.authorizationEndpoint = authorizationEndpoint;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.baseUrl);
        dest.writeString(this.callbackUrl);
        dest.writeString(this.consumerKey);
        dest.writeString(this.consumerSecret);
        dest.writeString(this.requestTokenEndpoint);
        dest.writeString(this.accessTokenEndpoint);
        dest.writeString(this.authorizationEndpoint);
    }

    public OAuthConfig() {
    }

    protected OAuthConfig(Parcel in) {
        this.baseUrl = in.readString();
        this.callbackUrl = in.readString();
        this.consumerKey = in.readString();
        this.consumerSecret = in.readString();
        this.requestTokenEndpoint = in.readString();
        this.accessTokenEndpoint = in.readString();
        this.authorizationEndpoint = in.readString();
    }

    public static final Parcelable.Creator<OAuthConfig> CREATOR = new Parcelable.Creator<OAuthConfig>() {
        @Override
        public OAuthConfig createFromParcel(Parcel source) {
            return new OAuthConfig(source);
        }

        @Override
        public OAuthConfig[] newArray(int size) {
            return new OAuthConfig[size];
        }
    };
}
