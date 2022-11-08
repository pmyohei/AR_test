package com.example.ar_test;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;


/*
 * * ネストあり処理ビュー（ループ／分岐処理）
 */
public class NestProcessView extends ProcessView {

    //---------------------------
    // 定数
    //----------------------------


    //---------------------------
    // フィールド変数
    //----------------------------
    public NestProcessView(Context context) {
        this(context, null);
    }
    public NestProcessView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public NestProcessView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.nest_process_layout, this);

        // ネスト処理ビュー初期処理
        init();
    }

    /*
     * 初期化処理
     */
    private void init() {
        // ID設定
//        setId( View.generateViewId() );

//        // ロングクリックリスナーの設定
//        setLongClickListerner();
//        // onDragリスナーの設定（本体側）
//        setDragAndDropBodyListerner();
        // onDragリスナーの設定（入れ子の親レイアウト側）
        setDragAndDropNestListerner();
    }

    /*
     * onLongClickリスナーの設定
     */
/*
    private void setLongClickListerner() {

        // ドラッグ用
        setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onLongClick(View view) {

//                Log.i("ClipData", "送信 " + Integer.toString(view.getId()));

                //--------------------------
                // ドロップ先へ渡すデータ生成
                //--------------------------
                // ClipDataとしてレイアウトIDを渡す
                ClipData.Item item = new ClipData.Item(Integer.toString(view.getId()));
                ClipData dragData = new ClipData(
                        (CharSequence) Integer.toString(view.getId()),
                        new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN},
                        item);

                //--------------------------
                // ドラッグ処理
                //--------------------------
                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(view);
                view.startDragAndDrop(dragData, myShadow, view, 0);

                return true;
            }
        });
    }
*/


    /*
     * onDragリスナーの設定（本体側）
     */
/*
    private void setDragAndDropBodyListerner() {

        // レイアウト最上位にリスナーを設定
        ViewGroup cl_root = findViewById(R.id.ll_UIRoot);
        cl_root.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_STARTED");
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_ENTERED");

                        //----------------------
                        // 同一ビューチェック
                        //----------------------
                        // ドラッグされてきたビューが同じビューであれば何もしない
                        if ( isSameDraggedView(dragEvent) ) {
                            return false;
                        }

                        //-------------------------
                        // 処理追加イメージビューの生成
                        //-------------------------
                        createAddImageView();

                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_LOCATION");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_EXITED");

                        // 処理追加イメージビューの削除
                        removeAddImageView( (ViewGroup) getParent() );

                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.i("ドラッグテスト2", "ACTION_DROP");

                        //----------------------
                        // 同一ビューチェック
                        //----------------------
                        // ドラッグされてきたビューが、同じビューであれば何もしない
                        if ( isSameDraggedView(dragEvent) ) {
                            return false;
                        }


                        // 処理追加イメージビューの削除
                        removeAddImageView( (ViewGroup) getParent() );

                        // ドロップされた処理を元の位置から削除
                        removeProcessView( dragEvent );

                        // ドロップされた処理を生成
                        createProcessView( dragEvent );

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
    }
*/

    /*
     * onDragリスナーの設定（入れ子の親レイアウト側）
     */
    private void setDragAndDropNestListerner() {

        // 入れ子の親レイアウトにリスナーを設定
        ViewGroup ll_insideRoot = findViewById(R.id.ll_insideRoot);
        ll_insideRoot.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                switch (dragEvent.getAction()) {

                    case DragEvent.ACTION_DRAG_STARTED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_STARTED");
                        return true;

                    case DragEvent.ACTION_DRAG_ENTERED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_ENTERED");

                        //----------------------
                        // 同一ビューチェック
                        //----------------------
                        // ドラッグされてきたビューが、同じビューであれば何もしない
                        if ( isSameDraggedView(dragEvent) ) {
                            return false;
                        }

                        //-------------------------
                        // 処理追加イメージビューの生成
                        //-------------------------
                        createAddImageViewToNestParent();

                        return true;

                    case DragEvent.ACTION_DRAG_LOCATION:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_LOCATION");
                        return true;

                    case DragEvent.ACTION_DRAG_EXITED:
                        Log.i("ドラッグテスト2", "ACTION_DRAG_EXITED");

                        // 処理追加イメージビューの削除
                        ViewGroup parent = findViewById(R.id.ll_insideRoot);
                        removeAddImageView( parent );

                        return true;

                    case DragEvent.ACTION_DROP:
                        Log.i("ドラッグテスト2", "ACTION_DROP");

                        //----------------------
                        // 同一ビューチェック
                        //----------------------
                        // ドラッグされてきたビューが、同じビューであれば何もしない
                        if ( isSameDraggedView(dragEvent) ) {
                            return false;
                        }

                        // 処理追加イメージビューの削除
                        parent = findViewById(R.id.ll_insideRoot);
                        removeAddImageView( parent );
                        // ドロップされた処理を元の位置から削除
                        removeProcessView( dragEvent );
                        // ドロップされた処理を生成
                        createProcessViewToNestParent( dragEvent );

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
    }

    /*
     * ドラッグされてきたビューが、ドロップ先と同じかどうかをチェック
     * @return：true：同じ
     */
/*
    private boolean isSameDraggedView( DragEvent dragEvent ) {

        int myselfID = getId();

        View draggedView = (View) dragEvent.getLocalState();
        int draggedID = draggedView.getId();

        // ドラッグされてきたビューが同じならtrueを返す
        return (myselfID == draggedID);
    }
*/

    /*
     * 処理追加イメージビューの生成
     */
/*
    private void createAddImageView() {

        //-------------------------------
        // 処理追加イメージビューをレイアウトに追加
        //-------------------------------
        // 処理追加イメージビューを生成
        View addImageView = new View( getContext() );
        addImageView.setBackgroundColor(getResources().getColor(R.color.black_50));
        addImageView.setTag( TAG_ADD_IMAGE_VIEW );

        // 親レイアウトから見た時の自分の子ビューindexを取得
        int myselfIndex = getMyselfChildIndex();

        // 親レイアウトに追加
        ViewGroup parentView = (ViewGroup) getParent();
        parentView.addView(addImageView, myselfIndex + 1, new ViewGroup.LayoutParams(200, 80));

        //--------------------------------------
        // 処理追加イメージビューを他の処理と左揃えにする
        //--------------------------------------
        // 左揃えにするために、自分の左マージンを取得
        MarginLayoutParams parentMlp = (MarginLayoutParams) getLayoutParams();
        final int anchorLeft = parentMlp.leftMargin;

        // 処理追加イメージビューに左マージンを設定
        MarginLayoutParams mlp = (MarginLayoutParams) addImageView.getLayoutParams();
        mlp.setMargins(anchorLeft, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
    }
*/

    /*
     * 処理追加イメージビューの生成（入れ子の親レイアウト側）
     */
    private void createAddImageViewToNestParent() {

        //-------------------------------
        // 処理追加イメージビューをレイアウトに追加
        //-------------------------------
        // 処理追加イメージビューを生成
        View addImageView = new View( getContext() );
        addImageView.setBackgroundColor(getResources().getColor(R.color.black_50));
        addImageView.setTag( TAG_ADD_IMAGE_VIEW );

        // 親レイアウトに追加
        ViewGroup parentView = findViewById(R.id.ll_insideRoot);
        parentView.addView(addImageView, 0, new ViewGroup.LayoutParams(200, 80));
    }

    /*
     * 処理ビューの生成（本体側直下へ追加）
     */
/*
    private void createProcessView( DragEvent dragEvent ) {

        //-------------------------------
        // 処理追加イメージビューをレイアウトに追加
        //-------------------------------
        // 処理追加イメージビューを生成
        View processView = (View) dragEvent.getLocalState();

        // 親レイアウトから見た時の自分の子ビューindexを取得
        int myselfIndex = getMyselfChildIndex();

        // 親レイアウトに追加
        ViewGroup parentView = (ViewGroup) getParent();
        parentView.addView(processView, myselfIndex + 1);

        //--------------------------------------
        // 処理追加イメージビューを他の処理と左揃えにする
        //--------------------------------------
        // 左揃えにするために、自分の左マージンを取得
        MarginLayoutParams parentMlp = (MarginLayoutParams) getLayoutParams();
        final int anchorLeft = parentMlp.leftMargin;

        // 処理追加イメージビューに左マージンを設定
        MarginLayoutParams mlp = (MarginLayoutParams) processView.getLayoutParams();
        mlp.setMargins(anchorLeft, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin);
    }
*/

    /*
     * 処理ビューの生成（入れ子の親レイアウト側）
     */
    private void createProcessViewToNestParent( DragEvent dragEvent ) {

        //-------------------------------
        // 処理ビューをレイアウトに追加
        //-------------------------------
        // ドラッグされてきた処理ビューを取得
        View processView = (View) dragEvent.getLocalState();

        // 親レイアウトに追加
        ViewGroup parentView = findViewById(R.id.ll_insideRoot);
        parentView.addView(processView, 0);
    }

    /*
     * 親レイアウトから見た時の本ビューのchildIndexを取得
     */
/*
    private int getMyselfChildIndex(){

        // 自分のレイアウトID
        int myID = getId();

        // 子レイアウトの数
        ViewGroup parentView = (ViewGroup) getParent();
        int childNum = parentView.getChildCount();

        Log.i("処理イメージ", "追加 childNum=" + childNum);

        // 親レイアウトの子ビュー分繰り返し
        for( int i = 0; i < childNum; i++ ){
            // 自分と同じIDがあれば、その時のindexを変えす
            View checkTarget = parentView.getChildAt( i );
            int checkID = checkTarget.getId();
            if( myID == checkID ){
                return i;
            }
        }

        return -1;
    }
*/

    /*
     * 処理追加イメージビューの削除
     */
/*
    private void removeAddImageView(ViewGroup parentView){

        // 子レイアウトの数
        int childNum = parentView.getChildCount();

        Log.i("処理イメージ", "削除 childNum=" + childNum);

        // 親レイアウトの子ビュー分繰り返し
        for( int i = 0; i < childNum; i++ ){
            View target = parentView.getChildAt( i );
            String tag = (String) target.getTag();

            // タグ未設定のビューは対象外
            if( tag == null ){
                continue;
            }
            // タグが処理追加イメージビューの時
            if( tag.equals( TAG_ADD_IMAGE_VIEW ) ){
                parentView.removeView( target );
                return;
            }
        }
    }
*/

    /*
     * 処理ビューの削除
     *   ※ない場合はなにもしない
     */
/*
    private void removeProcessView(DragEvent dragEvent) {

        // 削除対象（ドラッグされたビュー）のID
        View draggedView = (View) dragEvent.getLocalState();
        int draggedID = draggedView.getId();

        // 子ビューから検索して、該当ビューを削除
        //ViewGroup ll_UIRoot = getRootView().findViewById(R.id.ll_UIRoot);
        ViewGroup parentView = (ViewGroup) draggedView.getParent();
        if( parentView == null ){
            // 新規追加の場合は、親レイアウトなしのため処理終了
            return;
        }

        int childNum = parentView.getChildCount();
        for( int i = 0; i < childNum; i++ ){
            View target = parentView.getChildAt( i );
            int id = target.getId();

            // IDの一致するビューがあれば削除
            if( id == draggedID ){
                parentView.removeView( target );
                return;
            }
        }
    }
*/

}
