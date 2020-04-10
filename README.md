# TMoney
![Kind of logo](https://theseems.ru/tmoney/logo.png)  

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/696b14ae195643c79e6e2c0d6375133d)](https://app.codacy.com/manual/TheSeems/TMoney?utm_source=github.com&utm_medium=referral&utm_content=TheSeems/TMoney&utm_campaign=Badge_Grade_Dashboard)  
A minecraft plugin providing support for multiple economies on your server

## Commands
_() - optional argument_  
_[] - required argument_  
_If economy is not specified plugin will use default one (usually Vault)_  

### Balance
/tmoney balance (economy) [player] - Get balance of player in certain economy
### Deposit
/tmoney deposit (economy) [player] [amount] - Deposit money on a player's balance in certain economy 
### Withdraw
/tmoney withdraw (economy) [player] [amount] - Withdraw money from a player's balance in certain economy 
### Reload
/tmoney reload - Reloads the config
### List
/tmoney list - Returns a list of all economies there are

## PlaceholderAPI
You need to execute: `/papi ecloud download TMoney` in order to use placeholders
This plugin provides placeholder: `%tmoney_<economy>%`  
It returns balance in certain economy 

## Config
Here is example config (plugins/TMoney/data.json)  

```json
{
  "economies": [
    {
      "config": {
        "url": "jdbc:h2:file:/home/minecraft/inst/plugins/TMoney/Economy.db",
        "user": "minecraft",
        "password": "password",
        "prefix": "mc"
      },
      "type": "jdbc",
      "name": "donate"
    }
  ],
  "libsUrl": "https://theseems.ru/tmoney/libs",
  "libs": [
    "org.h2.Driver",
    "org.postgresql.Driver"
  ]
}
```

### Currently supported types of economies in a config:
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
