package se.pederjonsson.apps.quizkids;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import se.pederjonsson.apps.quizkids.fragments.QuestionFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btnstart)
    public Button btnStart;

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        unbinder = ButterKnife.bind(this);
        btnStart.setOnClickListener(v -> { startQuiz(); });
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unbinder.unbind();
    }

    private void startQuiz(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        QuestionFragment fragment = new QuestionFragment();
        //Bundle bundle = new Bundle();
        //bundle.putSerializable(QuestionFragment.EXTRA_DATA, mQueueEntityKey);
        //fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.fragmentcontainer, fragment);
        fragmentTransaction.commit();
    }
}
