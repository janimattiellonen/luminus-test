FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/guestbook2.jar /guestbook2/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/guestbook2/app.jar"]
