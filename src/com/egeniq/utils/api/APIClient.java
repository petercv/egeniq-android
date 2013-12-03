package com.egeniq.utils.api;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import ch.boye.httpclientandroidlib.Header;
import ch.boye.httpclientandroidlib.HttpEntity;

import com.egeniq.utils.net.AbstractHTTPClient;

/**
 * Simple API client.
 */
public class APIClient extends AbstractHTTPClient {
    /**
     * Specifies the expected response type of an API request.
     */
    protected enum ResponseType {
        JSONObject, JSONArray, Raw
    }

    /**
     * Constructor.
     */
    public APIClient(String baseURL) {
        super(baseURL);
    }

    /**
     * Constructor.
     */
    public APIClient(String baseURL, String secureBaseURL) {
        super(baseURL, secureBaseURL);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * Doesn't use SSL.
     * 
     * @param location Location.
     * 
     * @return Object.
     */
    public JSONObject get(String location) throws APIException {
        return get(location, false, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * @param useSSL Use SSL when available.
     * 
     * @return Object.
     */
    public JSONObject get(String location, boolean useSSL) throws APIException {
        return get(location, useSSL, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * @param useSSL Use SSL when available.
     * @param headers HTTP headers.
     * 
     * @return Object.
     */
    public JSONObject get(String location, boolean useSSL, Header[] headers) throws APIException {
        return _executeJSONObjectAPIRequest("GET", location, useSSL, headers, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON array.
     * 
     * 
     * @param location Location.
     * 
     * @return List.
     */
    public JSONArray getArray(String location) throws APIException {
        return getArray(location, false, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON array.
     * 
     * 
     * @param location Location.
     * @param useSSL Use SSL when available?
     * 
     * @return List.
     */
    public JSONArray getArray(String location, boolean useSSL) throws APIException {
        return getArray(location, useSSL, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a JSON array.
     * 
     * 
     * @param location Location.
     * @param useSSL Use SSL when available?
     * @param headers HTTP headers.
     * 
     * @return List.
     */
    public JSONArray getArray(String location, boolean useSSL, Header[] headers) throws APIException {
        return _executeJSONArrayAPIRequest("GET", location, useSSL, headers, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a string.
     * 
     * 
     * @param location Location.
     * 
     * @return String.
     */
    public String getRaw(String location) throws APIException {
        return getRaw(location, false, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a string.
     * 
     * 
     * @param location Location.
     * @param useSSL Use SSL when available?
     * 
     * @return String.
     */
    public String getRaw(String location, boolean useSSL) throws APIException {
        return getRaw(location, useSSL, null);
    }

    /**
     * Performs a GET request to the given location (which is appended to the base URL) and returns the result as a string.
     * 
     * 
     * @param location Location.
     * @param useSSL Use SSL when available?
     * @param headers HTTP headers.
     * 
     * @return String.
     */
    public String getRaw(String location, boolean useSSL, Header[] headers) throws APIException {
        return _executeRawAPIRequest("GET", location, useSSL, headers, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a JSON object..
     * 
     * @param location Location.
     * @param entity Post entity.
     */
    public JSONObject post(String location, HttpEntity entity) throws APIException {
        return post(location, entity, false, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a JSON object..
     * 
     * @param location Location.
     * @param entity Post entity.
     * @param useSSL Use SSL when available.
     */
    public JSONObject post(String location, HttpEntity entity, boolean useSSL) throws APIException {
        return post(location, entity, useSSL, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a JSON object..
     * 
     * @param location Location.
     * @param entity Post entity.
     * @param useSSL Use SSL when available.
     * @param headers Headers.
     * 
     * @return Object.
     */
    public JSONObject post(String location, HttpEntity entity, boolean useSSL, Header[] headers) throws APIException {
        return _executeJSONObjectAPIRequest("POST", location, useSSL, headers, entity);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result raw string
     * 
     * @param location Location.
     * @param entity Post entity.
     */
    public String postRaw(String location, HttpEntity entity) throws APIException {
        return postRaw(location, entity, false, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a raw string
     * 
     * @param location Location.
     * @param entity Post entity.
     * @param useSSL Use SSL when available.
     */
    public String postRaw(String location, HttpEntity entity, boolean useSSL) throws APIException {
        return postRaw(location, entity, useSSL, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a raw string
     * 
     * @param location Location.
     * @param entity Post entity.
     * @param useSSL Use SSL when available.
     * @param headers Headers.
     * 
     * @return Object.
     */
    public String postRaw(String location, HttpEntity entity, boolean useSSL, Header[] headers) throws APIException {
        return _executeRawAPIRequest("POST", location, useSSL, headers, entity);
    }

    /**
     * Performs a DELETE request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * 
     * @return Object.
     */
    public JSONObject delete(String location) throws APIException {
        return delete(location, false, null);
    }

    /**
     * Performs a DELETE request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * @param useSSL Use SSL when available.
     * 
     * @return Object.
     */
    public JSONObject delete(String location, boolean useSSL) throws APIException {
        return delete(location, useSSL, null);
    }

    /**
     * Performs a DELETE request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * @param useSSL Use SSL when available.
     * @param headers Headers.
     * 
     * @return Object.
     */
    public JSONObject delete(String location, boolean useSSL, Header[] headers) throws APIException {
        return _executeJSONObjectAPIRequest("DELETE", location, useSSL, headers, null);
    }

    /**
     * Executes an API request that has been fully configured with an expected JSONObject response.
     * 
     * Handles response processing and error handling in a uniform way.
     */
    private JSONObject _executeJSONObjectAPIRequest(String method, String location, boolean useSSL, Header[] headers, HttpEntity entity) throws APIException {
        return (JSONObject)_executeAPIRequest(ResponseType.JSONObject, method, location, useSSL, headers, entity);
    }

    /**
     * Executes an API request that has been fully configured with an expected JSONArray response.
     * 
     * Handles response processing and error handling in a uniform way.
     */
    private JSONArray _executeJSONArrayAPIRequest(String method, String location, boolean useSSL, Header[] headers, HttpEntity entity) throws APIException {
        return (JSONArray)_executeAPIRequest(ResponseType.JSONArray, method, location, useSSL, headers, entity);
    }

    /**
     * Executes an API request that has been fully configured with an expected response.
     * 
     * Handles response processing and error handling in a uniform way.
     */
    private String _executeRawAPIRequest(String method, String location, boolean useSSL, Header[] headers, HttpEntity entity) throws APIException {
        return (String)_executeAPIRequest(ResponseType.Raw, method, location, useSSL, headers, entity);
    }

    /**
     * Creates the HTTP connection.
     * 
     * @param method
     * @param location
     * @param useSSL
     * @param headers
     * 
     * @return Connection.
     * @throws ProtocolException
     */
    protected HttpURLConnection _createConnection(String method, String location, boolean useSSL, Header[] headers) throws ProtocolException {
        HttpURLConnection conn = _getClient().open(_getURL(location, useSSL));
        conn.setRequestMethod(method);

        if (headers != null) {
            for (Header header : headers) {
                conn.setRequestProperty(header.getName(), header.getValue());
            }
        }

        return conn;
    }

    /**
     * Executes an API request that has been fully configured. An expected response type must be specified.
     * 
     * Handles response processing and error handling in a uniform way.
     */
    protected Object _executeAPIRequest(ResponseType responseType, String method, String location, boolean useSSL, Header[] headers, HttpEntity entity) throws APIException {
        try {
            HttpURLConnection conn = _createConnection(method, location, useSSL, headers);

            if (_isLoggingEnabled()) {
                Log.d(_getLoggingTag(), method + " " + conn.getURL());
            }

            if (entity != null) {
                OutputStream out = conn.getOutputStream();
                entity.writeTo(out);
                out.close();
            }

            String responseBody = _getResponseBody(conn);

            if (_isLoggingEnabled()) {
                Log.v(_getLoggingTag(), "Response body: " + responseBody);
            }

            if (conn.getResponseCode() >= 400) {
                try {
                    JSONObject object = new JSONObject(responseBody);
                    throw new APIException(object.getString("code"), object.getString("message"), conn.getResponseCode());
                } catch (JSONException e) {
                    throw new APIException(conn.getResponseCode(), e);
                }

            } else {
                if (responseBody == null || responseBody.trim().length() == 0) {
                    return null;
                }

                switch (responseType) {
                    case JSONArray:
                        return new JSONArray(responseBody);

                    case JSONObject:
                        return new JSONObject(responseBody);
                    case Raw:
                        return responseBody;
                    default:
                        return null;
                }
            }
        } catch (APIException e) {
            // Re-throw APIExceptions.
            throw e;
        } catch (Exception e) {
            if (_isLoggingEnabled()) {
                Log.e(_getLoggingTag(), "Unexpected error", e);
            }

            throw new APIException(e);
        }
    }
}