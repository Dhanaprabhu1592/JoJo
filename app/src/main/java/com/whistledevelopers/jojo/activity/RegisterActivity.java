package com.whistledevelopers.jojo.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.MyLocationUsingLocationAPI;
import com.whistledevelopers.jojo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    Button btn_register;
    public static int APP_REQUEST_CODE = 99;
    RelativeLayout relativeLayout;
    EditText editText_name, editText_primarynumber, editText_secondarynumber, editText_email, editText_password, editText_confirmPassword;
    String name, primary_number, secondary_number, email, password, confirm_password;
    ProgressBar progressBar;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = (Button) findViewById(R.id.btn_register);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_register);
        editText_name = (EditText) findViewById(R.id.editText_name);
        editText_primarynumber = (EditText) findViewById(R.id.editText_primarynumber);
        editText_secondarynumber = (EditText) findViewById(R.id.editText_secondarynumber);
        editText_email = (EditText) findViewById(R.id.editText_email);
        editText_password = (EditText) findViewById(R.id.editText_password);
        editText_confirmPassword = (EditText) findViewById(R.id.editText_confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText_name.getText().toString();
                primary_number = editText_primarynumber.getText().toString();
                secondary_number = editText_secondarynumber.getText().toString();
                email = editText_email.getText().toString();
                password = editText_password.getText().toString();
                confirm_password = editText_confirmPassword.getText().toString();


                if (TextUtils.isEmpty(name)) {
                    showSnackBar("Username is Required!");
                } else if (TextUtils.isEmpty(primary_number)) {
                    showSnackBar("Primary Mobile Number is Required!");
                } else if (TextUtils.isEmpty(secondary_number)) {
                    showSnackBar("Secondary number is Required!");
                } else if (TextUtils.isEmpty(email)) {
                    showSnackBar("Email is Required!");
                } else if (TextUtils.isEmpty(password)) {
                    showSnackBar("Password is Required!");
                } else if (TextUtils.isEmpty(confirm_password)) {
                    showSnackBar("Confirm Password is Required!");
                } else if (!password.equals(confirm_password)) {
                    showSnackBar("Passwords are mismatch!");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    userRegister();

                }

                //  phoneLogin(v);

            }
        });


    }

    public void userRegister() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);

        Call<ResponseBody> postCall = api.userLogin(name,
                email,
                primary_number,
                secondary_number,
                password);

        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = String.valueOf(jsonObject.getInt("status"));
                    String message = jsonObject.getString("message");
                    if (status.equals("200")) {
                        phoneLogin();
                    } else {
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Something Went Wrong..Please try again later", Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        });


    }

    public void otpVerify() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        String status = "true";
        Call<ResponseBody> postCall = api.otpverify(primary_number, status);

        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = String.valueOf(jsonObject.getInt("status"));
                    String message = jsonObject.getString("message");
                    if (status.equals("200")) {
                        editor.putString("isLoggedin", "true");
                        editor.putString("phone", primary_number);
                        editor.apply();

                        Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity.this, MyLocationUsingLocationAPI.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        Snackbar snackbar = Snackbar
                                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
                        snackbar.show();


                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(relativeLayout, "Something Went Wrong..Please try again later", Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        });

    }

    public void phoneLogin() {
        final Intent intent = new Intent(getApplicationContext(), AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage = null;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Athentication Cancelled By User";
                Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();

            } else {
                if (loginResult.getAccessToken() != null) {
                    // toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                    progressBar.setVisibility(View.VISIBLE);
                    otpVerify();
                } else {
                    // toastMessage = String.format("Success:%s...",loginResult.getAuthorizationCode().substring(0, 10));
                    progressBar.setVisibility(View.VISIBLE);
                    otpVerify();
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                //Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            }

            // Surface the result to your user in an appropriate way.
            /*Toast.makeText(
                    this,
                    toastMessage,
                    Toast.LENGTH_LONG)
                    .show();
            *///startActivity(new Intent(RegisterActivity.this, HomeActivity.class));

        }
    }

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();


    }
}
