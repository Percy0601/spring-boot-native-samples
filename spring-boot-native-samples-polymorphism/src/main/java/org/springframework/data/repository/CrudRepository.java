package org.springframework.data.repository;

import io.samples.thrift.api.entity.AbstractEntity;

public interface CrudRepository {
    <S extends AbstractEntity> S save(S entity);
}
