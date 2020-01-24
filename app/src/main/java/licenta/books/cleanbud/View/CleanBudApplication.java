package licenta.books.cleanbud.View;

import android.app.Application;

import licenta.books.cleanbud.View.Database.ObjectBox;

public class CleanBudApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);

    }
}
