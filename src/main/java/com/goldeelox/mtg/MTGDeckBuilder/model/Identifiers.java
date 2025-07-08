package com.goldeelox.mtg.MTGDeckBuilder.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Identifiers {

    private String cardKingdomId;
    private String cardsphereId;
    private String deckboxId;
    private String mcmId;
    private String mcmMetaId;
    private String mtgjsonFoilVersionId;
    private String mtgjsonV4Id;
    private String mtgoFoilId;
    private String mtgoId;
    private String multiverseId;
    private String scryfallCardBackId;
    private String scryfallId;
    private String scryfallIllustrationId;
    private String scryfallOracleId;
    private String tcgplayerProductId;

    // Getters and Setters

    public String getCardKingdomId() { return cardKingdomId; }
    public void setCardKingdomId(String cardKingdomId) { this.cardKingdomId = cardKingdomId; }

    public String getCardsphereId() { return cardsphereId; }
    public void setCardsphereId(String cardsphereId) { this.cardsphereId = cardsphereId; }

    public String getDeckboxId() { return deckboxId; }
    public void setDeckboxId(String deckboxId) { this.deckboxId = deckboxId; }

    public String getMcmId() { return mcmId; }
    public void setMcmId(String mcmId) { this.mcmId = mcmId; }

    public String getMcmMetaId() { return mcmMetaId; }
    public void setMcmMetaId(String mcmMetaId) { this.mcmMetaId = mcmMetaId; }

    public String getMtgjsonFoilVersionId() { return mtgjsonFoilVersionId; }
    public void setMtgjsonFoilVersionId(String mtgjsonFoilVersionId) { this.mtgjsonFoilVersionId = mtgjsonFoilVersionId; }

    public String getMtgjsonV4Id() { return mtgjsonV4Id; }
    public void setMtgjsonV4Id(String mtgjsonV4Id) { this.mtgjsonV4Id = mtgjsonV4Id; }

    public String getMtgoFoilId() { return mtgoFoilId; }
    public void setMtgoFoilId(String mtgoFoilId) { this.mtgoFoilId = mtgoFoilId; }

    public String getMtgoId() { return mtgoId; }
    public void setMtgoId(String mtgoId) { this.mtgoId = mtgoId; }

    public String getMultiverseId() { return multiverseId; }
    public void setMultiverseId(String multiverseId) { this.multiverseId = multiverseId; }

    public String getScryfallCardBackId() { return scryfallCardBackId; }
    public void setScryfallCardBackId(String scryfallCardBackId) { this.scryfallCardBackId = scryfallCardBackId; }

    public String getScryfallId() { return scryfallId; }
    public void setScryfallId(String scryfallId) { this.scryfallId = scryfallId; }

    public String getScryfallIllustrationId() { return scryfallIllustrationId; }
    public void setScryfallIllustrationId(String scryfallIllustrationId) { this.scryfallIllustrationId = scryfallIllustrationId; }

    public String getScryfallOracleId() { return scryfallOracleId; }
    public void setScryfallOracleId(String scryfallOracleId) { this.scryfallOracleId = scryfallOracleId; }

    public String getTcgplayerProductId() { return tcgplayerProductId; }
    public void setTcgplayerProductId(String tcgplayerProductId) { this.tcgplayerProductId = tcgplayerProductId; }

}