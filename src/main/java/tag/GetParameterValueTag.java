package tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class GetParameterValueTag extends SimpleTagSupport {
    private String paramName;

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public void doTag() throws IOException {
        PageContext pageContext = (PageContext) getJspContext();
        String paramValue = pageContext.getRequest().getParameter(paramName);
        if (paramValue != null)
            getJspContext().getOut().print(paramValue);
    }
}
