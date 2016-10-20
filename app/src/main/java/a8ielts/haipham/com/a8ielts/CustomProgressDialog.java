package a8ielts.haipham.com.a8ielts;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;


public class CustomProgressDialog extends ProgressDialog {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_dialog);
    }

    public CustomProgressDialog(Context context) {
        super(context, R.style.MyProgressDialogTheme);
        setCancelable(false);
        setProgressStyle(android.R.style.Widget_ProgressBar_Small);
    }
}
