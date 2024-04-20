package com.monaim.tournoi.repos;

import com.monaim.tournoi.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Site findSiteByNameIs(String name);
}
