package com.socialv2.ewallet.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.socialv2.ewallet.dtos.banks.BankAppDto;

import java.util.ArrayList;
import java.util.List;

public class BankUtils {

    private Context context;

    private String[] bankApps = {
            "com.VCB",                  // Vietcombank (VCB Digibank)
            "vn.com.techcombank.bb.app",          // Techcombank (Techcombank Mobile)
            "com.vietinbank.ipay",      // VietinBank (iPay Mobile)
            "com.vnpay.bidv",    // BIDV (Smart Banking)
            "com.mbmobile",             // MB Bank (MBBank)
            "com.acb.ebiz",             // ACB (ACB Mobile Banking)
            "com.sacombank.mbanking",   // Sacombank (Sacombank mBanking)
            "com.tpb.mb.gprsandroid",   // TPBank (TPBank Mobile)
            "com.vnpay.vpbankonline",     // VPBank (VPBank NEO)
            "com.vnpay.Agribank3g",       // Agribank (Agribank E-Mobile)
            "com.vnpay.hdbank", // HDBank (HDBank App)
            "com.eximbank.ebanking",    // Eximbank (Eximbank Mobile Banking)
            "com.shb.ebank",            // SHB (SHB Mobile)
            "com.SeAMobile",            // SeABank (SeAMobile)
            "com.mbbank.mbbank",        // SCB (SCB Mobile Banking)
            "com.msb.mbanking",         // Maritime Bank (MSB Mobile Banking)
            "com.ocb.omb",              // OCB (OMNI Mobile Banking)
            "vn.com.timo",              // Timo (Timo Digital Bank)
            "vn.vnpay.wallet",          // VNPAY (VNPAY Wallet)
            "com.pvcombank.smartbanking", // PVcomBank (PV Mobile Banking)
            "com.bacAbank.mobilebanking", // Bac A Bank (Bac A Bank Mobile Banking)
            "com.ncbbank.mobile",       // NCB (NCB Mobile Banking)
            "com.vab.mobile",           // VAB (Viet A Bank Mobile Banking)
            "com.baoVietBank.mobile",   // BaoVietBank (Bao Viet Bank Mobile Banking)
            "vn.viviet",                // LienVietPostBank (LienVietPostBank Mobile Banking)
            "com.kienlongbank.ebank",   // KienlongBank (KienlongBank Mobile Banking)
            "com.pgbank.smartbanking",  // PGBank (PGBank Mobile Banking)
            "com.vpbmobile.fc",         // VPBank (VPBank NEO for Finance Companies)
            "com.sappvietcapitalbank"   // VietCapital Bank (VietCapital Mobile Banking)
    };

    public BankUtils(Context context) {
        this.context = context;
    }

    public List<BankAppDto> getOtherBankInstalled() {
        PackageManager pm = context.getPackageManager();
        List<BankAppDto> bankInstalledList = new ArrayList<>();
        for (String packageName : bankApps) {
            PackageInfo packageInfo = findPackage(packageName);
            if (packageInfo != null) {

                String appName = (String) pm.getApplicationLabel(packageInfo.applicationInfo);
                Drawable appIcon = pm.getApplicationIcon(packageInfo.applicationInfo);

                bankInstalledList.add(new BankAppDto(appName, appIcon));
            }
        }

        return bankInstalledList;
    }

    public PackageInfo findPackage(String packageName) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return packageInfo;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("BankUtils", "Package not found: " + packageName);
            return null;
        }
    }

    public String[] getBankNames() {
        return bankApps;
    }
}
