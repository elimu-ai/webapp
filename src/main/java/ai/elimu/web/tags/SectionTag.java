package ai.elimu.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Implementation of the {@code content:section} tag.
 */
public class SectionTag extends BodyTagSupport {

    private String cssId;

    private String cssClass;

    @Override
    public int doEndTag() throws JspException {
        pageContext.setAttribute("cssId", cssId);
        pageContext.setAttribute("cssClass", cssClass);
        pageContext.setAttribute("sectionContent", bodyContent.getString());
        return EVAL_PAGE;
    }

    public void setCssId(String cssId) {
        this.cssId = cssId;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
