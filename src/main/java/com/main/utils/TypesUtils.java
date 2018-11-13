package com.main.utils;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author User
 */
public class TypesUtils {
    
    public static int getIntFromString(String str, int defValue){
        int result;
        try {
            result = Integer.parseInt(str);
        }
        catch (NumberFormatException ex){
            result = defValue;    
        }    
        return result;
    }
    
    
    /**
     * <p>A faster replacement for {@link HttpServletRequest#getRequestURL()}
     *  (returns a {@link String} instead of a {@link StringBuffer} - and internally uses a {@link StringBuilder})
     *  that also includes the {@linkplain HttpServletRequest#getQueryString() query string}.</p>
     * @param req
     * @return 
     */
    public static String getRequestUrl(final HttpServletRequest req){
        final String scheme = req.getScheme();
        final int port = req.getServerPort();
        final StringBuilder url = new StringBuilder(256);
        url.append(scheme);
        url.append("://");
        url.append(req.getServerName());
        if(!(("http".equals(scheme) && (port == 0 || port == 80))
                || ("https".equals(scheme) && port == 443))){
            url.append(':');
            url.append(port);
        }
        url.append(req.getRequestURI());
        final String qs = req.getQueryString();
        if(qs != null){
            url.append('?');
            url.append(qs);
        }
        final String result = url.toString();
        return result;
    }
    
    public static String getRequestContextPath(final HttpServletRequest req){
        final String scheme = req.getScheme();
        final int port = req.getServerPort();
        final StringBuilder url = new StringBuilder(256);
        url.append(scheme);
        url.append("://");
        url.append(req.getServerName());
        if(!(("http".equals(scheme) && (port == 0 || port == 80))
                || ("https".equals(scheme) && port == 443))){
            url.append(':');
            url.append(port);
        }
        
        url.append(req.getContextPath());
        final String result = url.toString();
        return result;
    }    
    
    
    
}

