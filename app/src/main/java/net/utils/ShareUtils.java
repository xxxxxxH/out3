package net.utils;

import android.content.Context;
import android.content.Intent;

public class ShareUtils {

    public static void share(Context context,String content) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        //要分享的文本内容，选择某项后会直接把这段文本发送出去，相当于调用选中的应用的接口，并传参
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        //需要使用Intent.createChooser，这里我们直接复用。第二个参数并不会显示出来
        shareIntent = Intent.createChooser(shareIntent, "Here is the title of Select box");
        context.startActivity(shareIntent);
    }
}
