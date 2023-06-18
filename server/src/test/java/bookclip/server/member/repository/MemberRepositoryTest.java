package bookclip.server.member.repository;

import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.repository.MemberRepository;
import bookclip.server.factory.MemberFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void delete() {
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원 저장 테스트")
    void saveMemberTest() {
        //given
        Member member = MemberFactory.createMember();
        //when
        Member savedMember = memberRepository.save(member);
        //then
        assertNotNull(savedMember);
        assertEquals(member.getMemberId(), savedMember.getMemberId());
        assertEquals(member.getEmail(), savedMember.getEmail());
    }

    @Test
    @DisplayName("회원 찾기 테스트 - email")
    void findByEmailMemberTest() {
        //given
        Member member = MemberFactory.createMember();
        memberRepository.save(member);
        //when
        Member findMember = memberRepository.findByEmail(member.getEmail()).get();
        //then
        assertNotNull(findMember);
        assertEquals(member.getMemberId(), findMember.getMemberId());
        assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    @DisplayName("회원 찾기 테스트 - id")
    void findByIdMemberTest() {
        //given
        Member member = MemberFactory.createMember();
        memberRepository.save(member);
        //when
        Member findMember = memberRepository.findById(member.getMemberId()).get();
        //then
        assertNotNull(findMember);
        assertEquals(member.getMemberId(), findMember.getMemberId());
        assertEquals(member.getEmail(), findMember.getEmail());
    }

    @Test
    @DisplayName("회원 삭제 테스트")
    void changeStatusMemberTest() {
        // given
        Member member = MemberFactory.createMember();
        memberRepository.save(member);
        // when
        memberRepository.delete(member);
        Optional<Member> findMember = memberRepository.findById(member.getMemberId());
        // then
        assertTrue(findMember.isEmpty());
    }
}
