package com.example.a50067.huanhuan.Model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.a50067.huanhuan.Entity.User;
import com.example.a50067.huanhuan.Model.ModelListener.OnGetURLListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by 50067 on 2018/5/9.
 */

public class UserLoginModel implements IUserLoginModel {
    URL url;
    StringBuilder response;
    Document document;
    String __VIEWSTATE=null;
    String __EVENTVALIDATION=null;
    String Usercookie;
    String data;
    Message msg;

    Document document3;
    String checkCodeURL;


    //test
    HttpURLConnection connection;

    @Override
        public String getCheckCodeURL(OnGetURLListener onGetURLListener) {
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("http://jwc.jxnu.edu.cn/Portal/LoginAccount.aspx?t=account")
                .build();

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Response response=client.newCall(request).execute();

                    BufferedReader reader=null;
                    StringBuilder response2;
                    InputStream in=response.body().byteStream();
                    reader=new BufferedReader(new InputStreamReader(in));
                    response2=new StringBuilder();
                    String line;
                    while ((line=reader.readLine())!=null){
                        response2.append(line);
                    }
                    document3= Jsoup.parse(response2.toString());


                    Element checkcode=document3.getElementById("_ctl0_cphContent_imgPasscode");
                    checkCodeURL="http://jwc.jxnu.edu.cn/Portal/"+checkcode.attr("src");
                    Log.d(TAG, "getCheckCodeURL: checkcodeurl"+checkCodeURL);
                    if (checkcode.equals(null)){
                        onGetURLListener.getURLFailed();
                    }else {
                        onGetURLListener.getURLSuccess(checkCodeURL);
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();*/
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onGetURLListener.getURLFailed();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                BufferedReader reader=null;
                StringBuilder response2;
                InputStream in=response.body().byteStream();
                reader=new BufferedReader(new InputStreamReader(in));
                response2=new StringBuilder();
                String line;
                while ((line=reader.readLine())!=null){
                    response2.append(line);
                }
                document3= Jsoup.parse(response2.toString());


                Element checkcode=document3.getElementById("_ctl0_cphContent_imgPasscode");
                checkCodeURL="http://jwc.jxnu.edu.cn/Portal/"+checkcode.attr("src");
                Log.d(TAG, "getCheckCodeURL: checkcodeurl"+checkCodeURL);
                onGetURLListener.getURLSuccess(checkCodeURL);

                Headers headers=response.headers();
                List<String> cookies=headers.values("Set-Cookie");
                String session=cookies.get(0);
                Usercookie=session.substring(0,session.indexOf(";"));
                Log.d(TAG, "onResponse: session : "+session);
                Log.d(TAG, "onResponse: sessionid :"+Usercookie);

                document= Jsoup.parse(response2.toString());
                Element _v=document.getElementById("__VIEWSTATE");
                __VIEWSTATE=_v.attr("value");
                Log.d(TAG, "onResponse: __VIEWSTATE    "+__VIEWSTATE);
                Element _e=document.getElementById("__EVENTVALIDATION");
                __EVENTVALIDATION=_e.attr("value");
//                        data="__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUJNjA5MzAzMTcwD2QWAmYPZBYCAgMPZBYGZg8WAh4EVGV4dAUpMjAxOeW5tDPmnIgxMuaXpSDmmJ%2FmnJ%2FkuowmbmJzcDvmpI3moJHoioJkAgIPZBYCAgEPFgIfAAUS6LSm5Y%2B35a%2BG56CB55m75b2VZAIDD2QWBAIBDw8WAh4HVmlzaWJsZWdkFgoCAQ8QZGQWAWZkAgMPZBYCAgEPFgIfAAUG5a2m5Y%2B3ZAIFDw8WAh8BaGQWAgIBDxAPFgYeDURhdGFUZXh0RmllbGQFDOWNleS9jeWQjeensB4ORGF0YVZhbHVlRmllbGQFCeWNleS9jeWPtx4LXyFEYXRhQm91bmRnZBAVGxLotKLmlL%2Fph5Hono3lrabpmaIS5Z%2BO5biC5bu66K6%2B5a2m6ZmiEuWIneetieaVmeiCsuWtpumZohXlnLDnkIbkuI7njq%2FlooPlrabpmaIS5YWs6LS55biI6IyD55Sf6ZmiEuWbvemZheaVmeiCsuWtpumZohLljJblrabljJblt6XlrabpmaIb6K6h566X5py65L%2Bh5oGv5bel56iL5a2m6ZmiEue7p%2Be7reaVmeiCsuWtpumZogzmlZnogrLlrabpmaIe5Yab5LqL5pWZ56CU6YOo77yI5q2m6KOF6YOo77yJEuenkeWtpuaKgOacr%2BWtpumZohvljoblj7LmlofljJbkuI7ml4XmuLjlrabpmaIV6ams5YWL5oCd5Li75LmJ5a2m6ZmiDOe%2Bjuacr%2BWtpumZogzova%2Fku7blrabpmaIJ5ZWG5a2m6ZmiEueUn%2BWRveenkeWtpuWtpumZohvmlbDlrabkuI7kv6Hmga%2Fnp5HlrablrabpmaIM5L2T6IKy5a2m6ZmiD%2BWkluWbveivreWtpumZognmloflrabpmaIb54mp55CG5LiO6YCa5L%2Bh55S15a2Q5a2m6ZmiDOW%2Fg%2BeQhuWtpumZohXmlrDpl7vkuI7kvKDmkq3lrabpmaIM6Z%2Bz5LmQ5a2m6ZmiDOaUv%2BazleWtpumZohUbCDY4MDAwICAgCDYzMDAwICAgCDgyMDAwICAgCDQ4MDAwICAgCDU3MDAwICAgCDY5MDAwICAgCDYxMDAwICAgCDYyMDAwICAgCDQ1MCAgICAgCDUwMDAwICAgCDM3MDAwICAgCDgxMDAwICAgCDU4MDAwICAgCDQ2MDAwICAgCDY1MDAwICAgCDY3MDAwICAgCDU0MDAwICAgCDY2MDAwICAgCDU1MDAwICAgCDU2MDAwICAgCDUyMDAwICAgCDUxMDAwICAgCDYwMDAwICAgCDQ5MDAwICAgCDY0MDAwICAgCDUzMDAwICAgCDU5MDAwICAgFCsDG2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZxYBZmQCCw8PFgIeCEltYWdlVXJsBSRjaGVja2NvZGUuYXNweD9jb2RlPUJBREY2Q0IxQjU0NDM1RDZkZAINDxYCHwAFEEJBREY2Q0IxQjU0NDM1RDZkAgMPDxYCHwFoZGRkNKJZY%2FRrUPPlyxWnEwAZRqG8EGcC8yJC%2FopEvkVZ1q4%3D&__EVENTVALIDATION=%2FwEWCgLsibKTBwKFsp%2FHCgL%2B44ewDwKiwZ6GAgKWuv6KDwLj3Z22BgL6up5fAv%2FWopgDAqbyykwC68zH9gYNwPnfWdsSb0HKrryc56CIpiItaNlVOKq7pap8D7scdA%3D%3D&_ctl0%3AcphContent%3AddlUserType=Student&_ctl0%3AcphContent%3AtxtUserNum=201526706019&_ctl0%3AcphContent%3AtxtPassword=6235548wxy&_ctl0%3AcphContent%3AtxtCheckCode=QNFT&_ctl0%3AcphContent%3AbtnLogin=%E7%99%BB%E5%BD%95";
                Log.d(TAG, "onResponse:  __EVENTVALIDATION    "+__EVENTVALIDATION);
            }
        });

        return checkCodeURL;
    }

/*
    @Override
    public String getCheckCodeURL(OnGetURLListener onGetURLListener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                connection=
            }
        }).start();
        return null;
    }
*/

    @Override
    public void login(final String account, final String password,final String checkcode, final Handler handler) {
        /*
         * 开启线程访问教务在线
         * 登陆
         * */

        /*
         * 首先判断账号密码是否正确，错误则handler返回错误
         * 再判断是否登录成功，再用handler返回结果
         * */

        new Thread(new Runnable() {
            @Override
            public void run() {
                msg=new Message();
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{

                    /*
                     * GET:获取要填的表单数据
                     * */

                    url= new URL("http://jwc.jxnu.edu.cn/Portal/LoginAccount.aspx?t=account");
                    connection =(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setRequestProperty("Connection","keep-alive"); //长连接
                    connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 ");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.connect();

                    /*
                     * 获取cookie
                     * */

                    String cookiesKey="Set-Cookie";
                    String cookieVal = "";
                    String key = null;

                   /* Usercookie=null;
                    for (int i = 1; (key = connection.getHeaderFieldKey(i)) != null; i++) {
                        if (key.equalsIgnoreCase("set-cookie")) {
                            cookieVal = connection.getHeaderField(i);
                            cookieVal = cookieVal.substring(0, cookieVal.indexOf(";"));
                            Usercookie = Usercookie + cookieVal + ";";

                        }
                    }
                    String[] array=Usercookie.split("null");
                    Usercookie=array[1];
                    Log.d(TAG, "run: cookie： "+Usercookie);
                    Log.d(TAG, "run:cookie  a1"+array[1]);*/

                    /*
                     * 读取输入流
                     * */
                    Log.d(TAG, "run: connection.getResponseCode()1111111 +"+connection.getResponseCode());
                    if(connection.getResponseCode()==200){
                        //读取输入流数据
                        InputStream in=connection.getInputStream();
                        reader=new BufferedReader(new InputStreamReader(in));
                        response=new StringBuilder();
                        String line;
                        while ((line=reader.readLine())!=null){
                            response.append(line);
                        }
                        document= Jsoup.parse(response.toString());
                       /* Log.d(TAG, "run: documents___________________"+document.outerHtml());
                        Element _v=document.getElementById("__VIEWSTATE");
                        __VIEWSTATE=_v.attr("value");

                        Element _e=document.getElementById("__EVENTVALIDATION");
                        __EVENTVALIDATION=_e.attr("value");*/
//                        data="__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=%2FwEPDwUJNjA5MzAzMTcwD2QWAmYPZBYCAgMPZBYGZg8WAh4EVGV4dAUpMjAxOeW5tDPmnIgxMuaXpSDmmJ%2FmnJ%2FkuowmbmJzcDvmpI3moJHoioJkAgIPZBYCAgEPFgIfAAUS6LSm5Y%2B35a%2BG56CB55m75b2VZAIDD2QWBAIBDw8WAh4HVmlzaWJsZWdkFgoCAQ8QZGQWAWZkAgMPZBYCAgEPFgIfAAUG5a2m5Y%2B3ZAIFDw8WAh8BaGQWAgIBDxAPFgYeDURhdGFUZXh0RmllbGQFDOWNleS9jeWQjeensB4ORGF0YVZhbHVlRmllbGQFCeWNleS9jeWPtx4LXyFEYXRhQm91bmRnZBAVGxLotKLmlL%2Fph5Hono3lrabpmaIS5Z%2BO5biC5bu66K6%2B5a2m6ZmiEuWIneetieaVmeiCsuWtpumZohXlnLDnkIbkuI7njq%2FlooPlrabpmaIS5YWs6LS55biI6IyD55Sf6ZmiEuWbvemZheaVmeiCsuWtpumZohLljJblrabljJblt6XlrabpmaIb6K6h566X5py65L%2Bh5oGv5bel56iL5a2m6ZmiEue7p%2Be7reaVmeiCsuWtpumZogzmlZnogrLlrabpmaIe5Yab5LqL5pWZ56CU6YOo77yI5q2m6KOF6YOo77yJEuenkeWtpuaKgOacr%2BWtpumZohvljoblj7LmlofljJbkuI7ml4XmuLjlrabpmaIV6ams5YWL5oCd5Li75LmJ5a2m6ZmiDOe%2Bjuacr%2BWtpumZogzova%2Fku7blrabpmaIJ5ZWG5a2m6ZmiEueUn%2BWRveenkeWtpuWtpumZohvmlbDlrabkuI7kv6Hmga%2Fnp5HlrablrabpmaIM5L2T6IKy5a2m6ZmiD%2BWkluWbveivreWtpumZognmloflrabpmaIb54mp55CG5LiO6YCa5L%2Bh55S15a2Q5a2m6ZmiDOW%2Fg%2BeQhuWtpumZohXmlrDpl7vkuI7kvKDmkq3lrabpmaIM6Z%2Bz5LmQ5a2m6ZmiDOaUv%2BazleWtpumZohUbCDY4MDAwICAgCDYzMDAwICAgCDgyMDAwICAgCDQ4MDAwICAgCDU3MDAwICAgCDY5MDAwICAgCDYxMDAwICAgCDYyMDAwICAgCDQ1MCAgICAgCDUwMDAwICAgCDM3MDAwICAgCDgxMDAwICAgCDU4MDAwICAgCDQ2MDAwICAgCDY1MDAwICAgCDY3MDAwICAgCDU0MDAwICAgCDY2MDAwICAgCDU1MDAwICAgCDU2MDAwICAgCDUyMDAwICAgCDUxMDAwICAgCDYwMDAwICAgCDQ5MDAwICAgCDY0MDAwICAgCDUzMDAwICAgCDU5MDAwICAgFCsDG2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZ2dnZxYBZmQCCw8PFgIeCEltYWdlVXJsBSRjaGVja2NvZGUuYXNweD9jb2RlPUJBREY2Q0IxQjU0NDM1RDZkZAINDxYCHwAFEEJBREY2Q0IxQjU0NDM1RDZkAgMPDxYCHwFoZGRkNKJZY%2FRrUPPlyxWnEwAZRqG8EGcC8yJC%2FopEvkVZ1q4%3D&__EVENTVALIDATION=%2FwEWCgLsibKTBwKFsp%2FHCgL%2B44ewDwKiwZ6GAgKWuv6KDwLj3Z22BgL6up5fAv%2FWopgDAqbyykwC68zH9gYNwPnfWdsSb0HKrryc56CIpiItaNlVOKq7pap8D7scdA%3D%3D&_ctl0%3AcphContent%3AddlUserType=Student&_ctl0%3AcphContent%3AtxtUserNum=201526706019&_ctl0%3AcphContent%3AtxtPassword=6235548wxy&_ctl0%3AcphContent%3AtxtCheckCode=QNFT&_ctl0%3AcphContent%3AbtnLogin=%E7%99%BB%E5%BD%95";
                        data = "__EVENTTARGET=&__EVENTARGUMENT=&__LASTFOCUS=&__VIEWSTATE=" + URLEncoder.encode(__VIEWSTATE, "utf-8") + "&__EVENTVALIDATION="
                                + URLEncoder.encode(__EVENTVALIDATION, "utf-8") +"&"
                                +URLEncoder.encode("_ctl0:cphContent:ddlUserType", "utf-8")+"=Student"+"&"
                                +URLEncoder.encode("_ctl0:cphContent:txtUserNum", "utf-8")+"="+account + "&"
                                +URLEncoder.encode("_ctl0:cphContent:txtPassword","utf-8")+"=" + password +"&"
                                +URLEncoder.encode("_ctl0:cphContent:txtCheckCode","utf-8")+"="+checkcode+"&"
                                + URLEncoder.encode("_ctl0:cphContent:btnLogin","utf-8")+"="+URLEncoder.encode("登录","utf-8");
                        Log.d(TAG, "run: data+"+data.toString());
                        Log.d(TAG, "run: data length "+data.length());
/*
                    POST:进行登录请求
                    */


                       /* URL url2=connection.getURL();
                        Log.d(TAG, "run: !!!!!!url2"+url2.toString());*/
                        connection =(HttpURLConnection)url.openConnection();
                        connection.setRequestMethod("POST");     //
                        connection.setConnectTimeout(80000);
                        connection.setReadTimeout(80000);
                        connection.setRequestProperty("Cookie",Usercookie);
                        connection.setRequestProperty("Connection", "keep-alive");
                        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                        connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36 SE 2.X MetaSr 1.0");
                       connection.setRequestProperty("Content-Length",data.length()+"");
//                        connection.setRequestProperty("Upgrade-Insecure-Requests","1");
//                        connection.setRequestProperty("Origin","http://jwc.jxnu.edu.cn");
//                        connection.setRequestProperty("Referer","http://jwc.jxnu.edu.cn/Portal/Index.aspx");
//                       connection.setRequestProperty("Accept-Encoding", "identity");////////
                        connection.setDoOutput(true); // 发送POST请求必须设置允许输出
                        connection.setDoInput(true); // 发送POST请求必须设置允许输入
                        connection.connect();

                        DataOutputStream outputStream=new DataOutputStream(connection.getOutputStream());
                        outputStream.writeBytes(data);
                        outputStream.flush();
                        outputStream.close();

                        Log.d(TAG, "run: connection.getResponseCode()222222 +"+connection.getResponseCode());

                        if(connection.getResponseCode()==200){
                            Log.d(TAG, "run:connection.getResponseCode()     "+connection.getResponseCode());
                            //读取输入流数据
                            InputStream in2=connection.getInputStream();
                            BufferedReader reader2=new BufferedReader(new InputStreamReader(in2));
                            Document document2;
                            StringBuilder response2;
                            response2=new StringBuilder();
                            String line2;
                            while ((line2=reader2.readLine())!=null){
                                response2.append(line2);
                            }
                            document2= Jsoup.parse(response2.toString());

                            Log.d(TAG, "run: document content "+document2.toString());
                            /*
                             * 判断登陆是否成功
                             * */
                            Log.d(TAG, "run: document2 l +"+document2.location());
                            Log.d(TAG, "run: document2 t "+document2.title());
                            Log.d(TAG, "run: document2 n"+document2.nodeName());
                            Log.d(TAG, "run: document2 h"+document2.head());

                            if(!document2.title().equals("江西师范大学教务在线")){
                                Log.d(TAG, "run: hello is empty");
                                //向presenter返回登录失败信息
                                msg.what=111;
                                handler.sendMessage(msg);
                            }else {
//                                Element a=document2.getElementById("_ctl0_cphContent_IndexLogin1_imgPhoto");
//                                // Bitmap pic=a.get
//                                String hello=a.text();
//                                Log.d(TAG, "run: hello success"+hello.toString());
                                //登陆成功，像presenter返回登录成功信息
                                msg.what=000;
                                handler.sendMessage(msg);
                            }

                        }else {
                            Log.d(TAG, "run: 登录失败 code"+connection.getResponseCode());
                            //向presenter返回登录失败信息
                            msg.what=111;
                            handler.sendMessage(msg);
                        }
                    }


                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    connection.disconnect();
                }
            }
        }).start();
    }
}
