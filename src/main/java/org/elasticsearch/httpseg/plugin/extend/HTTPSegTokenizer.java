package org.elasticsearch.httpseg.plugin.extend;

/**
 * @author BD-PC27
 */
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.elasticsearch.env.Environment;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

public class HTTPSegTokenizer extends Tokenizer {
    private Segmenter scanner;
    private BufferedReader bufferReader;
    private List<Token> tokenBuffer;
    private int tokenIndex;
    private int finalOffset;

    private final CharTermAttribute termAtt = addAttribute(CharTermAttribute.class);
    private final OffsetAttribute offsetAtt = addAttribute(OffsetAttribute.class);
    private final PositionIncrementAttribute posIncrAtt = addAttribute(PositionIncrementAttribute.class);

    public HTTPSegTokenizer() {
        this.scanner = new Segmenter();
    }
    public HTTPSegTokenizer(Environment environment) {
        this.scanner = new Segmenter(environment);
    }

    @Override
    public final boolean incrementToken() throws IOException {
        if (bufferReader == null) {
            throw new IllegalStateException("must call reset before call incrementToken()");
        }
        clearAttributes();
        if (tokenBuffer == null || tokenIndex >= tokenBuffer.size()) {
            String line = bufferReader.readLine();
            if (line == null||line.length()==0) {
                return false;
            }
            boolean forSearch = true;
            try {
                tokenBuffer = scanner.tokenize(line);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            tokenIndex = 0;
        }
        Token token = tokenBuffer.get(tokenIndex);
        termAtt.append(token.value);
        offsetAtt.setOffset(correctOffset(token.startPos),
                            correctOffset(token.endPos));
        posIncrAtt.setPositionIncrement(1);
        tokenIndex += 1;
        finalOffset = correctOffset(token.endPos);
        return true;
    }

    @Override
    public void end() throws IOException {
        super.end();
        offsetAtt.setOffset(finalOffset +1,finalOffset+1);
    }

    @Override
    public void reset() throws IOException {
        super.reset();
        if (BufferedReader.class.isAssignableFrom(input.getClass())) {
            bufferReader = (BufferedReader) input;
        }else {
            bufferReader = new BufferedReader(this.input);
        }
        tokenIndex = 0;
        tokenBuffer = null;
    }
}
