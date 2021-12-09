package hello.hellospring.domain;

public class Member {

    private Long id;  // 로그인 아이디가 아닌 시스템이 관리하는 아이디
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
