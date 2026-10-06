# spring-boot-demo

Java Spring Boot server REST API to return real estate listings.

The main controller endpoint is "/listings" returns a paginated results list of real estate listings and accepts the following query parameters:
    int page
    int size,
    String minPrice,
    String maxPrice,
    String minBedrooms,
    String city,
    String description

Example URL: "http://localhost:8080/listings?minPrice=0&maxPrice=10000000&minBedrooms=0&page=2"

To run locally type ./gradlew build and ./gradlew bootRun.
