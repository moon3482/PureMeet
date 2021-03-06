package com.example.PureMeeting;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.loader.content.CursorLoader;

import com.android.volley.Request;
import com.android.volley.RequestQueue;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.PureMeeting.Camera.Camera1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.*;

public class Signup_information_input extends AppCompatActivity {
    ImageView iv_image;
    Calendar calendar;
    int Year, Month, Day;
    EditText birthday, email, name;
    String password;
    String password_Check;
    boolean pw_Check = false;
    String sign_Gender;
    String sign_Area;
    String sign_Jabs;
    Button id_Check;
    String imgPath;
    String EmailCode;
    String sign_Name, sign_email, sign_Birthday;
    TextView timer;
    LinearLayout Email_code_Linear;
    Button submit;
    EditText input_email_code;
    EditText input_Pw;
    EditText input_Pw_Check;
    String intent_email, intent_img, intent_gender, intent_kakaoid, imgurl;
    Bitmap bm;
    String img;
    int CAMERA_RESULT_CODE = 335;
    private AlertDialog dialog;
    boolean validate, spinner_Gen, spinner_Jab, spinner_Area;
    int sec;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            timer.setText(sec + "???");
            sec--;
            handler.postDelayed(this, 1000);
            if (sec < 0) {

                handler.removeCallbacks(this);
//                timer.setVisibility(View.INVISIBLE);


            }
        }
    };


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_infomation_input);
        //?????? ??????
        Toolbar toolbar = (Toolbar) findViewById(R.id.signup_information_ToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("??????????????????");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        //?????? ??????
        timer = findViewById(R.id.timer);
        birthday = findViewById(R.id.Sign_input_Birthday);
        iv_image = findViewById(R.id.imageView);

        input_Pw = (EditText) findViewById(R.id.sign_input_Pw);
        password = input_Pw.getText().toString();
        input_Pw_Check = (EditText) findViewById(R.id.sign_input_PwCheck);
        password_Check = input_Pw_Check.getText().toString();
        id_Check = findViewById(R.id.id_Check_Button);
        email = findViewById(R.id.sign_input_Email);
        name = findViewById(R.id.sing_input_name);
        Email_code_Linear = findViewById(R.id.email_code_insert_Linear);
        submit = findViewById(R.id.sign_Submit);
        Button button_email_code = (Button) findViewById(R.id.button_Email_Code);
        input_email_code = (EditText) findViewById(R.id.sign_input_code);
        Email_code_Linear.setVisibility(GONE);
        Intent intent = getIntent();

        if (intent.hasExtra("kakaoid")) {
            intent_kakaoid = intent.getExtras().getString("kakaoid");
        }
        if (intent.hasExtra("email")) {
            intent_email = intent.getExtras().getString("email");
            email.setText(intent_email);
            email.setEnabled(false);
            validate = true;
            id_Check.setBackgroundColor(Color.parseColor("#19FC00"));
            id_Check.setText("????????????");


        }

        /**
         *
         *  ?????????????????? ??????????????????  ???????????? ?????????  ??????????????? ???????????? ?????????
         *  2021-03-20 ??????  ????????? ?????????
         */
//        if (intent.hasExtra("img")) {
//            intent_img = intent.getExtras().getString("img");
//
//
//            Thread t = new Thread(new Runnable() {
//                @Override
//                public void run() {    // ?????? ?????? ????????? ????????????
//                    // TODO Auto-generated method stub
//                    try {
//                        // ??? ???????????? ?????? -_-;
//
//                        URL url = new URL(intent_img);
//                        InputStream is = url.openStream();
//                        bm = BitmapFactory.decodeStream(is);
//                        handler.post(new Runnable() {
//
//                            @Override
//                            public void run() {  // ????????? ????????? ??????
//
//                                iv_image.setImageBitmap(bm);
//                            }
//                        });
//                        iv_image.setImageBitmap(bm); //????????? ????????? ????????????
//
//                    } catch (Exception e) {
//
//                    }
//
//                }
//            });
//
//            t.start();
//            Glide.with(Signup_information_input.this)
//                    .load(imgPath)
//
//                    .into(iv_image);
//    }


        /** ????????? **/
        //?????? ?????????
        Spinner gender = (Spinner) findViewById(R.id.sign_gender_spinner);
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_dropdown_item);
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(gender_adapter);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                sign_Gender = parent.getItemAtPosition(position).toString();
                if (sign_Gender.equals("??????")) {
                    spinner_Gen = false;
                } else {
                    spinner_Gen = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });

//        //?????? ?????????
//        final Spinner area = (Spinner) findViewById(R.id.sign_area_spinner);
//        ArrayAdapter<CharSequence> area_adapter = ArrayAdapter.createFromResource(this, R.array.area, android.R.layout.simple_spinner_dropdown_item);
//        area_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        area.setAdapter(area_adapter);
//        area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sign_Area = parent.getItemAtPosition(position).toString();
//                if (sign_Area.equals("??????")) {
//                    spinner_Area = false;
//                } else {
//                    spinner_Area = true;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//
//
//        });
//        //?????? ?????????
//        final Spinner jabs = (Spinner) findViewById(R.id.sign_jabs_spinner);
//        ArrayAdapter<CharSequence> jabs_adapter = ArrayAdapter.createFromResource(this, R.array.jabs, android.R.layout.simple_spinner_dropdown_item);
//        jabs_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        jabs.setAdapter(jabs_adapter);
//        jabs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                sign_Jabs = parent.getItemAtPosition(position).toString();
//                if (sign_Jabs.equals("??????")) {
//                    spinner_Jab = false;
//                } else {
//                    spinner_Jab = true;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        /**?????? ?????? ?????????**/
        birthday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????? ??????
                calendar = Calendar.getInstance(Locale.KOREA);
                Year = calendar.get(Calendar.YEAR);
                Month = calendar.get(Calendar.MONTH);
                Day = calendar.get(Calendar.DAY_OF_MONTH);

                //??????????????? ??????
                DatePickerDialog datePickerDialog = new DatePickerDialog(Signup_information_input.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {
                        String pickerY = String.valueOf(year);
                        String pickerM = String.valueOf(month + 1);
                        String pickerD = String.valueOf(day);
                        Log.i("???", pickerM);

                        birthday.setText(pickerY + "-" + pickerM + "-" + pickerD);

                    }
                }, Year, Month, Day);

                //Call show() to simply show the dialog
                datePickerDialog.show();
            }
        });
        /**********************************/
        /************????????????***************/
        /**********************************/
        iv_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int permissionResult = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

                    if (permissionResult == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 11);


                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                            Log.d(TAG, "?????? ?????? ??????");
                        } else {
//                            Log.d(TAG, "?????? ?????? ??????");
                            ActivityCompat.requestPermissions(Signup_information_input.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                        }
                    } else {

                    }
                }
                Intent intent1 = new Intent(Signup_information_input.this, Camera1.class);
                startActivityForResult(intent1, CAMERA_RESULT_CODE);

            }
        });

        //???????????? ????????? ??????
        input_Pw_Check.addTextChangedListener(new TextWatcher() {
            TextView match_Pw = (TextView) findViewById(R.id.match_Pw);
            TextView different_Pw = (TextView) findViewById(R.id.different_Pw);

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (input_Pw.getText().toString().equals(input_Pw_Check.getText().toString())) {
                    different_Pw.setVisibility(GONE);
                    match_Pw.setVisibility(VISIBLE);
                    pw_Check = true;
                } else if (!input_Pw.getText().toString().equals(input_Pw_Check.getText().toString())) {
                    match_Pw.setVisibility(GONE);
                    different_Pw.setVisibility(VISIBLE);
                    pw_Check = false;
                }
                if (input_Pw_Check.getText().toString().length() == 0) {
                    different_Pw.setVisibility(GONE);
                    match_Pw.setVisibility(INVISIBLE);
                    pw_Check = false;

                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //?????? ?????? ??????
        id_Check.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = email.getText().toString();
                if (validate) {
                    return;
                }
                if (userID.equals("")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                    dialog = builder.setMessage("???????????? ??? ?????? ??? ????????????")
                            .setPositiveButton("??????", null)
                            .create();
                    dialog.show();
                    return;
                }
                if (!isValidEmail(userID)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                    dialog = builder.setMessage("????????? ????????? ????????????.")
                            .setPositiveButton("??????", null)
                            .create();
                    dialog.show();
                    return;
                }
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            Log.e("?????????", String.valueOf(success));

                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                                String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                                        "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
                                String newCode = new String();

                                for (int x = 0; x < 8; x++) {
                                    int random = (int) (Math.random() * str.length);
                                    newCode += str[random];
                                }
                                EmailCode = newCode;
                                sendMail();
                                handler.removeCallbacks(runnable);
                                Email_code_Linear.setVisibility(VISIBLE);
                                sec = 60;

                                dialog = builder.setMessage("????????? ??????????????? ??????????????????")
                                        .setTitle("????????? ??????")
                                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                handler.postDelayed(runnable, 3500);
                                            }
                                        })
                                        .create();
                                dialog.show();


                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                                dialog = builder.setMessage("?????? ?????? ??? ??????????????????.")
                                        .setNegativeButton("??????", null)
                                        .create();
                                dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest = new ValidateRequest(userID, responseListener);
                RequestQueue queue = Volley.newRequestQueue(Signup_information_input.this);
                queue.add(validateRequest);


            }


            /** ???????????? ????????? 2020-11-23 '???????????? ????????????'
             AppHelper.requestQueue = Volley.newRequestQueue(Signup_information_input.this);
             AppHelper.requestQueue.add(request);
             println("????????????");**/

            /** ????????? ??????????????? ?????? ????????? */
            public boolean isValidEmail(String email) {
                boolean err = false;
                String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(email);
                if (m.matches()) {
                    err = true;
                }
                return err;
            }


        });
        /** ????????? ?????? ??? ???????????? ?????? ?????? */
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validate = false;
                id_Check.setText("????????? ??????");
                id_Check.setBackgroundResource(android.R.drawable.btn_default);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        /** ????????? ???????????? ????????? ????????????*/
        button_email_code.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                String string_email_code = input_email_code.getText().toString();

                if (EmailCode.equals(string_email_code)) {
                    handler.removeCallbacks(runnable);
                    validate = true;
                    id_Check.setBackgroundColor(Color.parseColor("#19FC00"));
                    id_Check.setText("????????????");

                    Email_code_Linear.setVisibility(GONE);
                    input_email_code.setText("");
                } else if (sec <= 0) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                    builder.setTitle("????????? ??????");
                    builder.setMessage("??????????????? ?????? ???????????????.");
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            validate = false;
                        }
                    });

                    builder.show();
                } else if (!EmailCode.equals(string_email_code)) {
                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(v.getContext());
                    builder.setTitle("????????? ??????");
                    builder.setMessage("??????????????? ???????????? ????????????.");
                    builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            validate = false;
                        }
                    });

                    builder.show();
                }
            }
        });
        /**?????? ????????????*/
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
                String ID = email.getText().toString();
                String Password = input_Pw_Check.getText().toString();
                String birth = birthday.getText().toString();
                String input_name = name.getText().toString();
                boolean name_check = Pattern.matches("^[???-???]*$", input_name);
                Log.i(input_name, String.valueOf(name_check));
                if (validate) {
                    if (!pw_Check) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                        alertDialog.setMessage("??????????????? ??????????????????");
                        alertDialog.setPositiveButton("??????", null);
                        alertDialog.show();

                    } else if (input_name.length() < 1 || name_check == false) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                        alertDialog.setMessage("????????? ??????????????????");
                        alertDialog.setPositiveButton("??????", null);
                        alertDialog.show();
                    } else if (birth.length() == 0) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                        alertDialog.setMessage("????????? ??????????????????");
                        alertDialog.setPositiveButton("??????", null);
                        alertDialog.show();
                    } else if (sign_Gender.equals("??????")) {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
                        alertDialog.setMessage("????????? ??????????????????");
                        alertDialog.setPositiveButton("??????", null);
                        alertDialog.show();
                    } else {//????????????????????? ?????? ???????????? ?????? php ?????? ??????
                        String serverUrl = new ServerIP().http+"Android/insertDB.php";

                        //Volley plus Library??? ????????????
                        //?????? ???????????????..
                        //Volley+??? AndroidStudio?????? ????????? ?????? [google ?????? ??????]

                        //?????? ?????? ?????? ?????? ??????[????????? String?????? ??????]
                        SimpleMultiPartRequest smpr = new SimpleMultiPartRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                JSONObject jsonResponse = null;
                                try {
                                    jsonResponse = new JSONObject(response);
                                    boolean success = jsonResponse.getBoolean("success");
                                    if (success) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                                        dialog = builder.setMessage("??????????????? ?????????????????????.").setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(Signup_information_input.this, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }).create();
                                        dialog.show();

                                        validate = true; //?????? ??????

                                    } else {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                                        dialog = builder.setMessage("????????????").setNegativeButton("??????", null).create();
                                        dialog.show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Signup_information_input.this, "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //?????? ????????? ?????? ???????????? ??????
                        smpr.setShouldCache(false);
                        smpr.addStringParam("email", ID);
                        Log.e("?????? ?????????:", "" + ID);
                        smpr.addStringParam("password", Password);
                        smpr.addStringParam("birthday", birth);
                        smpr.addStringParam("name", input_name);
                        smpr.addStringParam("gender", sign_Gender);
                        if (intent_kakaoid != null) {
                            Log.e("???????????? ?????????", "" + intent_kakaoid);
                            smpr.addStringParam("kakaoid", intent_kakaoid);
                        }
                        //????????? ?????? ??????

                        smpr.addFile("img", img);

                        //??????????????? ????????? ?????? ????????? ?????? ?????? ??????

                        RequestQueue requestQueue = Volley.newRequestQueue(Signup_information_input.this);
                        requestQueue.add(smpr);
                    }
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Signup_information_input.this);
                    dialog = builder.setMessage("????????? ????????? ???????????????").setNegativeButton("??????", null).create();
                    dialog.show();
                }
            }


        });
    }


    private void sendMail() {


        //Send Mail
        JavaMailAPI javaMailAPI = new JavaMailAPI(this, email.getText().toString(), "[?????????] ????????? ????????????", "???????????? ????????? ???????????? ?????? ????????? ???????????????.\n" + "???????????? : " + EmailCode);

        javaMailAPI.execute();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "RESULT_OK", Toast.LENGTH_SHORT).show();
                    Uri uri = data.getData();
                    if (uri != null) {


                        Glide.with(Signup_information_input.this)
                                .load(uri)

                                .into(iv_image);

                        //?????????????????? ???????????? DB????????? ?????????, ????????? ????????? [?????? ?????? ????????? ??????!!]
                        //????????? Uri??? Gallery?????? DB?????????. (content://-----/2854)
                        //???????????? ????????? ???????????? ????????????(?????? ??????: file:// -------/aaa.png ?????????)??? ?????????
                        //Uri -->????????????(String)??? ??????
                        imgPath = getRealPathFromUri(uri);   //????????? ?????? ????????? (??????????????? ???????????? ?????????)

                        //????????? ?????? uri ???????????????
                        new AlertDialog.Builder(this).setMessage(uri.toString() + "\n" + imgPath).create().show();
                    }
                } else {
                    Toast.makeText(this, "????????? ????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                }
                break;
            case 335:
                if (resultCode == RESULT_OK) {
                    String imglog = data.getExtras().getString("?????????");
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {    // ?????? ?????? ????????? ????????????
                            // TODO Auto-generated method stub
                            try {
                                // ??? ???????????? ?????? -_-;

                                img = "storage/emulated/0/camtest/" + imglog;

                                bm = BitmapFactory.decodeFile(img);
                                handler.post(new Runnable() {

                                    @Override
                                    public void run() {  // ????????? ????????? ??????

                                        iv_image.setImageBitmap(bm);
                                    }
                                });
                                iv_image.setImageBitmap(bm); //????????? ????????? ????????????

                            } catch (Exception e) {

                            }

                        }
                    });

                    t.start();

                }
                break;
            /** ?????? ????????? ???????????? ????????? ?????? ???????????? 2020-11-25 ??????????????? ???????????? ????????????**/
//                if(resultCode==RESULT_OK){
//
//                    //????????? ????????? ??????(Uri)?????? ????????????
//                    Uri uri= data.getData();
//                    if(uri!=null){
//                        Glide.with(Signup_information_input.this)
//                        .load(uri)
//                        .circleCrop()
//                        .into(iv_image);
//                    }
//
//                }else
//                {
//                    Toast.makeText(this, "????????? ????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
//                }
//                break;
        }
    }

    String getRealPathFromUri(Uri uri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, uri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }


    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 11:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "?????? ????????? ??????/?????? ?????? ??????", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "?????? ????????? ??????/?????? ??????", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
//            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }

    }
}