package com.example.roman.baidumap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roman on 2016/10/16.
 */
public class FriendActivity extends AppCompatActivity implements View.OnClickListener{

    private Context mContext;
    private ListView list = null;
    private Friend_Adapter mAdapter = null;
    private Button add_btn;
    private Button edit_btn;
    private Button ok_btn;
    private Button radar_btn;
    private Button close_btn;
    private View AddFriendDialogView;
    private EditText mfriendName;
    private EditText mfriendNum;
    private AlertDialog dialog = null;
    private AlertDialog.Builder builder = null;
    private boolean add_friend = false;//是否添加朋友的标记
    private final int requestCode1 = 1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends_list);
        mContext = FriendActivity.this;
        //动态加载布局生成View对象
        LayoutInflater layoutInflater = FriendActivity.this.getLayoutInflater();
        AddFriendDialogView = layoutInflater.inflate(R.layout.dialog_add_friend, null,false);

        initView();
    }

    private void initView(){
        mAdapter = new Friend_Adapter(MainActivity.FriendList,mContext);
        list = (ListView)findViewById(R.id.lvw_friends_list);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendActivity.this, FriendDetailActivity.class);
                friend f = (friend)MainActivity.FriendList.get(position);
                intent.putExtra("name",f.getName());
                intent.putExtra("number",f.getNumber());
                if ((f.getLatitude()!=null)&&(f.getLongitude()!=null)) {
                    Pattern pattern = Pattern.compile("(\\d+)\\.(\\d+)");
                    Matcher la = pattern.matcher(f.getLatitude());
                    if (la.find())
                        intent.putExtra("latitude",la.group(1)+"."+la.group(2).substring(0,4));
                    Matcher lo = pattern.matcher(f.getLongitude());
                    if (lo.find())
                        intent.putExtra("longitude",lo.group(1)+"."+lo.group(2).substring(0,4));
                }
                if (f.getDistant()!=null){
                    intent.putExtra("distant",f.getDistant());
                }
                startActivityForResult(intent,requestCode1);
                FriendActivity.this.finish();
            }
        });
        //获取布局中的控件
        mfriendName = (EditText)AddFriendDialogView.findViewById(R.id.txt_friend_name);
        mfriendNum = (EditText)AddFriendDialogView.findViewById(R.id.txt_friend_number);
        ok_btn = (Button)AddFriendDialogView.findViewById(R.id.btn_dialog_ok);
        close_btn = (Button)AddFriendDialogView.findViewById(R.id.btn_dialog_close);

        builder = new AlertDialog.Builder(mContext);
        dialog = builder.setView(AddFriendDialogView).create();

        add_btn = (Button)findViewById(R.id.btn_friends_list_add);
        edit_btn = (Button)findViewById(R.id.btn_friends_list_edit);
        radar_btn = (Button)findViewById(R.id.btn_friends_list_radar);

        add_btn.setOnClickListener(this);
        edit_btn.setOnClickListener(this);
        radar_btn.setOnClickListener(this);
        ok_btn.setOnClickListener(this);
        close_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_friends_list_add:
                dialog.show();
                add_friend = true;
                break;
            case R.id.btn_dialog_ok:
                if (add_friend){
                    MainActivity.FriendNum++;
                    friend fr = new friend(mfriendName.getText().toString(),mfriendNum.getText().toString());
                    MainActivity.FriendList.add(fr);
                    saveList("friend",MainActivity.FriendList);
                    mAdapter = new Friend_Adapter(MainActivity.FriendList,mContext);
                    list.setAdapter(mAdapter);
                    add_friend = false;
                    dialog.dismiss();
                }
                break;
            case R.id.btn_dialog_close:
                dialog.dismiss();
                break;
            case R.id.btn_friends_list_radar:
                Intent intent = getIntent();
                FriendActivity.this.setResult(RESULT_OK,intent);
                FriendActivity.this.finish();
                break;
            case R.id.btn_friends_list_edit:
                break;
        }
    }

    private void saveList(String name, ArrayList<Object> list){
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null){
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getList(String name){
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null){
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
}
