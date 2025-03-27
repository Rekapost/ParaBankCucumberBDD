FROM openjdk:11-jdk-slim

# Set JAVA_HOME and PATH for Java 11
ENV JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# Install Maven and necessary dependencies
RUN apt-get update && apt-get install -y \
    maven wget gnupg libnss3 libx11-xcb1 libxcomposite1 libxdamage1 libxi6 libxtst6 \
    fonts-liberation libappindicator3-1 xdg-utils libatk-bridge2.0-0 libgtk-3-0

# Verify Java version in Docker container
RUN echo "JAVA_HOME is set to $JAVA_HOME" && java -version

# Verify Maven version in Docker container
RUN mvn -v

# Set working directory
WORKDIR /home/BDD-Parabank-Cucumber

# Copy application files into the container
COPY . /home/BDD-Parabank-Cucumber

# Run Maven build (skip tests)
RUN mvn clean install -DskipTests=true

# Expose port for the application
EXPOSE 8080

# Default command to run after the container starts
CMD ["mvn", "clean", "install", "-DskipTests=true"]
