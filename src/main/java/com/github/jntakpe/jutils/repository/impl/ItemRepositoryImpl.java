package com.github.jntakpe.jutils.repository.impl;

import com.github.jntakpe.fmk.exception.BusinessCode;
import com.github.jntakpe.fmk.exception.BusinessException;
import com.github.jntakpe.jutils.domain.Item;
import com.github.jntakpe.jutils.repository.ItemRepositoryCustom;
import com.github.jntakpe.jutils.util.constants.ItemLdapAttrs;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémentation de la récupération des données relatives à l'entité {@link Item} via le LDAP
 *
 * @author jntakpe
 */
public class ItemRepositoryImpl implements ItemRepositoryCustom {

    private static final String COLO2_DIR = "OU=Toulouse Colomiers 2,OU=FR,OU=Workstations";

    @Autowired
    private LdapTemplate ldapTemplate;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Item> findAllLdapItems() {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "computer")).and(new LikeFilter("name", "ITEM-*"));
        List<Item> items = ldapTemplate.search(COLO2_DIR, filter.encode(), new ItemAttributeMapper());
        return items;
    }

    /**
     * Inner classe permettant de mapper le résultat d'une requête ldap sur un attribut avec un {@link Item}
     */
    private class ItemAttributeMapper implements AttributesMapper {

        @Override
        public Object mapFromAttributes(Attributes attrs) {
            Map<ItemLdapAttrs, String> attrsMap = new HashMap<>();
            for (ItemLdapAttrs mandatoryAttr : ItemLdapAttrs.values()) {
                try {
                    attrsMap.put(mandatoryAttr, (String) attrs.get(mandatoryAttr.getAttr()).get());
                } catch (NamingException e) {
                    e.printStackTrace();
                    throw new BusinessException(BusinessCode.LDAP_ITEM_ATTR_MISSING, mandatoryAttr, attrs);
                }
            }
            Item item = new Item();
            item.setNom(attrsMap.get(ItemLdapAttrs.NOM));
            String fullDate = attrsMap.get(ItemLdapAttrs.DATE_CREATION);
            String dateItem = fullDate.substring(0, fullDate.indexOf("."));
            item.setCreation(DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(dateItem).toDate());
            item.setDescription(attrsMap.get(ItemLdapAttrs.DESCRIPTION));
            return item;
        }
    }
}
