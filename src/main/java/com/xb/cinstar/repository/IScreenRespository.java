package com.xb.cinstar.repository;

import com.xb.cinstar.models.ScreenModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IScreenRespository extends JpaRepository<ScreenModel,Long> {

}
