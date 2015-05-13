package profile.auto.com.autoprofile_1;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.os.Message;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MSGService extends Service {

    int newintvalr;
    Calendar c;
    String phoneNumber;
    DBHandler.DBHandler db1;
    String msg = null;
    String tim;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        db1 = new DBHandler.DBHandler(this);
        super.onCreate();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        phoneNumber = intent.getStringExtra("num");
        tim = intent.getStringExtra("tm");
        //System.out.println("time of call"+tim);
        //int newintval=Integer.parseInt(tim);
        retraiveFromTimesAndToTimes(tim);
        super.onStart(intent, startId);
    }

    private void sendEmail(String msg, String subject) {
        try {
            ContentResolver cr = getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
            Cursor cursor = cr.query(uri, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{phoneNumber}, null);

            String contact_id = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    contact_id = cursor.getString(cursor.getColumnIndex("_id"));
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (contact_id == null)
                return;
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                    ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=?", new String[]{contact_id}, null);
            String email = null;
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                if (!cursor.isClosed()) {
                    cursor.close();
                }
            }
            if (email == null)
                return;
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("message/rfc822");
            i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
            i.putExtra(Intent.EXTRA_SUBJECT, subject);
            i.putExtra(Intent.EXTRA_TEXT, msg);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void retraiveFromTimesAndToTimes(String tim2) {
        // TODO Auto-generated method stub

        String nw = tim2;
        System.out.println("value test 1" + nw);
        Toast.makeText(getApplicationContext(), "@@@" + nw, 60).show();

        List<String> alistaa = this.db1.msgsend(nw);
String subject = "", imagePath = null;
        for (int i = 0; i < alistaa.size(); i++) {
            String pname = alistaa.get(0);
            msg = alistaa.get(1);
            subject = alistaa.get(0);
            imagePath = alistaa.get(2);
            Toast.makeText(getApplicationContext(), "@#@#" + msg, 60).show();
            System.out.println("@@@@@@@@@###########$$$$$$$$$$$%%%%%%%%%%%" + msg);
        }


        Toast.makeText(getApplicationContext(), "aaaaaaaa" + phoneNumber, 20).show();

        sendmsg(phoneNumber, msg, imagePath);
        sendEmail(msg, subject);
    }

    private void sendmsg(String phoneNumber, String msg, String imagePath) {
        // TODO Auto-generated method stub
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);


        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub

                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }


            }
        }, new IntentFilter(SENT));


        registerReceiver(new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));
        final TextToSpeech ttobj = new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if (status != TextToSpeech.ERROR) {
//                            ttobj.setLanguage(Locale.UK);
                        }
                    }
                });
        HashMap<String, String> myHashRender = new HashMap<String, String>();
        String speakTextTxt = "Hello World. I am good";
        myHashRender.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
                speakTextTxt);

        String exStoragePath = Environment
                .getExternalStorageDirectory().getAbsolutePath();
        File appTmpPath = new File(exStoragePath + "/sounds/");
        if (!appTmpPath.exists())
            appTmpPath. mkdirs();
        String tempFilename = "tmpaudio.wav";
        String tempDestFile = appTmpPath.getAbsolutePath() + "/"
                + tempFilename;
        appTmpPath = new File(tempDestFile);
        if (!appTmpPath.exists())
            try {
                boolean o = appTmpPath.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        int k = ttobj.synthesizeToFile(speakTextTxt, myHashRender, tempDestFile);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra("sms_body", msg);
        /*sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                getResources().getResourcePackageName(R.drawable.ic_launcher) + '/' +
                getResources().getResourceTypeName(R.drawable.ic_launcher) + '/' +
                getResources().getResourceEntryName(R.drawable.ic_launcher)));*/
        sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(imagePath));
        sendIntent.setType("image/*");
        sendIntent.putExtra("address",phoneNumber);
        sendIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(sendIntent);
        ConfigUtil.setPhno(phoneNumber);
        SmsManager sms = SmsManager.getDefault();
//        smsddd.sendTextMessage(phoneNumber, null, msg, sentPI, deliveredPI);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }


}
