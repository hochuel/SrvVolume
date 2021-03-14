package com.srv.crawler;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CrawlerMain {


    public List getDayVolumeData(String stockCode, int num){
        List resultList = new ArrayList();
        try {

            //stockCode = String.format("%06d", Integer.parseInt(stockCode));
            //System.out.println("stockCode ::" + stockCode);
            // 1. 수집 대상 URL
            String URL = "https://finance.naver.com/item/sise_day.nhn?page="+num+"&code="+stockCode;

            // 2. Connection 생성
            Connection conn = Jsoup.connect(URL);

            // 3. HTML 파싱.
            Document html = conn.get(); // conn.post();

            Elements elements = html.select(".type2 tr td");



            Map dataMap = null;
            int loop = 0;
            for (int i = 0; i < elements.size(); i++) {

                Element element = elements.get(i);
                if(element != null && !element.text().equals("")) {
                    String data = "";
                    if (element.html().indexOf("img") > 0) {
                        String str1 = element.html();
                        String str2 = element.text();
                        String temp = (str1.indexOf("하락") > 0) ? "-" : "+";

                        data = temp + str2;

                    } else {
                        data = element.text();
                    }

                    if(loop == 0){
                        dataMap = new HashMap();
                    }

                    if(loop <= 6) {
                        data = data.replaceAll(",", "");
                        if(loop == 0) dataMap.put("날짜", data.replaceAll("\\.", ""));
                        else if(loop == 1) dataMap.put("종가", data == null || data.equals("")?0:data);
                        else if(loop == 2) dataMap.put("전일대비", data == null || data.equals("")?0:data);
                        else if(loop == 3) dataMap.put("시가", data == null || data.equals("")?0:data);
                        else if(loop == 4) dataMap.put("고가", data == null || data.equals("")?0:data);
                        else if(loop == 5) dataMap.put("저가", data == null || data.equals("")?0:data);
                        else if(loop == 6) dataMap.put("거래량", data == null || data.equals("")?0:data);


                        loop ++;

                        if(loop == 7){
                            loop = 0;
                            resultList.add(dataMap);
                        }

                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public List getVolumeData(String stockCode){
        List resultList = new ArrayList();
        try {

            //stockCode = String.format("%06d", Integer.parseInt(stockCode));
            //System.out.println("stockCode ::" + stockCode);
            // 1. 수집 대상 URL
            String URL = "https://open.shinhaninvest.com/goodicyber/mk/1206.jsp?code="+stockCode;

            // 2. Connection 생성
            Connection conn = Jsoup.connect(URL);

            // 3. HTML 파싱.
            Document html = conn.get(); // conn.post();

            Elements elements = html.select(".content1 tr td");

            Map dataMap = null;
            int loop = 0;
            for (int i = 0; i < elements.size(); i++) {
                if (i > 11) {
                    Element element = elements.get(i);

                    int num = i - 12;
                    int temp1 = num / 11;

                    //System.out.println(num + ":" + temp1 + ":" + element.text());
                    //System.out.println(temp1 + ":" + loop +":" + i +":" + i % 12);
                    if(loop == 0){
                        dataMap = new HashMap();
                    }
                    if(loop <= 10) {
                        String data = element.text().replaceAll(",", "");
                        if(loop == 0) dataMap.put("날짜", data.replaceAll("/", ""));
                        else if(loop == 1) dataMap.put("개인", data == null || data.equals("")?0:data);
                        else if(loop == 2) dataMap.put("외국인계", data == null || data.equals("")?0:data);
                        else if(loop == 3) dataMap.put("기관계", data == null || data.equals("")?0:data);
                        else if(loop == 4) dataMap.put("증권", data);
                        else if(loop == 5) dataMap.put("투신", data);
                        else if(loop == 6) dataMap.put("은행", data);
                        else if(loop == 7) dataMap.put("종금", data);
                        else if(loop == 8) dataMap.put("보험", data);
                        else if(loop == 9) dataMap.put("기금", data);
                        else if(loop == 10)dataMap.put("기타", data);

                        loop ++;

                        if(loop == 11){
                            loop = 0;
                            resultList.add(dataMap);
                        }

                    }
                }
            }


/*
            if(resultList != null && resultList.size() > 0){
                for(int i = 0; i < resultList.size(); i++){
                    Map data = (Map)resultList.get(i);
                    System.out.println("data ::" + i +":" + data);
                }
            }
*/
        } catch (IOException e) {
            log.error("error stockCode :" + stockCode);
            e.printStackTrace();
        }

        return resultList;
    }

    public List getKospiData(String type, int page){
        List resultList = new ArrayList();

        String URL = "";
        try {

            if(type.equals("kospi")) {
                URL = "https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI&page=" + page;
            }else if(type.equals("kosdaq")) {
                URL = "https://finance.naver.com/sise/sise_index_day.nhn?code=KOSDAQ&page=" + page;
            }
            // 2. Connection 생성
            Connection conn = Jsoup.connect(URL);

            // 3. HTML 파싱.
            Document html = conn.get(); // conn.post();

            Elements elements = html.select(".type_1 tr td");

            Map dataMap = null;
            int loop = 0;
            for (int i = 0; i < elements.size(); i++) {

                Element element = elements.get(i);
                if(element != null && !element.text().equals("")) {
                    String data = element.text();

                    if(loop == 0){
                        dataMap = new HashMap();
                    }

                    if(loop <= 6) {
                        data = data.replaceAll(",", "");
                        if(loop == 0) dataMap.put("날짜", data.replaceAll("\\.", ""));
                        else if(loop == 1) dataMap.put("종가", data == null || data.equals("")?0:data);
                        loop ++;

                        if(loop == 6){
                            loop = 0;
                            resultList.add(dataMap);
                        }

                    }
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

/*
    public static void main(String[] args){
        CrawlerMain crawlerMain = new CrawlerMain();
        List resultList = crawlerMain.getKospiData("kosdaq",1);

        if(resultList != null && resultList.size() > 0){
            for(int i = 0; i < resultList.size(); i++){
                Map data = (Map)resultList.get(i);
                System.out.println("data ::" + i +":" + data);
            }
        }
    }
*/
}
