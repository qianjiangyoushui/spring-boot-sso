package com.test.web1.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Administrator on 2017/10/13.
 */

public class WeatherService {

    public String weather(String site,String fileString,String txtFile) {
        JSONObject dict1 = new JSONObject();
        dict1.put("001", "白天天气");
        dict1.put("002", "晚上天气");
        dict1.put("003", "最高温度");
        dict1.put("004", "最低温度");
        JSONObject dict2 = new JSONObject();
        dict2.put("00", "晴");
        dict2.put("01", "多云");
        dict2.put("02", "阴");
        dict2.put("03", "阵雨");
        dict2.put("04", "雷阵雨");
        dict2.put("05", "雷阵雨伴有冰雹");
        dict2.put("06", "雨夹雪");
        dict2.put("07", "小雨");
        dict2.put("08", "中雨");
        dict2.put("09", "大雨");
        dict2.put("10", "暴雨");
        dict2.put("11", "大暴雨");
        dict2.put("12", "特大暴雨");
        dict2.put("13", "阵雪");
        dict2.put("14", "小雪");
        dict2.put("15", "中雪");
        dict2.put("16", "大雪");
        dict2.put("17", "暴雪");
        dict2.put("18", "雾");
        dict2.put("19", "冻雨");
        dict2.put("20", "沙尘暴");
        dict2.put("21", "小到中雨");
        dict2.put("22", "中到大雨");
        dict2.put("23", "大到暴雨");
        dict2.put("24", "暴雨到大暴雨");
        dict2.put("25", "大暴雨到特大暴雨");
        dict2.put("26", "小到中雪");
        dict2.put("27", "中到大雪");
        dict2.put("28", "大到暴雪");
        dict2.put("29", "浮尘");
        dict2.put("30", "扬沙");
        dict2.put("31", "强沙尘暴");
        dict2.put("53", "霾");
        dict2.put("99", "无");
        dict2.put("32", "浓雾");
        dict2.put("49", "强浓雾");
        dict2.put("54", "中度霾");
        dict2.put("55", "重度霾");
        dict2.put("56", "严重霾");
        dict2.put("57", "大雾");
        dict2.put("58", "特强浓雾");
        dict2.put("301", "雨");
        dict2.put("302", "雪");
        //String site = "[{\"id\":\"101300906001\",\"name\":\"北市镇\"},{\"id\":\"101300906002\",\"name\":\"城隍镇\"},{\"id\":\"101300906003\",\"name\":\"大平山镇\"},{\"id\":\"101300906004\",\"name\":\"高峰镇\"},{\"id\":\"101300906005\",\"name\":\"葵阳镇\"},{\"id\":\"101300906006\",\"name\":\"龙安镇\"},{\"id\":\"101300906007\",\"name\":\"洛阳镇\"},{\"id\":\"101300906008\",\"name\":\"卖酒镇\"},{\"id\":\"101300906009\",\"name\":\"蒲塘镇\"},{\"id\":\"101300906010\",\"name\":\"沙塘镇\"},{\"id\":\"101300906011\",\"name\":\"山心镇\"},{\"id\":\"101300906012\",\"name\":\"石南镇\"},{\"id\":\"101300906013\",\"name\":\"小平山镇\"}]";
        JSONArray jsonArray = JSONArray.parseArray(site);
        JSONObject place = new JSONObject();
        StringBuffer url = new StringBuffer("http://api.weatherdt.com/common/?area=");
        StringBuffer result=new StringBuffer();
        StringBuffer uri = new StringBuffer();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject j = jsonArray.getJSONObject(i);
            place.put(j.getString("id"), j.getString("name"));
            if(i==jsonArray.size()-1){
                uri.append(j.getString("id"));
            }else{
                uri.append(j.getString("id")).append("|");
            }
        }
        try {
            url.append(uri.toString());
            url.append("&type=").append("forecast");
            url.append("&key=").append("5558664710ddbf02c08ffcac9fee25f5");
            System.out.println(url.toString());
            //String encodeUrl =  UriUtils.encode(url.toString(),"ISO8859-1");
            //result = HttpUtil.httpGet(url.toString(), HttpMethod.GET, null, null);
            File file = new File(txtFile);
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(file));
            String s = null;
            while((s = reader.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //String result = "{\"forecast\":{\"24h\":{\"101300906002\":{\"1001001\":[{\"003\":\"23\",\"004\":\"19\",\"001\":\"07\",\"002\":\"07\"},{\"003\":\"23\",\"004\":\"19\",\"001\":\"07\",\"002\":\"02\"},{\"003\":\"26\",\"004\":\"20\",\"001\":\"07\",\"002\":\"07\"},{\"003\":\"27\",\"004\":\"21\",\"001\":\"01\",\"002\":\"02\"}],\"000\":\"2017111408\"}}}}";

//        System.out.println(jsonObject31.toString());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        createSheetStyle(workbook, sheet);
        HSSFRow row0 = sheet.createRow(0);
        HSSFCell cell0;
        cell0 = row0.createCell(0);
        cell0 = row0.createCell(1);
        cell0.setCellValue("今天"+format.format(calendar.getTime()));
        cell0 = row0.createCell(4);
        calendar.add(Calendar.DATE,1);
        cell0.setCellValue("明天"+format.format(calendar.getTime()));
        cell0 = row0.createCell(7);
        calendar.add(Calendar.DATE,1);
        cell0.setCellValue("后天"+format.format(calendar.getTime()));
        cell0 = row0.createCell(10);
        calendar.add(Calendar.DATE,1);
        cell0.setCellValue("大后天"+format.format(calendar.getTime()));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 3));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 6));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 10, 12));
        HSSFRow row1 = sheet.createRow(1);
        HSSFCell cell1;
        cell1 = row1.createCell(0);
        cell1.setCellValue("地区");
        cell1 = row1.createCell(1);
        cell1.setCellValue("温度范围");
        cell1 = row1.createCell(2);
        cell1.setCellValue("白天天气");
        cell1 = row1.createCell(3);
        cell1.setCellValue("夜间天气");
        cell1 = row1.createCell(4);
        cell1.setCellValue("温度范围");
        cell1 = row1.createCell(5);
        cell1.setCellValue("白天天气");
        cell1 = row1.createCell(6);
        cell1.setCellValue("夜间天气");
        cell1 = row1.createCell(7);
        cell1.setCellValue("温度范围");
        cell1 = row1.createCell(8);
        cell1.setCellValue("白天天气");
        cell1 = row1.createCell(9);
        cell1.setCellValue("夜间天气");
        cell1 = row1.createCell(10);
        cell1.setCellValue("温度范围");
        cell1 = row1.createCell(11);
        cell1.setCellValue("白天天气");
        cell1 = row1.createCell(12);
        cell1.setCellValue("夜间天气");
        int rowIndex = 2;
        JSONObject jsonObject = JSONObject.parseObject(result.toString());
        JSONObject jsonObject11 = jsonObject.getJSONObject("forecast");
        JSONObject jsonObject1 = jsonObject11.getJSONObject("24h");
        String key1 ="";
        for(String earekey:jsonObject1.keySet()) {
            key1 = earekey;
            JSONObject jsonObject2 = jsonObject1.getJSONObject(key1);
            JSONArray jsonObject3 = jsonObject2.getJSONArray("1001001");
            String jsonObject31 = jsonObject2.getString("000");
            String name = "";
            name = place.getString(earekey);
            HSSFRow row = sheet.createRow(rowIndex);
            HSSFCell cell;
            cell = row.createCell(0);
            cell.setCellValue(name);
            int index = 0;
            for (int i = 0; i < jsonObject3.size(); i++) {
                JSONObject j = jsonObject3.getJSONObject(i);
                String w = dict2.getString(j.getString("001"));
                String b = dict2.getString(j.getString("002"));
                String h = j.getString("003");
                String l = j.getString("004");
                index = index + 1;
                cell = row.createCell(index);
                cell.setCellValue(l + "-" + h + "℃");
                index = index + 1;
                cell = row.createCell(index);
                cell.setCellValue(w);
                index = index + 1;
                cell = row.createCell(index);
                cell.setCellValue(b);
            }
            rowIndex++;
        }
        File file = new File(fileString);
        try {
            workbook.write(file);
        }catch (Exception e){

        }
        return "";
    }

    public static void main(String[] args){
        String xingye = "[{\"id\":\"101300906001\",\"name\":\"北市镇\"},{\"id\":\"101300906002\",\"name\":\"城隍镇\"},{\"id\":\"101300906003\",\"name\":\"大平山镇\"},{\"id\":\"101300906004\",\"name\":\"高峰镇\"},{\"id\":\"101300906005\",\"name\":\"葵阳镇\"},{\"id\":\"101300906006\",\"name\":\"龙安镇\"},{\"id\":\"101300906007\",\"name\":\"洛阳镇\"},{\"id\":\"101300906008\",\"name\":\"卖酒镇\"},{\"id\":\"101300906009\",\"name\":\"蒲塘镇\"},{\"id\":\"101300906010\",\"name\":\"沙塘镇\"},{\"id\":\"101300906011\",\"name\":\"山心镇\"},{\"id\":\"101300906012\",\"name\":\"石南镇\"},{\"id\":\"101300906013\",\"name\":\"小平山镇\"}]";
        String bobai="[{\"id\":\"101300902001\",\"name\":\"博白镇\"},{\"id\":\"101300902002\",\"name\":\"大坝镇\"},{\"id\":\"101300902003\",\"name\":\"大垌镇\"},{\"id\":\"101300902004\",\"name\":\"东平镇\"},{\"id\":\"101300902005\",\"name\":\"顿谷镇\"},{\"id\":\"101300902006\",\"name\":\"凤山镇\"},{\"id\":\"101300902007\",\"name\":\"黄凌镇\"},{\"id\":\"101300902008\",\"name\":\"江宁镇\"},{\"id\":\"101300902009\",\"name\":\"径口镇\"},{\"id\":\"101300902010\",\"name\":\"浪平乡\"},{\"id\":\"101300902011\",\"name\":\"菱角镇\"},{\"id\":\"101300902012\",\"name\":\"龙潭镇\"},{\"id\":\"101300902013\",\"name\":\"那卜镇\"},{\"id\":\"101300902014\",\"name\":\"那林镇\"},{\"id\":\"101300902015\",\"name\":\"宁潭镇\"},{\"id\":\"101300902016\",\"name\":\"三滩镇\"},{\"id\":\"101300902017\",\"name\":\"沙陂镇\"},{\"id\":\"101300902018\",\"name\":\"沙河镇\"},{\"id\":\"101300902019\",\"name\":\"双凤镇\"},{\"id\":\"101300902020\",\"name\":\"双旺镇\"},{\"id\":\"101300902021\",\"name\":\"水鸣镇\"},{\"id\":\"101300902022\",\"name\":\"松旺镇\"},{\"id\":\"101300902023\",\"name\":\"旺茂镇\"},{\"id\":\"101300902024\",\"name\":\"文地镇\"},{\"id\":\"101300902025\",\"name\":\"新田镇\"},{\"id\":\"101300902026\",\"name\":\"亚山镇\"},{\"id\":\"101300902027\",\"name\":\"英桥镇\"},{\"id\":\"101300902028\",\"name\":\"永安镇\"}]";
        String beiliu="[{\"id\":\"101300903001\",\"name\":\"白马镇\"},{\"id\":\"101300903002\",\"name\":\"北流镇\"},{\"id\":\"101300903003\",\"name\":\"城北街道\"},{\"id\":\"101300903004\",\"name\":\"城南街道\"},{\"id\":\"101300903005\",\"name\":\"大里镇\"},{\"id\":\"101300903006\",\"name\":\"大伦镇\"},{\"id\":\"101300903007\",\"name\":\"大坡外镇\"},{\"id\":\"101300903008\",\"name\":\"扶新镇\"},{\"id\":\"101300903009\",\"name\":\"陵城街道\"},{\"id\":\"101300903010\",\"name\":\"六靖镇\"},{\"id\":\"101300903011\",\"name\":\"六麻镇\"},{\"id\":\"101300903012\",\"name\":\"隆盛镇\"},{\"id\":\"101300903013\",\"name\":\"民安镇\"},{\"id\":\"101300903014\",\"name\":\"民乐镇\"},{\"id\":\"101300903015\",\"name\":\"平政镇\"},{\"id\":\"101300903016\",\"name\":\"清水口镇\"},{\"id\":\"101300903017\",\"name\":\"清湾镇\"},{\"id\":\"101300903018\",\"name\":\"沙垌镇\"},{\"id\":\"101300903019\",\"name\":\"山围镇\"},{\"id\":\"101300903020\",\"name\":\"石窝镇\"},{\"id\":\"101300903021\",\"name\":\"塘岸镇\"},{\"id\":\"101300903022\",\"name\":\"西垠镇\"},{\"id\":\"101300903023\",\"name\":\"新丰镇\"},{\"id\":\"101300903024\",\"name\":\"新荣镇\"},{\"id\":\"101300903025\",\"name\":\"新圩镇\"}]";
        String rongxian="[{\"id\":\"101300904001\",\"name\":\"浪水镇\"},{\"id\":\"101300904002\",\"name\":\"黎村镇\"},{\"id\":\"101300904003\",\"name\":\"灵山镇\"},{\"id\":\"101300904004\",\"name\":\"六王镇\"},{\"id\":\"101300904005\",\"name\":\"罗江镇\"},{\"id\":\"101300904006\",\"name\":\"容西镇\"},{\"id\":\"101300904007\",\"name\":\"容州镇\"},{\"id\":\"101300904008\",\"name\":\"十里镇\"},{\"id\":\"101300904009\",\"name\":\"石头镇\"},{\"id\":\"101300904010\",\"name\":\"石寨镇\"},{\"id\":\"101300904011\",\"name\":\"松山镇\"},{\"id\":\"101300904012\",\"name\":\"县底镇\"},{\"id\":\"101300904013\",\"name\":\"杨村镇\"},{\"id\":\"101300904014\",\"name\":\"杨梅镇\"},{\"id\":\"101300904015\",\"name\":\"自良镇\"}]";
        String luchuan="[{\"id\":\"101300905001\",\"name\":\"大桥镇\"},{\"id\":\"101300905002\",\"name\":\"古城镇\"},{\"id\":\"101300905003\",\"name\":\"横山镇\"},{\"id\":\"101300905004\",\"name\":\"良田镇\"},{\"id\":\"101300905005\",\"name\":\"马坡镇\"},{\"id\":\"101300905006\",\"name\":\"米场镇\"},{\"id\":\"101300905007\",\"name\":\"平乐镇\"},{\"id\":\"101300905008\",\"name\":\"清湖镇\"},{\"id\":\"101300905009\",\"name\":\"沙湖镇\"},{\"id\":\"101300905010\",\"name\":\"沙坡镇\"},{\"id\":\"101300905011\",\"name\":\"珊罗镇\"},{\"id\":\"101300905012\",\"name\":\"滩面镇\"},{\"id\":\"101300905013\",\"name\":\"温泉镇\"},{\"id\":\"101300905014\",\"name\":\"乌石镇\"}]";
        String yvlin="[{\"id\":\"101300901001\",\"name\":\"成均镇\"},{\"id\":\"101300901002\",\"name\":\"福绵镇\"},{\"id\":\"101300901003\",\"name\":\"沙田镇\"},{\"id\":\"101300901004\",\"name\":\"沙田镇\"},{\"id\":\"101300901005\",\"name\":\"石和镇\"},{\"id\":\"101300901006\",\"name\":\"新桥镇\"},{\"id\":\"101300901007\",\"name\":\"樟木镇\"},{\"id\":\"101300901008\",\"name\":\"城北街道\"},{\"id\":\"101300901009\",\"name\":\"城西街道\"},{\"id\":\"101300901010\",\"name\":\"大塘镇\"},{\"id\":\"101300901011\",\"name\":\"茂林镇\"},{\"id\":\"101300901012\",\"name\":\"名山街道\"},{\"id\":\"101300901013\",\"name\":\"南江街道\"},{\"id\":\"101300901014\",\"name\":\"仁东镇\"},{\"id\":\"101300901015\",\"name\":\"仁厚镇\"},{\"id\":\"101300901016\",\"name\":\"玉城街道\"}]";
        String qu="[{\"id\":\"101300901\",\"name\":\"玉林\"},{\"id\":\"101300902\",\"name\":\"博白\"},{\"id\":\"101300903\",\"name\":\"北流\"},{\"id\":\"101300904\",\"name\":\"容县\"},{\"id\":\"101300905\",\"name\":\"陆川\"},{\"id\":\"101300906\",\"name\":\"兴业\"},{\"id\":\"101300907\",\"name\":\"玉州\"},{\"id\":\"101300908\",\"name\":\"福绵\"}]";
        String file_xingye="E:/weather_xingye.xls";

        String file_bobai="E:/weather_bobai.xls";
        String file_bobai_1="E:/weather_bobai_1.xls";

        String file_beiliu="E:/weather_beiliu.xls";
        String file_beiliu_1="E:/weather_beiliu_1.xls";

        String file_rongxian="E:/weather_rongxian.xls";
        String file_luchuan="E:/weather_luchuan.xls";

        String file_yvlin="E:/weather_yvlin.xls";
        String file_qu="E:/weather_qu.xls";
//        String file_yvlin_1="E:/weather_yvlin_1.xls";

        String txt_xingye="E:/weather_xingye.txt";
        String txt_bobai="E:/weather_bobai.txt";
        String txt_bobai_1="E:/weather_bobai_1.txt";
        String txt_beiliu="E:/weather_beiliu.txt";
        String txt_beiliu_1="E:/weather_beiliu_1.txt";
        String txt_rongxian="E:/weather_rongxian.txt";
        String txt_luchuan="E:/weather_luchuan.txt";
        String txt_yvlin="E:/weather_yvlin.txt";
        String txt_qu="E:/weather_qu.txt";
//        String txt_yvlin_1="E:/weather_yvlin_1.txt";

        WeatherService weatherService = new WeatherService();
        weatherService.weather(luchuan,file_luchuan,txt_luchuan);
    }
    private void createSheetStyle(HSSFWorkbook _workbook, HSSFSheet _sheet) {
        // 设置表字体
        HSSFFont font10 = _workbook.createFont();
        font10.setFontHeightInPoints((short) 12);
        font10.setFontName("黑体");
        // 设置表样
//        _sheet.textAlignCenter = getCellStyle(_workbook, font10, HSSFCellStyle.ALIGN_CENTER);
//        this.textAlignLeft = getCellStyle(_workbook, font10, HSSFCellStyle.ALIGN_LEFT);
        // 设置列宽
        _sheet.setColumnWidth(0, 2000);
        _sheet.setColumnWidth(1, 2000);
        _sheet.setColumnWidth(2, 2000);
        _sheet.setColumnWidth(3, 2000);
    }
}
