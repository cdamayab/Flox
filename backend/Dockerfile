# Base image with gradle & JDK
FROM gradle:7.6-jdk17
WORKDIR /app
COPY . .

EXPOSE 5005

# Entrypoint script
COPY entrypoint.sh /entrypoint.sh
RUN chmod +x /entrypoint.sh

ENTRYPOINT ["/bin/bash", "/entrypoint.sh"]
