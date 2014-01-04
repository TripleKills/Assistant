package org.sa.studyassistant.view;

import org.sa.studyassistant.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.EditText;

public class EditDialog {
	private Context mContext;
	private int title_res_id;
	private OnPositiveListener positiveListener;
	
	public EditDialog(Context mContext, int title_res_id) {
		super();
		this.mContext = mContext;
		this.title_res_id = title_res_id;
	}

	public void setPositiveListener(OnPositiveListener positiveListener) {
		this.positiveListener = positiveListener;
	}

	public void show() {
		Builder builder = new Builder(mContext);
		builder.setTitle(title_res_id);
		final EditText edit = new EditText(mContext);
		builder.setNegativeButton(R.string.button_cancel,
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.setPositiveButton(R.string.button_confirm, new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				positiveListener.onPositive(edit.getText().toString());
			}
		});
		AlertDialog dialog = builder.create();
		dialog.setView(edit, 10, 20, 10, 20);
		dialog.show();
	}

	public interface OnPositiveListener {
		public void onPositive(String inputtext);
	}
}
