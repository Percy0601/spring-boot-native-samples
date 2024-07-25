## Summary

https://github.com/mybatis/spring-boot-starter/wiki/Quick-Start-for-building-native-image#create-a-project

### Config:
https://github.com/mybatis/spring-boot-starter/wiki/MyBatisNativeConfiguration.java

### Resolve:
https://github.com/nieqiurong/mybatis-native-demo/commit/7c8977a

### Build

```
mvn clean -DskipTests=true -Pnative native:compile
```


### Xml
runtime using xml:
```
    mybatis-native-sample -Djavax.xml.accessExternalDTD=all
```

### Repository
./h2

### Url
#### Create
http://localhost:8080/mybatis/create-book?title=java-test03

#### Find-All
http://localhost:8080/mybatis/find-all-book

#### Find-One
http://localhost:8080/mybatis/find-book-id?id=1

