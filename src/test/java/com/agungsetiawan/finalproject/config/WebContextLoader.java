package com.agungsetiawan.finalproject.config;

/**
 *
 * @author awanlabs
 */
public class WebContextLoader extends GenericWebContextLoader{
    public WebContextLoader() {
        super("src/test/resources/META-INF/web-resources", false);
    }
}
