# STEP 2-4
==========

특정 도메인은 shorten-url 제공을 거부하고 싶어요.
-------------

***

## 목차🧭

- [개요](#개요)
- [배경](#배경)
- [목표](#목표)
- [목표가 아닌 것](#목표가-아닌-것)
***

## 개요📜

>특정 도메인에 대해 url-shorten 서비스 제공을 거부하여  
>안전하지 않은 웹페이지에 대한 연결을 최소화합니다.

***   

## 배경🖼️

>url-shorten 서비스는 원본 url 을 단축하는 서비스입니다.  
>이는 단축된 url 을 전달받은 사용자는 목적지를 알 수 없어 취약한 상태에 놓이게 됩니다.  
>사용자들에 대한 최소한의 보호를 할 필요가 있다고 생각했습니다.
***

## 목표📌

1. 특정 도메인 혹은 특정 형태의 도메인을 차단합니다.
   - 도메인 목록은 [StevenBlack/hosts](https://raw.githubusercontent.com/StevenBlack/hosts/master/alternates/fakenews-gambling-porn-social/hosts)의 `hosts.txt` 를 이용합니다. (정보 공유해주신 개발자님 감사합니다.😊)
2. 단축 url 접근 시 사용자에게 목적지에 대한 정보를 추가로 제공합니다.
   - 단축 url, 원본 url, 생성 일시 등을 응답으로 추가합니다. (바로 redirect 하지 않으려 합니다.)
***

## 목표가 아닌 것✂️

- DB 를 활용한 기능 구현
***

## 질문 사항🧑‍💻
1. 서비스 제공을 `거부` 하고자 하는 `의도` 를 말씀해주실 수 있을까요?
   - 피싱 사이트, 스팸 방지 등의 목적이 예상됩니다.
   - 서비스 제공 거부가 아닌 단축 url 의 목적지를 사용자에게 제공하는 방법도 있을 것 같습니다.


2. 외부 데이터 혹은 서비스를 활용하는 방법은 어떨까요?
   - [KISA-피싱사이트-URL](https://www.data.go.kr/data/15109780/fileData.do)
   - [Google-Safe-Browsing](https://developers.google.com/safe-browsing?hl=ko) **상업적 이용 X** (무료)
   - [Google-Web-Risk](https://cloud.google.com/web-risk/docs?hl=ko) **상업적 이용 O** (일부 무료)
   - 회사 내부적으로 유사한 데이터, 서비스를 제공한다면 이를 활용할 수도 있을 것 같습니다.
