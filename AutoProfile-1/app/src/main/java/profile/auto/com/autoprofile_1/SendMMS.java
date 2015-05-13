package profile.auto.com.autoprofile_1;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spandana on 4/14/2015.
 */
public class SendMMS {
    Context context;

/*    public void sendMMS(Context context, final String recipient, final MMSPart[] parts) {
        this.context = context;
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final int result = mConnMgr.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE, "enableMMS");

        if (result != 0) {
            IntentFilter filter = new IntentFilter();
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            BroadcastReceiver receiver = new BroadcastReceiver() {

                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();

                    if (!action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                        return;
                    }

                    @SuppressWarnings("deprecation")
                    NetworkInfo mNetworkInfo = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

                    if ((mNetworkInfo == null) || (mNetworkInfo.getType() != ConnectivityManager.TYPE_MOBILE_MMS)) {
                        return;
                    }

                    if (!mNetworkInfo.isConnected()) {
                        return;
                    } else {
                        sendData(recipient, parts);

                    }

                }

            };

        } else {
            sendData(recipient, parts);
        }
    }

    public void sendData(final String recipient, final MMSPart[] parts) {

        new Thread(new Runnable() {

            @Override
            public void run() {

                final SendReq sendRequest = new SendReq();

                final EncodedStringValue[] phoneNumber = EncodedStringValue.extract(recipient);

                if (phoneNumber != null && phoneNumber.length > 0) {
                    sendRequest.addTo(phoneNumber);
                }

                final PduBody pduBody = new PduBody();

                if (parts != null) {
                    for (MMSPart part : parts) {
                        if (part != null) {
                            try {
                                final PduPart partPdu = new PduPart();
                                partPdu.setName(part.Name.getBytes());
                                partPdu.setContentType(part.MimeType.getBytes());
                                partPdu.setData(part.Data);
                                pduBody.addPart(partPdu);
                            } catch (Exception e) {

                            }
                        }
                    }
                }

                sendRequest.setBody(pduBody);

                final PduComposer composer = new PduComposer(context, sendRequest);
                final byte[] bytesToSend = composer.make();

                List<APN> apns = new ArrayList<APN>();

                try {
                    APNHelper helper = new APNHelper(context);
                    apns = helper.getMMSApns();
                } catch (Exception e) {
                    APN apn = new APN(sharedPrefs.getString("mmsc_url", ""), sharedPrefs.getString("mms_port", ""), sharedPrefs.getString("mms_proxy", ""));
                    apns.add(apn);
                }

                try {
                    HttpUtils.httpConnection(context, 4444L, apns.get(0).MMSCenterUrl, bytesToSend, HttpUtils.HTTP_POST_METHOD, !TextUtils.isEmpty(apns.get(0).MMSProxy), apns.get(0).MMSProxy, Integer.parseInt(apns.get(0).MMSPort));

                    ConnectivityManager mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    mConnMgr.startUsingNetworkFeature(ConnectivityManager.TYPE_MOBILE_MMS, "enableMMS");

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
                    BroadcastReceiver receiver = new BroadcastReceiver() {

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Cursor query = context.getContentResolver().query(Uri.parse("content://mms"), new String[]{"_id"}, null, null, "date desc");
                            query.moveToFirst();
                            String id = query.getString(query.getColumnIndex("_id"));
                            query.close();

                            ContentValues values = new ContentValues();
                            values.put("msg_box", 2);
                            String where = "_id" + " = '" + id + "'";
                            context.getContentResolver().update(Uri.parse("content://mms"), values, where, null);

                            context.unregisterReceiver(this);
                        }

                    };

                } catch (Exception e) {
                    Cursor query = context.getContentResolver().query(Uri.parse("content://mms"), new String[]{"_id"}, null, null, "date desc");
                    query.moveToFirst();
                    String id = query.getString(query.getColumnIndex("_id"));
                    query.close();

                    ContentValues values = new ContentValues();
                    values.put("msg_box", 5);
                    String where = "_id" + " = '" + id + "'";
                    context.getContentResolver().update(Uri.parse("content://mms"), values, where, null);

                    ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content).post(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(context, "MMS Error", Toast.LENGTH_SHORT).show();
                        }

                    });
                }

            }

        }).start();

    }*/
}
