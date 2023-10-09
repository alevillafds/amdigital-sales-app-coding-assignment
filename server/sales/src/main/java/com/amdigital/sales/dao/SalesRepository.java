package com.amdigital.sales.dao;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.amdigital.sales.model.Sale;

/**
 * Repository to manage {@link com.amdigital.sales.model.Sale}.
 *
 * @author Alejandra Villa Fernandes (alejandra.villafernandes@gmail.com)
 *
 */
@Repository
public interface SalesRepository extends CassandraRepository<Sale, String>{
}
