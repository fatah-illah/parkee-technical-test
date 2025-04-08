package com.parksys.backend.service;

import com.parksys.backend.model.Member;
import com.parksys.backend.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member registerMember(Member member) {
        // Set default expiry date if not provided (1 year from now)
        if (member.getExpiryDate() == null) {
            member.setExpiryDate(LocalDate.now().plusYears(1));
        }

        if (member.getVehicles() != null) {
            member.getVehicles().forEach(vehicle -> vehicle.setMember(member));
        }

        return memberRepository.save(member);
    }

    public Member updateMember(Long memberId, Member updatedMember) {
        Member existingMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        // Update fields
        existingMember.setMemberName(updatedMember.getMemberName());
        existingMember.setParkingType(updatedMember.getParkingType());
        existingMember.setMemberUnit(updatedMember.getMemberUnit());

        if (updatedMember.getExpiryDate() != null) {
            existingMember.setExpiryDate(updatedMember.getExpiryDate());
        }

        existingMember.setActive(updatedMember.isActive());

        return memberRepository.save(existingMember);
    }

    public void deactivateMember(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        member.setActive(false);
        memberRepository.save(member);
    }

    public Member renewMembership(Long memberId, int extendMonths) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        // Set new expiry date from the current expiry date or today if expired
        LocalDate baseDate = member.getExpiryDate();
        if (baseDate.isBefore(LocalDate.now())) {
            baseDate = LocalDate.now();
        }

        member.setExpiryDate(baseDate.plusMonths(extendMonths));
        member.setActive(true);

        return memberRepository.save(member);
    }

    public Page<Member> getAllMembers(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }

    public Page<Member> findExpiredMembers(Pageable pageable) {
        return memberRepository.findByExpiryDateBefore(LocalDate.now(), pageable);
    }

    public Page<Member> findMembersByNameContaining(String name, Pageable pageable) {
        return memberRepository.findByMemberNameContainingIgnoreCase(name, pageable);
    }

    public Optional<Member> findMemberByVehiclePlate(String plateNumber) {
        return memberRepository.findByVehiclePlateNumber(plateNumber);
    }
}
