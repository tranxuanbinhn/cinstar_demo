package com.xb.cinstar.repository;

import com.xb.cinstar.models.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMovieRespository extends JpaRepository<MovieModel,Long> {
    @Query(value = "select * from movie where title like %?1% or overview like %?1%", nativeQuery = true)
     List<MovieModel> findMovieByKeyword(String keyword);


    @Query(value = "select * from movie order by release_date desc limit  5;", nativeQuery = true)
    List<MovieModel> showFiveFilmNew();

    List<MovieModel> findAllByTheatersId(Long id);

    @Query(value = "select count(*) from movie where type_movie = 0", nativeQuery = true)
    Long countMovieShowing();

    @Query(value = "select count(*) from movie where type_movie = 1 ", nativeQuery = true)
   Long countMovieUpcoming();
}
