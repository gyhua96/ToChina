package cn.gongyuhua.gyh.tochina;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mLoginTask = null;
    private UserSignUpTask mSignUpTask=null;

    // UI references.
    private EditText mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    private boolean isSignIn = true;


    private String[] mProvinceDatas={"北京市","天津市","河北省","山西省","内蒙古自治区","辽宁省","吉林省","黑龙江省","上海市","江苏省","浙江省","安徽省","福建省","江西省","山东省","河南省","湖北省","湖南省","广东省","广西壮族自治区","海南省","重庆市","四川省","贵州省","云南省","西藏自治区","陕西省","甘肃省","青海省","宁夏回族自治区","新疆维吾尔自治区","香港特别行政区","澳门特别行政区","台湾省"};
    private WheelView mProvince;
    private Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    private String mCurrentProviceName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (EditText) findViewById(R.id.email);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        TextView signText = (TextView) findViewById(R.id.sign_text);
        signText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSignIn) {
                    LinearLayout lin = (LinearLayout) findViewById(R.id.email_login_form);
                    LayoutInflater inflater = LayoutInflater.from(LoginActivity.this);
                    View signUp = inflater.inflate(R.layout.sign_up_form, null);
                    lin.addView(signUp);
                    isSignIn = false;
                    ((TextView) findViewById(R.id.sign_text)).setText(getString(R.string.action_sign_in));
                    ((Button) findViewById(R.id.email_sign_in_button)).setText(getString(R.string.action_sign_up));
                    setTitle(getString(R.string.action_sign_up));

                    mProvince = (WheelView) findViewById(R.id.id_province);

                    mProvince.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    mProvince.setViewAdapter(new ArrayWheelAdapter<String>(getWindow().getContext(), mProvinceDatas));
                    mProvince.addChangingListener(new OnWheelChangedListener() {
                        @Override
                        public void onChanged(WheelView wheel, int oldValue, int newValue) {
                            mCurrentProviceName=mProvinceDatas[newValue];
                            Log.d("wheel",mCurrentProviceName);
                        }
                    });
                }else {
                    LinearLayout lin = (LinearLayout) findViewById(R.id.email_login_form);
                    View signUp = findViewById(R.id.sign_up);
                    lin.removeView(signUp);
                    isSignIn = true;
                    ((TextView) findViewById(R.id.sign_text)).setText(getString(R.string.action_sign_up));
                    ((Button) findViewById(R.id.email_sign_in_button)).setText(getString(R.string.action_sign_in));
                    setTitle(getString(R.string.action_sign_in));
                }

            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */

    private void attemptLogin() {
        if (mLoginTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            if(isSignIn) {
                mLoginTask = new UserLoginTask(email, password);
                mLoginTask.execute((Void) null);
            }else {
                mSignUpTask = new UserSignUpTask(email, password,"teststr1","teststr2");
                mSignUpTask.execute((Void) null);
            }
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public class UserSignUpTask extends AsyncTask<Void,Void,Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mtest1;
        private final String mtest2;
        private String token;

        UserSignUpTask(String email, String password,String test1,String test2) {
            mEmail = email;
            mPassword = password;
            mtest1=test1;
            mtest2=test2;

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                //Thread.sleep(2000);
                String api = "http://gongyuhua.cn/tochina/signup.php";
                String data = "user=" + mEmail + "&pwd=" + mPassword+"&test1="+mtest1+"&test2="+mtest2;
                String res = HTTP.HttpPost(api, data);
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(res);
                if (json.get("status").toString().equals("error")) {
                    return false;
                }
                Log.d("login", res);
            } catch (Exception e) {
                return false;
            }
            /*
            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", mEmail);
            editor.putString("token", token);
            editor.apply();
            editor.commit();
*/
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSignUpTask = null;
            showProgress(false);

            if (success) {
                Toast toast = Toast.makeText(getWindow().getDecorView().findViewById(android.R.id.content).getContext(),
                        "Sign Up success!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mSignUpTask = null;
            showProgress(false);
        }
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private String token;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                //Thread.sleep(2000);
                String api = "http://gongyuhua.cn/tochina/login.php";
                String data = "user=" + mEmail + "&pwd=" + mPassword;
                String res = HTTP.HttpPost(api, data);
                JsonParser parser = new JsonParser();
                JsonObject json = (JsonObject) parser.parse(res);
                if (json.get("status").toString().equals("error")) {
                    return false;
                }
                token = json.get("token").toString();
                Log.d("login", res);
                Log.d("login", token);
            } catch (Exception e) {
                return false;
            }

            SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("user", mEmail);
            editor.putString("token", token);
            editor.apply();
            editor.commit();

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mLoginTask = null;
            showProgress(false);

            if (success) {
                Toast toast = Toast.makeText(getWindow().getDecorView().findViewById(android.R.id.content).getContext(),
                        "Login success!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mLoginTask = null;
            showProgress(false);
        }
    }
}

