package su.mehsoft.documentflow.bottomnavfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import su.mehsoft.documentflow.FeedActivity;
import su.mehsoft.documentflow.LoginActivity;
import su.mehsoft.documentflow.R;

public class HomeFragment extends Fragment implements View.OnClickListener{
    private Button btnLogOut;
    private FirebaseAuth mAuth;
    private TextView tvFragmentHome;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,null);

        tvFragmentHome = view.findViewById(R.id.tvFragmentHome);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            tvFragmentHome.setText(currentUser.getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                try {
                    getActivity().finish();
                }
                catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
