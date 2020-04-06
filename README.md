# TMoney

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/696b14ae195643c79e6e2c0d6375133d)](https://app.codacy.com/manual/TheSeems/TMoney?utm_source=github.com&utm_medium=referral&utm_content=TheSeems/TMoney&utm_campaign=Badge_Grade_Dashboard)

A minecraft plugin providing support for multiple economies on your server

## Commands
_() - optional argument_  
_[] - required argument_  
_If economy is not specified plugin will use default one (usually Vault)_  

### Balance
/tmoney (economy) [player] - Get balance of player in certain economy
### Deposit
/tmoney deposit (economy) [player] - Deposit money on player's balance in certain economy 
### Withdraw
/tmoney withdraw (economy) [player] - Withdraw money from player's balance in certain economy 

## PlaceholderAPI
This plugin provides placeholder: `%tmoney_<economy>%`  
It returns balance in certain economy 

## Config
Here is example config (plugins/TMoney/data.json)  

```json
{
  "economies": [
    {
      "type": "memory",
      "name": "<name>"
    },
    {
      "config": {
        "url": "jdbc:postgresql://localhost/minecraft",
        "user": "minecraft",
        "password": "doiwannaknow"
      },
      "type": "jdbc",
      "name": "<name>"
    }
  ]
```

### Currently supported types of economies in config:
Type __memory__ means that all balances of players will not be load on server restart  
Type __jdbc__ means that all balances of players will be stored on a database (JDBC) and will be load from it on server restart 

## API
### Getting economy
```Java
TMoneyAPI.getEconomy("<name>").ifPresent(economy -> {
  // Do some awesome work here
  economy.withdraw(player, amount);
  economy.deposit(player, amount);
  economy.getBalance(player);
});
```
### Getting default economy
```Java
Economy economy = TMoneyAPI.getDefault();
```
### Registering own economy
```Java
TMoneyAPI.getManager().addEconomy(new Economy() {...});
```
### Removing economy
```Java
TMoneyAPI.getManager().removeEconomy("<name>");
```
### Get all economies there are
```Java
TMoneyAPI.getManager().getEconomies();
```
