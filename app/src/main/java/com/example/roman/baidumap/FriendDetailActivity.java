package com.example.roman.baidumap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Roman on 2016/10/17.
 */
public class FriendDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView numberView = null;
    TextView nameView = null;
    TextView locationView = null;
    Button list_btn = null;
    Button radar_btn = null;
    private final int requestCode1 = 1;
    private final int requestCode2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_detail);
        bindView();
    }

    private void bindView(){
        nameView = (TextView)findViewById(R.id.txt_friend_name);
        numberView = (TextView)findViewById(R.id.txt_friend_number);
        locationView = (TextView)findViewById(R.id.txt_friend_long_lang);
        list_btn = (Button)findViewById(R.id.btn_friends_list);
        radar_btn = (Button)findViewById(R.id.btn_radar);

        Intent intent = getIntent();
        nameView.setText(intent.getExtras().getString("name"));
        numberView.setText(intent.getExtras().getString("number"));
        if (intent.getExtras().getString("latitude")!=null&&intent.getExtras().getString("longitude")!=null){
            String s = intent.getExtras().getString("latitude")+"N/"+intent.getExtras().getString("longitude")+"E";
            locationView.setText(s);
        }

        list_btn.setOnClickListener(this);
        radar_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_friends_list:
                Intent intent1 = new Intent(FriendDetailActivity.this, FriendActivity.class);
                startActivityForResult(intent1,requestCode1);
                FriendDetailActivity.this.finish();
                break;
            case R.id.btn_radar:
                Intent intent2 = new Intent(FriendDetailActivity.this, MainActivity.class);
                startActivityForResult(intent2,requestCode2);
                FriendDetailActivity.this.finish();
                break;
        }
    }

}
