package com.kosta.api2.service;

import com.kosta.api2.dto.BuisnessDto;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class BuisnessService {
    public String buisnessExist(String buisnessNum) throws Exception{
        // request url
       StringBuilder urlBuilder = new StringBuilder("http://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=");
        urlBuilder.append(URLEncoder.encode("8QO4Tk4YuUnLlJdy8iwXiyXivrscVpOjJmNGvn96wwtS2Kl+o6jiBPT20OsIYMZXYeqDsg6tfWyo95yxNUXyZQ==", "UTF-8"));
        //urlBuilder.append(URLEncoder.encode("?returnType=JSON"));

        // 데이터 변환
        String str = "{\n" +
                "    \"b_no\":[\""+buisnessNum+"\"]\n" +
                "}";
        System.out.println(str);

        /*JSONParser jsonParser = new JSONParser();
        JSONObject data = (JSONObject) jsonParser.parse(str);
        String reulstData = data.toString();*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");  // GET 메서드를 사용
        conn.setRequestProperty("Content-type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = str.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        BufferedReader br;
        int resultCode = conn.getResponseCode();
        System.out.println(resultCode);

        if (resultCode >= 200 && resultCode < 300) {  // 정상인 경우
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 에러
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while((line=br.readLine()) != null){
            responseBuilder.append(line);
        }
        br.close();
        conn.disconnect();

        /*JSONParser parser = new JSONParser();
        JSONObject mobj = (JSONObject) parser.parse(responseBuilder.toString()); // 가져온 데이터를 json 형태로 파싱 진행
        JSONObject data = (JSONObject) mobj .get("data"); // 가져온 데이터 중 LOCALDATA_020301을 가져옴

        System.out.println(data.toJSONString());*/

        return responseBuilder.toString();
    }
}
