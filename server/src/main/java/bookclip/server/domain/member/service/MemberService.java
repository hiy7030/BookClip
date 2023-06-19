package bookclip.server.domain.member.service;

import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.repository.MemberRepository;
import bookclip.server.global.exception.BusinessLogicException;
import bookclip.server.global.exception.ExceptionCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    // 회원 생성
    public Member createMember(Member member) {
        // 존재하는 회원인지 확인 -> 이메일 중복 검사
        verifyExistEmail(member.getEmail());
        // 비밀번호 암호화
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);

        return memberRepository.save(member);
    }
    // 회원 삭제 -> 상태 변경
    public void deleteMember(long memberId) {
        Member member = findMember(memberId);
        member.setMemberStatus(Member.MemberStatus.MEMBER_QUIT);

        memberRepository.save(member);
    }

    // 이메일로 회원 중복 확인
    public void verifyExistEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }
    // 회원 찾기
    public Member findMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        return optionalMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );
    }
}
