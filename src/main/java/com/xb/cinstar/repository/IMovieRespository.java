package com.xb.cinstar.repository;

import com.xb.cinstar.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMovieRespository extends JpaRepository<MovieModel,Long> {
}
