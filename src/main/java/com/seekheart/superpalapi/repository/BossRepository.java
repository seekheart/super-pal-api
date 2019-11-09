package com.seekheart.superpalapi.repository;

import com.seekheart.superpalapi.model.domain.Boss;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BossRepository extends CrudRepository<Boss, UUID> {

}
