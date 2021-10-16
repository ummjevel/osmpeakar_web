package org.ummjevel.osmapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCOsmRepository implements OsmRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OsmVO> findTop5() {
        return jdbcTemplate.query(
                "select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where \"natural\" = 'peak' and \"name:en\" is not null\n" +
                "order by fid desc limit 5;", (rs, rowNum) -> new OsmVO(
                          rs.getInt("fid")
                        , rs.getLong("osm_id")
                        , rs.getString("natural")
                        , rs.getString("name:en")
                        , rs.getDouble("lat")
                        , rs.getDouble("long")
                ));
    }

    @Override
    public List<OsmVO> findByFid(int fid) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where fid = ?", new Object[] {fid}, (rs, rowNum) -> new OsmVO(
                        rs.getInt("fid")
                        , rs.getLong("osm_id")
                        , rs.getString("natural")
                        , rs.getString("name:en")
                        , rs.getDouble("lat")
                        , rs.getDouble("long")
        ));
    }

    @Override
    public List<OsmVO> findByOsmid(Long osm_id) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where osm_id = ?", new Object[] {osm_id}, (rs, rowNum) -> new OsmVO(
                rs.getInt("fid")
                , rs.getLong("osm_id")
                , rs.getString("natural")
                , rs.getString("name:en")
                , rs.getDouble("lat")
                , rs.getDouble("long")
        ));
    }

    @Override
    public List<OsmVO> findByName(String name) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where \"name:en\" = ?", new Object[] {name}, (rs, rowNum) -> new OsmVO(
                rs.getInt("fid")
                , rs.getLong("osm_id")
                , rs.getString("natural")
                , rs.getString("name:en")
                , rs.getDouble("lat")
                , rs.getDouble("long")
        ));
    }

    @Override
    public List<OsmVO> findByLatLon(double lat, double lon) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where lat between ? - 0.1 and ? + 0.1 and long between ? - 0.1 and ? + 0.1 and \"name:en\" is not null"
                , new Object[] {lat, lat, lon, lon}
                , (rs, rowNum) -> new OsmVO(
                    rs.getInt("fid")
                    , rs.getLong("osm_id")
                    , rs.getString("natural")
                    , rs.getString("name:en")
                    , rs.getDouble("lat")
                    , rs.getDouble("long")
        ));
    }

    @Override
    public List<OsmVO> findByLatLonStr(String lat, String lon) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point \n" +
                "where lat between ? - 0.1 and ? + 0.1 and long between ? - 0.1 and ? + 0.1 and \"name:en\" is not null"
                , new Object[] {Double.parseDouble(lat), Double.parseDouble(lat), Double.parseDouble(lon), Double.parseDouble(lon)}
                , (rs, rowNum) -> new OsmVO(
                    rs.getInt("fid")
                    , rs.getLong("osm_id")
                    , rs.getString("natural")
                    , rs.getString("name:en")
                    , rs.getDouble("lat")
                    , rs.getDouble("long")
        ));
    }

    @Override
    public List<OsmVO> findByNameForList(String name) {
        return jdbcTemplate.query("select fid, osm_id, \"natural\", \"name:en\", lat, long from planet_osm_point " +
                        "where lat between (select lat from planet_osm_point where \"name:en\" = ?) - 0.1 " +
                        "and (select lat from planet_osm_point where \"name:en\" = ?) + 0.1" +
                        "and long between (select long from planet_osm_point where \"name:en\" = ?) - 0.1 " +
                        "and (select long from planet_osm_point where \"name:en\" = ?) + 0.1  and \"name:en\" is not null" +
                        "order by lat asc, long asc;"
                , new Object[] {name, name, name, name}
                , (rs, rowNum) -> new OsmVO(
                        rs.getInt("fid")
                        , rs.getLong("osm_id")
                        , rs.getString("natural")
                        , rs.getString("name:en")
                        , rs.getDouble("lat")
                        , rs.getDouble("long")
                ));
    }
}
