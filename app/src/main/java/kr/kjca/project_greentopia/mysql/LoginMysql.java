package kr.kjca.project_greentopia.mysql;

import android.os.Handler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import kr.kjca.project_greentopia.Login;

public class LoginMysql extends Thread {
    public static boolean active=false;

    Handler mHandler;
    String userId=null;
    String userPw=null;
    String url=null;
    String login_url="http://192.168.0.237/study/android/login/sql/chkid.php?id=";      //server IP

    public LoginMysql(String userId, String userPw) {
        mHandler=new Handler();
        this.userId = userId;
        this.userPw = userPw;
        url=login_url+userId;
    }

    @Override
    public void run() {
        super.run();
        if(active){
            StringBuilder jsonHtml = new StringBuilder();
            try {
                URL phpUrl = new URL(url);

                HttpURLConnection conn = (HttpURLConnection)phpUrl.openConnection();

                if ( conn != null ) {
                    conn.setConnectTimeout(10000);
                    conn.setUseCaches(false);

                    if ( conn.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                        while ( true ) {
                            String line = br.readLine();
                            if ( line == null )
                                break;
                            jsonHtml.append(line + "\n");
                        }
                        br.close();
                    }
                    conn.disconnect();
                }
            } catch ( Exception e ) {
                e.printStackTrace();
            }
            show(jsonHtml.toString());
        }



    }

    void show(final String result){
        mHandler.post(new Runnable(){

            @Override
            public void run() {

                try {

                    JSONObject jObject = new JSONObject(result);

//                    String getpw =jObject.get("PW").toString();
//                    String getname=jObject.get("Name").toString();
//                    String getage =jObject.get("Age").toString();
//                    String getphone=jObject.get("Phone").toString();
//                    String getmail =jObject.get("Email").toString();
//                    String getaddress=jObject.get("Address").toString();
//
//                    Login.result_login(getpw, userPw, getname,getage,getphone,getmail,getaddress);
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Login.result_login("false", "false", "false", "false", "false", "false", "false");
                }


            }
        });

    }

}
