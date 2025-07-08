package com.goldeelox.mtg.MTGDeckBuilder.model;

import java.util.List;

public class Card {

    private String name;
    private List<String> colors;
    private List<String> colorIdentity;
    private String power;
    private String toughness;
    private String setCode;
    private List<String> types;
    private List<String> subtypes;
    private List<String> supertypes;
    private String text;
    private String uuid;
    private Identifiers identifiers;

    // Getters and Setters

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getColors() { return colors; }
    public void setColors(List<String> colors) { this.colors = colors; }

    public List<String> getColorIdentity() { return colorIdentity; }
    public void setColorIdentity(List<String> colorIdentity) { this.colorIdentity = colorIdentity; }

    public String getPower() { return power; }
    public void setPower(String power) { this.power = power; }

    public String getToughness() { return toughness; }
    public void setToughness(String toughness) { this.toughness = toughness; }

    public String getSetCode() { return setCode; }
    public void setSetCode(String setCode) { this.setCode = setCode; }

    public List<String> getTypes() { return types; }
    public void setTypes(List<String> types) { this.types = types; }

    public List<String> getSubtypes() { return subtypes; }
    public void setSubtypes(List<String> subtypes) { this.subtypes = subtypes; }

    public List<String> getSupertypes() { return supertypes; }
    public void setSupertypes(List<String> supertypes) { this.supertypes = supertypes; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String getUuid() { return uuid; }
    public void setUuid(String uuid) { this.uuid = uuid; }

    public Identifiers getIdentifiers() { return identifiers; }
    public void setIdentifiers(Identifiers identifiers) { this.identifiers = identifiers; }

}