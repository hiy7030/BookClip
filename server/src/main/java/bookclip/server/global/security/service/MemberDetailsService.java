package bookclip.server.global.security.service;

import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.repository.MemberRepository;
import bookclip.server.global.exception.BusinessLogicException;
import bookclip.server.global.exception.ExceptionCode;
import bookclip.server.global.security.utils.MemberAuthorityUtils;
import bookclip.server.global.security.utils.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final MemberAuthorityUtils authorityUtils;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(
                () -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND)
        );

        return new MemberDetails(authorityUtils, member);
    }
}
