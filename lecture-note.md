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


***controller***
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

***view***<br>
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

<br>
<br>
<br>