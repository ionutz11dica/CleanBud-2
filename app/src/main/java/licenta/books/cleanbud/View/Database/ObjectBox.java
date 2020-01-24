package licenta.books.cleanbud.View.Database;

import android.content.Context;
import android.util.Log;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;
import licenta.books.cleanbud.BuildConfig;
import licenta.books.cleanbud.View.Models.MyObjectBox;

public class ObjectBox {
    private static BoxStore boxStore;

    public static void init(Context context){
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(boxStore).start(context);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static BoxStore get(){ return boxStore; }
}
