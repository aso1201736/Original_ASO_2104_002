package jp.ac.st.asojuku.original2014002;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements
View.OnClickListener {

	SQLiteDatabase sdb = null;
	MySQLiteOpenHelper helper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ

		Intent vIntent = null;
		switch(v.getId()){
		case R.id.btn:  //メンテボタンが押された
			vIntent = new Intent(MainActivity.this,MaintenanceActivity.class);
			startActivity(vIntent);
			break;

		case R.id.btntou:  //登録ボタンを押された
			//エディットテキストから入力内容を取り出す
			EditText etv = (EditText)findViewById(R.id.edtMsg);
			String inputMsg = etv.getText().toString();

			if(inputMsg!=null && !inputMsg.isEmpty()){
				helper.insertHitokoto(sdb, inputMsg);
			}

			//入力欄をクリア
			etv.setText("");
			break;

		case R.id.btnch:  //一言チェックが押された

			//MySQLiteOpenHelperのセレクト一言メソッドを呼び出して一言をランダムに取得
			String strHitokoto = helper.selectRandomHitokoto(sdb);
			//エディットテキストから入力内容を取り出す
			vIntent = new Intent(this,HitokotoActivity.class);
			//インテントに一言を混入
			vIntent.putExtra("hitokoto", strHitokoto);
			startActivity(vIntent);
			break;
			}
	}

	@Override
	protected void onStart() {
		// TODO 自動生成されたメソッド・スタブ
		super.onStart();
	}

	@Override
	protected void onResume() {
		// TODO 自動生成されたメソッド・スタブ
		super.onResume();
		Button btn = (Button)findViewById(R.id.btn);
		btn.setOnClickListener(this);

		Button btntou = (Button)findViewById(R.id.btntou);
		btntou.setOnClickListener(this);

		Button btnch = (Button)findViewById(R.id.btnch);
		btnch.setOnClickListener(this);

		if(sdb == null){
			helper = new MySQLiteOpenHelper(getApplicationContext());
		}
		try{
			sdb = helper.getWritableDatabase();
		}catch(SQLiteException e){
			//異常終了
			return;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

