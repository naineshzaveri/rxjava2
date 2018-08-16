package demo.blog.presenter;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

import demo.blog.network.NetworkClient;
import demo.blog.network.NetworkInterface;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by naineshzaveri on 12/08/18.
 */

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.View mView;

    public MainActivityPresenter(MainActivityContract.View view) {
        mView = view;
    }

    @Override
    public void onContentButtonClick() {
        try {
            mView.showProgressVisibility(View.VISIBLE);
            getBlogContentObservable();
        } catch (Exception e) {
            e.printStackTrace();
            mView.showProgressVisibility(View.INVISIBLE);
        }
    }

    private Observable<String> getTenthCharacterObservable(String text) {

        return Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            emitter.onNext("TENTH CHARACTER-->" + getTenthIndexedString(text));
                        }
                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    private Observable<String> getEveryTenthCharacterObservable(String text) {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    for (int i = 0; i < text.length(); i++) {
                        if (i % 9 == 0) {
                            emitter.onNext("EVERY TENTH CHARACTER--> " + text.charAt(i));
                        }
                    }
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observable<String> getCharacterCountObservable(String text) {

        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    HashMap<String, Integer> stringIntegerHashMap = getCharacterCount(text);
                    for (Map.Entry<String, Integer> entry : stringIntegerHashMap.entrySet()) {
                        System.out.println(entry.getKey() + " = " + entry.getValue());
                        emitter.onNext("CHARACTER COUNT -->" + entry.getKey() + " = " + entry.getValue());
                    }
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private DisposableObserver<String> getObserver() {
        return new DisposableObserver<String>() {

            public void onNext(String s) {
                mView.addContent(s);
            }

            @Override
            public void onError(Throwable e) {
                mView.showProgressVisibility(View.GONE);
            }

            @Override
            public void onComplete() {
                mView.showProgressVisibility(View.GONE);
            }
        };
    }

    public String getTenthIndexedString(String blogText) {
        String tenthIndexedName = "" + blogText.charAt(10);
        return tenthIndexedName;
    }

    private HashMap<String, Integer> getCharacterCount(String blogText) {
        String[] wordsArray = blogText.split("\\s+");
        HashMap<String, Integer> map = new HashMap<>();
        int count;
        for (String word : wordsArray) {
            if (map.containsKey(word)) {
                count = map.get(word);
                map.put(word, count + 1);
            } else {
                map.put(word, 1);
            }
        }
        return map;
    }

    private void getBlogContentObservable() {
        NetworkClient.getRetrofit().create(NetworkInterface.class)
                .getTrueCallerLife()
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        return Observable.merge(getTenthCharacterObservable(s), getEveryTenthCharacterObservable(s), getCharacterCountObservable(s));
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getObserver());
    }
}
