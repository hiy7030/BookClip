package bookclip.server.factory;

import bookclip.server.domain.member.dto.MemberDto;
import bookclip.server.domain.member.entity.Member;

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
                .email("test@gmail.com")
                .password("12345678")
                .build();

        return member;
    }
}
