package com.ghostFood.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.ghostFood.R;
import com.ghostFood.adapter.ItemAdapter;
import com.ghostFood.adapter.ItemListGridAdapter;
import com.ghostFood.model.Category;
import com.ghostFood.model.Item;
import com.ghostFood.util.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.facebook.FacebookSdk.getApplicationContext;


public class CategoryItem extends Fragment {

    public static final String POSITION = "position";
    @BindView(R.id.lvAddress)
    GridView lvItems;
    Unbinder unbinder;
    @BindView(R.id.tvNoDataFound)
    TextView tvNoDataFound;
    @BindView(R.id.rvItem)
    RecyclerView rvItem;

    private View view;
    private TextView positionText;
    ItemListGridAdapter mItemListingAdapter;


    List<Item> items;
    List<Item> filteredItems = new ArrayList<>();
    ArrayList<Item> filterList;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    ItemAdapter itemAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, view);


        int position = getArguments().getInt(POSITION);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        // Defining Linear Layout Manager (here, 3 column span count)
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);

        ArrayList<Category> data = Constants.mListDataHeader;
        HashMap<Category, List<Item>> mHeader = Constants.listDataChild;

        List<Item> items = (List<Item>) mHeader.get(data.get(position - 1));

        //        mItemListingAdapter = new ItemListGridAdapter(getActivity(), items);
//        lvItems.setAdapter(mItemListingAdapter);

        rvItem.setLayoutManager(gridLayoutManager);
        itemAdapter = new ItemAdapter(getActivity(), items);
        rvItem.setAdapter(itemAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}