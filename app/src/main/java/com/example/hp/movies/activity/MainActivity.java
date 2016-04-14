package com.example.hp.movies.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.hp.movies.R;
import com.example.hp.movies.fragment.MovieGridFragment;

/**
 * Created by hp on 20-02-2016.
 */
public class MainActivity extends AppCompatActivity {
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Movies");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(false);




            MovieGridFragment movieGridFragment =  new MovieGridFragment();
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.fragment, movieGridFragment);
        //    ft.addToBackStack(null);
            ft.commit();





    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
       // return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       int id= item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    public void switchContent(int id,Fragment fragment) {
       android.support.v4.app. FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(id,fragment,fragment.toString());
      //  ft.addToBackStack(null);
        ft.commit();
    }
}
