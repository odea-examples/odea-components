package com.odea.components.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.util.resource.IResourceStream;
import org.apache.wicket.util.resource.StringResourceStream;

/**
 * User: pbergonzi
 * Date: 02/07/12
 * Time: 12:17
 */
public abstract class AjaxCSVDownloadLink extends AjaxLink {
    private static final String CONTENT_TYPE = "text/csv";

    private AjaxDownloadBehavior ajaxDownloadBehavior = new AjaxDownloadBehavior() {
        @Override
        protected String getFileName() {
            return AjaxCSVDownloadLink.this.getFileName();
        }

        @Override
        protected IResourceStream getResourceStream() {
            return new StringResourceStream(AjaxCSVDownloadLink.this.getCsvData(), CONTENT_TYPE);
        }
    };

    public AjaxCSVDownloadLink(String id) {
        super(id);
        this.add(this.ajaxDownloadBehavior);
    }

    @Override
    public void onClick(AjaxRequestTarget target) {
        this.ajaxDownloadBehavior.initiate(target);
    }

    protected abstract String getFileName();

    protected abstract String getCsvData();

}
