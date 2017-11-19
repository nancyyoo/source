package com.coffee.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.coffee.coffeebean.R;
/**
 * Created by 유희영 on 2017-06-11.
 */
public class ActivityListView extends Activity implements OnItemClickListener, OnClickListener {
	// 오버라이드 된 애들을 갖고 온다.

	private EditText mEtInputText;

	private Button mBInputToList;

	private ListView mLvList;

	private ArrayList<String> mAlData;

	private ArrayAdapter<String> mAaString;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);

		mEtInputText = (EditText) findViewById(R.id.et_comment);
		mBInputToList = (Button) findViewById(R.id.bt_ok);

		mLvList = (ListView) findViewById(R.id.lv_comment);

		mBInputToList.setOnClickListener(this);

		mAlData = new ArrayList<String>();

		mAaString = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mAlData);

		mLvList.setAdapter(mAaString);

		mLvList.setOnItemClickListener(this);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mAlData.clear();

		defaultData();
	}

	private void defaultData() {
		mAlData.add("좋아요~!");
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

		case R.id.bt_ok:
			if (mEtInputText.getText().length() == 0) {
				Toast.makeText(this, R.string.listview_input_data, Toast.LENGTH_LONG).show();
			}

			else {
				String data = mEtInputText.getText().toString();

				mAlData.add(data);

				mAaString.notifyDataSetChanged();

				Toast.makeText(this, R.string.listview_add_data, Toast.LENGTH_SHORT).show();

				mEtInputText.setText("");

				mLvList.setSelection(mAlData.size() - 1);
			}
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, final int position, long id) {
		// TODO Auto-generated method stub

		String data = mAlData.get(position);

		String message = ""+R.string.listview_delete_data;
		// message.charAt(R.string.listview_delete_data);
		
		DialogInterface.OnClickListener deleteListener = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int arg1) {
				// TODO Auto-generated method stub
				mAlData.remove(position);

				mAaString.notifyDataSetChanged();
			}
		};

		new AlertDialog.Builder(this)
		.setMessage(Html.fromHtml(message))
		.setNegativeButton(R.string.listview_delete, deleteListener)
		.setPositiveButton(R.string.listview_close, null)
		.show();
	}
}