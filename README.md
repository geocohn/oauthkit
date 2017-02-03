# oauthkit  <img src="http://i.imgur.com/O63xrx7.png" height="200"/>

A convenience library for Android that uses signpost and an okhttp client to complete OAuth 1.0 easily with an RxJava adapted Retrofit service.

It was started from codepath's excellent helper code and uses the signpost-okhttp module.


To use:

1) add to gradle as a dependency:

    compile project(':oauthkit')

2) To convey private key info to a packaged library, it must be passed in
  at runtime as data. The singleton class OAuthConfig is provided for this,
  its use is described in the next step.

   There are 7 fields you need to provide. You can inject them into the build via a .properties file, which should be ignored by vcs. Remember to escape ‘colon :’ with a  backslash ’\’ and enclose values in quotes!

   ~~~
   baseUrl="base_url_here"
   callbackUrl="oauth\://cprest"  
   consumerKey="<consumer_key_from_api_provider>"
   consumerSecret="<consumer_secret_from_api_provider>"
   requestTokenEndpoint="req_url_here"
   accessTokenEndpoint="access_url_here"
   authorizationEndpoint="auth_url_here"
   ~~~

3) In your login activity:
 extend OAuthLoginActivity

 e.g.

~~~
LoginActivity extends OAuthLoginActivity
~~~
In your LoginActivity's OnCreate(), initialize the OAuthConfig
by calling its `getInstance()` method before calling `super.OnCreate()`.

e.g.
~~~
@Override
public void onCreate(Bundle savedInstanceState) {
    oAuthConfig = OAuthConfig.getInstance(BuildConfig.baseUrl,
            BuildConfig.callbackUrl,
            BuildConfig.consumerKey,
            BuildConfig.consumerSecret,
            BuildConfig.requestTokenEndpoint,
            BuildConfig.accessTokenEndpoint,
            BuildConfig.authorizationEndpoint);

    super.onCreate(savedInstanceState);
~~~
Subsequent `OAuthConfig.getInstance()` calls can be used anywhere in your app
to retrieve the configuration, so that any field can be accessed via a getter:
~~~
public String getBaseUrl()
public String getCallbackUrl()
public String getConsumerKey()
public String getConsumerSecret()
public String getRequestTokenEndpoint()
public String getAccessTokenEndpoint()
public String getAuthorizationEndpoint()
~~~

4) In your AndroidManifest.xml

      <activity android:name="com.anubis.oauthkit.OAuthLoginActivity" />
      (use your own activity package/name)

5) In your AndroidManifest.xml

 **Place this intent filter in your launcher activity, which should be the login activity from 2)

        <intent-filter>
                <action android:name="android.intent.action.VIEW" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.BROWSABLE" />

                <data
                    android:host="cprest"
                    android:scheme="oauth" />
       </intent-filter>
**Note: if you change the name of the callbackUrl in the OAuthConfig, it must match the host and scheme in the
data element of this filter.

6) In your LoginActivity,

      OAuthBaseClient client;

       client = OAuthBaseClient.getInstance(<pass_in_the_context>, this);
       client.connect();

   Then override the success and failure callbacks:

        @Override
        public void onLoginSuccess(OkHttpOAuthConsumer consumer, String baseUrl) {

        }
        @Override
        public void onLoginFailure(Exception e) {}

        //note here that you can pass the consumer from success callback into an okhttpclient, which
        // can be set into an RxJava adapted Retrofit Builder
        e.g. (elsewhere in code)
         OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();


        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(converter)
                .addCallAdapterFactory(rxAdapter)
                .build();
        }



 Now you have a signing retrofit service!

 7) All response params and tokens are set in SharedPreferences.

 Access them via:

          SharedPreferences authPrefs = getApplicationContext().getSharedPreferences("OAuthKit_Prefs", 0);

 ENJOY!
