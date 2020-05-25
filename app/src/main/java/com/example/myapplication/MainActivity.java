package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    EditText Height ;
    EditText Weight ;
    Button Calculate ;
    Button Reset ;
    RecyclerView RecyclerView ;
    List<Double> BMI_List = new ArrayList<>();
    LinearLayoutManager layoutManager ;
    RecyclerView.Adapter<ViewHoler> Adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        Click();

    }

    private void init(){
        Height = findViewById(R.id.Edit_Height);
        Weight = findViewById(R.id.Eedit_Weight);
        Calculate = findViewById(R.id.Calculate);
        Reset = findViewById(R.id.Reset);

        RecyclerView = findViewById(R.id.RecyclerView);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(androidx.recyclerview.widget.RecyclerView.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);
        Adapter = new Adapter();
    }

    private void Click(){

        layoutManager = new LinearLayoutManager(this);

        Calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    float height = Float.parseFloat(Height.getText().toString());
                    float weight = Float.parseFloat(Weight.getText().toString());
                    Double bmi = weight/Math.pow(height,2);
                    BMI_List.add(bmi);
                    Dialog(true);
                    RecyclerView.setAdapter(Adapter);
                    RecyclerView.scrollToPosition(BMI_List.size()-1);  //滑至底部

                }catch (NumberFormatException N){
                    Dialog(false);
                }
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Height.getText().clear();
                Weight.getText().clear();
            }
        });

    }

    private void Dialog(Boolean bo){

        if (bo){
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("成功");
            dialog.setCancelable(false);
            dialog.setMessage("請於下方查看BMI");
            dialog.setNegativeButton("了解", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
            dialog.setTitle("失敗");
            dialog.setCancelable(false);
            dialog.setMessage("請填入正確的身高、體重");
            dialog.setNegativeButton("了解", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }


    static class ViewHoler extends RecyclerView.ViewHolder{
        TextView textView ;

        ViewHoler(@NonNull View itemView) {
            super(itemView);
            Log.d("test_", "new Holder");
            textView = itemView.findViewById(R.id.BMI);
        }
    }


    private class Adapter extends RecyclerView.Adapter<ViewHoler> {

        @Override
        public ViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d("test_", "onCreateViewHolder");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, null, false);   // 每次生成一個 View ，若中間不為nul，會被覆蓋

            return new ViewHoler(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHoler holder, int position) {
            Log.d("test_", "onBindView");
            int num = position + 1;

            /*
            int k = position % 3;
            switch (k){
                case 0:
                    holder.textView.setBackgroundColor(Color.BLUE);
                    break;
                case 1:
                    holder.textView.setBackgroundColor(Color.GREEN);
                    break;
                case 2:
                    holder.textView.setBackgroundColor(Color.RED);
                    break;
            }
            */

            holder.textView.setText("第" + num + "次測量：" + BMI_List.get(position).toString());

        }

        @Override
        public int getItemCount() {
            Log.d("test_", "getItemCount");

            return BMI_List.size();
        }



    }


    public boolean onTouchEvent(MotionEvent event) {

        InputMethodManager manager= (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if(getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null){
                assert manager != null;
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }    //

}
