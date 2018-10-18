# Stations REST webservice 
## Relational persistence, SpringData/Hibernate ORM, and Lucene file system indexed search (HibernationSearch integration).

## Some docs on things i needed to research or reference.
* https://www.baeldung.com/spring-data-repositories
* https://docs.jboss.org/hibernate/search/5.6/reference/en-US/html/ch04.html#indexed-annotation

## Run REST webservice in local embedded tomcat on port 8080 (one could package and deploy as executable jar in say Docker containers)
```./gradle bootRun```

## Some sample calls (some sample data was bootstrapped). 
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

## Actuator (port 8081)
### Example
$ curl -X GET http://localhost:8081/metrics
```{"mem":581959,"mem.free":456896,"processors":4,"instance.uptime":40568,"uptime":51747,"systemload.average":2.97998046875,"heap.committed":499712,"heap.init":131072,"heap.used":42815,"heap":1864192,"nonheap.committed":83800,"nonheap.init":2496,"nonheap.used":82249,"nonheap":0,"threads.peak":44,"threads.daemon":37,"threads.totalStarted":57,"threads":40,"classes":11470,"classes.loaded":11470,"classes.unloaded":0,"gc.ps_scavenge.count":9,"gc.ps_scavenge.time":235,"gc.ps_marksweep.count":3,"gc.ps_marksweep.time":329,"httpsessions.max":-1,"httpsessions.active":0,"datasource.primary.active":0,"datasource.primary.usage":0.0}```

## Notes 
### Test coverage imcomplete and Jacoco may need further configuration
```./gradlew clean test jacocoTestReport```
### Swagger2-generated API doc. 
Default setup be seen by going to http://localhost:8080/v2/api-docs and then pasting into online swagger editor
Hosting the generated API doc with updated descriptions would be another possibility in real life. Would want to markup controller info with more but swagger2 does a good first pass.
### Some experimentation here as a learning experience and other things to consider for scalability like caching or async programming could be investigated.
