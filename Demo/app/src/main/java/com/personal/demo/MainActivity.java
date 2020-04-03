package com.personal.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context mContext;
    private List<Model> datas;
    private RecyclerView recyclerView;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        recyclerView = findViewById(R.id.recyclerView);

        for (int i = 0; i < 50; i++) {
            Model model = new Model();
            model.i = i;
            model.isDetele = false;
            getDatas().add(model);
        }

        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        recyclerView.setPadding(DensityUtil.dip2px(this, 20),DensityUtil.dip2px(this, 5), DensityUtil.dip2px(this, 20),0);
        adapter = new ItemAdapter(this, getDatas());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onClick(final int position) {
                iconHiddenDelete();
            }

            @Override
            public void onDeleteClickListener(int i) {
                getDatas().remove(i);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onLongClick(final View view, int position) {
                Model model = getDatas().get(position);
                model.isDetele = true;
                getDatas().set(position, model);
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    iconHiddenDelete();
                }
                return false;
            }
        });
    }

    private void iconHiddenDelete() {
        for (int i = 0; i < getDatas().size(); i++) {
            Model model = getDatas().get(i);
            model.isDetele = false;
            getDatas().set(i, model);
        }
        adapter.notifyDataSetChanged();
    }

    public List<Model> getDatas() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        return datas;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                iconHiddenDelete();
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
        }

        return super.onTouchEvent(event);
    }
}
