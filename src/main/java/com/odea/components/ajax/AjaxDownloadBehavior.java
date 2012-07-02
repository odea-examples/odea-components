package com.odea.components.ajax;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.ContentDisposition;
import org.apache.wicket.util.resource.IResourceStream;


public abstract class AjaxDownloadBehavior extends AbstractAjaxBehavior {
    private boolean addAntiCache;

    public AjaxDownloadBehavior() {
        this(true);
    }

    public AjaxDownloadBehavior(boolean addAntiCache) {
        super();
        this.addAntiCache = addAntiCache;
    }

    /**
     * Call this method to initiate the download.
     *
     * @param target
     */
    public void initiate(AjaxRequestTarget target) {
        String url = getCallbackUrl().toString();

        if (addAntiCache) {
            url = url + (url.contains("?") ? "&" : "?");
            url = url + "antiCache=" + System.currentTimeMillis();
        }

        // the timeout is needed to let Wicket release the channel
        target.appendJavaScript("setTimeout(\"window.location.href='" + url + "'\", 100);");
    }

    public void onRequest() {
        ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(getResourceStream(), getFileName());
        handler.setContentDisposition(ContentDisposition.ATTACHMENT);
        getComponent().getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
    }

    protected abstract String getFileName();

    protected abstract IResourceStream getResourceStream();
}