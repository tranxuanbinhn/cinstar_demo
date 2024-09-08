package com.xb.cinstar.repository;

import com.xb.cinstar.models.ScreenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IScreenRespository extends JpaRepository<ScreenModel,Long> {
List<ScreenModel> findAllByTheaterId(Long id);
}
