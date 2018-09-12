package com.sty.dynamically.additem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ScrollView scrollView;
    private LinearLayout llVipNumContainer;
    private LinearLayout llAddVipNum;
    private Button btnYes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListeners();
    }

    private void initView(){
        scrollView = findViewById(R.id.scroll_view);
        llVipNumContainer = findViewById(R.id.ll_vip_num_container);
        llAddVipNum = findViewById(R.id.ll_add_vip_num);
        btnYes = findViewById(R.id.btn_yes);

        for(int i = 0; i < 10; i++){
            addViewItem();
        }
    }

    private void setListeners(){
        llAddVipNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addViewItem();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> list = getDataList();
                if(list.size() <= 0){
                    Toast.makeText(MainActivity.this, "请输入会员号码", Toast.LENGTH_SHORT).show();
                }else{
                    StringBuilder sb = new StringBuilder("");
                    for(String str : list){
                        sb.append(str).append(",");
                    }
                    Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 添加item
     */
    private void addViewItem(){
        View viewItem = LayoutInflater.from(this).inflate(R.layout.item_add_vip_num, llVipNumContainer,false);
        llVipNumContainer.addView(viewItem);
        sortViewItem();
        //添加并且排序之后将布局滚动到底部，方便用户继续添加
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    /**
     * 该方法主要用于排序（每个item中的序号），主要针对从中间删除item的情况
     */
    private void sortViewItem(){
        for(int i = 0; i < llVipNumContainer.getChildCount(); i++){
            final View viewItem = llVipNumContainer.getChildAt(i);
            TextView tvIndex = (TextView)viewItem.findViewById(R.id.tv_index);
            tvIndex.setText((i+1) + "");
            LinearLayout llDelete = (LinearLayout) viewItem.findViewById(R.id.ll_delete);
            llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llVipNumContainer.removeView(viewItem);
                    sortViewItem();
                }
            });
        }
    }

    private List<String> getDataList() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < llVipNumContainer.getChildCount(); i++) {
            View itemView = llVipNumContainer.getChildAt(i);
            EditText et = (EditText) itemView.findViewById(R.id.et_vip_number);
            if (et != null) {
                String vipNum = et.getText().toString().trim();
                if (!TextUtils.isEmpty(vipNum)) {
                    result.add(vipNum);
                }
            }
        }
        return result;
    }
}
