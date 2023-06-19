package bookclip.server.factory;

import bookclip.server.domain.member.dto.MemberDto;
import bookclip.server.domain.member.entity.Member;

import java.util.List;

public class MemberFactory {
    public static MemberDto.Post postMember() {
        MemberDto.Post post = MemberDto.Post.builder()
                .email("test@gmail.com")
                .password("12345678")
                .build();

        return post;
    }
    public static Member createMember() {
        Member member = Member.builder()
                .memberId(1L)
                .email("test@gmail.com")
                .password("12345678")
                .roles(List.of("USER"))
                .memberStatus(Member.MemberStatus.MEMBER_ACTIVE)
                .build();

        return member;
    }
}
