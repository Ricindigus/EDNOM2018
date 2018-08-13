package pe.com.ricindigus.appednom2018.util;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * Created by otin016 on 17/07/2017.
 */

public class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return source;
    }
}
