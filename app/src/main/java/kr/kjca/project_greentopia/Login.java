package kr.kjca.project_greentopia;


import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Login extends Fragment {

    //login
    EditText et_id;
    String str_id;
    EditText et_pw;
    String str_pw;
    Button bt_sign_in;
    Button bt_sign_up;
    TextView outPut;

    String str; //서버로 전송할 데이터
    String addr; //호스트 IP

    String response; //서버 응답

    Handler handler = new Handler();

    private ArrayList<Movie> movieList = new ArrayList();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.login, container, false);

        //Retrofit 객체 생성
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://jsonplaceholder.typicode.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();


            //Get
            final String[] monitoring_data = {""};
            RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
            retrofitAPI.getData("1").enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    if (response.isSuccessful()) {
                        List<Post> data = response.body();
                        Log.d("test", "성공");
                        Log.d("test", data.get(0).getTitle());
                        outPut.setText(data.get(0).getTitle());
                    }
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    Log.d("test", "실패");
                    t.printStackTrace();
                }
            });


            //Post
            HashMap<String, Object> input = new HashMap<>();
            input.put("userId", 1);
            input.put("title", "title!!");
            input.put("body", "body!!");
            retrofitAPI.postData(input).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {

                    if (response.isSuccessful()) {
                        Post data = response.body();
                        Log.d("test", "post 성공");
                        Log.d("test", data.getBody());
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    Log.d("test", "실패");
                }
            });

        } catch (Exception e) {
            Log.d("test", "url 오류");
        }


        //        일단 보류
//        //URL 설정
//        String url = "http://192.168.210.245:8080/app/monitoring?device_code=sm01";
//
//        // AsyncTask를 통해 HttpURLConnection 수행.
//        NetworkTask networkTask = new NetworkTask(url, null);
//        networkTask.execute();

        et_id = rootView.findViewById(R.id.et_id);
        et_pw = rootView.findViewById(R.id.et_pw);
        outPut = rootView.findViewById(R.id.output);

        bt_sign_in = rootView.findViewById(R.id.bt_sign_in);
        bt_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //로그인
                addr = "";
                str = "";

                if (et_id.getText().toString() != null && et_pw.getText().toString() != null) {

                } else {
                }
                //확인용
//                Toast.makeText(getContext(), str_id + ", " + str_pw, Toast.LENGTH_LONG).show();

//                //아이디 비밀번호 확인
//                if (et_id.getText().toString().length() == 0){
//                    Toast.makeText(getContext(), "id 를 입력하세요", Toast.LENGTH_SHORT).show();
//                    et_id.requestFocus();
//                    return;
//                }
//                if (et_pw.getText().toString().length() == 0){
//                    Toast.makeText(getContext(), "password 를 입력하세요", Toast.LENGTH_SHORT).show();
//                    et_pw.requestFocus();
//                    return;
//                }


                //db와 연결 후 id, password 확인
            }
        });

        bt_sign_up = rootView.findViewById(R.id.bt_sign_up);
        bt_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //회원가입
                jsonParsing(getJsonString());
                et_id.setText(movieList.get(0).getTitle());
                et_pw.setText(movieList.get(0).getCategory());
            }


        });


        return rootView;
    }


    public class NetworkTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public NetworkTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            response = result;
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
            outPut.setText(s);
        }
    }


    //JsonParsing
    private String getJsonString() {
        String json = "";

        try {
            InputStream is = getActivity().getAssets().open("Movies.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return json;
    }


    private void jsonParsing(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);

            JSONArray movieArray = jsonObject.getJSONArray("Movies");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieObject = movieArray.getJSONObject(i);

                Movie movie = new Movie();

                movie.setTitle(movieObject.getString("title"));
                movie.setGrade(movieObject.getString("grade"));
                movie.setCategory(movieObject.getString("category"));

                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

