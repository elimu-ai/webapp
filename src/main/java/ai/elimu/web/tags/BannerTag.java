package ai.elimu.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Implementation of the {@code content:banner} tag.
 */
public class BannerTag extends BodyTagSupport {

    @Override
    public int doEndTag() throws JspException {
        pageContext.setAttribute("bannerContent", bodyContent.getString());
        pageContext.setAttribute("hasBanner", true);
        return EVAL_PAGE;
    }
}
