# Use the official OpenJDK 17 image as the base
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /usr/app

# Copy the JAR file into the container
COPY ./target/CreditCard.jar /usr/app/

#RUN sh -c 'touch CreditCard-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","CreditCard.jar"]

# Specify the command to run your application
#CMD ["java","-jar","CreditCard.jar"]