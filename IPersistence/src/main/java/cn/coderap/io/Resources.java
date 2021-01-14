package cn.coderap.io;

import java.io.InputStream;

/**
 * Created by yw
 * 2021/1/11
 */
public class Resources {

    public static InputStream getResourceAsStream(String path) {
        return Resources.class.getClassLoader().getResourceAsStream(path);
    }
}
