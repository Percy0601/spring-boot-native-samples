package io.samples.web;

import org.springframework.stereotype.Service;

import io.samples.web.entity.AbstractEntity;
import io.samples.web.entity.Author;
import io.samples.web.entity.Company;
import io.samples.web.repo.AuthorRepository;
import io.samples.web.repo.CompanyRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CommonService implements AuthorRepository, CompanyRepository {
    @Override
    public <S extends AbstractEntity> S save(S entity) {
        if(entity instanceof Author) {
            log.info("save author, {}", entity);
            return entity;
        }
        if(entity instanceof Company) {
            log.info("save company, {}", entity);
            return entity;
        }
        return entity;
    }
}
