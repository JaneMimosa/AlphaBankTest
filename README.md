# AlphaBankTest
Описание
Создать сервис, который обращается к сервису курсов валют, и отображает gif:
• если курс по отношению к USD за сегодня стал выше вчерашнего, то отдаем рандомную отсюда https://giphy.com/search/rich
• если ниже - отсюда https://giphy.com/search/broke
Ссылки
• REST API курсов валют - https://docs.openexchangerates.org/
• REST API гифок - https://developers.giphy.com/docs/api#quick-start-guide
Must Have
• Сервис на Spring Boot 2 + Java / Kotlin
• Запросы приходят на HTTP endpoint (должен быть написан в соответствии с rest conventions), туда передается код валюты по отношению с которой сравнивается USD
• Для взаимодействия с внешними сервисами используется Feign
• Все параметры (валюта по отношению к которой смотрится курс, адреса внешних сервисов и т.д.) вынесены в настройки
• На сервис написаны тесты (для мока внешних сервисов можно использовать @mockbean или WireMock)
• Для сборки должен использоваться Gradle
• Результатом выполнения должен быть репо на GitHub с инструкцией по запуску

Инструкция по запуску
gradlew bootRun

После старта приложения открываем в браузере страницу по адресу http://localhost:8080/api/[currency] , вместо [currency] необходимо указать код валюты, например RUB. Для стандартного тарифа в качестве базовой валюты сервис openexchangerates.org предоставляет только USD. Настройки базовой валюты и другие параметры можно поменять в application.properties файле. Сервис возвращает клиенту JSON объект c адресом GIF картинки в соответствии с техзаданием.

