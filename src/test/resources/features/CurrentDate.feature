# language: en
@rate
Feature: currency exchange rate
  Scenario: get current rate

    When User get current exchange rate
    Then User check base currency "EUR"
    Then User check date
    Then User check historical "false"
    Then User check rate "EUR"
    Then User check rate "RUB"

  Scenario: get historical rate

    When User get exchange rate on date "2000-01-03"
    Then User check base currency "EUR"
    Then User check date
    Then User check historical "true"
    Then User check rate "EUR"
    Then User check rate "RUB"
