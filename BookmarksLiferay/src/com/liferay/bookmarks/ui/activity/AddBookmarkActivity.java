package com.liferay.bookmarks.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.comm.AddBookmarkTask;
import com.liferay.bookmarks.model.BookmarkEntry;
import com.liferay.bookmarks.util.ConnectionUtil;

public class AddBookmarkActivity extends Activity {
	
	private static final String PREFIX_URL= "http://";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bookmark_form);

		final EditText name = (EditText) findViewById(R.id.nameTextView);

		final EditText url = (EditText) findViewById(R.id.urlTextView);
		url.setText(PREFIX_URL);

		final EditText description = (EditText) findViewById(R.id.descriptionTextView);

		Button addButton = (Button) findViewById(R.id.addButton);
		addButton.setVisibility(View.VISIBLE);
		addButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(ConnectionUtil.verifyConnectivity(AddBookmarkActivity.this)){
					String potentialURL = url.getText().toString();
					if (!potentialURL.isEmpty()) {
						if (Patterns.WEB_URL.matcher(potentialURL).matches()) {
							BookmarkEntry entry = new BookmarkEntry();
							entry.setDescription(description.getText().toString());
							entry.setName(name.getText().toString());
							entry.setUrl(potentialURL);
	
							new AddBookmarkTask(AddBookmarkActivity.this, entry).execute();
						} else {
							Toast.makeText(AddBookmarkActivity.this,
									R.string.invalid_url_message, Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(AddBookmarkActivity.this,
								R.string.url_field_is_required_message, Toast.LENGTH_SHORT).show();
					}
				} else {
					Toast.makeText(AddBookmarkActivity.this, R.string.check_your_connection_and_try_again,
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}

	public void updateScreen() {
		Toast.makeText(this, R.string.bookmark_added_message, Toast.LENGTH_LONG).show();
		this.finish();
	}

}
