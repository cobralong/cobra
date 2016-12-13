
package com.appchina.ios.mgnt.web.resolver;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

/**
 * @author mei
 *
 */
public class OutputStackSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver{
	/** The default name of the exception stack trace string attribute: "exceptionStack". */
	public static final String DEFAULT_EXCEPTION_STACK_ATTRIBUTE = "exceptionStack";

	private String exceptionStackAttribute = DEFAULT_EXCEPTION_STACK_ATTRIBUTE;

	public void setExceptionStackAttribute(String exceptionStackAttribute) {
		this.exceptionStackAttribute = exceptionStackAttribute;
	}
	@Override
	protected ModelAndView getModelAndView(String viewName, Exception ex) {
		ModelAndView mv = super.getModelAndView(viewName, ex);
		StringBuffer sb = new StringBuffer();
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		sb.append(stringWriter.getBuffer());
		printWriter.close();
		/* XXX 只能获得最后一次封装的stackTrace，需要递归获得最早的stackTrace
		StackTraceElement[] stackTraceElements = ex.getStackTrace();			
		sb.append(ex.toString()+"<br>");
		for(StackTraceElement ste : stackTraceElements){
			sb.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ste.toString()+"<br>");
		}*/
		mv.addObject(this.exceptionStackAttribute, sb.toString());
		return mv;
	}
}
