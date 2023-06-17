package bookclip.server.domain.member.controller;

import bookclip.server.domain.member.dto.MemberDto;
import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.mapper.MemberMapper;
import bookclip.server.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Validated
public class MemberController {
    private final MemberMapper mapper;
    private final MemberService memberService;

    // 회원 생성
    @PostMapping("/signup")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto) {

        memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));

        return new ResponseEntity(HttpStatus.CREATED);
    }
    // 회원 탈퇴
    @DeleteMapping("/members/{member-id}")
    public ResponseEntity deleteMember(@Positive @PathVariable("member-id")Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
