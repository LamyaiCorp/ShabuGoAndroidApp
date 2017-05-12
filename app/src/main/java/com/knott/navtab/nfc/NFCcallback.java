package com.knott.navtab.nfc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.knott.navtab.unity.Utinity;

import java.lang.ref.WeakReference;

/**
 * Created by achaf on 13/5/2560.
 */

@RequiresApi(api = Build.VERSION_CODES.KITKAT)
public class NFCcallback implements NfcAdapter.ReaderCallback {

    // Weak reference to prevent retain loop. mAccountCallback is responsible for exiting
    // foreground mode before it becomes invalid (e.g. during onPause() or onStop()).
    private WeakReference<AccountCallback> mAccountCallback;

    public interface AccountCallback {
        public void onAccountReceived(String account);
    }

    public NFCcallback(AccountCallback accountCallback) {
        mAccountCallback = new WeakReference<AccountCallback>(accountCallback);
    }

    /**
     * Callback when a new tag is discovered by the system.
     *
     * <p>Communication with the card should take place here.
     *
     * @param tag Discovered tag
     */
    @Override
    public void onTagDiscovered(Tag tag) {

        String tagInfo = new String();
        byte[] tagId = tag.getId();

        for(int i=0; i<tagId.length; i++){
            tagInfo += Integer.toHexString(tagId[i] & 0xFF);
        }
        mAccountCallback.get().onAccountReceived(tagInfo);
        Utinity.table_id = tagInfo;
    }
}

