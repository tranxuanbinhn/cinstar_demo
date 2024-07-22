package com.xb.cinstar.service;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IGenricService <T>{
    T save(T t);
    boolean delete(Long id);
    boolean update(Long id);
    List<T> findAll();
    List<T> findAll(Pageable pageable);

}
