package com.deepjoshi.chocopyk;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.deepjoshi.chocopyk.AsyncTasks.AsyncResponse;
import com.deepjoshi.chocopyk.AsyncTasks.WebserviceCall;
import com.deepjoshi.chocopyk.Helper.Utils;
import com.deepjoshi.chocopyk.Model.RegisterModel;
import com.google.gson.Gson;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity {

    private Spinner spinner_state;
    private Spinner spinner_city;
    private Spinner spinner_area;
    private Spinner spinner_type;
    private Spinner spinner_sec;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        final EditText uName=(EditText)findViewById(R.id.signup_ev_usnm);
        final EditText uemailId=(EditText)findViewById(R.id.signup_ev_eid);
        final EditText addRess=(EditText)findViewById(R.id.signup_ev_add);
        final EditText passWrd=(EditText)findViewById(R.id.signup_ev_pw);
        final EditText cnum=(EditText)findViewById(R.id.signup_ev_cn);

        Button bt3=(Button) findViewById (R.id.signup_createanaccount);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String uname = uName.getText().toString();
                String emailid = uemailId.getText().toString();
                String stradd = addRess.getText().toString();
                String passw = passWrd.getText().toString();
                String cono = cnum.getText().toString();

                if (uname.isEmpty()) {
                    Toast.makeText(Sign_up.this, "User Name cannot be empty!", Toast.LENGTH_SHORT).show();

                } else if (emailid.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Email Id cannot be empty", Toast.LENGTH_SHORT).show();

                } else if (stradd.isEmpty()) {
                    Toast.makeText(Sign_up.this, "Enter Address", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(emailid)) {
                    Toast.makeText(Sign_up.this, "Enter valid email", Toast.LENGTH_SHORT).show();
                } else if (passw.isEmpty()) {
                    Toast.makeText(Sign_up.this, "please enter password", Toast.LENGTH_SHORT).show();
                } else if (cono.isEmpty()) {
                    Toast.makeText(Sign_up.this, "please enter password", Toast.LENGTH_SHORT).show();
                }
                else {


                    String[]keys=new String[]{"mode","userName","emailId","address","password","contactNumber"};
                    String[]values=new String[]{"Register",uname,emailid,stradd,passw,cono};
                    String jsonRequest= Utils.createJsonRequest(keys,values);

                    String URL = "http://development.ifuturz.com/core/FLAT_TEST/ecart_new/admin/webservice.php";
                    new WebserviceCall(Sign_up.this, URL, jsonRequest, "Signing Up...!!", true, new AsyncResponse() {
                        @Override
                        public void onCallback(String response) {
                            Log.d("myapp",response);
                            RegisterModel model = new Gson().fromJson(response,RegisterModel.class);
                            Toast.makeText(Sign_up.this,model.getMessage() , Toast.LENGTH_SHORT).show();
                            if (model.getStatus()==1)
                            {
                                Intent intent = new Intent(Sign_up.this,MainActivity.class);
                                startActivity(intent);
                            }

                        }
                    }).execute();

                }

            }
        });

    }

    public static boolean isValidEmail(CharSequence target)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern =Pattern .compile(EMAIL_PATTERN );
        matcher =pattern .matcher(target ) ;
        return matcher.matches() ;
    }

}