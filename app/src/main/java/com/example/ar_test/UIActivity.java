package com.example.ar_test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactivity_ll);

        TextView tx_1 = findViewById(R.id.textView1);
        TextView bt = findViewById(R.id.bt);

        tx_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);
                view.startDrag(null, myShadow, view, 0);
                return false;
            }
        });

//        tx_1.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View view, DragEvent dragEvent) {
//
//                switch ( dragEvent.getAction() ){
//
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        Log.i("ドラッグテスト1", "ACTION_DRAG_STARTED");
//                        return true;
//
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        Log.i("ドラッグテスト1", "ACTION_DRAG_LOCATION");
//                        break;
//
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        Log.i("ドラッグテスト1", "ACTION_DRAG_EXITED");
//                        break;
//
//                    case DragEvent.ACTION_DROP:
//                        Log.i("ドラッグテスト1", "ACTION_DROP");
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        Log.i("ドラッグテスト1", "ACTION_DRAG_ENDED");
//                        break;
//
//                    default:
//                        Log.i("ドラッグテスト1", "default");
//                        break;
//                }
//
//                return false;
//            }
//        });

//        View v_tmp = findViewById(R.id.v_tmp);

        bt.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                switch (dragEvent.getAction()) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_STARTED");
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_ENTERED");

                        ViewGroup cl_root = findViewById(R.id.ll_UIRoot);

//                        moveDownPrcView(bt);
//                        createTmpView(cl_root, bt);
                        createTmpViewInLL(cl_root, bt);

                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_LOCATION");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_EXITED");
                        removeTmpView();
//                        moveUpPrcView(bt);
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.i("ドラッグテスト2", "ACTION_DROP");
                        removeTmpView();
                        createProcessViewInLL(findViewById(R.id.ll_UIRoot), bt);
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_ENDED");
                        return true;

                    default:
                        Log.i("ドラッグテスト2", "default");
                        break;
                }

                return false;
            }
        });

        LinearLayout ll_insideRoot = findViewById(R.id.ll_insideRoot);
        ll_insideRoot.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                switch (dragEvent.getAction()) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("ドラッグテスト3", "ACTION_DRAG_STARTED");
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("ドラッグテスト3", "ACTION_DRAG_ENTERED");

//                        moveDownPrcView(bt);
//                        createTmpView(bt);

                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("ドラッグテスト3", "ACTION_DRAG_LOCATION");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i("ドラッグテスト3", "ACTION_DRAG_EXITED");
//                        removeTmpView();
//                        moveUpPrcView(bt);
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.i("ドラッグテスト3", "ACTION_DROP");
                        return true;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("ドラッグテスト3", "ACTION_DRAG_ENDED");
                        return true;

                    default:
                        Log.i("ドラッグテスト3", "default");
                        break;
                }

                return false;
            }
        });
    }

    /*
     *
     */
    private void moveDownPrcView(View parentProcView) {

        TextView bt2 = findViewById(R.id.bt2);

        // 処理追加先の影ビューに位置を設定
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) bt2.getLayoutParams();
        mlp.setMargins(mlp.leftMargin, mlp.topMargin + 80, mlp.rightMargin, mlp.bottomMargin);
    }

    /*
     *
     */
    private void moveUpPrcView(View parentProcView) {

        TextView bt2 = findViewById(R.id.bt2);

        // 処理追加先の影ビューに位置を設定
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) bt2.getLayoutParams();
        mlp.setMargins(mlp.leftMargin, mlp.topMargin - 80, mlp.rightMargin, mlp.bottomMargin);
    }

    /*
     *
     */
    private void createTmpView(ViewGroup root, View aboveProcView) {

        //-------------------------------
        // 影ビューをレイアウトに追加
        //-------------------------------
        // 影ビューを生成
        View tmp = new View(this);
        tmp.setBackgroundColor(getResources().getColor(R.color.black_50));
        tmp.setTag("tmp");

        // レイアウトに追加
        root.addView(tmp, new ViewGroup.LayoutParams(200, 80));

        //-------------------------------
        // 影ビューの位置座標を設定
        //-------------------------------
        // 座標の取得
        ViewGroup.MarginLayoutParams parentMlp = (ViewGroup.MarginLayoutParams) aboveProcView.getLayoutParams();
        final int anchorLeft = parentMlp.leftMargin;
        final int anchorTop = parentMlp.topMargin + aboveProcView.getHeight();

        // 処理追加先の影ビューに位置を設定
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tmp.getLayoutParams();
        mlp.setMargins(anchorLeft, anchorTop, mlp.rightMargin, mlp.bottomMargin);
    }

    /*
     *
     */
    private void createTmpViewInLL(ViewGroup root, View aboveProcView){

        //-------------------------------
        // 影ビューをレイアウトに追加
        //-------------------------------
        // 影ビューを生成
        View tmp = new View(this);
        tmp.setBackgroundColor(getResources().getColor(R.color.black_50));
        tmp.setTag("tmp");

        // レイアウトに追加
        root.addView(tmp, 2, new ViewGroup.LayoutParams(200, 80));

        //-------------------------------
        // 影ビューの位置座標を設定
        //-------------------------------
        // 座標の取得
        ViewGroup.MarginLayoutParams parentMlp = (ViewGroup.MarginLayoutParams) aboveProcView.getLayoutParams();
        final int anchorLeft = parentMlp.leftMargin;
        final int anchorTop = parentMlp.topMargin + aboveProcView.getHeight();

        // 処理追加先の影ビューに位置を設定
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tmp.getLayoutParams();
        mlp.setMargins(anchorLeft, 0, mlp.rightMargin, mlp.bottomMargin);
    }

    /*
     *
     */
    private void createProcessViewInLL(ViewGroup root, View aboveProcView){

        //-------------------------------
        // 影ビューをレイアウトに追加
        //-------------------------------
        // 影ビューを生成
        View tmp = new SingleProcessView(this);
        tmp.setBackgroundColor(getResources().getColor(R.color.teal_700));

        // レイアウトに追加
        root.addView(tmp, 2, new ViewGroup.LayoutParams(200, 80));

        //-------------------------------
        // 影ビューの位置座標を設定
        //-------------------------------
        // 座標の取得
        ViewGroup.MarginLayoutParams parentMlp = (ViewGroup.MarginLayoutParams) aboveProcView.getLayoutParams();
        final int anchorLeft = parentMlp.leftMargin;
        final int anchorTop = parentMlp.topMargin + aboveProcView.getHeight();

        // 処理追加先の影ビューに位置を設定
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) tmp.getLayoutParams();
        mlp.setMargins(anchorLeft, 0, mlp.rightMargin, mlp.bottomMargin);
    }

    /*
     *
     */
    private void removeTmpView(){

        ViewGroup cl_root = findViewById(R.id.ll_UIRoot);

        int childNum = cl_root.getChildCount();
        for( int i = 0; i < childNum; i++ ){
            View target = cl_root.getChildAt( i );
            String tag = (String) target.getTag();

            if( tag == null ){
                continue;
            }

            if( tag.equals( "tmp" ) ){
                cl_root.removeView( target );
                return;
            }
        }
    }
}