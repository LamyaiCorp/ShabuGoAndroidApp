package com.knott.navtab.fragement_home;

import java.io.Serializable;

/**
 * Created by KNOTT on 12/5/2560.
 */

public class Promotion  implements Serializable{

    final String url;
    final String content;

    public Promotion(String url, String content) {
        this.url = url;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
