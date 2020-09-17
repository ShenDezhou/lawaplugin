package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author BD-PC27
 */
public class Token {
    public String value;
    public String pos;
    public int startPos;
    public int endPos;

    public Token(String value,String pos,int startPos,int endPos) {
        this.value = value;
        this.pos = pos;
        this.startPos = startPos;
        this.endPos = endPos;
    }

    @Override
    public String toString() {
        return "token: value = "+value+";startPos = "+startPos + ";endPos = " + endPos;
    }
}
