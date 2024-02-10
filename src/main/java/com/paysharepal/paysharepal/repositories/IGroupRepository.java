package com.paysharepal.paysharepal.repositories;

import com.paysharepal.paysharepal.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface IGroupRepository extends JpaRepository<Group, UUID> {
}
