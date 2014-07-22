package com.liferay.bookmarks.ui.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.comm.DeleteBookmarkTask;
import com.liferay.bookmarks.comm.UpdateBookmarkTask;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.util.ConnectionUtil;

public class ViewBookmarkActivity extends Activity implements OnClickListener {

	public static final String DESCRIPTION_KEY = "description_key";
	public static final String URL_KEY = "url_key";
	public static final String NAME_KEY = "name_key";
	public static final String ENTRYID_KEY = "entryid_key";

	private EditText name;
	private EditText url;
	private EditText description;

	private long entryId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bookmark_form);

		Intent intent = getIntent();

		this.name = (EditText) findViewById(R.id.nameTextView);
		this.name.setText(intent.getStringExtra(NAME_KEY));

		this.url = (EditText) findViewById(R.id.urlTextView);
		this.url.setText(intent.getStringExtra(URL_KEY));

		this.description = (EditText) findViewById(R.id.descriptionTextView);
		this.description.setText(intent.getStringExtra(DESCRIPTION_KEY));

		this.entryId = intent.getLongExtra(ENTRYID_KEY, 0);

		Button deleteButton = (Button) findViewById(R.id.deleteButton);
		deleteButton.setVisibility(View.VISIBLE);
		deleteButton.setOnClickListener(this);

		Button editButton = (Button) findViewById(R.id.editButton);
		editButton.setVisibility(View.VISIBLE);
		editButton.setOnClickListener(this);
	}

	public void deleteBookmarkMessage() {
		Toast.makeText(this, R.string.bookmark_deleted_message, Toast.LENGTH_LONG).show();
		ViewBookmarkActivity.this.finish();
	}

	public void updateBookmarkMessage() {
		Toast.makeText(this, R.string.bookmark_updated_message, Toast.LENGTH_LONG).show();
		ViewBookmarkActivity.this.finish();
	}

	@Override
	public void onClick(View v) {
		if (ConnectionUtil.verifyConnectivity(ViewBookmarkActivity.this)) {
			if (v.getId() == R.id.deleteButton) {
				new DeleteBookmarkTask(ViewBookmarkActivity.this, entryId)
						.execute();
			} else if (v.getId() == R.id.editButton) {
				String potentialURL = url.getText().toString();
				if (!potentialURL.isEmpty()) {
					if (Patterns.WEB_URL.matcher(potentialURL).matches()) {
						BookmarkEntry entry = new BookmarkEntry();
						entry.setDescription(description.getText().toString());
						entry.setName(name.getText().toString());
						entry.setUrl(potentialURL);
						entry.setEntryId(entryId);

						new UpdateBookmarkTask(this, entry).execute();
					} else {
						Toast.makeText(this, R.string.invalid_url_message, Toast.LENGTH_SHORT)
								.show();
					}
				} else {
					Toast.makeText(this, R.string.url_field_is_required_message,
							Toast.LENGTH_SHORT).show();
				}
			}
		} else {
			Toast.makeText(ViewBookmarkActivity.this,
					R.string.check_your_connection_and_try_again,
					Toast.LENGTH_LONG).show();
		}
	}

}
