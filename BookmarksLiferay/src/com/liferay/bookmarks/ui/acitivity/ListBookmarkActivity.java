package com.liferay.bookmarks.ui.acitivity;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.comm.BookmarksGetEntriesTask;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.ui.adapter.BookmarkAdapter;
import com.liferay.bookmarks.util.ConnectionUtil;

public class ListBookmarkActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	private BookmarkAdapter adapter;
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bookmark_list_view);

		listview = (ListView) findViewById(R.id.listview);
		listview.setOnItemClickListener(this);

		Button refreshButton = (Button) findViewById(R.id.refreshButton);
		refreshButton.setOnClickListener(this);

		Button addButton = (Button) findViewById(R.id.addButton);
		addButton.setOnClickListener(this);

	}

	@Override
	protected void onResume() {
		super.onResume();

		if (ConnectionUtil.verifyConnectivity(this)) {
			new BookmarksGetEntriesTask(this).execute();
		} else {
			Toast.makeText(ListBookmarkActivity.this, R.string.check_your_connection_and_try_again,
					Toast.LENGTH_LONG).show();
			finish();
		}
	}

	public void fetchBookmarks(ArrayList<BookmarkEntry> bookmarks) {

		adapter = new BookmarkAdapter(this, bookmarks);

		listview.setAdapter(adapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		if (parent != null) {
			BookmarkEntry bookmark = (BookmarkEntry) parent
					.getItemAtPosition(position);
			if (bookmark != null) {
				Intent intent = new Intent(ListBookmarkActivity.this,
						ViewBookmarkActivity.class);

				intent.putExtra(ViewBookmarkActivity.ENTRYID_KEY,
						bookmark.getEntryId());
				intent.putExtra(ViewBookmarkActivity.NAME_KEY,
						bookmark.getName());
				intent.putExtra(ViewBookmarkActivity.URL_KEY,
						bookmark.getUrl());
				intent.putExtra(
						ViewBookmarkActivity.DESCRIPTION_KEY,
						bookmark.getDescription());

				ListBookmarkActivity.this.startActivity(intent);

			}
		}

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.refreshButton) {
			Intent intent = new Intent(this, ListBookmarkActivity.class);
			startActivity(intent
					.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT));

		} else if (v.getId() == R.id.addButton) {
			startActivity(new Intent(this, AddBookmarkActivity.class));
		}

	}
}
