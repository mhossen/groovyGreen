package jsonTests

import groovy.json.JsonOutput

class TestJsonMapCompare {
    private static Map params = [:]

    static void main(String[] args) {
        params.put("requestUrl", "https://www.example.com/api/endpoint")
        params.put("headers", ["Accept": "content/json", "Content-Type": "content/json"])
        params.put("auth", ["type": "basic", "username": "Admin123", "password": "pass123\$"])
        params.put("queryString", ["userId":"123344"])
        params.put("body", JsonOutput.toJson([:]))

        List<String> requiredParams = ["requestUrl", "headers", "auth", "queryString", "body"]
        List<String> optionalParams = []

        // Validate the parameters
        params.each { key, value ->
            if (!requiredParams.contains(key) && !optionalParams.contains(key)) {
                throw new IllegalArgumentException("Invalid parameter: $key = $value")
            }

            if (key == "auth") {
                if (value instanceof Map) {
                    def type = value.get("type")
                    if (type == "basic") {
                        if (!value.containsKey("username") || !value.containsKey("password")) {
                            throw new IllegalArgumentException("Invalid auth parameter: 'username' and 'password' are required for 'basic' auth")
                        }
                    } else if (type == "bearer") {
                        if (!value.containsKey("token")) {
                            throw new IllegalArgumentException("Invalid auth parameter: 'token' is required for 'bearer' auth")
                        }
                    } else {
                        throw new IllegalArgumentException("Invalid auth parameter: 'type' must be either 'basic' or 'bearer'")
                    }
                } else if (value != null || value == ["type": "basic", "username": "Admin123", "password": "pass123\$"]) {
                    throw new IllegalArgumentException("Invalid auth parameter: must be a map or null and not default")
                }
            }
        }
    }
}