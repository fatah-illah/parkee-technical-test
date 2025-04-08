package com.parksys.backend.repository;

import com.parksys.backend.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberName(String memberName);

    Page<Member> findByExpiryDateBefore(LocalDate date, Pageable pageable);

    Page<Member> findByMemberNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT m FROM Member m JOIN m.vehicles v WHERE v.plateNumber = :plateNumber")
    Optional<Member> findByVehiclePlateNumber(@Param("plateNumber") String plateNumber);
}
