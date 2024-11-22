package com.example.calamitycall;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsPreferences {

    private static final String PREFS_NAME = "NotificationPreferences";

    private static final String WATCH_FLASHING_KEY = "watch_flashing";
    private static final String WATCH_NOISE_KEY = "watch_noise";
    private static final String WATCH_NOTIFICATION_ON_KEY = "watch_notification_on";
    private static final String WATCH_NOTIFICATION_TYPE_KEY = "watch_notification_type";

    private static final String WARNING_FLASHING_KEY = "warning_flashing";
    private static final String WARNING_NOISE_KEY = "warning_noise";
    private static final String WARNING_NOTIFICATION_ON_KEY = "warning_notification_on";
    private static final String WARNING_NOTIFICATION_TYPE_KEY = "warning_notification_type";

    private static final String URGENT_FLASHING_KEY = "urgent_flashing";
    private static final String URGENT_NOISE_KEY = "urgent_noise";
    private static final String URGENT_NOTIFICATION_ON_KEY = "urgent_notification_on";
    private static final String URGENT_NOTIFICATION_TYPE_KEY = "urgent_notification_type";

    private static final String CRITICAL_FLASHING_KEY = "critical_flashing";
    private static final String CRITICAL_NOISE_KEY = "critical_noise";
    private static final String CRITICAL_NOTIFICATION_ON_KEY = "critical_notification_on";
    private static final String CRITICAL_NOTIFICATION_TYPE_KEY = "critical_notification_type";

    private SharedPreferences sharedPreferences;

    public SettingsPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // Watch settings
    public boolean isWatchFlashingOn() {
        return sharedPreferences.getBoolean(WATCH_FLASHING_KEY, false);
    }

    public boolean isWatchNoiseOn() {
        return sharedPreferences.getBoolean(WATCH_NOISE_KEY, false);
    }

    public boolean isWatchNotificationOn() {
        return sharedPreferences.getBoolean(WATCH_NOTIFICATION_ON_KEY, true);
    }

    public boolean isWatchNotificationTypePush() {
        String watch_result = sharedPreferences.getString(WATCH_NOTIFICATION_TYPE_KEY, "push");
        boolean watchNotificationType = true;
        if (watch_result.equals("push"))
        {
            return watchNotificationType = true;
        }
        else
        {
            return watchNotificationType = false;
        }
    }

    public String getWatchNotificationType() {
        return sharedPreferences.getString(WATCH_NOTIFICATION_TYPE_KEY, "push");
    }

    public void setWatchFlashingOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WATCH_FLASHING_KEY, isOn).apply();
    }

    public void setWatchNoiseOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WATCH_NOISE_KEY, isOn).apply();
    }

    public void setWatchNotificationOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WATCH_NOTIFICATION_ON_KEY, isOn).apply();
    }

    public boolean isWarningNotificationTypePush() {
        String warning_result = sharedPreferences.getString(WARNING_NOTIFICATION_TYPE_KEY, "push");
        boolean warningNotificationType = true;
        if (warning_result.equals("push"))
        {
            return warningNotificationType = true;
        }
        else
        {
            return warningNotificationType = false;
        }
    }



    // Warning settings
    public boolean isWarningFlashingOn() {
        return sharedPreferences.getBoolean(WARNING_FLASHING_KEY, false);
    }

    public boolean isWarningNoiseOn() {
        return sharedPreferences.getBoolean(WARNING_NOISE_KEY, false);
    }

    public boolean isWarningNotificationOn() {
        return sharedPreferences.getBoolean(WARNING_NOTIFICATION_ON_KEY, true);
    }

    public String getWarningNotificationType() {
        return sharedPreferences.getString(WARNING_NOTIFICATION_TYPE_KEY, "push");
    }

    public void setWarningFlashingOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WARNING_FLASHING_KEY, isOn).apply();
    }

    public void setWarningNoiseOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WARNING_NOISE_KEY, isOn).apply();
    }

    public void setWarningNotificationOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(WARNING_NOTIFICATION_ON_KEY, isOn).apply();
    }

    public boolean isUrgentNotificationTypePush() {
        String urgent_result = sharedPreferences.getString(URGENT_NOTIFICATION_TYPE_KEY, "push");
        boolean urgentNotificationType = true;
        if (urgent_result.equals("push"))
        {
            return urgentNotificationType = true;
        }
        else
        {
            return urgentNotificationType = false;
        }
    }

    public boolean isCriticalNotificationTypePush() {
        String critical_result = sharedPreferences.getString(CRITICAL_NOTIFICATION_TYPE_KEY, "push");
        boolean criticalNotificationType = true;
        if (critical_result.equals("push"))
        {
            return criticalNotificationType = true;
        }
        else
        {
            return criticalNotificationType = false;
        }
    }

    // Urgent settings
    public boolean isUrgentFlashingOn() {
        return sharedPreferences.getBoolean(URGENT_FLASHING_KEY, false);
    }

    public boolean isUrgentNoiseOn() {
        return sharedPreferences.getBoolean(URGENT_NOISE_KEY, false);
    }

    public boolean isUrgentNotificationOn() {
        return sharedPreferences.getBoolean(URGENT_NOTIFICATION_ON_KEY, true);
    }

    public String getUrgentNotificationType() {
        return sharedPreferences.getString(URGENT_NOTIFICATION_TYPE_KEY, "push");
    }

    public void setUrgentFlashingOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(URGENT_FLASHING_KEY, isOn).apply();
    }

    public void setUrgentNoiseOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(URGENT_NOISE_KEY, isOn).apply();
    }

    public void setUrgentNotificationOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(URGENT_NOTIFICATION_ON_KEY, isOn).apply();
    }



    // Critical settings
    public boolean isCriticalFlashingOn() {
        return sharedPreferences.getBoolean(CRITICAL_FLASHING_KEY, false);
    }

    public boolean isCriticalNoiseOn() {
        return sharedPreferences.getBoolean(CRITICAL_NOISE_KEY, false);
    }

    public boolean isCriticalNotificationOn() {
        return sharedPreferences.getBoolean(CRITICAL_NOTIFICATION_ON_KEY, true);
    }



    public void setCriticalFlashingOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(CRITICAL_FLASHING_KEY, isOn).apply();
    }

    public void setCriticalNoiseOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(CRITICAL_NOISE_KEY, isOn).apply();
    }

    public void setCriticalNotificationOn(boolean isOn) {
        sharedPreferences.edit().putBoolean(CRITICAL_NOTIFICATION_ON_KEY, isOn).apply();
    }

    public void setCriticalNotificationType(String type) {
        sharedPreferences.edit().putString(CRITICAL_NOTIFICATION_TYPE_KEY, type).apply();
    }
}
