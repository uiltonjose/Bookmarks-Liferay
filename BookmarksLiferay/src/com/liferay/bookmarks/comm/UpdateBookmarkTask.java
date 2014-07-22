package com.liferay.bookmarks.comm;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.ui.activity.ViewBookmarkActivity;
import com.liferay.bookmarks.util.ConnectionUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;

public class UpdateBookmarkTask extends AsyncTask<Void, Void, BookmarkEntry> {

	private ViewBookmarkActivity activity;
	private static String TAG_NAME = UpdateBookmarkTask.class.getName();
	private ProgressDialog progressDialog;
	private BookmarkEntry bookmarkEntry;

	public UpdateBookmarkTask(ViewBookmarkActivity activity,
			BookmarkEntry bookmarkEntry) {
		this.activity = activity;
		this.bookmarkEntry = bookmarkEntry;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progressDialog = ProgressDialog.show(activity, "",
				activity.getString(R.string.updating_bookmark_message));
	}

	@Override
	protected BookmarkEntry doInBackground(Void... arg0) {

		return loadBookmarkEntry();

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		progressDialog.dismiss();

		Toast.makeText(activity, R.string.error_to_try_update_bookmark_message,
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPostExecute(BookmarkEntry result) {
		super.onPostExecute(result);

		progressDialog.dismiss();

		activity.updateBookmarkMessage();
	}

	private BookmarkEntry loadBookmarkEntry() {
		BookmarkEntry bookmarkEntry = null;

		Session session = ConnectionUtil.getSession();
		BookmarksEntryService bookmarksEntryService = new BookmarksEntryService(
				session);
		try {
			JSONObject jsonOjb = bookmarksEntryService.updateEntry(
					this.bookmarkEntry.getEntryId(), ConnectionUtil.GROUP_ID,
					ConnectionUtil.FOLDER_ID, this.bookmarkEntry.getName(),
					this.bookmarkEntry.getUrl(),
					this.bookmarkEntry.getDescription(), null);
			bookmarkEntry = new BookmarkEntry(jsonOjb);
		} catch (Exception e) {
			Log.e(TAG_NAME, activity.getString(R.string.error_to_try_update_bookmark_message), e);
			cancel(true);
		}

		return bookmarkEntry;
	}
}
