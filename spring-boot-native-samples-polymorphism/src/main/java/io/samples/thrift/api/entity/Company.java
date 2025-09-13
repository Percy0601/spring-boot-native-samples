package io.samples.thrift.api.entity;

import java.util.Objects;

import org.springframework.data.annotation.Id;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
public class Company extends AbstractEntity{
    @Id
    private final Long id;

    private final String name;

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        return Objects.equals(id, company.id) && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Company{" + "name='" + name + '\'' + '}';
    }
}
