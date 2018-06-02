package com.lpoo1718_t1g3.mariokart;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.lpoo1718_t1g3.QRCodeUtils.QRCodeIntegrator;

public class AndroidLauncher extends AndroidApplication implements QRCodeIntegrator {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		MarioKart.getInstance().setCallback(this);
		initialize(MarioKart.getInstance(), config);
	}

	@Override
	public void startScanner() {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				IntentIntegrator integrator = new IntentIntegrator((AndroidLauncher) MarioKart.getInstance().getCallback());
				integrator.initiateScan();
			}
		};
		runOnUiThread(r);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null && scanResult.getContents() != null) {
			MarioKart.getInstance().scanResult(scanResult.getContents());
		}
	}
}
