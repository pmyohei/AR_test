package com.example.ar_test;

import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UIActivity_custom extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiactivity_ll);

        Log.i("ドラッグテスト", "カスタム側の起動");

        // ドラッグ用
        TextView tx_1 = findViewById(R.id.textView1);
        tx_1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // 新規処理ビューの生成
                View processView = new SingleProcessView( view.getContext() );
                // ドラッグ中の影を生成
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);

                // ドラッグ開始
                view.startDrag(null, myShadow, processView, 0);
                return false;
            }
        });

        // ドラッグ用
        TextView tx_2 = findViewById(R.id.textView2);
        tx_2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                // 新規処理ビューの生成
                View processView = new NestProcessView( view.getContext() );
                // ドラッグ中の影を生成
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);

                // ドラッグ開始
                view.startDrag(null, myShadow, processView, 0);
                return false;
            }
        });

        // 削除エリア
        View v_removeArea = findViewById(R.id.v_removeArea);
        v_removeArea.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {

                switch (dragEvent.getAction()) {
                    case DragEvent.ACTION_DRAG_STARTED:
                    case DragEvent.ACTION_DRAG_ENTERED:
                    case DragEvent.ACTION_DRAG_LOCATION:
                    case DragEvent.ACTION_DRAG_EXITED:
                    case DragEvent.ACTION_DRAG_ENDED:
                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.i("ドラッグテスト", "削除エリアへドロップ");

                        // ドロップされたビューをレイアウトから削除
                        removeProcessView( dragEvent );
                        return true;

                    default:
                        break;
                }

                return false;
            }
        });
    }


    /*
     * ドラッグビューをレイアウトから削除
     */
    private void removeProcessView(DragEvent dragEvent) {

        // 削除対象（ドラッグされてきたビュー）のID
        View draggedView = (View) dragEvent.getLocalState();
        int draggedID = draggedView.getId();

        // 削除対象（ドラッグされてきたビュー）の親レイアウト
        ViewGroup parent = (ViewGroup) draggedView.getParent();

        //---------------------------
        // 検索してレイアウトから削除
        //---------------------------
        // 子ビューを全て検索
        int childNum = parent.getChildCount();
        for( int i = 0; i < childNum; i++ ){
            // 検索対象の子ビューのID
            View target = parent.getChildAt( i );
            int id = target.getId();

            // IDの一致するビューがあれば
            if( id == draggedID ){
                // レイアウトから削除
                parent.removeView( target );
                return;
            }
        }
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