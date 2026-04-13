## 1.  개요

> Basic

- Java ver 21.
- Spring Boot ver 4.0.1 

> Monitoring

- Elastic Search ver 9.1.0
- Log Stash ver 9.1.0

## 2. Projects

- System과 명확하게 상호작용하여 안정성있게 관리하기 위한 일관된 Logging 방안에 대해 분석한다.
- Logging Framework / Elastic Search / Kibana / Actuator / Grafana & Prometheus

## 3. 로그는 기본적으로 I/O이다.

> Device I/O || Disk I/O

- 로그는 기본적으로 I/O이며, 사용 방법에 따라 메모리 혹은 디스크 레벨에서의 I/O를 유발한다.
    - Logger : Console은 Console I/O(stdout), File Appender가 Disk I/O 유발.
    - System.out.println / Console : OS가 제공하는 출력 스트림(stdout, Standard Output)을 사용하여 OS(Device) I/O 유발.
        - OS에게 stdout를 통해 원하는 메시지를 출력하라는 system call을 발생시켜 I/O 유발(file처럼 다루어 write call).
        - 이 상태에서 file로 redirect하면 Disk I/O 유발.
- 결국 로그는 프로세스와 OS 사이에서 IPC가 발생하는 I/O의 과정이다.
    - 모든 로그는 파일처럼 쓰고, 읽을 수 있는 하나의 통로이자 파일이다.

## 4. 주석과 로그는 본질적으로 다르다.

> 누가, 언제 읽는가

- 주석 : 개발자가 코드를 수정하거나, 기능을 파악하기 위해 코드 편집툴에서 읽는 것. 
  - 비즈니스 로직의 의도나 복잡한 알고리즘 설명, 오픈소스 사용법등
- 로그 : 운영자 및 개발자가 애플리케이션이 실행 중인 내역을 콘솔이나 파일을 통해 읽는 것.
  - 프로그램의 상태 및 향후 오류가 발생가능한 지점에 대해 확인하는 용도

## 5. 상세 명세와 설명

> 상세한 명세와 설명은 100% 주석의 영역

- 상세한 설명을 모두 로그로 세세하게 남기면 Device I/O, Disk I/O가 발생하여 시스템 성능 저하(Kernel의 System call을 유발하기에)
- 너무 많은 정보가 로그에 찍히면, 정작 장애가 났을 때 중요한 로그를 찾기가 불가능한 log noise 발생.

``
따라서, 클래스나 메서드의 역할을 설명하실때는 주석으로 작성하는 것을 권장한다.
``

## 6. Log Info/Warn

> Log INFO : 실행되는 로직의 핵심단계
- Framework Library에서 사용하는 방법과 동일하게 과정, 흐름을 파악할 수 있는 정도로 기재.
- 이 과정에서 식별자(traceId)가 중요한 개념.

> Log WARN : 시스템이 중단되지는 않지만, 나중에 문제가 될 요소
- 픈소스라면 설정값이 권장 사양보다 낮을때
- 곧 사라질 기능을 호출 했을때
- api 응답이 평소보다 늦어지는 임계치 도달 임박 시
- 설정 중복 시
- 임시적인 조치 방안에 대해