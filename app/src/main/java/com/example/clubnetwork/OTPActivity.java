package com.example.clubnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;
import android.Manifest;
import android.content.Context;
import androidx.core.app.ActivityCompat;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;


public class OTPActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 0;
    private EditText editTextName;
    private EditText editTextMobile;
    private EditText editTextOTP;
    private Button buttonGetOTP;
    private Button buttonRegister;
    private CountryCodePicker countryCodePicker;
    private String verificationId;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        mAuth = FirebaseAuth.getInstance();

        editTextMobile = findViewById(R.id.editTextMobile);
        editTextOTP = findViewById(R.id.editTextOTP);
        buttonGetOTP = findViewById(R.id.buttonGetOTP);
        buttonRegister = findViewById(R.id.buttonRegister);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        countryCodePicker.registerCarrierNumberEditText(editTextMobile);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            // If not, request the permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, SMS_PERMISSION_CODE);
        }
        buttonGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = countryCodePicker.getFullNumberWithPlus().replace(" ", "");
                sendVerificationCode(phoneNumber);
            }
        });


        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = editTextOTP.getText().toString();
                if (code.isEmpty() || code.length() < 6) {
                    editTextOTP.setError("Enter valid code");
                    editTextOTP.requestFocus();
                } else {
                    verifyCode(code);
                }
            }
        });
    }
    private BroadcastReceiver otpReceiver;
    @Override
    protected void onResume() {
        super.onResume();
        // Initialize the BroadcastReceiver
        otpReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SmsReceiver.OTP_RECEIVED_ACTION.equals(intent.getAction())) {
                    String otp = intent.getStringExtra("otp");
                    if (otp != null) {
                        editTextOTP.setText(otp);
                    }
                }
            }
        };
        // Register the receiver
        registerReceiver(otpReceiver, new IntentFilter(SmsReceiver.OTP_RECEIVED_ACTION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Unregister the receiver
        if (otpReceiver != null) {
            unregisterReceiver(otpReceiver);
            otpReceiver = null;
        }
    }
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "SMS permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        super.onCodeSent(s, forceResendingToken);
                        verificationId = s;
                        editTextOTP.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        final String code = phoneAuthCredential.getSmsCode();
                        if (code != null) {
                            editTextOTP.setText(code);
                            verifyCode(code);
                        }
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UserProfile userProfile = getUserProfileFromIntent();
                            Intent intent = new Intent(OTPActivity.this, StartActivity.class);
                            intent.putExtra("user_profile", (Serializable) userProfile);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(OTPActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private UserProfile getUserProfileFromIntent() {
        // Get UserProfile from intent
        Intent intent = getIntent();

        if (intent.hasExtra("user_profile")) {
            return (UserProfile) intent.getSerializableExtra("user_profile");
        } else {
            // Handle the case where the UserProfile is not present in the intent
            return new UserProfile();  // Or return null, depending on your design
        }
    }

}