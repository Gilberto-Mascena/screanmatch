package br.com.mascenadev.screanmatch.model.enums;

public enum Category {
    ACAO("Action", "Ação"),
    ROMANCE("Romance", "Romance"),
    DRAMA("Drama", "Drama"),
    COMEDIA("Comedy", "Comédia"),
    CRIME("Crime", "Crime");

    private String categoryOmdb;
    private String categoryPortuguese;

    Category(String categoryOmdb, String categoryPortuguese) {
        this.categoryOmdb = categoryOmdb;
        this.categoryPortuguese = categoryPortuguese;
    }

    public static Category fromString(String text) {
        for (Category category : Category.values()) {
            if (category.categoryOmdb.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada: ");
    }

    public static Category fromStringPortuguese(String text) {
        for (Category category : Category.values()) {
            if (category.categoryPortuguese.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada: ");
    }
}