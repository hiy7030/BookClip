package bookclip.server.member.controller;

import bookclip.server.domain.member.controller.MemberController;
import bookclip.server.domain.member.dto.MemberDto;
import bookclip.server.domain.member.entity.Member;
import bookclip.server.domain.member.mapper.MemberMapper;
import bookclip.server.domain.member.service.MemberService;
import bookclip.server.factory.MemberFactory;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(MemberController.class)
//@MockBean(JpaMetamodelMappingContext.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private MemberMapper mapper;

    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 테스트")
    void createMemberTest() throws Exception {
        // given
        MemberDto.Post postMember = MemberFactory.postMember();
        Member expected = MemberFactory.createMember();
        String content = gson.toJson(postMember);

        given(memberService.createMember(Mockito.any(Member.class))).willReturn(expected);
        // when
        ResultActions actions = mockMvc.perform(post("/signup")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));
        // then
        actions.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("회원 탈퇴 테스트")
    @WithMockUser(roles = "USER")
    void deleteMemberTest() throws Exception {
        //given
        Member expected = MemberFactory.createMember();

        doNothing().when(memberService).deleteMember(expected.getMemberId());
        //when
        ResultActions actions = mockMvc.perform(delete("/members/{member-id}", expected.getMemberId())
                .accept(MediaType.APPLICATION_JSON));
        //then
        actions.andExpect(status().isNoContent());
    }
}
