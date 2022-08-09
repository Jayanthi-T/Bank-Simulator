package com.batonsystems.banksimulator.repository;

import com.batonsystems.banksimulator.entity.Branch;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//This BranchRepository class holds the database operations related to Branch entity.
public interface BranchRepository extends JpaRepository<Branch,Long> {
    public Branch findByBranchCode(String branchCode);
    public boolean existsByBranchCode(String branchCode);
    public List<Branch> getBranchesByBranchCity(String branchCity);
    public void deleteByBranchCode(String branchCode);
}
