package org.ummjevel.osmapi;

import org.springframework.beans.factory.annotation.Autowired;
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
    private List<OsmVO> getTop5() {
        List<OsmVO> listTop5 = osmRepository.findTop5();
        System.out.println(listTop5);
        return listTop5;
    }

    @GetMapping("/fid/{fid}")
    private List<OsmVO> GetListByFid(@PathVariable int fid) {
        List<OsmVO> listByFid = osmRepository.findByFid(fid);
        System.out.println(listByFid);
        return listByFid;
    }

    @GetMapping("/osmid/{osmid}")
    private List<OsmVO> GetListByOsmid(@PathVariable long osmid) {
        List<OsmVO> listByOsmid = osmRepository.findByOsmid(osmid);
        System.out.println(listByOsmid);
        return listByOsmid;
    }

    @GetMapping("/name/{name}")
    private List<OsmVO> GetListByOsmid(@PathVariable String name) {
        List<OsmVO> listByName= osmRepository.findByName(name);
        System.out.println(listByName);
        return listByName;
    }

    @GetMapping("/latlon")
    private List<OsmVO> GetListByLatLon(@RequestParam(name="lat") Double lat, @RequestParam(name="lon") Double lon) {
        List<OsmVO> listByLatLon = osmRepository.findByLatLon(lat, lon);
        System.out.println(listByLatLon);
        return listByLatLon;
    }

    @GetMapping("/latlonstr")
    private List<OsmVO> GetListByLatLonStr(@RequestParam Map<String, String> customQuery) {
        String lat = customQuery.getOrDefault("lat", "0.0");
        String lon = customQuery.getOrDefault("lon", "0.0");

        List<OsmVO> listByLatLonStr = osmRepository.findByLatLonStr(lat, lon);
        System.out.println(listByLatLonStr);
        return listByLatLonStr;
    }

    @GetMapping("/{lat}/{lon}")
    private List<OsmVO> GetListByLatLonStr(@PathVariable Double lat, @PathVariable Double lon) {
        List<OsmVO> listByLatLon = osmRepository.findByLatLon(lat, lon);
        System.out.println(listByLatLon);
        return listByLatLon;
    }

    @GetMapping("/{name}")
    private List<OsmVO> GetListByLatLonStr(@PathVariable String name) {
        List<OsmVO> listByName = osmRepository.findByNameForList(name);
        System.out.println(listByName);
        return listByName;
    }
}
