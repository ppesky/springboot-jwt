# springboot-jwt

springboot 2.7.x 기반의 jwt 인증처리 방식에 대한 코드

1. Interceptor 를 이용하는 방식
2. Servlet Filter 를 이용하는 방식

Bean 등록은 [WebConfig.java](src/main/java/io/ppesky/auth/config/WebConfig.java) 클래스 참조.

jwt Token 조정은 [JwtTokenProvider](src/main/java/io/ppesky/auth/config/util/JwtTokenProvider.java) 클래스 참조.

Interceptor 와 Servlet Filter 중 어떤 것을 선택할 것이냐는 당신의 선택이다.
