이 프로젝트는 Synology NAS Webhook 알림을 수신하여 Slack으로 전달하는 개인적인 사용을 목적으로 시작되었습니다. 
향후 다양한 이벤트 소스와 멀티 알림 채널을 손쉽게 확장, 자동화할 수 있도록 Spring StateMachine을 기반으로 설계되었습니다.

예정된 개발 사항:

- 추가 이벤트 입력 소스 지원 (SNMP, Syslog 파싱 등)
- 멀티 알림 채널 확장 (Slack, Discord 등)
