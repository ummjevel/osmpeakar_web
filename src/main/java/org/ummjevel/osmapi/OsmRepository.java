package org.ummjevel.osmapi;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OsmRepository {

    List<OsmVO> findTop5();

    List<OsmVO> findByFid(int fid);

    List<OsmVO> findByOsmid(Long osm_id);

    List<OsmVO> findByName(String name);

    List<OsmVO> findByLatLon(double lat, double lon);

    List<OsmVO> findByLatLonStr(String lat, String lon);

    List<OsmVO> findByNameForList(String name);
}
