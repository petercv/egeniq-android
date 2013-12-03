package com.egeniq.utils.net;

import java.io.OutputStream;
import java.net.HttpURLConnection;

import android.util.Log;
import ch.boye.httpclientandroidlib.Header;
import ch.boye.httpclientandroidlib.HttpEntity;

/**
 * Simple HTTP client.
 */
public class HTTPClient extends AbstractHTTPClient {
    /**
     * Constructor.
     */
    public HTTPClient(String baseURL) {
        super(baseURL);
    }

    /**
     * Constructor.
     */
    public HTTPClient(String baseURL, String secureBaseURL) {
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
    public String get(String location) throws HTTPException {
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
    public String get(String location, boolean useSSL) throws HTTPException {
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
    public String get(String location, boolean useSSL, Header[] headers) throws HTTPException {
        return _executeAPIRequest("GET", location, useSSL, headers, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a JSON object..
     * 
     * @param location Location.
     * @param entity Post entity.
     */
    public String post(String location, HttpEntity entity) throws HTTPException {
        return post(location, entity, false, null);
    }

    /**
     * Performs a POST request to the given location (which is appended to the base URL) and returns the result as a JSON object..
     * 
     * @param location Location.
     * @param entity Post entity.
     * @param useSSL Use SSL when available.
     */
    public String post(String location, HttpEntity entity, boolean useSSL) throws HTTPException {
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
    public String post(String location, HttpEntity entity, boolean useSSL, Header[] headers) throws HTTPException {
        return _executeAPIRequest("POST", location, useSSL, headers, entity);
    }

    /**
     * Performs a DELETE request to the given location (which is appended to the base URL) and returns the result as a JSON object.
     * 
     * @param location Location.
     * 
     * @return Object.
     */
    public String delete(String location) throws HTTPException {
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
    public String delete(String location, boolean useSSL) throws HTTPException {
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
    public String delete(String location, boolean useSSL, Header[] headers) throws HTTPException {
        return _executeAPIRequest("DELETE", location, useSSL, headers, null);
    }

    /**
     * Executes an API request that has been fully configured. An expected response type must be specified.
     * 
     * Handles response processing and error handling in a uniform way.
     */
    protected String _executeAPIRequest(String method, String location, boolean useSSL, Header[] headers, HttpEntity entity) throws HTTPException {
        try {
            HttpURLConnection conn = _getClient().open(_getURL(location, useSSL));
            conn.setRequestMethod(method);

            if (headers != null) {
                for (Header header : headers) {
                    conn.setRequestProperty(header.getName(), header.getValue());
                }
            }

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
                throw new HTTPException(conn.getResponseCode(), responseBody);
            } else {
                if (responseBody == null || responseBody.trim().length() == 0) {
                    return null;
                } else {
                    return responseBody;
                }
            }
        } catch (HTTPException e) {
            // Re-throw HTTPExceptions.
            throw e;
        } catch (Exception e) {
            if (_isLoggingEnabled()) {
                Log.e(_getLoggingTag(), "Unexpected error", e);
            }

            throw new HTTPException(e);
        }
    }
}
