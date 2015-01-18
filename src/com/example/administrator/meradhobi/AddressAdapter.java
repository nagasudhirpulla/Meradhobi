package com.example.administrator.meradhobi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class AddressAdapter extends ArrayAdapter<AddressListItem> {

	Context context;
	ViewHolder holder;
	List<AddressListItem> objects;


	public AddressAdapter(Context context, int textViewResourceId,
			List<AddressListItem> objects) {
		super(context, textViewResourceId, objects);
		this.context = context;
		this.objects = objects;
	}

	private static class ViewHolder {
		TextView pname;
		TextView pcity;	
		TextView pstate;
		TextView paddress;
		TextView ppin;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		holder = null;
		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.adapter_adress_list, null);
			holder = new ViewHolder();
			holder.pname = (TextView) convertView.findViewById(R.id.address_Name_reflect);
			holder.pcity = (TextView) convertView.findViewById(R.id.address_City_reflect);
			holder.pstate = (TextView) convertView.findViewById(R.id.address_State_reflect);
			holder.paddress = (TextView) convertView.findViewById(R.id.address_address_reflect);
			holder.ppin = (TextView) convertView.findViewById(R.id.address_pin_reflect);
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();

		holder.pname.setText(objects.get(position).getpname());
		holder.pcity.setText(objects.get(position).getpcity());
		holder.pstate.setText(objects.get(position).getpstate());
		holder.paddress.setText(objects.get(position).getpaddress());
		holder.ppin.setText(objects.get(position).getppin());
		return convertView;
	}
}