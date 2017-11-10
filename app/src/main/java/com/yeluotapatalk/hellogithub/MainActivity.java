package com.yeluotapatalk.hellogithub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

	private String TAG = "yeluotapatalk";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		testRxJava();
	}

	private void testRxJava(){
		Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
			@Override
			public void subscribe(ObservableEmitter<Integer> e) throws Exception {
				Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
				e.onNext(1);
				e.onNext(3);
				e.onNext(5);
				e.onComplete();
			}
		});

		Observer<Integer> observer = new Observer<Integer>() {
			@Override
			public void onSubscribe(Disposable d) {
				Log.d(TAG, "onSubscribe thread is : " + Thread.currentThread().getName());
				Log.d(TAG,"subscribe");
			}

			@Override
			public void onNext(Integer value) {
				Log.d(TAG, "onNext thread is : " + Thread.currentThread().getName());
				Log.d(TAG,""+value);
			}

			@Override
			public void onError(Throwable e) {
				Log.d(TAG, "onError thread is : " + Thread.currentThread().getName());
				Log.d(TAG,"error");
			}

			@Override
			public void onComplete() {
				Log.d(TAG, "onComplete thread is : " + Thread.currentThread().getName());
				Log.d(TAG,"complete");
			}
		};

		observable.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(observer);
	}
}
