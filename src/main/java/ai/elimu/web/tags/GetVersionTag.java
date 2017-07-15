package ai.elimu.web.tags;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import ai.elimu.util.ConfigHelper;

/**
 * Implementation of the {@code content:getversion} tag.
 */
public class GetVersionTag extends TagSupport {

    @Override
    public int doStartTag() throws JspException {
        Writer out = pageContext.getOut();
        try {
            String content = ConfigHelper.getProperty("application.version");
            out.write(content);
        } catch (IOException ex) {
            throw new JspTagException(ex);
        }
        return SKIP_BODY;
    }
}
