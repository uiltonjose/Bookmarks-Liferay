package com.liferay.bookmarks.comm;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.ui.acitivity.ListBookmarkActivity;
import com.liferay.bookmarks.util.ConnectionUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;

public class BookmarksGetEntriesTask extends
		AsyncTask<Void, Void, ArrayList<BookmarkEntry>> {

	private ListBookmarkActivity activity;
	private static String TAG_NAME = BookmarksGetEntriesTask.class.getName();
	private ProgressDialog progressDialog;

	public BookmarksGetEntriesTask(ListBookmarkActivity activity) {
		this.activity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progressDialog = ProgressDialog.show(activity, "", activity.getString(R.string.loading_bookmarks_message));
	}

	@Override
	protected ArrayList<BookmarkEntry> doInBackground(Void... arg0) {

		return loadBookmarksEntry();
	}

	private ArrayList<BookmarkEntry> loadBookmarksEntry() {
		ArrayList<BookmarkEntry> bookmarks = new ArrayList<BookmarkEntry>();
		Session session = ConnectionUtil.getSession();

		try {
			BookmarksEntryService bes = new BookmarksEntryService(session);
			JSONArray entries = bes.getEntries(ConnectionUtil.GROUP_ID, ConnectionUtil.FOLDER_ID, ConnectionUtil.START_PAGINATOR, ConnectionUtil.END_PAGINATOR);

			for (int i = 0; i < entries.length(); i++) {
				JSONObject jsonObj = entries.getJSONObject(i);
				bookmarks.add(new BookmarkEntry(jsonObj));
			}
		} catch (Exception e) {
			Log.e(TAG_NAME, activity.getString(R.string.couldn_t_get_bookmarks_message), e);
			cancel(true);
		}

		return bookmarks;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
		
		progressDialog.dismiss();

		Toast.makeText(activity, R.string.couldn_t_get_bookmarks_message, Toast.LENGTH_LONG)
				.show();
	}

	@Override
	protected void onPostExecute(ArrayList<BookmarkEntry> result) {
		super.onPostExecute(result);

		progressDialog.dismiss();
		
		this.activity.fetchBookmarks(result);

	}

}
