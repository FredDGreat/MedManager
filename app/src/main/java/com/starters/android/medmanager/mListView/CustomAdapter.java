package com.starters.android.medmanager.mListView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.starters.android.medmanager.R;
import com.starters.android.medmanager.SelectedItemActivity;
import com.starters.android.medmanager.mDataObject.DrugContact;

import java.util.ArrayList;

/**
 * Created by Frederick
 */
public class CustomAdapter extends BaseAdapter implements Filterable{

    Context c;
    ArrayList<DrugContact> drugContacts;
    ArrayList<DrugContact> filteredmList ;
    private SearchFilter filter;

    DrugContact drugContact;
    public CustomAdapter(Context c, ArrayList<DrugContact> drugContacts) {
        this.c = c;
        this.drugContacts = drugContacts;
        this.filteredmList = drugContacts;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder = null;
        if(convertView==null)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView=inflater.inflate(R.layout.list_item_home_fragment,parent,false);
            //BIND DATA
            holder=new MyViewHolder(convertView);
            holder.mMedName = (TextView) convertView.findViewById(R.id.medName);
            holder.mDesc = (TextView) convertView.findViewById(R.id.medDesc);
            holder.mInterval = (TextView) convertView.findViewById(R.id.notificationLabel);
            holder.mDate = (TextView) convertView.findViewById(R.id.startNEndDate);
            convertView.setTag(holder);
        }else{
            holder = (MyViewHolder)convertView.getTag();
        }
        holder.mMedName.setText(drugContacts.get(position).getMedName());
        holder.mDesc.setText(drugContacts.get(position).getDesc());
        holder.mInterval.setText(drugContacts.get(position).getInterval());
        holder.mDate.setText(drugContacts.get(position).getStartNEndDate());
        final String medName = holder.mMedName.getText().toString();
        final String medDesc = holder.mDesc.getText().toString();
        final String medDate = holder.mDate.getText().toString();
        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onItemLongClick() {
                drugContact = (DrugContact) getItem(position);
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(c, "Item "+position+" clicked", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(c,SelectedItemActivity.class);
                mIntent.putExtra("medName",medName);
                mIntent.putExtra("medDesc",medDesc);
                //mIntent.putExtra("medInterval",holder.mInterval.getText().toString());
                mIntent.putExtra("medDate",medDate);
                c.startActivity(mIntent);
            }
        });
        /*convertView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(c, "Item "+i+" clicked", Toast.LENGTH_SHORT).show();
                Intent mIntent = new Intent(c,SelectedItemActivity.class);
                c.startActivity(mIntent);
            }
        });*/

        /*convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, drugContacts.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });*/

        return convertView;
    }
    /**
     *
     * @return returns an id of a selected item
     */
    public int getSelectedItemID()
    {
        return drugContact.getId();
    }
    /**
     *
     * @return returns the medication name of the selected item
     */
    public String getSelectedItemName()
    {
        return drugContact.getMedName();
    }

    @Override
    public int getCount() {
        return drugContacts.size();
    }

    @Override
    public Object getItem(int position) {
        return drugContacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new SearchFilter();
        }
        return filter;
    }
    /** * Too filter the the adapter with the searched new text. */

    private class SearchFilter extends Filter

    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            constraint = constraint.toString().toLowerCase();

            FilterResults filteredResults = new FilterResults();

        // to cretate a list with the same size as mList.

        // final List<String> originalList = mList;

            int Count = drugContacts.size();

            ArrayList<String> filterList = new ArrayList<String>(Count);

            String filterableString;
            for (int i = 0; i < Count; i++) {

                filterableString = drugContacts.get(i).getMedName();

                if (filterableString.toLowerCase().startsWith(constraint.toString())) {

            // Add strings starting with searched letter prefix to arraylist.

                    filterList.add(filterableString);

                }
            }

            filteredResults.values = filterList;

            filteredResults.count = filterList.size();
            //Toast.makeText(c, "Filter values="+filteredResults.values.toString(), Toast.LENGTH_SHORT).show();

            return filteredResults;

        }

        /**
         * To display the filtered result. *
         */

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredmList = (ArrayList<DrugContact>) results.values;
            //filteredmList = (ArrayList<DrugContact>) results.values;
            notifyDataSetChanged();
        }
    }
}








