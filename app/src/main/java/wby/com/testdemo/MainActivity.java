package wby.com.testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wby.com.loading_lib.ThreeDotLoading;

public class MainActivity extends AppCompatActivity {
    ThreeDotLoading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loading = (ThreeDotLoading) findViewById(R.id.maind_loading_three);
        loading.startAnim();
    }
}
