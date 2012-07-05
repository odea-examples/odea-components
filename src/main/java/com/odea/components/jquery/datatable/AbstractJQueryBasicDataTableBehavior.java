package com.odea.components.jquery.datatable;

import com.google.gson.Gson;
import org.apache.wicket.Application;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.TextRequestHandler;
import org.apache.wicket.util.template.PackageTextTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: pbergonzi
 * Date: 19/06/12
 * Time: 10:35
 */
public abstract class AbstractJQueryBasicDataTableBehavior<T> extends AbstractAjaxBehavior {
    private static final Logger logger = LoggerFactory.getLogger(AbstractJQueryBasicDataTableBehavior.class);
    private static final String JSON_CONTENT_TYPE = "application/json";
    private static final String ENCODING = Application.get().getMarkupSettings().getDefaultMarkupEncoding();

    private final String jQuerySelector;

    public AbstractJQueryBasicDataTableBehavior(String jQuerySelector) {
        logger.debug("Adding AbstractJQueryBasicDataTableBehavior to " + jQuerySelector);
        this.jQuerySelector = jQuerySelector;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        logger.debug("Rendering HEAD");
        Map<String, CharSequence> map = new HashMap<String, CharSequence>(3);
        map.put("selector", this.jQuerySelector);
        map.put("callbackUrl", this.getCallbackUrl());
        map.put("columns", this.formatColumnNames());
        PackageTextTemplate packageTextTemplate = new PackageTextTemplate(getClass(), "datatabletemplate.js", "text/javascript");
        String resource = packageTextTemplate.asString(map);
        String uniqueName = Long.toString(Calendar.getInstance().getTimeInMillis());
        response.renderJavaScript(resource, "datatable_" + uniqueName);
    }

    public void onRequest() {
        logger.debug("Ajax call to AbstractJQueryBasicDataTableBehavior from " + this.jQuerySelector);
        RequestCycle requestCycle = RequestCycle.get();
        Request request = requestCycle.getRequest();
        IRequestParameters params = request.getRequestParameters();
        String searchToken = params.getParameterValue("searchToken").toString();
        logger.debug("Received searchtoken : " + searchToken);
        String jsonResultList = this.formatColumnData(this.getSearchResults(searchToken));
        logger.debug("Search result : " + jsonResultList);
        requestCycle.scheduleRequestHandlerAfterCurrent(new TextRequestHandler(JSON_CONTENT_TYPE, ENCODING, jsonResultList));
    }

    abstract Collection<T> getSearchResults(String searchToken);

    abstract String[] getColumns();

    private String formatColumnData(Collection<T> results) {
        return "{ \"aaData\": " + this.toJson(results) + "}";
    }

    private String formatColumnNames() {
        String[] propertyNames = this.getColumns();
        StringBuilder orderedColumns = new StringBuilder();

        orderedColumns.append("[");
        for (int i = 0; i < propertyNames.length; i++) {
            orderedColumns.append("{ \"mDataProp\": \"" + propertyNames[i] + "\"}");
            if (i != (propertyNames.length - 1)) {
                orderedColumns.append(",");
            }
        }
        orderedColumns.append("]");

        return orderedColumns.toString();
    }

    private String toJson(Collection<T> results) {
        Gson gson = new Gson();
        return gson.toJson(results);
    }
}
