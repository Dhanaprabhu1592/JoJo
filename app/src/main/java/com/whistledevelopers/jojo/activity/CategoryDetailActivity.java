package com.whistledevelopers.jojo.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.whistledevelopers.jojo.Api;
import com.whistledevelopers.jojo.MyLocationUsingLocationAPI;
import com.whistledevelopers.jojo.R;
import com.whistledevelopers.jojo.model.Categories;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryDetailActivity extends AppCompatActivity {
    TextView text_name, text_email_id, text_mobile_num, text_full_address;
    TextView text_name_user, text_user_address, text_user_city, text_user_pincode;
    private ProgressBar spinner;
    EditText editText_date, editText_time;
    Calendar calendar;
    String status;
    Button btn_book;
    String expertId, user_phone;
    public static final String SHARED_PREF_NAME = "Joju";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    RelativeLayout relative_vendor;
    ImageView img_update_location;
    Dialog dialog;
    EditText editText_update_address, editText_update_city, editText_update_pincode;
    String address1, city, pincode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        text_name = (TextView) findViewById(R.id.text_name);
        // text_organisation = (TextView) findViewById(R.id.text_organisation);
        text_email_id = (TextView) findViewById(R.id.text_email_id);
        text_mobile_num = (TextView) findViewById(R.id.text_mobile_num);
        text_full_address = (TextView) findViewById(R.id.text_full_address);

        text_name_user = (TextView) findViewById(R.id.text_name_user);
        text_user_address = (TextView) findViewById(R.id.text_user_address);
        text_user_city = (TextView) findViewById(R.id.text_user_city);
        text_user_pincode = (TextView) findViewById(R.id.text_user_pincode);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        relative_vendor = (RelativeLayout) findViewById(R.id.relativie_vendor);
        img_update_location = (ImageView) findViewById(R.id.img_update_location);

        sp = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        user_phone = sp.getString("phone", "");

        // text_address = (TextView) findViewById(R.id.text_address);
        editText_date = (EditText) findViewById(R.id.editText_date);
        editText_time = (EditText) findViewById(R.id.editText_time);
        calendar = Calendar.getInstance();
        btn_book = (Button) findViewById(R.id.btn_book);
        Intent intent = getIntent();
        expertId = intent.getStringExtra("experts");


        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                bookExpert();


               /* if(!TextUtils.isEmpty(editText_date.getText().toString())&&!TextUtils.isEmpty(editText_time.getText().toString())){

                }else{
                    Toast.makeText(CategoryDetailActivity.this, "Date Should Not Be Empty", Toast.LENGTH_SHORT).show();
                }
*/
            }
        });


        img_update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLLocationUpdate();
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> call = api.getOneExpert(expertId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                spinner.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {
                    try {
                        relative_vendor.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        text_name.setText(jsonObject1.getString("name"));
                        text_mobile_num.setText(jsonObject1.getString("mobile"));
                        text_email_id.setText(jsonObject1.getString("email"));
                        // text_address.setText(jsonObject1.getString("address"));
                        text_full_address.setText(jsonObject1.getString("city") + " " + jsonObject1.getString("pincode"));
                        userProfile();

                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("sdgdsgg  IO" + e.getMessage());

                        Toast.makeText(CategoryDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(CategoryDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


        editText_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(editText_date,"start");;

            }
        });

        editText_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pickDate(editText_time,"end");
                /*int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(CategoryDetailActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if (selectedHour > 11) {
                            status = "PM";

                        } else {
                            status = "AM";

                        }
                        int hour_of_12_hour_format;

                        if (selectedHour > 11) {

                            // If the hour is greater than or equal to 12
                            // Then we subtract 12 from the hour to make it 12 hour format time
                            hour_of_12_hour_format = selectedHour - 12;
                        } else {
                            hour_of_12_hour_format = selectedHour;
                        }


                        editText_time.setText(hour_of_12_hour_format + ":" + selectedMinute + " " + status);
                    }
                }, hour, minute, false);//Yes 12 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
*/
            }

        });

    }

    private void bookExpert() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        String date=editText_date.getText().toString();
        //Call<ResponseBody> postCall = api.bookExpert(user_phone,expertId,user_phone,"12/12/19");
        Call<ResponseBody> postCall = api.bookExpert(user_phone,expertId,user_phone,date);
        Toast.makeText(this, expertId+" "+editText_date.getText().toString(),Toast.LENGTH_SHORT).show();

        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                        String status = String.valueOf(jsonObject.getInt("status"));
                        String message = jsonObject.getString("message");
                        Toast.makeText(CategoryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                        if(status.equals("200")){

                            showDialog(message);
                        }else{
                            spinner.setVisibility(View.GONE);
                            Toast.makeText(CategoryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(CategoryDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void userProfile() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> postCall = api.getUserProfile(user_phone);

        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                spinner.setVisibility(View.GONE);

                if (response.isSuccessful()) {
                    // textName.setText("Dhan");
                    try {
                        spinner.setVisibility(View.GONE);
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        String user_name = jsonObject1.getString("name");
                        address1 = jsonObject1.getString("address1");
                        String address2 = jsonObject1.getString("address2");
                        city = jsonObject1.getString("city");
                        pincode = jsonObject1.getString("pincode");
                        text_name_user.setText(user_name);
                        text_user_address.setText(address1);
                        //text_user_city.setText(city);
                        //text_user_pincode.setText(pincode);


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
                spinner.setVisibility(View.GONE);
                Toast.makeText(CategoryDetailActivity.this, "Something Went Wrong..Please try again later", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void userLLocationUpdate() {
        dialog = new Dialog(CategoryDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.updatelocationdialog);

        editText_update_address = (EditText) dialog.findViewById(R.id.editText_update_address);
        editText_update_city = (EditText) dialog.findViewById(R.id.editText_update_city);
        editText_update_pincode = (EditText) dialog.findViewById(R.id.editText_update_pincode);
        final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);
        Button update = (Button) dialog.findViewById(R.id.btn_update);
        Button cancel = (Button) dialog.findViewById(R.id.btn_cancel);

        if (address1.equals("")) {
            editText_update_address.setVisibility(View.GONE);
        } else {
            editText_update_address.setText(address1);
        }
        editText_update_city.setText(city);
        editText_update_pincode.setText(pincode);

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
                sendLocation();
                //dialog.dismiss();
            }
        });

        dialog.show();


    }

    public void sendLocation() {
        spinner.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        final Api api = retrofit.create(Api.class);
        Call<ResponseBody> postCall = api.sendLocation(user_phone, editText_update_address.getText().toString(),
                "", editText_update_city.getText().toString(), editText_update_pincode.getText().toString());
        postCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                spinner.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String status = jsonObject.getString("status");
                    String message = jsonObject.getString("message");
                    Toast.makeText(CategoryDetailActivity.this, message, Toast.LENGTH_SHORT).show();
                    if (status.equals("200")) {
                        dialog.dismiss();
                     userProfile();
                    }
                    else{
                        Toast.makeText(CategoryDetailActivity.this, status, Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                spinner.setVisibility(View.GONE);
                Toast.makeText(CategoryDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    private  void pickDate(final EditText editText,String info){
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR, i);
                calendar.set(Calendar.MONTH, i1);
                calendar.set(Calendar.DAY_OF_MONTH, i2);
                updateLabel(editText);
            }


        };

        DatePickerDialog datePickerDialog = new DatePickerDialog(CategoryDetailActivity.this, onDateSetListener, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        //calendar.add(Calendar.DAY_OF_MONTH, -7);
        // Date result = calendar.getTime();
        if("start".equals(info)){
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 604800000 - 86400000);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }else{
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        }

        datePickerDialog.show();

    }

    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText.setText(sdf.format(calendar.getTime()));
    }
    private void showDialog(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(CategoryDetailActivity.this);
        builder.setTitle(getString(R.string.app_name));

        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(CategoryDetailActivity.this, HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);


                    }
                });
        /*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });*/


        AlertDialog alert = builder.create();
        alert.show();
    }


}
