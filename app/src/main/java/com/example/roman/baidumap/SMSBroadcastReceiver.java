package com.example.roman.baidumap;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Roman on 2016/10/21.
 */
public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[])intent.getExtras().get("pdus");   //接收数据
        String latitude = Double.toString(MainActivity.latitude);
        String longitude = Double.toString(MainActivity.longitude);
        boolean isFriend = false;
        for(Object p: pdus){
            byte[]pdu = (byte[])p;
            SmsMessage message = SmsMessage.createFromPdu(pdu); //根据获得的byte[]封装成SmsMessage
            String body = message.getMessageBody();             //发送内容
            String sender = message.getOriginatingAddress();    //短信发送方
            int index = 0;
            //判断发送方是否是好友
            for (int i=0;i<MainActivity.FriendNum;i++){
                friend f = (friend)MainActivity.FriendList.get(i);
                String s = "+86"+f.getNumber();
                if (s.equals(sender)){
                    index = i;
                    isFriend = true;
                    break;
                }
            }

            //若收到请求位置的短信，发送经纬度给对方
            if("where are you?".equals(body)&&isFriend){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(sender, null, latitude+"/"+longitude, null, null);
            }

            //接收回复经纬度的短信内容
            Pattern pattern = Pattern.compile("(\\d+\\.\\d+)/(\\d+\\.\\d+)");
            Matcher m = pattern.matcher(body);
            if (isFriend&&m.find()){
                friend f = (friend)MainActivity.FriendList.get(index);
                f.setLatitude(m.group(1));
                f.setLongitude(m.group(2));
                MainActivity.FriendList.set(index,f);
                MainActivity.isRestart = true;
                Intent intent1 = new Intent(context, MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);

            }
        }

    }
}
