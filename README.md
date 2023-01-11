# Gon's LOG
나만의 블로그 웹 사이트 개발 프로젝트

# 목차
- [개발 환경](#개발-환경)
- [사용 기술](#사용-기술)
    * [백엔드](#백엔드)
    * [프론트엔드](#프론트엔드)
    * [기타 주요 라이브러리](#기타-주요-라이브러리)
- [핵심 키워드](#핵심-키워드)
- [E-R 다이어그램](#e-r-다이어그램)
- [프로젝트 목적](#프로젝트-목적)
    * [블로그 프로젝트를 기획한 이유?](#블로그-프로젝트를-기획한-이유?)
- [핵심 기능](#핵심-기능)
    * [소셜 로그인](#소셜-로그인)
    * [로그 추적기](#로그-추적기)
    * [summernote editor](#summernote)
    * [기본적인 게시물 CRUD](#기본적인-게시물-CRUD)
    * [댓글과 대댓글 구현](#댓글과-대댓글-구현)
    * [계층형 카테고리](#계층형-카테고리)
    * [카테고리 편집기 구현](#카테고리-편집기-구현)
    * [캐싱](#캐싱)
    * [CI/CD 무중단 배포](#CI/CD-무중단-배포)
- [프로젝트를 통해 느낀점](#프로젝트를-통해-느낀점)


## 개발 환경
- IntelliJ
- Postman
- GitHub
- SourceTree
- Mysql Workbench
- Visual Studio Code

## 사용 기술
### 백엔드
#### 주요 프레임워크 / 라이브러리
- Java 11 openjdk
- SpringBoot 2.7.5
- SpringBoot Security
- Spring Data JPA
- EhCache
- QuesyDsl

#### Build tool
- Gradle

#### Database
- Mysql

#### Infra
- AWS EC2
- AWS S3
- Github Actions
- AWS CodeDeploy
- AWS Route53

### 프론트엔드
- Javascript
- Html/Css
- Thymeleaf
- Bootstrap 5

### 기타 주요 라이브러리
- Lombok
- Github-api
- summernote

## 핵심 키워드

- 스프링 부트, 스프링 시큐리티를 사용하여 웹 애플리케이션 생애 주기 기획부터 배포 유지 보수까지 전과정 개발과 운영 경험 확보
- AWS / 리눅스 기반 CI/CD 무중단 배포 인프라 구축
- JPA, Hibernate를 사용한 도메인 설계
- MVC 프레임워크 기반 백엔드 서버 구축
- 헥사고날 아키텍처

## E-R 다이어그램
![블로그프로젝트erd](https://user-images.githubusercontent.com/105222802/211770103-ee4ad761-d02e-4e59-adb7-8ba0272d13ad.png)

## 프로젝트 목적

### 블로그 프로젝트를 기획한 이유? 

최소한의 구현능력을 학습한 이후 머릿속에 다양한 프로젝트 기획안들이 떠올랐습니다. 

욕심 같아서는 머릿속의 기획들을 모두 만들어서 서비스해 보고 싶었지만 아직 주니어 개발자라고 하기에도 많이 부족했기 때문에

우선은 **준비된 쥬니어 웹 개발자가 되자** 라는 눈앞의 목표를 최우선으로 삼고, 프로젝트안들을 다시 검토해 보았습니다.

 '**준비된 쥬니어 웹 개발자**가 되기위해 나는 무엇을 **준비**해야할까?' 

결론은 간단하게도 개발에 대한 끊임없는 공부와 열정.

그리고 그것들을 문서화하여 증명하고 공유하고 자기 PR을 하는 것이 저를 개발자 지망생이 아닌 개발자로 만들어줄 가장 빠르고 정확한 길이라고 생각했습니다. 

웹 개발자로서 웹 애플리케이션의 전 과정에대해 최소 한번쯤은 숙지를 해야한다고 판단했고,

개발 연습을 위한 개발, 포트폴리오를 위한 프로젝트로 남는것이 아닌 프로덕트 단계를 넘어, 

앞으로 제 프로그래밍 공부와 개발 기록, 그리고 유지보수를 같이할 블로그를 만들어 보기로 결정했습니다.


## 핵심 기능


### CI 툴 트래비스에서 github 액션으로 스택 마이그레이션

트래비스가 org에서 com으로 변경된뒤 기존 무료 정책에서 구독형 유료 모델로 변경되었으며

개인적으로 개인프로젝트에서 서버도 아니고 ci 툴을 사용하기 위해 비용을 지불하는것은 불필요하다고 판단했고, 코드 관리가 github에서 이루어지는 만큼

ci역시 같은 github내에서 진행되는것이 보다 바람직하다고 판단하여 github actions로 진행하였습니다.


### 로그 추적기

스프링 AOP 기술을 사용하여 프로젝트의 *Controller, *Service, *Repository 에 포인트컷을 지정, 로그를 찍고

요청별로 로그를 추적하기 위해 쓰레드 로컬을 사용하여 로그 추적기를 구현 및 운영서버에 기능을 추가하였습니다.

![블로그 프로젝트 aop](https://user-images.githubusercontent.com/105222802/211771505-c4e88b1f-5bfd-4abc-b5d5-176e378579f4.png)

AOP 학습과 해당 기능 개발을 위해 [인프런, 김영한님의 스프링 핵심 원리 - 고급편](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%95%B5%EC%8B%AC-%EC%9B%90%EB%A6%AC-%EA%B3%A0%EA%B8%89%ED%8E%B8/dashboard) 을 참고했습니다.


### 소셜 로그인

소셜 로그인 구현을 위해 스프링 시큐리티와 OAuth2 인증방식을 사용했으며,

소셜 인증 제공자 추가로 인한 확장을 대비해 엑세스 토큰으로 받아오는 유저 정보를 OAuth2UserInfo 인터페이스로 추상화하여 파싱하도록 설계했습니다.

[Oauth2UserInfo인터페이스](https://github.com/HyungGon86/springaws/blob/master/src/main/java/study/springaws/global/oauth/provider/Oauth2UserInfo.java)

### summernote

#### 글작성은 오픈소스 이지윅 에디터로

블로그의 글은 웹에 게시되는 컨텐츠인만큼 다양한 양식과 스타일을 지워하기 위해 오픈소스 에디터인 summernote를 적용 하였습니다.



[Ajax로 이미지 업로드](https://github.com/HyungGon86/springaws/blob/master/src/main/resources/static/js/postwrite.js)

summernote editor는 기본적으로 base64로 인코딩 후 저장하는 방식이여서 이미지 파일 관리가 어렵습니다.

그래서 callback을 이용하여 이미지를 특정 경로에 업로드 후 고유한 url를 리턴하는 방식으로 구현하였습니다.

[서버단에서 처리하는 컨트롤러]https://github.com/HyungGon86/springaws/blob/master/src/main/java/study/springaws/domain/post/controller/PostController.java)


### 기본적인 게시물 CRUD

게시물에 대한 기본적인 CRUD를 모두 구현하였습니다.

글작성은 위의 내용처럼 summernote editor을 통해 게시물이 등록되게 되며

글 읽기도 위의 내용처럼 summernote edit를 기반으로 작성된 데이터를 토대로 DB에서 불러와 조회하게 됩니다.

수정과 삭제의 경우 관리자 Role을 가진 계정만 인가하여 권한을 제한하였고 타임리프로 관리자 계정에만 해당 기능을 표시토록 하였습니다.

![수정 삭제기능 버튼](https://user-images.githubusercontent.com/105222802/211774387-f24be3e0-3e43-434d-ae2f-b2ae02158360.png)

[관리자 계정에서만 보이는 글 수정 삭제 버튼]


### 댓글과 대댓글 구현

댓글과 대댓글의 경우 엔티티 구조는 셀프조인으로 참조하였습니다.

댓글의 깊이별로 먼저 조회하고 서버단에서 Stream Api를 이용하여 부모 댓글이 해당하는 자식 댓글을 가지고 있는 중첩 형태로 구현하였습니다.


### 계층형 카테고리

카테고리 역시 위의 댓글처럼 계층 레벨을 표현하는 컬럼을 사용하고 셀프조인으로 계층을 형성하도록 엔티티 구조를 설계하였습니다.


### 카테고리 편집기 구현

카테고리 추가, 삭제, 상위 카테고리로 이동, 하위 카테고리로 이동, 카테고리 순서 변경이 가능한 관리자용 API를 구현하여 블로그 운영을 보다 용이하게 만들었습니다.

![카테고리 편집](https://user-images.githubusercontent.com/105222802/211777229-8f859d15-6376-4115-8326-53850926f3d5.png)

[카테고리 편집 화면]

클라이언트단에서는 바닐라 자바스크립트를 통해 DTO를 수정하고 DOM을 조작하여 구현하였고 변경된 카테고리 리스트를 DTO로 백단으로 넘기면 

백단에서는 변경된 카테고리리스트와 기존 카테고리리스트 두개를 비교대조를 통해 신규 카테고리생성, 기존카테고리 이름과 순서 변경, 카테고리 삭제 로직을 수행토록 했습니다.


### 캐싱

고정된 레이아웃상에 카테고리 목록들, 최신 댓글은 물론 메인화면상의 최신 게시물이나 인기게시물등 화면을 렌더링하기 위해 상당히 많은 데이터가 필요하고

해당 데이터들을 구하기위해 모든 클라이언트의 모든 조회시마다 DB에 쿼리를 날린다면 성능상으로 상당히 부하가 될것이라 판단하였습니다.

따라서 자주 사용되는 메서드들에 대하여 EhCache 를 통해 캐시 처리를 하고 만약 해당 캐시에 대한 데이터 정합성이 깨지는 메서드들을 사용할경우 

캐시를 폐기하는 정책을 사용하였습니다.

[캐시 설정 클래스](https://github.com/HyungGon86/springaws/blob/master/src/main/java/study/springaws/global/config/CacheConfig.java)

또한 캐시 생명주기를 6시간으로 설정하여 별도의 메서드 없이도 6시간마다 캐시를 폐기하여 데이터 정합성을 유지하도록 설정했습니다.


### CI/CD 무중단 배포

애플리케이션 출시에 있어서 지속적 통합과 지속적 배포를 위해 Github, Github Actions, AWS CodeDeploy를 사용했으며 빌드와 배포를 분리하기 위해 Github Actions와 AWS s3를 이용했습니다.

깃헙으로 push된 프로젝트는 Github Actions에서 설정에따라 자동화 테스트를 거쳐 빌드되며 빌드된 jar는 AWS S3에 저장됩니다. 

[Github Actions 설정파일](https://github.com/HyungGon86/springaws/blob/master/.github/workflows/gradle.yml)


이후 배포요청을 받은 CodeDeploy는 S3에서 jar 파일을 넘겨받아 ec2로 파일을 넘겨주며 이때 배포 수명 주기의 순서대로 설정된 스크립트를 실행하게 됩니다.

[appspec 설정](https://github.com/HyungGon86/springaws/blob/master/appspec.yml)

여기서 무중단 배포를 구현하기 위하여 EC2의 8081과 8082 포트에 프로젝트 jar 두개를 구동시키고 엔진엑스로 8080포트를 열되

엔진엑스는 81과 82중 하나의 포트만 리버스 프록시하도록 구현하려 하였으나 

이 부분이 현재 생각처럼 구현되지 않았기 때문에 Nginx를 연동하여 리버스 프록시하는 부분은 추후 업데이트 예정입니다.


## 프로젝트를 통해 느낀점

처음으로 혼자 프로젝트를 진행하다보니 제가 작성하고 있는 코드가 정말 괜찮은 코드인지, 설계나 구현등을 이렇게 하는게 맞는건지, 더 나은 방법은 없는지 많은 고민을 하였습니다.

비전공이고 프로그래밍 공부를 시작한지 아직 오래되지 않았기 때문인지 모든것이 처음이고 굉장히 어려운 프로젝트였습니다.

하지만 프로젝트를 진행하는 기간동안 정말 하루하루가 빠르게 지나갔고, 어려운 문제를 마주할때마다 이를 해결하기 위해 노력하는 스스로를 보며 

**어떤 문제라도 노력하여 해결할수 있다**는 자신감을 갖게 되었습니다.

아직 부족한 것이 너무 많고, 공부를 하면 할수록 새로운 공부거리가 몇배로 새로 나오고 있지만

이번 프로젝트로 정말 많은것을 공부하고 배웠고, 문제에 직면했을때 이를 해결했던 크고 작은 경험들이 저에게 큰 자산이 되었다고 확신합니다.
