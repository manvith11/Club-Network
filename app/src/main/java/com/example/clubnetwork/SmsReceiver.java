package com.example.clubnetwork;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class SmsReceiver extends BroadcastReceiver {

    public static final String OTP_RECEIVED_ACTION = "otpReceived";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        if (data != null) {
            Object[] pdus = (Object[]) data.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();

                    // Extract OTP from messageBody
                    String otp = extractOTP(messageBody);

                    // Send OTP to MainActivity
                    Intent otpIntent = new Intent(OTP_RECEIVED_ACTION);
                    otpIntent.putExtra("otp", otp);
                    context.sendBroadcast(otpIntent);
                }
            }
        }
    }

    private String extractOTP(String messageBody) {
        // This regular expression will match any 6-digit number in the message
        Pattern pattern = Pattern.compile("(\\d{6})");
        Matcher matcher = pattern.matcher(messageBody);

        if (matcher.find()) {
            // If a match is found, the OTP is the first (and should be the only) match
            return matcher.group(1);
        } else {
            // If no match is found, return null or throw an exception as appropriate
            return null;
        }
    }
}