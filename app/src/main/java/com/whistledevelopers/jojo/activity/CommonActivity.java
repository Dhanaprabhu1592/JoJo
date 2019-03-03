package com.whistledevelopers.jojo.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.adapter.CategoryListAdapter;
import com.whistledevelopers.jojo.model.Experts;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommonActivity extends AppCompatActivity {
    ProgressBar progressBar;
    String user_phone,expertId,expertPhone;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);
        progressBar=(ProgressBar)findViewById(R.id.progressBarCommon);
        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user_phone=sp.getString("phone","");
        Intent intent=getIntent();
        expertId=intent.getStringExtra("expertId");
        expertPhone=intent.getStringExtra("expert_phone");
        showDialog("Are you sure to make a call ?");
    }

    public void callExpert(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        final Call<ResponseBody> callLog = api.callLog(user_phone,expertId);
        callLog.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressBar.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject=new JSONObject(response.body().string());
                    String status=jsonObject.getString("status");
                    if(status.equals("200")){
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+expertPhone));
                        startActivity(intent);
                        finish();
                    }                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(CommonActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void showDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CommonActivity.this);
        builder.setTitle(getString(R.string.app_name));

        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        callExpert();

                    }
                });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent=new Intent(CommonActivity.this,HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });


        AlertDialog alert = builder.create();
        alert.show();
    }

}



