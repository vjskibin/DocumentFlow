package su.mehsoft.documentflow;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import su.mehsoft.documentflow.magicwords.Tags;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private EditText etEmail;
    private EditText etPass;
    private EditText etPassAgain;
    private Button btnSignUp;

    private TextView tvLogIn;

    //private final String TAG = "Authentication";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        initView();
    }

    private void initView()
    {
        etEmail = findViewById(R.id.etEmail);
        etPass = findViewById(R.id.etPass);
        etPassAgain = findViewById(R.id.etPassConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvLogIn = findViewById(R.id.tvLogIn);

        btnSignUp.setOnClickListener(this);
        tvLogIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnSignUp:

                String email = etEmail.getText() .toString();
                String password = etPass.getText().toString();
                String passwordConfirm = etPassAgain.getText().toString();

                if (!password.equals(passwordConfirm))
                {
                    Toast.makeText(this, "Passwords does not match", Toast.LENGTH_LONG).show();
                    Log.d(Tags.AUTH_TAG,password);
                    Log.d(Tags.AUTH_TAG,passwordConfirm);
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(Tags.AUTH_TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseAuth.getInstance().signOut();
                                    //updateUI(user);
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    RegisterActivity.this.finish();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(Tags.AUTH_TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });


                break;

            case R.id.tvLogIn:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                finish();
        }
    }
}
