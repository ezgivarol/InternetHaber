package com.nomad.internethaber.event;

import com.nomad.internethaber.bean.CategoryResponseBean;
import com.nomad.internethaber.model.Categories;

public final class CategoriesSuccessResponseEvent {
    private CategoryResponseBean mBean;

    public void setBean(CategoryResponseBean bean) {
        mBean = bean;
    }

    public CategoryResponseBean getBean() {
        return mBean;
    }
}
