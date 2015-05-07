/*package com.simelabs.munchon.Activities;

import com.simelabs.munchon.Adapters.CustomListAdapterDialog;
import com.sinergia.munchon.R;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DialogIngredients extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String fontPath = "fonts/LaoUI.ttf";
		String fontPathBold = "fonts/LaoUIb.ttf";

		// Loading Font Face
		Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
		Typeface tfb = Typeface.createFromAsset(getAssets(), fontPathBold);
		
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		View view = getLayoutInflater().inflate(
				R.layout.dialog_select_ingredients, null);

		final ListView lv = (ListView) view.findViewById(R.id.listView1);

		TextView DishName = (TextView) view.findViewById(R.id.txt_DishName);
		DishName.setText("mini");
		TextView DishPrice = (TextView) view.findViewById(R.id.txt_ParameterName);
		DishPrice.setText("Cheese");
		DishName.setTypeface(tfb);
		DishPrice.setTypeface(tf);
		
		String[] name = { "Roquefort", "Camembert", "Cotija", "Feta",
				"Mozzarella", "Emmental", "Roquefort", "Camembert", "Cotija",
				"Feta", "Mozzarella", "Emmental" };
		Double[] price = { 1.3, 12.3, 3.5, 22.2, 10.7, 22.12, 1.3, 12.3, 3.5,
				22.2, 10.7, 22.12 };

		// Change MyActivity.this and myListOfItems to your own values
		CustomListAdapterDialog clad = new CustomListAdapterDialog(
				DialogIngredients.this, name, price,tf,tfb, dialog);

		lv.setAdapter(clad);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Object item = lv.getAdapter().getItem(position);

				Toast.makeText(getApplicationContext(), item.toString(),
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();

			}
		});

		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.argb(0, 0, 0, 0)));
		dialog.setContentView(view);

		dialog.show();

		ImageView close_button = (ImageView) view
				.findViewById(R.id.button_close);
		close_button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

	}

	private void showDialog() {

	}
}
*/