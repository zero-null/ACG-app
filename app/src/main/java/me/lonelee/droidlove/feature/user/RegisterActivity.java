package me.lonelee.droidlove.feature.user;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import me.lonelee.droidlove.R;
import me.lonelee.droidlove.util.UserUtil;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Button btnRegister;
    private TextInputEditText registerEmail;
    private TextInputEditText registerPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("注册");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = registerEmail.getText().toString();
                String password = registerPassword.getText().toString();
                UserUtil.registerByEmail(email, password, RegisterActivity.this);
                finish();
            }
        });

    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        btnRegister = findViewById(R.id.btn_register);
        registerEmail = findViewById(R.id.register_email);
        registerPassword = findViewById(R.id.register_password);
    }
}
