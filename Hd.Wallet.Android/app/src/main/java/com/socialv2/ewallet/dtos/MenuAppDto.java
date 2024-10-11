package com.socialv2.ewallet.dtos;

import java.util.Objects;

import javax.annotation.Nullable;

public class MenuAppDto {
    private String title;
    private String description;
    private int iconId;

    private Class<?> destActivity;

    private int leftTitleIcon;

    private int colorId;

    public MenuAppDto() {

    }

    public MenuAppDto(
            String title,
            @Nullable String description,
            @Nullable int iconId,
            @Nullable int leftTitleIcon,
            @Nullable Class<?> destActivity) {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.leftTitleIcon = leftTitleIcon;
        this.destActivity = destActivity;
    }

    public MenuAppDto(
            String title,
            @Nullable String description,
            @Nullable int iconId,
            @Nullable int leftTitleIcon,
            @Nullable Class<?> destActivity,
            @Nullable int colorId) {
        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.leftTitleIcon = leftTitleIcon;
        this.destActivity = destActivity;
        this.colorId = colorId;
    }

    public int getLeftTitleIcon() {
        return leftTitleIcon;
    }

    public void setLeftTitleIcon(int leftTitleIcon) {
        this.leftTitleIcon = leftTitleIcon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Class<?> getDestActivity() {
        return destActivity;
    }

    public void setDestActivity(Class<?> destActivity) {
        this.destActivity = destActivity;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuAppDto)) return false;
        MenuAppDto that = (MenuAppDto) o;
        return getIconId() == that.getIconId() && Objects.equals(getTitle(), that.getTitle()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getDestActivity(), that.getDestActivity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getDescription(), getIconId(), getDestActivity());
    }

    @Override
    public String toString() {
        return "MenuAppDto{" + "title='" + title + '\'' + ", description='" + description + '\'' + ", iconId=" + iconId + ", destActivity=" + destActivity + '}';
    }
}