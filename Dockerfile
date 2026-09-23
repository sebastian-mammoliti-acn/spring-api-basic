# Select the image to use as our java builder.
FROM maven:3.9-eclipse-temurin-17 AS builder 

# Select a work location
WORKDIR /workspace

# Clone the application code to the container
RUN git clone https://github.com/sebastian-mammoliti-acn/spring-api-basic.git .

# Build the application
RUN mvn clean package -DskipTests


# New create our actual application
FROM registry.access.redhat.com/ubi9/openjdk-17-runtime

# Set working directory
WORKDIR /app

# Copy the resulting .jar application files to the local container
COPY --from=builder /workspace/target/*.jar /app/app.jar

# Expose the port
EXPOSE 8080

# Set user to non-root
USER 1001

# Start application
ENTRYPOINT ["java","-jar","/app/app.jar"]