package com.example.administrator.meradhobi;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;

public class PopUpFactory {

	public static void createPopUp(Context context, final EditText anchor, String[] items){
		final PopupMenu popupMenu = new PopupMenu(context, anchor);
		for(int i=0;i<items.length;i++)
			popupMenu.getMenu().add(items[i]);
		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener(){
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				// TODO Auto-generated method stub
				anchor.setText(item.getTitle());
				return true;
			}			
		});
		//use on focuschangelistener instead
		anchor.setOnFocusChangeListener(new OnFocusChangeListener(){

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus)
				{
					popupMenu.show();
				}
				
			}
			
		});
		anchor.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				popupMenu.show();
			}			
		});	
	}

}
