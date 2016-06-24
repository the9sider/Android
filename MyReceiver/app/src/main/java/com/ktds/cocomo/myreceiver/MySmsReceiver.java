package com.ktds.cocomo.myreceiver;

import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.Date;

public class MySmsReceiver extends BroadcastReceiver {
    public MySmsReceiver() {
    }

    /**
     * 안드로이드에 문자메시지가 도착할 경우 실행된다.
     *
     * @param context
     * @param intent
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceive(Context context, Intent intent) {

        String sms = getSmsDescription(intent);

        if (sms != null) {
            Intent activityIntent = new Intent();
            activityIntent.setClass(context, MainActivity.class);
            activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activityIntent.putExtra("SMS", sms);
            context.startActivity(activityIntent);
        }
    }

    /**
     * @param intent
     * @return
     */
    public String getSmsDescription(Intent intent) {
        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
            Bundle bundle = intent.getExtras();
            Object[] messages = (Object[]) bundle.get("pdus");
            SmsMessage[] smsMessage = new SmsMessage[messages.length];

            Date currentDate = null;
            String incommingNumber = "", message = "", formattedMessage = "";

            for (int i = 0; i < message.length(); i++) {
                smsMessage[i] = SmsMessage.createFromPdu((byte[]) messages[i]);

                // 수신 시간 확인
                currentDate = new Date(smsMessage[i].getTimestampMillis());

                // 발신번호 확인
                incommingNumber = smsMessage[i].getOriginatingAddress();

                // 메시지 확인
                message = smsMessage[i].getMessageBody().toString();

                formattedMessage = String.format("%s, %s, %s", currentDate.toString(), incommingNumber, message);
            }
            return formattedMessage;
        }
        return null;
    }
}
