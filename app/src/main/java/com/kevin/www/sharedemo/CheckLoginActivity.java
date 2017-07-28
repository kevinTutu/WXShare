package com.kevin.www.sharedemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;


public class CheckLoginActivity extends AppCompatActivity implements ShareActionSheet.OnShareSnsClickListener {

    private ShareEntity shareEntity;
    public IWXAPI api;
    public static final int WECHAT = 0;
    public static final int TIMELINE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_login);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);
        api.registerApp(Constant.APP_ID);
    }

    //设置分享窗体弹出位置
    public void openShareSheet(ShareEntity shareEntity) {
        this.shareEntity = shareEntity;
        int height = 0;
        View rootView = getWindow().getDecorView();
        ShareActionSheet sheet = null;
        sheet = new ShareActionSheet(this, shareEntity);
        sheet.setOnShareSnsClickListener(this);
        sheet.showAtLocation(rootView, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, height);
    }


    public void shareWechat() {

        switch (shareEntity.shareType) {
            case "text":
                shareText();
                break;
            case "image":
                shareImage();
                break;
            case "music":
                shareMusic();
                break;
            case "video":
                shareVideo();
                break;
            case "web":
                shareWeb();
                break;
            case "mini":
                shareMiniGroup();
                break;
        }


    }


    //文字类型分享示例
    public void shareText() {
        //初始化一个WXTextObject对象，填写分享的文本内容
        WXTextObject textObject = new WXTextObject();
        textObject.text = shareEntity.shareTitle;

        //初始化WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObject;
        msg.description = shareEntity.shareTitle;

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }


    //图片类型分享示例
    public void shareImage() {

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.share_image);

        //初始化WXImageObject和WXMediaMessage对象
        WXImageObject imageObject = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;

        //设置缩略图
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R
                .mipmap.share_image);
        msg.thumbData = comBitmapOption(thumb);

        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    //音乐类型分享示例
    public void shareMusic() {
        //初始化一个WXMusicObject对象，填写url
        WXMusicObject musicObject = new WXMusicObject();
        musicObject.musicUrl = "http://dl.stream.qqmusic.qq.com/C400004ec8yZ02bRYx.m4a?vkey=A9ADF927BBC385B14A2F7080AEBA92401D4C1AA2BDB97C98EB180DACC518DE45AB57B0EBCDD830450CB179A4B0A54B891943CF9568DD8970&amp;guid=4372628376&amp;uin=0&amp;fromtag=66";

        //初始化WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = musicObject;
        msg.title = "音乐标题";
        msg.description = "音乐描述";

        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R
                .mipmap.share_music);
        msg.thumbData = comBitmapOption(thumb); //音乐缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("music");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    //视频类型分享示例
    public void shareVideo() {
        WXVideoObject videoObject = new WXVideoObject();
        videoObject.videoUrl = "http://220.195.17.22/vhot2.qqvideo.tc.qq.com/A04SeaW04BDucZMdOAdHFB3P_OtbAYdKj863LczJHIr0/w05113rftzf.mp4?sdtfrom=v3010&amp;guid=fdf42f1c79790b5cd3549d250605449a&amp;vkey=003423BA51A8E8377615178B27B16DC5D899F487F2363FC6CECE0EC33F780F8D9C305797F9325A7C8E0BF6AD7C1AC875AC312C44DCD4FDBB968CD85B4ED41519FEE9B02FECA2F50AE4103A4EB6381B5929DBBE0ACF8DFA86&amp;platform=2";

        //初始化WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(videoObject);
        msg.title = "视频标题";
        msg.description = "视频描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R
                .mipmap.share_video);
        msg.thumbData = comBitmapOption(thumb); //视频缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    //网页类型分享示例
    public void shareWeb() {
        WXWebpageObject webpageObject = new WXWebpageObject();
        webpageObject.webpageUrl = "http://www.xiaohongchun.com.cn";

        //初始化WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage(webpageObject);
        msg.title = "网页标题";
        msg.description = "网页描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R
                .mipmap.ic_launcher);
        msg.thumbData = comBitmapOption(thumb); //视频缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    //小程序类型分享示例
    public void shareMiniGroup() {

        WXMiniProgramObject miniProgram = new WXMiniProgramObject();
        miniProgram.webpageUrl ="http://www.xiaohongchun.com.cn";
        miniProgram.userName = "gh_70d342c6d2cd";
        miniProgram.path = "pages/indexApp/indexApp";

        WXMediaMessage msg = new WXMediaMessage(miniProgram);
        msg.title = "小程序标题";
        msg.description = "小程序描述";
        Bitmap thumb = BitmapFactory.decodeResource(getResources(), R
                .mipmap.ic_launcher);
        msg.thumbData = comBitmapOption(thumb);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    @Override
    public void onSnsClick(int type) {
        switch (type) {
            case WECHAT:
                if (api.isWXAppInstalled()) {
                    shareWechat();
                } else {
                    Toast.makeText(this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
                return;

            case TIMELINE:
                if (api.isWXAppInstalled()) {
//                    shareTimeLine();
                } else {
                    Toast.makeText(this, "请先安装微信客户端", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //宽高等比缩放压缩
    public static byte[] comBitmapOption(Bitmap thumb) {
        int size = 1;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumb.compress(Bitmap.CompressFormat.PNG, 100, baos);

        ByteArrayInputStream isBm = null;
        BitmapFactory.Options newOpts = new BitmapFactory.Options();

        byte[] result = bmpToByteArray(thumb, true);
        while (result.length > 32 * 1024) {
            size = size + 1;

            isBm = new ByteArrayInputStream(baos.toByteArray());

            newOpts.inSampleSize = size;

            thumb = BitmapFactory.decodeStream(isBm, null, newOpts);
            thumb.compress(Bitmap.CompressFormat.PNG, 100, baos);
            result = bmpToByteArray(thumb, true);
        }

        try {
            if (null != baos) {
                baos.close();
            }
            if (null != isBm) {
                isBm.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
