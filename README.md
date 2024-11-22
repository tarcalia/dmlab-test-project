
# **Weather Data Service For Cities**

## **Project Overview**

This project provides a REST API to fetch and analyze weather data. It uses the Quarkus framework, along with Redis for caching and PostgreSQL for persistent storage. The application is designed for efficient performance, offering cached responses, and detailed logging for data source tracking.

---

## **Configuration**

### **API Key Setup**
To use the weather API, replace the placeholder API key in the `application.properties` file:
```properties
weather.api-key=REPLACE_ME_FROM_EMAIL
```
You should have received the API key via email. Replace `REPLACE_ME_FROM_EMAIL` with the provided value.

### **Database and Redis Configuration**
Quarkus simplifies development by automatically managing Redis and PostgreSQL during development mode. No separate configuration is required for these services. However, if requested, I can provide a `docker-compose.yml` or detailed instructions for setting up these services.

---

## **Endpoints**

### **1. `/weather` [GET]**
Fetches the weather data for the configured location.

- **Response**:
    - Returns the weather details, including temperature, snow, wind speed, sunrise, and sunset times.
---

### **2. `/weather/snowy-days` [GET]**
Counts the number of snowy days in the last 30 days.

- **Response**:
    - An integer representing the count of snowy days where snowfall exceeds a configured threshold.
- **Caching**:
    - Results are cached in Redis for fast subsequent retrievals.
- **Logging**:
    - Logs whether the result was fetched from Redis or calculated from the database.

---

### **3. `/weather/sunlight-stats` [GET]**
Calculates sunlight statistics for the last 30 days.

- **Response**:
    - Returns the following:
        - **Average sunlight duration**: The average time between sunrise and sunset.
        - **Minimum sunlight duration**: The shortest day.
        - **Maximum sunlight duration**: The longest day.
- **Caching**:
    - The stats are cached for performance optimization.
- **Logging**:
    - Logs whether the data was retrieved from Redis or calculated from the database.

---

## **Features**

1. **Efficient Caching**:
    - Redis is used to store frequently requested data in-memory, ensuring rapid response times for subsequent API calls.

2. **Database Backup**:
    - PostgreSQL serves as a reliable persistent storage solution for long-term data availability.

3. **Logging**:
    - The application logs each API call, indicating whether the data was served from Redis (cache) or PostgreSQL (database).

---

## **Why Quarkus?**

Quarkus was chosen for this project due to its modern features and excellent support for cloud-native and high-performance applications:

1. **Hot Reload**:
    - Accelerates development by instantly reflecting changes without restarting the application.

2. **Memory Efficiency**:
    - Optimized memory usage ensures smooth performance, even in resource-constrained environments.

3. **Ahead-of-Time (AOT) Compilation**:
    - Quarkus compiles to native binaries, significantly reducing startup times and improving runtime efficiency.

4. **Seamless Integration**:
    - Built-in extensions for Redis, PostgreSQL, and REST API development simplify integration and reduce boilerplate code.

---

## **How it Works**

1. **Caching**:
    - Frequently requested data (e.g., snowy days count or sunlight stats) is stored in Redis.
    - Logs indicate whether data was retrieved from the cache or recalculated.

2. **Database Interaction**:
    - PostgreSQL serves as a fallback source for retrieving and storing weather data.

3. **API Flow**:
    - Incoming requests trigger REST endpoints.
    - Data is fetched from Redis (if available) or calculated using data from PostgreSQL.

---

## **Getting Started**

1. **Run the Application**:
    - Start the Quarkus application using:
      ```bash
      mvn quarkus:dev
      ```

2. **Access OpenAPI Documentation**:
    - OpenAPI documentation is available at:
      ```
      http://localhost:8080/q/dev-ui/endpoints
      ```

3. **Test Endpoints**:
    - Use tools like Postman or cURL to test the provided REST endpoints.

---

## **Future Enhancements**

- Add support for dynamic locations in the weather API.
- Provide Docker setup for Redis and PostgreSQL in production environments.
- Expand analytics for weather trends and historical data.

---

For any questions or additional setup requests, feel free to reach out at adm.tarcali@gmail.com!! 😊