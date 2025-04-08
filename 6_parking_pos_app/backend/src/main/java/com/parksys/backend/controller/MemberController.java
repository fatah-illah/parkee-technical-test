package com.parksys.backend.controller;

import com.parksys.backend.model.Member;
import com.parksys.backend.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> registerMember(@Valid @RequestBody Member member) {
        Member registeredMember = memberService.registerMember(member);
        return ResponseEntity.ok(registeredMember);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Member> updateMember(
            @PathVariable Long id,
            @Valid @RequestBody Member member) {
        Member updatedMember = memberService.updateMember(id, member);
        return ResponseEntity.ok(updatedMember);
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<Member> renewMembership(
            @PathVariable Long id,
            @RequestParam int months) {
        Member renewedMember = memberService.renewMembership(id, months);
        return ResponseEntity.ok(renewedMember);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateMember(@PathVariable Long id) {
        memberService.deactivateMember(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<Member>> getAllMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.getAllMembers(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Member>> searchMembers(
            @RequestParam String name,
            Pageable pageable) {
        return ResponseEntity.ok(memberService.findMembersByNameContaining(name, pageable));
    }

    @GetMapping("/expired")
    public ResponseEntity<Page<Member>> getExpiredMembers(Pageable pageable) {
        return ResponseEntity.ok(memberService.findExpiredMembers(pageable));
    }

    @GetMapping("/plate/{plateNumber}")
    public ResponseEntity<Member> getMemberByPlate(@PathVariable String plateNumber) {
        return memberService.findMemberByVehiclePlate(plateNumber)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
