package com.example.demo.entity;

import java.util.List;

public class NarMenu {
    private Integer menuId;
    private String title;
    private String icon;
    private String href;
    private String relation;
    private List<NarMenu> children;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<NarMenu> getChildren() {
        return children;
    }

    public void setChildren(List<NarMenu> children) {
        this.children = children;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    @Override
    public String toString() {
        return "NarMenu{" +
                "title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", href='" + href + '\'' +
                ", relation='" + relation + '\'' +
                ", children=" + children +
                '}';
    }
}
