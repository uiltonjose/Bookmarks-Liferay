package com.liferay.bookmarks.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.liferay.bookmarks.R;
import com.liferay.bookmarks.model.BookmarkEntry;

public class BookmarkAdapter extends ArrayAdapter<BookmarkEntry> {

	private Context context;
	private ArrayList<BookmarkEntry> bookmarks;

	public BookmarkAdapter(Context context, ArrayList<BookmarkEntry> bookmarks) {
		super(context, R.layout.item_bookmark, bookmarks);
		this.context = context;
		this.bookmarks = bookmarks;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.item_bookmark, parent, false);

		TextView textView = (TextView) rowView.findViewById(R.id.nameTextView);

		BookmarkEntry bookmark = bookmarks.get(position);
		textView.setText(bookmark.getName());

		return rowView;
	}

}
