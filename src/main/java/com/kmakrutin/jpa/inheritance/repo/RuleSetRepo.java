package com.kmakrutin.jpa.inheritance.repo;

import com.kmakrutin.jpa.inheritance.entity.RuleSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RuleSetRepo extends JpaRepository<RuleSet, Integer> {

    @Query(value = "SELECT new RuleSet(rs.id, rs.name) FROM RuleSet as rs")
    List<RuleSet> findAllSqueezed();
}
