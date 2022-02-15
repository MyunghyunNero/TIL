package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SingleTonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI컨테이너")
    void pureContainer(){
        AppConfig appConfig= new AppConfig();
        //1.조회 : 호출할 떄마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();  //hello.core.member.MemberServiceImpl@7530ad9c
        //2.조회 : 호출할 떄마다 객체를 생성
        MemberService memberService2 = appConfig.memberService();  //hello.core.member.MemberServiceImpl@58a9760d
        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1+"memberService2 = " + memberService2);
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest(){
        SingletonService singletonService1 = SingletonService.getInstance();
        SingletonService singletonService2 = SingletonService.getInstance();

        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);
        assertThat(singletonService1).isSameAs(singletonService2);
        //Same 인스턴스
        //equal
    }


    @Test
    @DisplayName("스프링 빈을 적용한 객체 사용")
    void springcontainer(){
       // AppConfig appConfig= new AppConfig();
        ApplicationContext ac =new AnnotationConfigApplicationContext(AppConfig.class);
        //1.조회 : 호출할 떄마다 객체를 생성
        MemberService memberService1 = ac.getBean("memberService",MemberService.class);  //hello.core.member.MemberServiceImpl@54e22bdd
        //2.조회 : 호출할 떄마다 객체를 생성
        MemberService memberService2 = ac.getBean("memberService",MemberService.class);  //hello.core.member.MemberServiceImpl@54e22bdd
        //참조값이 다른 것을 확인
        System.out.println("memberService1 = " + memberService1+"memberService2 = " + memberService2);
        assertThat(memberService1).isSameAs(memberService2);
    }

}
