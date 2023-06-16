package bookclip.server.domain.member.mapper;

import bookclip.server.domain.member.dto.MemberDto;
import bookclip.server.domain.member.entity.Member;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post post);
}
