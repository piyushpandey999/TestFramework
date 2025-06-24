package Util;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class ReportLoggingFilter implements Filter {


    private static final ThreadLocal<RequestDetails> requestDetails = new ThreadLocal<>();
    private static final ThreadLocal<ResponseDetails> responseDetails = new ThreadLocal<>();

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext context) {
        // Capture Request Details
        RequestDetails reqDetails = new RequestDetails();
        reqDetails.setMethod(requestSpec.getMethod());
        reqDetails.setUri(requestSpec.getURI());
        reqDetails.setHeaders(requestSpec.getHeaders());
        reqDetails.setBody(requestSpec.getBody() != null ? requestSpec.getBody().toString() : "");
        requestDetails.set(reqDetails);

        // Execute the request
        Response response = context.next(requestSpec, responseSpec);

        // Capture Response Details
        ResponseDetails resDetails = new ResponseDetails();
        resDetails.setStatusCode(response.getStatusCode());
        resDetails.setHeaders(response.getHeaders());
        resDetails.setBody(response.getBody().asPrettyString());
        responseDetails.set(resDetails);

        return response;
    }

    // Getters and Clear method
    public static RequestDetails getRequestDetails() {
        return requestDetails.get();
    }

    public static ResponseDetails getResponseDetails() {
        return responseDetails.get();
    }

    public static void clear() {
        requestDetails.remove();
        responseDetails.remove();
    }

    // Inner classes to hold data
    public static class RequestDetails {
        private String method;
        private String uri;
        private Headers headers;
        private String body;

        // Getters and Setters
        public String getMethod() { return method; }
        public void setMethod(String method) { this.method = method; }
        public String getUri() { return uri; }
        public void setUri(String uri) { this.uri = uri; }
        public Headers getHeaders() { return headers; }
        public void setHeaders(Headers headers) { this.headers = headers; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }

    public static class ResponseDetails {
        private int statusCode;
        private Headers headers;
        private String body;

        // Getters and Setters
        public int getStatusCode() { return statusCode; }
        public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
        public Headers getHeaders() { return headers; }
        public void setHeaders(Headers headers) { this.headers = headers; }
        public String getBody() { return body; }
        public void setBody(String body) { this.body = body; }
    }
}
