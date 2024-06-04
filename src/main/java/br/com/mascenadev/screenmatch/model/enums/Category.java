package br.com.mascenadev.screenmatch.model.enums;

public enum Category {

    ACTION("Action"),
    ADVENTURE("Adventure"),
    ANIMATION("Animation"),
    COMEDY("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    FAMILY("Family"),
    ROMANCE("Romance"),
    THRILLER("Thriller"),
    WAR("War"),;

    private String categoryOmdb;

    Category(String categoryOmdb) {
        this.categoryOmdb = categoryOmdb;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOmdb.equals(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada: " );
    }
}
