package com.cherrysoft.manics.repository.v2;

import com.cherrysoft.manics.model.v2.CategoryV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepositoryV2 extends JpaRepository<CategoryV2, Long> {
}
