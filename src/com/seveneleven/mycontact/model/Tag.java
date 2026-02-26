// Represents a tag that can be applied to contacts for categorization purposes
package com.seveneleven.mycontact.model;

import java.util.Objects;
import java.util.UUID;

public class Tag {

    private final UUID tagId;
    private final String name;

    public Tag(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Tag name cannot be empty.");
        this.tagId = UUID.randomUUID();
        this.name  = name.trim().toLowerCase();
    }

    public UUID getTagId() { return tagId; }
    public String getName() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        return name.equals(((Tag) o).name);
    }

    @Override
    public int hashCode() { return Objects.hash(name); }

    @Override
    public String toString() { return "#" + name; }
}
