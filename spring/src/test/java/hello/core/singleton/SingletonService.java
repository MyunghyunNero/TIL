package hello.core.singleton;

public class SingletonService {
    //static영역에 객체를 1개만 생성한다.
    private static final SingletonService instance= new SingletonService();
    //public으로 열어서 이 객체가 필요하면 오직 이 static 메서드로만 조회하도록 허용
    public static SingletonService getInstance(){
        return instance;
    }
    //생성자를 private으로 선언에서 외부에서 new 키워드를 사용한 객체 생성 못하게 막는다.
    private SingletonService(){

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }
    public static void main(String[] args) {
        SingletonService singletonService=new SingletonService();
    }
}
