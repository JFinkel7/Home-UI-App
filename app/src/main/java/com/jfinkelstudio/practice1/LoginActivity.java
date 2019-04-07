package com.jfinkelstudio.practice1;

import android.graphics.drawable.AnimationDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setUpLoginToolbar();
        loginAnimateBackground();

    }


    public void setUpLoginToolbar(){
        Toolbar loginToolbar = findViewById(R.id.loginToolbar);
        setSupportActionBar(loginToolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_login_items, menu);
        return(true);
    }

    public void loginAnimateBackground(){
        ConstraintLayout loginConstraintLayout = findViewById(R.id.loginConstrainLayout);
        AnimationDrawable animateLoginLayout = (AnimationDrawable) loginConstraintLayout.getBackground();
        animateLoginLayout.setEnterFadeDuration(4000);
        animateLoginLayout.setExitFadeDuration(4000);
        animateLoginLayout.start();
    }
}
