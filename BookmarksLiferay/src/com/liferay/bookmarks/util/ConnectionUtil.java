package com.liferay.bookmarks.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;

public class ConnectionUtil {

	private static final String LOGIN = "test@liferay.com";

	private static final String PASSWORD = "test";

	private static final String SERVER_URL = "http://187.103.76.153:8080";

	public static final long GROUP_ID = 10181;
	public static final long FOLDER_ID = 0;
	public static final int START_PAGINATOR = -1;
	public static final int END_PAGINATOR = -1;

	public static Session getSession() {
		return new SessionImpl(SERVER_URL, LOGIN, PASSWORD);
	}

	public static boolean verifyConnectivity(Context context) {
		if (context != null) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getApplicationContext().getSystemService(
							Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();

			return networkInfo != null && networkInfo.isAvailable()
					&& networkInfo.isConnected();
		} else {
			return false;
		}
	}

}