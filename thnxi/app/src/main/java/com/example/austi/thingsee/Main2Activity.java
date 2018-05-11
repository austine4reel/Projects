package  com.example.austi.thingsee;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.austi.thingsee.MainActivity;
import com.example.austi.thingsee.R;
import com.example.austi.thingsee.User;


public class Main2Activity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button button;

    String nam = "kari.salo@metropolia.fi";
    String pas = "Tinkku22";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.pass_word);


        button = (Button) findViewById(R.id.Login_btn);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals(nam) && password.getText().toString().equals(pas)){
                    //to pass info from usr class
                    User user = new User(Main2Activity.this);
                    user.setNam(nam);
                    user.setPas(pas);

                    Intent intent = new Intent(Main2Activity.this, MainActivity.class);

                    intent.putExtra("username", nam);
                    intent.putExtra("password", pas);

                    startActivity(intent);
                    finish();// so that it wont take back to the login screen


                }
                //if (nam.equals("")||pas.equals("")){
                    //Toast.makeText(Main2Activity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                //}
                else {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Main2Activity.this);

                    dlgAlert.setMessage("wrong password or username");
                    dlgAlert.setTitle("Error Message...");
                    dlgAlert.setPositiveButton("OK", null);
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    dlgAlert.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                }
                            });




                }


            }
        });


    }






}



