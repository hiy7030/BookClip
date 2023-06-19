package bookclip.server.member.service;

import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.repository.MemberRepository;
import bookclip.server.domain.member.service.MemberService;
import bookclip.server.factory.MemberFactory;
import bookclip.server.global.exception.BusinessLogicException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    @Mock
    private PasswordEncoder passwordEncoder;
    @BeforeEach
    void delete() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일 중복 확인 테스트") // exception 발생여부 확인
    void verifyExistEmailTest() {
        //given
        Member member = MemberFactory.createMember();

        given(memberRepository.findByEmail(Mockito.anyString())).willReturn(Optional.of(member));
        //when
        //then
        assertThrows(BusinessLogicException.class, ()->memberService.verifyExistEmail(member.getEmail()));
    }

    @Test
    @DisplayName("회원 찾기 테스트")
    void findMemberTest() {
        //given
        Member member = MemberFactory.createMember();

        given(memberRepository.findById(Mockito.anyLong())).willReturn(Optional.empty());

        assertThrows(BusinessLogicException.class, () -> memberService.findMember(member.getMemberId()));
    }

    @Test
    @DisplayName("회원 생성 테스트")
    void createMember() {
        //given
        Member member = MemberFactory.createMember();

        when(memberRepository.save(member)).thenReturn(member);
        //when
        Member createMember = memberService.createMember(member);
        //then
        verify(memberRepository).save(member);
        assertSame(member.getMemberId(), createMember.getMemberId());
        assertSame(member.getEmail(), createMember.getEmail());
        assertSame(member.getMemberStatus(), createMember.getMemberStatus());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    void deleteMemberTest() {
        //given
        Member member = MemberFactory.createMember();

        when(memberRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(member));
        //when
        memberService.deleteMember(member.getMemberId());
        //then
        verify(memberRepository).save(member);
        assertSame(Member.MemberStatus.MEMBER_QUIT, member.getMemberStatus());
    }

}
