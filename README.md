# Stations REST webservice 
## Relational persistence, SpringData/Hibernate ORM, and Lucene file system indexed search (HibernationSearch integration).

## Some docs on things i needed to research or reference.
* https://www.baeldung.com/spring-data-repositories
* https://docs.jboss.org/hibernate/search/5.6/reference/en-US/html/ch04.html#indexed-annotation

## Run REST webservice in local embedded tomcat on port 8080 (one could package and deploy as executable jar in say Docker containers)
```./gradlew bootRun```

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
### Test coverage 83%
```./gradlew clean test jacocoTestReport```
### Swagger2-generated API doc. 
Default setup be seen by going to http://localhost:8080/v2/api-docs and then pasting into online swagger editor
Hosting the generated API doc with updated descriptions would be another possibility in real life. Would want to markup controller info with more but swagger2 does a good first pass.
### Some experimentation here as a learning experience and other things to consider for scalability like caching or async programming could be investigated.

### Docker Container
#### Please see Dockerfile at project root.
#### Output from docker build and run                        
`$ docker build . -t stations-0.0.1`
> Sending build context to Docker daemon  44.55MB
Step 1/4 : FROM openjdk:8-jdk-alpine
 ---> 54ae553cb104
Step 2/4 : EXPOSE 8080 8081
 ---> Using cache
 ---> edeed18ad77c
Step 3/4 : ADD /build/libs/stations-0.0.1-SNAPSHOT.jar stations-0.0.1.jar
 ---> 99ec58f507b6
Step 4/4 : ENTRYPOINT ["java", "-jar", "/stations-0.0.1.jar"]
 ---> Running in 22e30f353721
Removing intermediate container 22e30f353721
 ---> 03792ef176ce
Successfully built 03792ef176ce
Successfully tagged stations-0.0.1:latest                        

`$ docker run -p 8080:8080 -p 8081:8081 stations-0.0.1:latest`
>
 :: Spring Boot ::  (v1.5.18.BUILD-SNAPSHOT)

2018-10-21 20:04:40.253  INFO 1 --- [           main] com.home.stations.StationsApplication    : Starting StationsApplication on ca1a7eb4fd5f with PID 1 (/stations-0.0.1.jar started by root in /)
2018-10-21 20:04:40.266  INFO 1 --- [           main] com.home.stations.StationsApplication    : No active profile set, falling back to default profiles: default
2018-10-21 20:04:40.440  INFO 1 --- [           main] ationConfigEmbeddedWebApplicationContext : Refreshing org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@30946e09: startup date [Sun Oct 21 20:04:40 GMT 2018]; root of context hierarchy
...2018-10-21 20:04:45.407  INFO 1 --- [           main] j.LocalContainerEntityManagerFactoryBean : Building JPA container EntityManagerFactory for persistence unit 'default'
2018-10-21 20:04:45.663  INFO 1 --- [           main] o.hibernate.jpa.internal.util.LogHelper  : HHH000204: Processing PersistenceUnitInfo [
	name: default
	...]
..{[/station/{id}],methods=[GET]}" onto public com.home.stations.domain.Station com.home.stations.controller.StationController.show(long)
2018-10-21 20:04:48.475  INFO 1 --- [           main] s.w.s.m.m.a.RequestMappingHandlerMapping : Mapped "...2018-10-21 2018-10-21 20:04:50.004  INFO 1 --- [ost-startStop-1] o.s.b.w.servlet.ServletRegistrationBean  : Mapping servlet: 'dispatcherServlet' to [/]
2018-10-21 20:04:50.117  INFO 1 --- [           main] o.s.b.a.e.mvc.EndpointHandlerMapping     : Mapped "{[/info || /info.json],methods=[GET],produces=[application/vnd.spring-boot.actuator.v1+json || application/json]}" onto public org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext@30946e09
2018-10-21 20:04:50.280  INFO 1 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8081 (http)
2018-10-21 20:04:50.286  INFO 1 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 0
2018-10-21 20:04:50.356  INFO 1 --- [           main] o.s.c.support.DefaultLifecycleProcessor  : Starting beans in phase 2147483647
2018-10-21 20:04:50.357  INFO 1 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Context refreshed
2018-10-21 20:04:50.388  INFO 1 --- [           main] d.s.w.p.DocumentationPluginsBootstrapper : Found 1 custom documentation plugin(s)
2018-10-21 20:04:50.435  INFO 1 --- [           main] s.d.s.w.s.ApiListingReferenceScanner     : Scanning for api listing references
2018-10-21 20:04:51.012  INFO 1 --- [           main] s.b.c.e.t.TomcatEmbeddedServletContainer : Tomcat started on port(s): 8080 (http)
2018-10-21 20:04:51.016  INFO 1 --- [           main] com.home.stations.StationsApplication    : Started StationsApplication in 11.408 seconds (JVM running for 12.326)

