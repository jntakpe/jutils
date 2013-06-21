package com.github.jntakpe.jutils.repository;

import com.github.jntakpe.jutils.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface définissant les méthodes permettant de manipuler l'entité {@link Item}
 *
 * @author jntakpe
 */
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    Item findByNom(String nom);
}
