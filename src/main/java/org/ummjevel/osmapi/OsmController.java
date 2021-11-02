package org.ummjevel.osmapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "top 5 is empty.");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find fid is " + Integer.toString(fid) + ".");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find osmid is " + Long.toString(osmid) + ".");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find name is " + name + ".");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find (latitude, longitude) is (" + Double.toString(lat) + ", " + Double.toString(lon) + ").");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find (latitude, longitude) is (" + lat + ", " + lon + ").");
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find (latitude, longitude) is (" + Double.toString(lat) + ", " + Double.toString(lon) + ").");
            }
        } catch(RestException e) {
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
                throw new RestException(HttpStatus.NOT_FOUND, "NOT_FOUND", "Can't find name is " + name + ".");
            }
        } catch(RestException e) {
            throw new RestException(e.getStatus(), e.getResult(), e.getMessage());
        } catch (Exception e) {
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, "ERROR", "server error");
        }
        return restReturn;
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