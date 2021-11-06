package org.ummjevel.osmapi;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value="/api/osm", produces = MediaType.APPLICATION_JSON_VALUE)
public class OsmController {

    // @Autowired
    // DataSource dataSource;

    @Autowired
    private OsmRepository osmRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/info")
    public Map<String, Object> testInfoGET() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("group", "org.ummjevel");
        resultMap.put("artifact", "osmapi");
        return resultMap;
    }

    @GetMapping("/top5")
    private RestReturn getTop5() {
        RestReturn restReturn;
        try {
            List<OsmVO> listTop5 = osmRepository.findTop5();
            if (!listTop5.isEmpty()) {
                restReturn = new RestReturn("OK", listTop5, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "top 5 is empty.");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/fid/{fid}")
    private RestReturn GetListByFid(@PathVariable int fid) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByFid = osmRepository.findByFid(fid);
            System.out.println(listByFid.size());
            System.out.println(listByFid.isEmpty());
            if (!listByFid.isEmpty()) {

                restReturn = new RestReturn("OK", listByFid, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find fid is " + Integer.toString(fid) + ".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/osmid/{osmid}")
    private RestReturn GetListByOsmid(@PathVariable long osmid) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByOsmid = osmRepository.findByOsmid(osmid);
            if (!listByOsmid.isEmpty()) {
                restReturn = new RestReturn("OK", listByOsmid, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find osmid is " + Long.toString(osmid) + ".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/name/{name}")
    private RestReturn GetListByOsmid(@PathVariable String name) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByName= osmRepository.findByName(name);
            if (!listByName.isEmpty()) {
                restReturn = new RestReturn("OK", listByName, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find name is " + name + ".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/latlon")
    private RestReturn GetListByLatLon(@RequestParam(name="lat") Double lat, @RequestParam(name="lon") Double lon) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByLatLon = osmRepository.findByLatLon(lat, lon);
            if (!listByLatLon.isEmpty()) {
                restReturn = new RestReturn("OK", listByLatLon, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find (latitude, longitude) is (" + Double.toString(lat) + ", " + Double.toString(lon) + ").");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/latlonstr")
    private RestReturn GetListByLatLonStr(@RequestParam Map<String, String> customQuery) {
        String lat = customQuery.getOrDefault("lat", "0.0");
        String lon = customQuery.getOrDefault("lon", "0.0");
        RestReturn restReturn;
        try {
            List<OsmVO> listByLatLonStr = osmRepository.findByLatLonStr(lat, lon);
            if (!listByLatLonStr.isEmpty()) {
                restReturn = new RestReturn("OK", listByLatLonStr, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find (latitude, longitude) is (" + lat + ", " + lon + ").");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/{lat}/{lon}")
    private RestReturn GetListByLatLonStr(@PathVariable Double lat, @PathVariable Double lon) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByLatLon = osmRepository.findByLatLon(lat, lon);
            if (!listByLatLon.isEmpty()) {
                restReturn = new RestReturn("OK", listByLatLon, "");
            } else {
                System.out.println("no content...");
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find (latitude, longitude) is (" + Double.toString(lat) + ", " + Double.toString(lon) + ").");
            }
        } catch(RestException e) {
            System.out.println("this is exception...");
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @GetMapping("/{name}")
    private RestReturn GetListByLatLonStr(@PathVariable String name) {
        RestReturn restReturn;
        try {
            List<OsmVO> listByName = osmRepository.findByNameForList(name);
            if (!listByName.isEmpty()) {
                restReturn = new RestReturn("OK", listByName, "");
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find name is " + name + ".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    // call naver papago api
    // post, 시작번호/개수, name:en is not null 인 것만.
    // 일단 post 호출해서 select 해서 반환까지 하고
    // naver papago 로 전달
    // 받아온 것 풀어서 update 호출
    @PostMapping("/papago")
    private RestReturn TranslateListAndUpdate(@RequestParam int start, @RequestParam int end) {
        RestReturn restReturn;
/*
        Map<String,Object> tempMap = new HashMap<>();
        tempMap.put("start", requestData.get("start"));
        tempMap.put("end", requestData.get("end"));
        int start = (int) tempMap.getOrDefault("start", 0);
        int end = (int) tempMap.getOrDefault("end", 4000);
*/
        try {
            // get list
            List<OsmVO> listForTranslate = osmRepository.findByNumberForTranslate(start, end);
            if (!listForTranslate.isEmpty()) {
                restReturn = new RestReturn("OK", listForTranslate, "");

                // call papago
                for(OsmVO osmVo : listForTranslate) {
                    osmVo.setName_ko(SendToTranslate(osmVo.getName_en()));
                }
                // update list

                for(OsmVO osmVo : listForTranslate) {
                    int result = osmRepository.updateTranslateResult(osmVo.getFid(), osmVo.getName_ko());
                    if (result != 1) {
                        System.out.println("NOT UPDATED AFTER THIS..");
                        System.out.println(osmVo.toString());
                        break;
                    }
                }
            } else {
                throw new RestException(HttpStatus.CREATED, "CREATED", "Can't find list between " + start + " and " + end +".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    @PostMapping("/papagotrans")
    private RestReturn SendToPapago(@RequestParam String en) {
        // Seongsan Ilchulbong (Sunrise Peak)
        // Dusanbong
        RestReturn restReturn;
        StringBuffer response = new StringBuffer();
        String clientId = "2Op_N9nwXO_wxXsv8Rzf";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "24DSayE0gF";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(en, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            restReturn = new RestReturn("OK", response, "");
        } catch (Exception e) {
            System.out.println(e);
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
    }

    private String SendToTranslate(String en) {
        StringBuffer response = new StringBuffer();
        String resultText = "";
        String clientId = "2Op_N9nwXO_wxXsv8Rzf";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "24DSayE0gF";//애플리케이션 클라이언트 시크릿값";
        try {
            String text = URLEncoder.encode(en, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            // post request
            String postParams = "source=en&target=ko&text=" + text;
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParams);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            //System.out.println(response.toString());

            JSONObject objList = new JSONObject(response.toString());
            JSONObject objMessage = (JSONObject) objList.get("message");
            //System.out.println(objMessage.toString());
            JSONObject objResult = (JSONObject) objMessage.get("result");
            //System.out.println(objResult.toString());
            resultText = objResult.getString("translatedText");
            //System.out.println(resultText);
        } catch (Exception e) {
            System.out.println(e);
            // throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return resultText;
    }
}

class RestReturn {
    private String result;
    private Object body;
    private String message;

    public RestReturn(String result, Object body, String message) {
        this.result = result;
        this.body = body;
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}