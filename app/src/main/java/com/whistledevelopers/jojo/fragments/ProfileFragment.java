package com.whistledevelopers.jojo.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.activity.HomeActivity;
import com.whistledevelopers.jojo.activity.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {
    TextView textName, textViewName, textViewContact, contactNumberSecondary, textViewEmailId;
    ProgressBar progressBar;
    FrameLayout frameLayout;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String phone;
    String name, email, mobile, alt_mobile;
    ImageView img_btn_edit_profile;
    Dialog dialog;
    EditText update_editText_email,update_editText_alt_mobile;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);
        sp = getContext().getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        editor = sp.edit();
        phone = sp.getString("phone", "");

        textName = (TextView) view.findViewById(R.id.name);
        textViewName = (TextView) view.findViewById(R.id.textViewName);
        textViewContact = (TextView) view.findViewById(R.id.textViewContact);
        contactNumberSecondary = (TextView) view.findViewById(R.id.contactNumberSecondary);
        textViewEmailId = (TextView) view.findViewById(R.id.textViewEmailId);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        frameLayout = (FrameLayout) view.findViewById(R.id.frame_profile);
        img_btn_edit_profile = (ImageView) view.findViewById(R.id.img_edit_profile);
        userProfile();
        img_btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userProfileUpdate();
                //progressBar.setVisibility(View.VISIBLE);
            }
        });
        return view;
    }

    private void userProfileUpdate() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.updateprofiledialog);

        update_editText_email = (EditText) dialog.findViewById(R.id.editText_updateEmail);
        update_editText_alt_mobile = (EditText) dialog.findViewById(R.id.editText_alterNumber);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
        Button update = (Button) dialog.findViewById(R.id.btn_update);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        if (email.equals("")) {
            update_editText_email.setVisibility(View.GONE);
        } else {
            update_editText_email.setText(email);
        }
        update_editText_alt_mobile.setText(alt_mobile);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                updateUser();
                //dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void updateUser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> updateUser = api.updateUser(phone, name,
                update_editText_email.getText().toString(),
                update_editText_alt_mobile.getText().toString());

        System.out.println("update user "+phone+name+email+alt_mobile);

        updateUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message=jsonObject.getString("message");
                    if (status.equals("200")) {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        userProfile();

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
                Toast.makeText(getContext(), "Something Went Wrong! Please Try again Later", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void userProfile() {
        System.out.println("user profile final "+phone);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> postCall = api.getUserProfile(phone);

        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    // textName.setText("Dhan");
                    try {
                        progressBar.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        name = jsonObject1.getString("name");
                        email = jsonObject1.getString("email");
                        mobile = jsonObject1.getString("mobile");
                        alt_mobile = jsonObject1.getString("alt_phone");
                        textName.setText("Hello "+name);
                        textViewName.setText(name);
                        textViewEmailId.setText(email);
                        textViewContact.setText(mobile);
                        contactNumberSecondary.setText(alt_mobile);
                        img_btn_edit_profile.setVisibility(View.VISIBLE);


                    } catch (IOException e) {
                        System.out.println("Name user profile " + e.getMessage());

                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("Name user profile " + e.getMessage());

                    }


                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Snackbar snackbar = Snackbar
                        .make(frameLayout, "Something Went Wrong..Please try again later", Snackbar.LENGTH_LONG);
                snackbar.show();


            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem=menu.findItem(R.id.action_settings);
        menuItem.setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }



}
