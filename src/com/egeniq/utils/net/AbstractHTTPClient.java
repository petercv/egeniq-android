package com.egeniq.utils.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import android.annotation.SuppressLint;

import com.squareup.okhttp.OkHttpClient;

/**
 * HTTP client.
 */
public abstract class AbstractHTTPClient {
    private final static int DEFAULT_TIMEOUT = 15000;

    private int _timeout = DEFAULT_TIMEOUT;

    private static OkHttpClient _httpClient = null;

    private String _loggingTag = getClass().getName();
    private boolean _loggingEnabled = false;

    private String _baseURL;
    private String _secureBaseURL;

    /**
     * Constructor.
     */
    public AbstractHTTPClient(String baseURL) {
        this(baseURL, baseURL);
    }

    /**
     * Constructor.
     */
    public AbstractHTTPClient(String baseURL, String secureBaseURL) {
        _baseURL = baseURL;
        _secureBaseURL = secureBaseURL;
    }

    /**
     * Returns the (secure?) base URL.
     * 
     * @param useSSL When true returns the secure base URL, else the default base URL.
     * 
     * @return base URL
     */
    protected String _getBaseURL(boolean useSSL) {
        return useSSL ? _secureBaseURL : _baseURL;
    }

    /**
     * Sets the base URLs.
     * 
     * @param baseURL
     * @param secureBaseURL
     */
    protected void _setBaseURLs(String baseURL, String secureBaseURL) {
        _baseURL = baseURL;
        _secureBaseURL = secureBaseURL;
    }

    /**
     * Creates the full URL for the given location.
     * 
     * Prepends the correct base URL (based on the useSSL) option unless the given location itself is already a full HTTP URL.
     * 
     * @param location Location.
     * @param useSSL Use SSL?
     * 
     * @return Full URL.
     */
    @SuppressLint("DefaultLocale")
    protected URL _getURL(String location, boolean useSSL) {
        try {
            if (location == null) {
                return new URL(_getBaseURL(useSSL));
            } else if (location.toLowerCase().startsWith("http:") || location.toLowerCase().startsWith("https:")) {
                return new URL(location);
            } else {
                return new URL(_getBaseURL(useSSL) + "/" + location);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Creates or returns the HTTP client object.
     */
    protected synchronized OkHttpClient _getClient() {
        if (_httpClient == null) {
            OkHttpClient client = new OkHttpClient();
            client.setConnectTimeout(_timeout, TimeUnit.MILLISECONDS);
            client.setReadTimeout(_timeout, TimeUnit.MILLISECONDS);
            _httpClient = client;
        }

        return _httpClient;
    }

    /**
     * Is logging enabled?
     */
    protected boolean _isLoggingEnabled() {
        return _loggingEnabled;
    }

    /**
     * Enable / disable logging.
     */
    protected void _setLoggingEnabled(boolean loggingEnabled) {
        _loggingEnabled = loggingEnabled;
    }

    /**
     * Returns the logging tag.
     */
    protected String _getLoggingTag() {
        return _loggingTag;
    }

    /**
     * Sets the logging tag.
     */
    protected void _setLoggingTag(String tag) {
        _loggingTag = tag;
    }

    /**
     * Gets the body of the HttpResponse as a String.
     * 
     * Returns null if response is null or if the body is empty.
     * 
     * @throws IOException
     * @throws IllegalStateException
     */
    protected String _getResponseBody(HttpURLConnection conn) throws IllegalStateException, IOException {
        InputStream content = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(content, Charset.forName("UTF-8")), 8192);
        StringBuilder builder = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        reader.close();
        content.close();

        return builder.toString();
    }
}
