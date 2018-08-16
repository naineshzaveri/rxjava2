package demo.blog.presenter;

/**
 * Created by naineshzaveri on 12/08/18.
 */

public interface MainActivityContract {
    interface Presenter {
        void onContentButtonClick() throws Exception;
    }

    interface View {
        void showProgressVisibility(int visibility);

        void addContent(String text);
    }
}
