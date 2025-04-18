# STEP 4-1
==========

# 애플리케이션이 재기동되도 데이터가 남아있도록 해주세요.
-------------

***

## 목차🧭

- [개요](#개요)
- [배경](#배경)
- [목표](#목표)
- [목표가 아닌 것](#목표가-아닌-것)
***

## 개요📜

>생성된 shorten-url 정보를 db 에 저장하여 애플리케이션이 재기동되도 데이터가 남아있도록 합니다.  
>dbms 는 추후 변경될 수 있으므로, 특정 dbms 에 종속되지 않도록 구현합니다.

***   

## 배경🖼️

>현재는 애플리케이션을 재기동하면 생성된 shorten-url 정보가 사라집니다.  
>이를 해결하기 위해 db 를 사용 할 필요성을 느끼게 되었습니다.  
>csv, json 등의 파일로 저장하는 방법도 있지만, 확장성을 고려하여 db 를 사용하는 것이 낫다고 판단했습니다.
***

## 목표📌

1. db server 를 별도로 구동합니다.
2. 어플리케이션을 db server 에 연결할 수 있도록 합니다.  
3. 생성된 shorten-url 이 영속성을 가지도록 합니다.
***

## 목표가 아닌 것✂️

- presentation layer, service layer 코드의 수정
***