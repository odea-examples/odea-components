package com.odea.components.jquery.datatable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;

import java.util.Collection;

/**
 * User: pbergonzi
 * Date: 19/06/12
 * Time: 11:39
 */
public abstract class JQueryBasicDataTable<T> extends WebMarkupContainer {
    public JQueryBasicDataTable(String id) {
        super(id);
        this.setOutputMarkupId(true);
        this.add(new AttributeModifier("class", new Model<String>("display")));
        String jQuerySelector = "#" + this.getMarkupId();
        this.prepare(jQuerySelector);
    }

    private void prepare(String jQuerySelector) {
        add(new AbstractJQueryBasicDataTableBehavior<T>(jQuerySelector) {
            @Override
            Collection<T> getSearchResults(String searchToken) {
                return JQueryBasicDataTable.this.getSearchResults(searchToken);
            }

            @Override
            String[] getColumns() {
                return JQueryBasicDataTable.this.getColumns();
            }
        });
    }

    public abstract Collection<T> getSearchResults(String searchToken);

    public abstract String[] getColumns();
}
