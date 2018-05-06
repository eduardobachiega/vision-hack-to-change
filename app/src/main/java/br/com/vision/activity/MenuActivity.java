package br.com.vision.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.com.edsb.dutils.DTDialogs;
import br.com.vision.R;
import br.com.vision.fragment.AchievementsFragment;
import br.com.vision.fragment.ChatBotFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import br.com.vision.fragment.DashboardFragment;

public class MenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DashboardFragment.OnFragmentInteractionListener,
        ChatBotFragment.OnFragmentInteractionListener, AchievementsFragment.OnFragmentInteractionListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    TextView tvUserName;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragmentTransaction(new DashboardFragment(), true);
        SharedPreferences preferences = getSharedPreferences("USER", MODE_PRIVATE);
        String user = preferences.getString("CPF", "DEFAULT");
        reference.child("users").child(user).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tvUserName = navigationView.getHeaderView(0).findViewById(R.id.tvUserName);
                tvUserName.setText(dataSnapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        navigationView.getMenu().clear();
        navigationView.inflateMenu(R.menu.menu_drawer1);

        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick(R.id.fab)
    public void fabOnClick(View view) {

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment fragment = null;
        Class fragmentClass = null;
        if (id == R.id.nav_dashboard)
            fragmentClass = DashboardFragment.class;
        else if (id == R.id.nav_chatbot)
            fragmentClass = ChatBotFragment.class;
        else if (id == R.id.nav_achievements)
            fragmentClass = AchievementsFragment.class;
        else if (id == R.id.nav_exit) {
            DTDialogs.showOkCancelPopUp(MenuActivity.this, "Sair", "Deseja realmente fazer logout?", getString(R.string.ok), getString(R.string.cancel), true, 0, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
                    sharedPreferences.edit().remove("CPF").apply();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            }, null);
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentTransaction(fragment, false);
        return true;
    }

    public void fragmentTransaction(final Fragment fragment, boolean initial) {
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragment);
                transaction.commit();
                drawer.removeDrawerListener(this);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawer.closeDrawer(GravityCompat.START);
        if (initial) {
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
