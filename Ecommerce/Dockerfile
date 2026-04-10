# ─────────────────────────────────────────────
# Stage 1 — Build the JAR with Maven
# ─────────────────────────────────────────────
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build

# Copy the Maven wrapper and pom first (layer-cache the dependency download)
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
RUN chmod +x mvnw && ./mvnw dependency:go-offline -q

# Copy source and build, skipping tests (tests run in CI, not in the image build)
COPY src ./src
RUN ./mvnw package -DskipTests -q

# ─────────────────────────────────────────────
# Stage 2 — Lean runtime image
# ─────────────────────────────────────────────
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Copy the fat JAR from the builder stage
# Your artifact is ApexTrade.jar (groupId: com.apex, version: 1.0)
COPY --from=builder /build/target/ApexTrade.jar app.jar

# Image uploads will be written here — mount a volume in production
RUN mkdir -p images && chown -R appuser:appgroup /app

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]