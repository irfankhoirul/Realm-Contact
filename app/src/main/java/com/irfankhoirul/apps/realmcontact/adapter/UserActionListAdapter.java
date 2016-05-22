package com.irfankhoirul.apps.realmcontact.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.irfankhoirul.apps.realmcontact.R;

import java.util.List;

/**
 * Created by Irfan Khoirul on 22/05/2016.
 */
public class UserActionListAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> items;
    int type;

    public UserActionListAdapter(Context context, int resource, List<String> objects, int type) {
        super(context, resource, objects);
        this.context = context;
        this.items = objects;
        this.type = type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.user_action_list, null);
        }

        String itemsText = items.get(position);
        if (itemsText != null) {
            TextView tvUserAction = (TextView) v.findViewById(R.id.tvUserAction);
            if (tvUserAction != null)
                tvUserAction.setText(itemsText);

        }
        return v;
    }
}
