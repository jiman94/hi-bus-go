package com.younghun.hibusgo.mapper;

import com.younghun.hibusgo.domain.Region;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RegionMapper {

  Region findById(long id);
  
  boolean existsById(long id);

  boolean existsByName(String name);

  List<Region> searchByName(String name);
  
  List<Region> searchTotal();

  void addRegion(Region region);

  void updateRegion(Region region);

  void deleteRegion(long id);

}
