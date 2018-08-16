package demo.blog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.blog.presenter.MainActivityContract;
import demo.blog.presenter.MainActivityPresenter;
import demo.truecaller.rxjava2demo.R;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {

    private MainActivityPresenter mPresenter;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind the view using butterknife
        ButterKnife.bind(this);
        mPresenter = new MainActivityPresenter(this);
    }

    @OnClick(R.id.fab)
    public void onButtonClick(View view) {
        mPresenter.onContentButtonClick();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showProgressVisibility(int visibility) {
        mProgressBar.setVisibility(visibility);
    }

    @Override
    public void addContent(String text) {
        tvContent.append("\n" + text);
    }
}
