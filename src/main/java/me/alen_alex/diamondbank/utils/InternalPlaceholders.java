package me.alen_alex.diamondbank.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public class InternalPlaceholders {

    private static final boolean placeholderAPIEnabled;

    static {
        placeholderAPIEnabled = Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    private final String placeholder;
    private final String toReplace;

    public InternalPlaceholders(String placeholder, String toReplace) {
        this.placeholder = placeholder;
        this.toReplace = toReplace;
    }

    public InternalPlaceholders(String placeholder, UUID toReplace){
        this.placeholder = placeholder;
        this.toReplace = toReplace.toString();
    }

    public InternalPlaceholders(String placeholder, Integer toReplace) {
        this.placeholder = placeholder;
        this.toReplace = String.valueOf(toReplace);
    }

    public InternalPlaceholders(String placeholder, Float toReplace) {
        this.placeholder = placeholder;
        this.toReplace = String.valueOf(toReplace);
    }

    public InternalPlaceholders(String placeholder, Long toReplace) {
        this.placeholder = placeholder;
        this.toReplace = String.valueOf(toReplace);
    }

    public InternalPlaceholders(String placeholder, Boolean toReplace) {
        this.placeholder = placeholder;
        this.toReplace = String.valueOf(toReplace);
    }

    public InternalPlaceholders(String placeholder, Double toReplace) {
        this.placeholder = placeholder;
        this.toReplace = String.valueOf(toReplace);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getToReplace() {
        return toReplace;
    }

    public String replacePlaceholders(String message){
        if(StringUtils.isBlank(message))
            return message;

        return message.replace(this.placeholder,this.toReplace);
    }

    public List<String> replacePlaceholders(@NonNull List<String> strings){
        if(strings.isEmpty())
            return strings;

        return strings.stream().map(this::replacePlaceholders).toList();
    }

    public static String replacePlaceholders(@NonNull List<InternalPlaceholders> placeholders,String message){
        var ref = new Object() {
            String retMessage = message;
        };
        placeholders.forEach((placeholder -> {
            ref.retMessage = placeholder.replacePlaceholders(message);
        }));
        return ref.retMessage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("placeholder", placeholder)
                .append("toReplace", toReplace)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        InternalPlaceholders that = (InternalPlaceholders) o;

        return new EqualsBuilder().append(placeholder, that.placeholder).append(toReplace, that.toReplace).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(placeholder).append(toReplace).toHashCode();
    }
}
