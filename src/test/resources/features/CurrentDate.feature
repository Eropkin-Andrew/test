# language: en
@rate
Feature: Проверка апи курсов валют
  Scenario: Получить тукущий курс валют.
  Проверить что:
  - запрос обработан успешно (код ответа 200, success - true)
  - базовая валюта Евро
  - дата равна текущей
  - historical отсутствует
  - курс Евро к Евро равен 1
  - курс Рубля к Евро представляет из себя положительное число

    When User get current exchange rate
    Then User check base currency "EUR"
    Then User check date
    Then User check historical is absent
    Then User check rate "EUR"
    Then User check rate "RUB"

  Scenario: Получить курс валют на заданную дату.
  Проверить что:
  - запрос обработан успешно (код ответа 200, success - true)
  - базовая валюта Евро
  - дата равна заданной
  - historical - true
  - курс Евро к Евро равен 1
  - курс Рубля к Евро представляет из себя положительное число

    When User get exchange rate on date "2000-01-03"
    Then User check base currency "EUR"
    Then User check date
    Then User check historical "true"
    Then User check rate "EUR"
    Then User check rate "RUB"
