# Lecture Note

## **프로젝트 생성**

### **사전 준비**

- Java 11 설치
- IntelliJ 설치
- 스프링 부터 스타터에서 프로젝트 생성 (https://start.spring.io)
  ![image](https://user-images.githubusercontent.com/60606025/145352775-233f5c1f-3deb-40b0-a234-4d3056619917.png)

<br>

### **Gradle 설정**

[build.gradle]

```java
plugins {
	id 'org.springframework.boot' version '2.6.1'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'hello'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'org.springframework.boot:spring-boot-devtools'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
	useJUnitPlatform()
```

- `spring-boot-devtools`는 서버 재시작 없이 View 파일 변경을 적용하는 라이브러리

<br>

### **실행: Gradle --> Java**

- Gradle로 실행보다 자바로 직접 실행하는 것이 더 빠름
- `Preferences Build -> Execution -> Deployment -> Build Tools -> Gradle`
- `Build and run using: Gradle -> IntelliJ IDEA`
- `Run tests using: Gradle -> IntelliJ IDEA`
  ![image](https://user-images.githubusercontent.com/60606025/145353558-b9385d62-8edb-4c64-b37e-3d433d3e388e.png)

<br>

### **라이브러리**

- Gradle은 의존관계가 있는 라이브러리를 함께 다운로드 함

**_스프링 부트 라이브러리_**

- spring-boot-starter-web
  - spring-boot-starter-tomcat: 톰캣 (웹서버)
  - spring-webmvc: 스프링 웹 MVC
- spring-boot-starter-thymeleaf: 타임리프 템플릿 엔진(View)
- spring-boot-starter(공통): 스프링 부트 + 스프링 코어 + 로깅
  - spring-boot
    - spring-core
  - spring-boot-starter-logging
    - logback, slf4j

**_테스트 라이브러리_**

- spring-boot-starter-test
  - junit: 테스트 프레임워크
  - mockito: 목 라이브러리
  - assertj: 테스트 코드를 좀 더 편하게 작성하게 도와주는 라이브러리
  - spring-test: 스프링 통합 테스트 지원

<br>

### **View 환경설정**

**_Welcome(index) Page 생성_**

- `resources/static/index.html` 경로에 인덱스 페이지 생성

**_Thymeleaf 템플릿 엔진_**

- 공식 문서: https://www.thymeleaf.org/
- 스프링 공식 튜토리얼: https://spring.io/guides/gs/serving-web-content/
- 스프링부트 메뉴얼: https://docs.spring.io/spring-boot/docs/2.3.1.RELEASE/reference/html/spring-boot-features.html#boot-features-spring-mvc-template-engines

<br>

- Controller를 생성 후 템플릿을 추가하면 라우터처럼 동작
- Controller의 리턴해주는 이름과 템플릿명을 일치시켜야 동작
- 아래의 경우 http://localhost:8080/hello 에서 동작
- 단 경로는 템플릿명이 아닌 `@GetMapping('경로명')`에 나타난 이름대로 실행

![image](https://user-images.githubusercontent.com/60606025/145355318-b7417641-11ac-4910-bcf7-352380a267d4.png)

<br>

**동작 환경 그림**
![image](https://user-images.githubusercontent.com/60606025/145355629-9cfe70ca-5b07-43fc-856e-c4ccdb38edb1.png)

- 컨트롤러에서 리턴 값으로 문자를 반환하면 뷰 리졸버 `viewResolver`가 화면을 찾아서 처리
- 스프링 부트 템플릿엔진 기본 viewName 매핑
- `resources:templates/` +{ViewName}+ .`html`

<br>

### **빌드하고 실행하기**

**_콘솔로 이동_**

1. `./gradlew build`
2. `cd build/libs`
3. `java -jar hello-spring-0.0.1-SNAPSHOT.jar`
4. 실행 확인

**_윈도우 사용자를 위한 팁_**

- 콘솔로 이동 명령 프롬프트(cmd)로 이동
- `./gradlew` -> `gradlew.bat` 를 실행하면 됩니다.
- 명령 프롬프트에서 `gradlew.bat` 를 실행하려면 `gradlew` 하고 엔터를 치면 됩니다.
- `gradlew build`
- 폴더 목록 확인 `ls` -> `dir`

> intelliJ에서 터미널에서 WSL 연결이 되기 때문에 위의 방식 사용 가능했음
> <br>터미널을 Powershell로 사용했을 때도 같은 방법으로 빌드 가능했음

---

<br>

## **스프링 웹 개발 기초**

- 정적 컨텐츠
  - 서버에서 별도의 조작 없이 그대로 웹브라우저에게 전달하는 방식
- MVC와 템플릿 엔진
  - JSP, PHP와 같은 템플릿 엔진으로 서버에서 HTML을 동적으로 조작하여 브라우저에게 전달하는 방식
  - Model, View, Controller를 MVC라고 함
- API
  - HTML 등 파일 자체가 아니라 JSON 형태로 전달
  - 데이터만 내려주면 클라이언트가 화면을 그리거나 서버끼리 통신할 때 사용

<br>

### **정적 컨텐츠**

- `resource/static` 디렉토리에 파일 생성후 `localhost:8080/파일명.확장자`를 브라우저에 입력시 파일을 볼 수 있음
- 어떤 프로그래밍은 되지 않고 그대로 전달됨
- 우선 서버가 받으면 컨트롤러가 우선순위를 갖고 관련 컨트롤러를 찾음
- 컨트롤러 없을 시 정적파일을 찾음

<br>

**정적 컨텐츠 작동**
![image](https://user-images.githubusercontent.com/60606025/145364887-c31e0def-7e63-4cbd-821a-544a125bd6f4.png)

<br>

### **MVC와 템플릿 엔진**

- MVC: Model, View, Controller
- Model: 사용자가 편집하길 원하는 모든 데이터를 담음
- View: 화면과 관련된 코드
- Controller: 서버 단이나 비즈니스 로직과 관련된 코드

**_controller_**

```java
@Controller
public class HelloController {

  @GetMapping("hello-mvc")
  public String helloMvc(@RequestParam(value= "name") String name, Model model)
  {
    model.addAttribute("name", name);
    return "hello-template";
  }
}
```

<br>

**_view_**<br>
[resources/template/hello-template.html]

```html
<html xmlns:th="http://www.thymeleaf.org">
  <body>
    <p th:text="'hello ' + ${name}">hello! empty</p>
  </body>
</html>
```

- 실행은 다음과 같이 Mapping한 경로를 추가하고 뒤에 파라미터(쿼리스트링) 추가
  - http://localhost:8080/hello-mvc?name="Smith"

<br>

**MVC, 템플릿 엔진 작동**
![image](https://user-images.githubusercontent.com/60606025/145366589-cf73c27c-0df3-492a-85b0-00c4b86d5c47.png)

<br>

### **API**

- API 방식은 데이터 그 자체를 넘겨줌 (보통 JSON 사용)
  - MVC는 View단에서 템플릿 엔진을 통해 조작, 렌더링된 데이터를 브라우저에 넘겨줌
- `@ResponseBody`를 사용
  - HTTP의 Body에 데이터를 직접 넘겨줄 때 사용
  - `@ResponseBody` 를 사용하면 `viewResolver`를 사용하지 않음

<br>

**API 방식 작동 원리**
![image](https://user-images.githubusercontent.com/60606025/145418341-719afefe-9f6f-44e7-8465-ef0cb1b4c3ef.png)

- `@ResponseBody` 를 사용
  - HTTP의 BODY에 문자 내용을 직접 반환
  - `viewResolver` 대신에 `HttpMessageConverter` 가 동작
  - 기본 문자처리: `StringHttpMessageConverter`
  - 기본 객체처리: `MappingJackson2HttpMessageConverter`
  - byte 처리 등등 기타 여러 HttpMessageConverter가 기본으로 등록되어 있음

---

<br>

## **회원관리 예제 구현**

### **비즈니스 요구사항 정리**

- 데이터: 회원ID, 이름
- 기능: 회원 등록, 조회
- 아직 데이터 저장소가 선정되지 않음(가상의 시나리오)

<br>

**일반적인 웹 애플리케이션 계층구조**
![image](https://user-images.githubusercontent.com/60606025/145427983-1f945bf8-2251-49eb-afd3-b199b0061310.png)

- **컨트롤러**: 웹 MVC의 컨트롤러 역할
- **서비스**: 핵심 비즈니스 로직 구현, 예) 중복 가입 금지 등
- **리포지토리**: 데이터베이스에 접근, 도메인 객체를 DB에 저장하고 관리
- **도메인**: 비즈니스 도메인 객체, 예) 회원, 주문, 쿠폰 등 주로 데이터베이스에 저장하고 관리

<br>

**클래스 의존관계**
![image](https://user-images.githubusercontent.com/60606025/145428053-0c63315c-b0f0-4cbf-bba2-58371e662039.png)

- 아직 데이터 저장소가 선정되지 않아서, 우선 인터페이스로 구현 클래스를 변경할 수 있도록 설계
- 데이터 저장소는 RDB, NoSQL 등등 다양한 저장소를 고민중인 상황으로 가정
- 개발을 진행하기 위해서 초기 개발 단계에서는 구현체로 가벼운 메모리 기반의 데이터 저장소 사용

<br>

### **회원 도메인과 리포지토리 생성**

- 구현
  - 회원 객체
  - 회원 리포지토리 인터페이스
  - 회원 리포지토리 메모리 구현체

<br>

### **회원 리포지토리 테스트 케이스 작성**

- main 메서드나 컨트롤러를 통해 테스트를 하는 것은 시간이 많이 걸림
- 여러 테스트를 한번에 실행하기도 힘듦
- JAVA는 JUnit이라는 프레임워크로 테스트를 실행해서 문제를 해결

<br>

- 동일한 파일 내의 테스트 함수의 순서는 보장되지 않음
- 따라서 순서와 상관 없이 메서드 별로 따로 동작하게 설계해야 함
- **테스트 메서드 하나 끝나면 데이터를 clear**
  - `@AfterEach` 어노테이션을 사용해서 각 테스트 종료시 정해진 작업을 실행
  - 변수명이 겹치는 등 에러를 방지할 수 있음
- 테스트가 독립적으로 실행되는 것이 좋은 코드
  - 테스트 순서에 의존관계 있다면 좋은 코드가 아님
- **TDD**에서는 테스트코드 먼저 만들어서 틀에 맞게 코딩함

<br>

### **회원 서비스 개발**

- 코드를 짜다가 내부에 로직이 발생할 때에는 따로 메서드로 빼는 것이 좋음
  - DI 가능하게 변경
- ex) 같은 이름이 있는 중복 회원X --> 중복이면 에러를 발생하는 로직을 void 함수로 뺐음

- Service는 회원가입, 회원조회처럼 비즈니스로직에 가까운 기능
- Repositorys는 저장소에서 넣었다 뺐다 코드

<br>

### **회원 서비스 테스트**

- Class명에서 `ctrl + shift + t` 누르면 자동으로 테스트 클래스 생성
- 테스트 코드 짤 때 **_given, when, then_** 주석을 달고 어떤 것이 주어졌을 때 언제 어떤 결과가 나와야 하는지를 나눠서 코드짜는 것 추천

<br>

**_진행상황_**

- 지금까지 Member 객체와 Service, Repository 생성
- 서비스를 통해 맴버를 가입, Repository에 저장하고 불러오기
- test 코드

---

<br>

## **스프링 빈과 의존관계**

- 스프링 빈을 등록하는 2가지 방법
  - 컴포넌트 스캔과 자동 의존관계 설정
  - 자바 코드로 직접 스프링 빈 등록하기

<br>

### **컴포넌트 스캔과 자동 의존관계 설정**

<br>

**_진행계획_**

- 화면 붙이기 위해서 view, controller 생성
- controller가 service를 통해서 회원가입, 데이터 조회 등 실행
  - controller가 service에 **의존**한다. (의존관계)

<br>

**_Controller 만들기 (자동 의존관계 설정)_**

- Controller 어노테이션으로 생성 시 스프링이 컨트롤러 객체를 컨테이너에 저장하고 관리
- 이때 new 로 생성하면 다른 컨트롤러에서도 공유가 되기 때문에 아래 코드처럼 하기

```java
// 다음과 같이 생성자로 생성하면 MemberController 이외의 다른 컨트롤러들도 접근할 수 있기 때문에 스프링에 등록된 것을 가져다 써야 함
@Controller
public class MemberController {
    private final MemberService memberService = new MemberService()
}

// 위의 코드를 아래와 같이 생성자와 @Autowired를 이용한 코드로 수정
// 자동 의존관계 설정
@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
  }
}
```

- 생성자에 `@Autowired` 가 있으면 스프링이 연관된 객체를 스프링 컨테이너에서 찾아서 넣어줌
- 객체 의존관계를 외부에서 넣어주는 것: DI (Dependency Injection), 의존성 주입
- 이전 테스트에서는 개발자가 직접 주입
- 여기서는 `@Autowired`에 의해 스프링이 주입해줌

> [참고]<br>생성자에 `@Autowired`를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아서 주입 <br> 생성자가 1개만 있으면 `@Autowired`는 생략 가능

<br>

- 실행 시 오류발생
  - `Consider defining a bean of type 'hello.hellospring.service.MemberService' in your configuration`
- memberService가 **_스프링 빈_**으로 등록되어 있지 않기 때문
- 이전에 만든 클래스에 어노테이션 추가 --> 스프링 빈에 등록됨
  - MemberService에는 `@Service`
  - MemberRepository에는 `@Repository`

<br>

- **컴포넌트 스캔 원리**
  - `@Component` 애노테이션이 있으면 스프링 빈으로 자동 등록
  - `@Controller` 컨트롤러가 스프링 빈으로 자동 등록된 이유도 컴포넌트 스캔 때문
  - `@Component` 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록
    - `@Controller`
    - `@Service`
    - `@Repository`
- 자동 등록은 같은 패키지(hello.hellospring) 내부에서만 해줌

<br>

**_스프링 빈 등록 이미지_**
![image](https://user-images.githubusercontent.com/60606025/146586498-651f5f0e-780e-4669-9cc0-81c5e1ced48a.png)

- 스프링은 스프링 컨테이너에 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록(유일하게 하나만 등록해서 공유)
- 따라서 같은 스프링 빈이면 모두 같은 인스턴스
- 설정으로 싱글톤이 아니게 설정 가능
- 하지만 특별한 경우를 제외하면 대부분 싱글톤을 사용

<br>
<br>

### **자바 코드로 직접 스프링 빈 등록하기**

<br>

## 회원 관리 예제 - 웹 MVC 개발

### **회원 웹 기능 - 홈화면 추가**

<br>

### **회원 웹 기능 - 등록**

<br>

### **회원 웹 기능 - 조회**

<br>
