package com.kosta.api.service;

import com.kosta.api.dto.AnimalClinic;
import com.kosta.api.dto.PageInfo;
import org.checkerframework.checker.units.qual.A;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class SeoulApiService {
    public List<AnimalClinic> animalClinicList(PageInfo pageInfo) throws Exception{
        int startIdx = (pageInfo.getCurPage()-1)*10+1;
        System.out.println(startIdx);

        // url을 만드는 과정 >> api를 요청할 때 작성해야 하는 순서 그대로 append 진행
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
        urlBuilder.append("/"+ URLEncoder.encode("45516a62786b686a343256574a434a", "UTF-8"));
        urlBuilder.append("/"+URLEncoder.encode("json", "UTF-8"));
        urlBuilder.append("/"+URLEncoder.encode("LOCALDATA_020301", "UTF-8"));
        urlBuilder.append("/"+URLEncoder.encode(startIdx+"", "UTF-8"));
        urlBuilder.append("/"+URLEncoder.encode(startIdx+10+"", "UTF-8"));

        // request >> 위에서 만든 url로 접속해서 get 메서드 형태로 읽어옴
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        // response
        BufferedReader br;
        int resultCode = conn.getResponseCode();
        System.out.println(resultCode);

        if(resultCode>=200 && resultCode<+300){ // 정상인 경우
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else{ //에러
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while((line=br.readLine()) != null){
            responseBuilder.append(line);
        }
        br.close();
        conn.disconnect();
        //System.out.println(responseBuilder.toString());

        List<AnimalClinic> acList = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject mobj = (JSONObject) parser.parse(responseBuilder.toString()); // 가져온 데이터를 json 형태로 파싱 진행
        JSONObject LOCALDATA_020301 = (JSONObject) mobj .get("LOCALDATA_020301"); // 가져온 데이터 중 LOCALDATA_020301을 가져옴
        Long list_total_count = (Long) LOCALDATA_020301.get("list_total_count"); // 위에서 가져온 데이터 중 토탈 개수를 가져옴
        JSONArray rowList =(JSONArray) LOCALDATA_020301.get("row"); // 위에서 가져온 데이터 중 리스트를 가져옴

        // 하나하나의 row를 AnimalClinic으로 만들어줌
        // 하나씩 가져올 때에는 get인덱스를 이용해 가져옴
        for(int i=0; i<rowList.size(); i++){
            JSONObject acJson = (JSONObject) rowList.get(i);
            String trdStateNm = (String)acJson.get("TRDSTATEMN");
            String siteTel = (String) acJson.get("SITETEL");
            String rdnwhlAddr = (String) acJson.get("RDNWHLADDR");
            String bplcnm = (String) acJson.get("BPLCNM");
            String x = (String) acJson.get("X");
            String y = (String) acJson.get("Y");

            // 가져온 각 row에 대한 정보를 list에 저장
            AnimalClinic animalClinic = new AnimalClinic(trdStateNm, siteTel, rdnwhlAddr, bplcnm, x, y);
            acList.add(animalClinic);
        }

        // 페이지 정보 설정
        int allPage = (int) Math.ceil(list_total_count.doubleValue()/10);
        int startPage = (pageInfo.getCurPage()-1)/10*10+1;
        int endPage = Math.min(startPage+10-1, allPage);

        pageInfo.setAllPage(allPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        if(pageInfo.getCurPage()>allPage) pageInfo.setCurPage(allPage);
        return acList;
    }
}
