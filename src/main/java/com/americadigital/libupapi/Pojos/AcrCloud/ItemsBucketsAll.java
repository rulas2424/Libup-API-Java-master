package com.americadigital.libupapi.Pojos.AcrCloud;

import java.util.List;

public class ItemsBucketsAll {
    public List<Object> items;
    public Object _links;
    public Object _meta;

    public Object get_meta() {
        return _meta;
    }

    public void set_meta(Object _meta) {
        this._meta = _meta;
    }

    public Object get_links() {
        return _links;
    }

    public void set_links(Object _links) {
        this._links = _links;
    }

    public List<Object> getItems() {
        return items;
    }

    public void setItems(List<Object> items) {
        this.items = items;
    }
}
