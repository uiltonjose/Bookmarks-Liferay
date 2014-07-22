package com.liferay.bookmarks.comm;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.ui.acitivity.AddBookmarkActivity;
import com.liferay.bookmarks.util.ConnectionUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;

public class AddBookmarkTask extends AsyncTask<Void, Void, BookmarkEntry> {

	private AddBookmarkActivity activity;
	private static String TAG_NAME = AddBookmarkTask.class.getName();
	private ProgressDialog progressDialog;
	private BookmarkEntry bookmarkEntry;

	public AddBookmarkTask(AddBookmarkActivity activity,
			BookmarkEntry bookmarkEntry) {
		this.activity = activity;
		this.bookmarkEntry = bookmarkEntry;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progressDialog = ProgressDialog
				.show(activity, "", activity.getString(R.string.adding_bookmark_message));
	}

	@Override
	protected BookmarkEntry doInBackground(Void... arg0) {

		return loadBookmarkEntry();

	}

	private BookmarkEntry loadBookmarkEntry() {
		BookmarkEntry bookmarkEntry = null;

		Session session = ConnectionUtil.getSession();
		BookmarksEntryService bookmarksEntryService = new BookmarksEntryService(
				session);
		try {
			JSONObject jsonOjb = bookmarksEntryService.addEntry(
					ConnectionUtil.GROUP_ID, ConnectionUtil.FOLDER_ID,
					this.bookmarkEntry.getName(), this.bookmarkEntry.getUrl(),
					this.bookmarkEntry.getDescription(), null);
			bookmarkEntry = new BookmarkEntry(jsonOjb);
		} catch (Exception e) {
			Log.e(TAG_NAME, activity.getString(R.string.error_to_try_add_bookmark_message), e);
			cancel(true);
		}

		return bookmarkEntry;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		progressDialog.dismiss();

		Toast.makeText(activity, R.string.error_to_try_add_bookmark_message,
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPostExecute(BookmarkEntry result) {
		super.onPostExecute(result);

		progressDialog.dismiss();

		activity.updateScreen();
	}
}
