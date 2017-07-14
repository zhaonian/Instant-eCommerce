/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author davis
 */
@Repository
@Transactional
public class InventoryRepositoryImpl implements InventoryRepositoryFulltext {
        @PersistenceContext
        private EntityManager entityManager;
        
        @Override
        public List<Inventory> searchInventoryByKeyword(String keyword) {
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

                QueryBuilder queryBuilder = 
                    fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder().forEntity(Inventory.class).get();

                org.apache.lucene.search.Query query =
                    queryBuilder
                      .keyword()
                      .onFields("title", "brand", "description")
                      .matching(keyword)
                      .createQuery();

                FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Inventory.class);

                @SuppressWarnings("unchecked")
                List<Inventory> results = jpaQuery.getResultList();
                return results;
        }
}
