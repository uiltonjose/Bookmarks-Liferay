package com.liferay.bookmarks.comm;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.ui.acitivity.ViewBookmarkActivity;
import com.liferay.bookmarks.util.ConnectionUtil;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;

public class DeleteBookmarkTask extends AsyncTask<Void, Void, Void> {

	private ViewBookmarkActivity activity;
	private static String TAG_NAME = DeleteBookmarkTask.class.getName();
	private ProgressDialog progressDialog;
	private long entryId;

	public DeleteBookmarkTask(ViewBookmarkActivity activity, long entryId) {
		this.activity = activity;
		this.entryId = entryId;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		progressDialog = ProgressDialog.show(activity, "",
				activity.getString(R.string.deleting_bookmark_message));
	}

	@Override
	protected Void doInBackground(Void... arg0) {

		Session session = ConnectionUtil.getSession();
		BookmarksEntryService bookmarksEntryService = new BookmarksEntryService(
				session);
		try {
			bookmarksEntryService.deleteEntry(entryId);
		} catch (Exception e) {
			Log.e(TAG_NAME, activity
					.getString(R.string.error_to_try_delete_bookmark_message),
					e);
			cancel(true);
		}

		return null;

	}

	@Override
	protected void onCancelled() {
		super.onCancelled();

		progressDialog.dismiss();

		Toast.makeText(
				activity,
				activity.getString(R.string.error_to_try_delete_bookmark_message),
				Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);

		progressDialog.dismiss();

		activity.deleteBookmarkMessage();
	}
}
