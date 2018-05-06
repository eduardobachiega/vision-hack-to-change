package br.com.vision.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import br.com.edsb.dutils.DTDialogs;
import br.com.vision.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etCPF)
    EditText tvCPF;

    Context context = LoginActivity.this;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference();
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("USER", MODE_PRIVATE);
        String user = preferences.getString("CPF", "DEFAULT");
        if (!user.equals("DEFAULT"))
            nextActivity(MenuActivity.class);
    }

    @OnClick(R.id.floatingActionButton)
    public void fabLoginClick() {
        final String cpf = tvCPF.getText().toString();
        reference.child("users").child(cpf).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    DTDialogs.showPopUpMessage(context, getString(R.string.not_found),
                            getString(R.string.user_not_found), getString(R.string.ok),
                            true, 0, null);
                } else {
                    preferences.edit().putString("CPF", cpf).apply();
                    nextActivity(MenuActivity.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                databaseError.toException().printStackTrace();
            }
        });
    }

    private void nextActivity(Class targetClass) {
        startActivity(new Intent(context, targetClass));
    }

}
