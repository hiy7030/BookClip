package bookclip.server.advice;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class LogAop {
    // 적용할 곳
    @Pointcut("execution(* bookclip.server.domain..*.*(..))")
    private void controller() {}


}
