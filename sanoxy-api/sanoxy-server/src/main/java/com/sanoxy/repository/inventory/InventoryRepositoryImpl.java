
package com.sanoxy.repository.inventory;

import com.sanoxy.dao.inventory.Inventory;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public class InventoryRepositoryImpl implements InventoryRepositoryFulltext {
        @PersistenceContext
        private EntityManager entityManager;
        
        @Override
        public Collection<Inventory> searchWorkspaceInventoriesByKeyword(Integer wid, String keyword) {
                FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

                QueryBuilder queryBuilder = 
                    fullTextEntityManager.getSearchFactory()
                    .buildQueryBuilder().forEntity(Inventory.class).get();

                org.apache.lucene.search.Query query =
                    queryBuilder
                      .keyword()
                      .onFields("title", "brand", "keyword", "description")
                      .matching(keyword)
                      .createQuery();

                FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Inventory.class);
                
                @SuppressWarnings("unchecked")
                Collection<Inventory> results = jpaQuery.getResultList();
                
                // @Fixme: don't do this in memory.
                Collection<Inventory> filtered = new ArrayList();
                results.stream().filter((inventory) -> (inventory.getInventoryCategory().getWorkspace().getWid().equals(wid)))
                        .forEachOrdered((inventory) -> {
                                filtered.add(inventory);
                });
                return filtered;
        }
}
