package com.intellibeacons.app;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ImageBrowserFragment extends ListFragment {
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        OnImageSelectionChangeListener listener =
                (OnImageSelectionChangeListener) getActivity();
        listener.OnImageSelectionChanged(position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] titles = getResources().getStringArray(R.array.image_titles);

        ArrayAdapter<String> titleAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, titles);
        setListAdapter(titleAdapter);
    }
}
