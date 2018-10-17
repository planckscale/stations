# Stations REST webservice 
## Relational persistence, SpringData/Hibernate ORM, and Lucene file system indexed search (HibernationSearch integration).

## Some docs on things i needed to research or reference.
* https://www.baeldung.com/spring-data-repositories
* https://docs.jboss.org/hibernate/search/5.6/reference/en-US/html/ch04.html#indexed-annotation

## Run REST webservice in local embedded tomcat (or package and deploy as executable jar in say Docker containers)
```./gradle bootRun```

## Some sample calls (some sample data was bootstrapped). Hosting the generated API doc with updated descriptions would ideal here. Can be seen by going to the defualt "http://localhost:8080/v2/api-docs" and then pasting into online swagger editor
### Add new station
$ curl -X POST http://localhost:8080/station --data '{"hdEnabled":true,"stationId":"189862"}' -H "Content-Type: application/json"
```{"id":10,"stationId":"189862","name":null,"hdEnabled":true,"callSign":null}```
### Update station
$ curl -X PUT http://localhost:8080/station/1 --data '{"id":1, "hdEnabled":true,"stationId":"777"}' -H "Content-Type: application/json"
```{"id":1,"version":1,"stationId":"777","name":null,"hdEnabled":true,"callSign":null}```
### Delete station
> $ curl -X DELETE http://localhost:8080/station/9
>> $ curl -X GET http://localhost:8080/station/9
### Search all enabled
$ curl -X GET http://localhost:8080/station/enabled
```[{"id":1,"stationId":"1","name":"Station 1","hdEnabled":true,"callSign":"Call Sign 1"},{"id":2,"stationId":"2","name":"Station 2","hdEnabled":true,"callSign":"Call Sign 2"},{"id":3,"stationId":"3","name":"Station 3","hdEnabled":true,"callSign":"Call Sign 3"},{"id":4,"stationId":"4","name":"Station 4","hdEnabled":true,"callSign":"Call Sign 4"},{"id":5,"stationId":"5","name":"Station 5","hdEnabled":true,"callSign":"Call Sign 5"},{"id":6,"stationId":"6","name":"Station 6","hdEnabled":true,"callSign":"Call Sign 6"},{"id":7,"stationId":"7","name":"Station 7","hdEnabled":true,"callSign":"Call Sign 7"},{"id":8,"stationId":"8","name":"Station 8","hdEnabled":true,"callSign":"Call Sign 8"},{"id":9,"stationId":"9","name":"Station 9","hdEnabled":true,"callSign":"Call Sign 9"},{"id":10,"stationId":"189863","name":null,"hdEnabled":true,"callSign":null}]```
### Search term (stationId or name)
$ curl -X GET http://localhost:8080/station/search?term=189862
```[{"id":10,"stationId":"189863","name":null,"hdEnabled":true,"callSign":null}```
