package com.whistledevelopers.jojo.activity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    TextView txtsignUp;
    EditText editText_phone, editText_password;
    Button btn_login;
    RelativeLayout relativeLayout;
    ProgressBar progressBar;
    String phone, user_phone,otp,password_dialog,confirm_password_dialog;
    String password;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    TextView txt_forget_password;
    Dialog dialog;
    ProgressBar progressBarDiaog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_login);
        txtsignUp = (TextView) findViewById(R.id.txt_signUp);
        txt_forget_password = (TextView) findViewById(R.id.txt_forget_password);
        editText_phone = (EditText) findViewById(R.id.editText_phone);
        editText_password = (EditText) findViewById(R.id.editText_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = editText_phone.getText().toString();
                password = editText_password.getText().toString();


                if (TextUtils.isEmpty(phone)) {
                    showSnackBar("Mobile Number Is Required!");
                } else if (TextUtils.isEmpty(password)) {
                    showSnackBar("Password Is Required!");
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    userLogin();

                }


            }
        });
        txt_forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog();
            }
        });
        txtsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
    }

    private void userLogin() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> postCall = api.userLogin(phone, password);

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
                        editor.putString("phone", phone);
                        editor.apply();


                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    } else {
                        progressBar.setVisibility(View.GONE);
                        showSnackBar(message);
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

    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();


    }

    private void forgotPasswordDialog() {
        dialog = new Dialog(LoginActivity.this);
       // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forgotpwddialog);
        final EditText edit_phone_number = dialog.findViewById(R.id.editText_mobile);
        Button btn_submit=dialog.findViewById(R.id.btn_submit);
        Button btn_cancel=dialog.findViewById(R.id.btn_cancel);
        progressBarDiaog = (ProgressBar) dialog.findViewById(R.id.progressBar);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_phone = edit_phone_number.getText().toString();

                if (user_phone.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (user_phone.length() < 10) {
                    Toast.makeText(LoginActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    sendOtp();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });





        dialog.show();


    }

    public void sendOtp() {
        progressBarDiaog.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Map map=new HashMap<String,String>();
        map.put("user_phone",user_phone);

        Call<ResponseBody> postCall = api.sendOtp(user_phone);
        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBarDiaog.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    //Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (status.equals("200")) {
                        dialog.dismiss();
                        verifyOtpDialog();
                    } else {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarDiaog.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });

    }
    private void verifyOtpDialog() {
        dialog = new Dialog(LoginActivity.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.forgotpwddialog);
        final EditText edit_phone_number = dialog.findViewById(R.id.editText_mobile);
        final EditText edit_otp = dialog.findViewById(R.id.editText_otp);
        TextView text_title=dialog.findViewById(R.id.text_title);
        TextView text_display_message=dialog.findViewById(R.id.txt_display_message);
        Button btn_submit=dialog.findViewById(R.id.btn_submit);
        Button btn_cancel=dialog.findViewById(R.id.btn_cancel);
        progressBarDiaog = (ProgressBar) dialog.findViewById(R.id.progressBar);
        edit_otp.setVisibility(View.VISIBLE);
        text_title.setText("Otp");
        edit_phone_number.setEnabled(false);
        edit_phone_number.setText(user_phone);
        text_display_message.setText("Please Enter Otp");


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_phone = edit_phone_number.getText().toString();
                otp=edit_otp.getText().toString();

                if (otp.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                } else if (otp.length() < 6) {
                    Toast.makeText(LoginActivity.this, "Please enter 6 digit otp", Toast.LENGTH_SHORT).show();
                } else {
                    verifyOtp();
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });





        dialog.show();


    }
    public void verifyOtp() {
        progressBarDiaog.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Map map=new HashMap<String,String>();
        map.put("user_phone",user_phone);

        Call<ResponseBody> postCall = api.verifyOtp(user_phone,otp);
        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBarDiaog.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (status.equals("200")) {
                        dialog.dismiss();
                        changepasswordDialog();
                    } else {
                        Toast.makeText(LoginActivity.this, status, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarDiaog.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });

    }

    private void changepasswordDialog() {
        dialog = new Dialog(LoginActivity.this);
        // dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.changepdialog);
        final EditText editText_password_dialog = dialog.findViewById(R.id.editText_password_dialog);
        final EditText editText_confirm_password_dialog = dialog.findViewById(R.id.editText_confirm_password_dialog);
       // TextView text_title=dialog.findViewById(R.id.text_title);
        Button btn_submit=dialog.findViewById(R.id.btn_submit);
        //Button btn_cancel=dialog.findViewById(R.id.btn_cancel);
        progressBarDiaog = (ProgressBar) dialog.findViewById(R.id.progressBar);



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password_dialog = editText_password_dialog.getText().toString();
                confirm_password_dialog=editText_confirm_password_dialog.getText().toString();

                if (password_dialog.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                } else if (confirm_password_dialog.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                }else if(!password_dialog.equals(confirm_password_dialog)){
                    Toast.makeText(LoginActivity.this, "password and confirm password should be same", Toast.LENGTH_SHORT).show();
                }
                else {
                    changePassword();
                }
            }
        });






        dialog.show();


    }

    public void changePassword() {
        progressBarDiaog.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
/*
        Map map=new HashMap<String,String>();
        map.put("user_phone",user_phone);
*/

        Call<ResponseBody> postCall = api.changePass(user_phone,password_dialog);
        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBarDiaog.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                   // Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (status.equals("200")) {
                        dialog.dismiss();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, status, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBarDiaog.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });

    }


}
