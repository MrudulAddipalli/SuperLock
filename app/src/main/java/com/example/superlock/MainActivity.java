package com.example.superlock;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
public class MainActivity extends AppCompatActivity {
    public static final int RESULT_ENABLE = 11;
    public DevicePolicyManager devicePolicyManager;
    public ActivityManager activityManager;
    public ComponentName compName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.device();
    }
    @Override
    protected void onResume() {
        super.onResume();
        this.device();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        this.device();
    }

    public void device(){
        devicePolicyManager = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
        activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        compName = new ComponentName(this, MyAdmin.class);
        boolean active = devicePolicyManager.isAdminActive(compName);
        if (active) {
            devicePolicyManager.lockNow();
            finish();
        }
        else {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, compName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "Additional text explaining why we need this permission");
            startActivityForResult(intent, RESULT_ENABLE);
        }
    }
}
